package com.gfk.business.common.file.service.impl;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.gfk.business.common.file.mapper.FileInfoMapper;
import com.gfk.business.common.file.model.FileInfo;
import com.gfk.business.common.file.service.FileInfoService;
import com.gfk.common.constant.BusinessConstants;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.model.ServerGroupConfig;
import com.gfk.framework.properties.ApplicationInfoProperties;
import com.gfk.framework.properties.FileProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 文件服务实现
 *
 * @author wzl
 * @version 1.1 2019/11/19
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {
    private static final String UPLOAD_DIR = "upload";

    private static final String PROJECT_IMG = "project_img";

    private final FileInfoMapper fileInfoMapper;

    /**
     * 校验上传的文件
     *
     * @param file 上传的文件
     */
    public static void checkUploadFile(MultipartFile file) {
        if (file == null) {
            throw new BusinessException("文件内容不能为空");
        }
        int maxUploadFileSize = FileProperties.getMaxUploadFileSize() * 1024 * 1024;
        // 文件大小校验
        if (file.getSize() > maxUploadFileSize) {
            throw new BusinessException("文件大小不能超过" + (maxUploadFileSize / 1024 / 1204) + "MB");
        }
    }

    /**
     * 校验上传的文件
     *
     * @param file 上传的文件
     */
    public static void checkUploadImg(MultipartFile file) {
        if (file == null) {
            throw new BusinessException("文件内容不能为空");
        }
        int maxUploadFileSize = FileProperties.getMaxUploadFileSize() * 1024 * 1024;
        // 文件大小校验
        if (file.getSize() > maxUploadFileSize) {
            throw new BusinessException("文件大小不能超过" + (maxUploadFileSize / 1024 / 1204) + "MB");
        }
        String originalFilename = file.getOriginalFilename();
        if (null == originalFilename) {
            throw new BusinessException("文件名为空");
        }
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (!"jpg".equalsIgnoreCase(suffix)
                && !"jpeg".equalsIgnoreCase(suffix)
                && !"bmp".equalsIgnoreCase(suffix)
                && !"gif".equalsIgnoreCase(suffix)
                && !"png".equalsIgnoreCase(suffix)) {
            throw new BusinessException("请上传正确的图片文件（jpg/bmp/gif/png）");
        }
    }

    /**
     * 初始化（目标）文件
     *
     * @param fileName 文件名称（包含路径）
     * @return
     */
    private static File initFile(String fileName) {
        File destFile = new File(fileName);
        FileUtil.mkParentDirs(destFile);
        // 获取服务器上真实文件地址（避免获取到tomcat根目录下的路径）
        File realFile = new File(destFile.getAbsolutePath());
        return realFile;
    }

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw new BusinessException("文件写入输出流失败。");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    log.error("文件流关闭失败", e1);
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    log.error("文件流关闭失败", e1);
                }
            }
        }
    }

    //type: 1-资源文件 2-静态文件
    public static void syncFile(String fileName, String targetDateDir, Integer type) {
        ServerGroupConfig localConfig = null;
        //获取本地配置
        for (ServerGroupConfig config : BusinessConstants.SERVER_GROUP) {
            if (ApplicationInfoProperties.getServerCurrentHost().equals(config.getServerCurrentHost())) {
                localConfig = config;
            }
        }
        //没配置本地信息则跳出
        if (localConfig == null) {
            return;
        }
        for (ServerGroupConfig config : BusinessConstants.SERVER_GROUP) {
            if (!ApplicationInfoProperties.getServerCurrentHost().equals(config.getServerCurrentHost())) {
                Connection con = new Connection(config.getServerCurrentHost(), 22);
                Session session = null;
                //连接
                try {
                    con.connect();
                    //远程服务器的用户名密码
                    boolean isAuth = con.authenticateWithPassword(config.getAccount(), config.getPassword());
                    //建立SCP客户端
                    if (isAuth) {

                        //将文件同步到指定的path目录下面
                        String fileUploadPath = "";
                        if (type == 1) {
                            fileUploadPath = FileProperties.getResourcePath() + File.separator + targetDateDir;
                        } else if (type == 2) {
                            fileUploadPath = FileProperties.getStaticPath() + File.separator + targetDateDir;
                        }
                        //执行远程命令 创建目录
                        session = con.openSession();
                        session.execCommand("mkdir -p " + fileUploadPath);//分号隔开执行多条指令

                        //同步文件
                        SCPClient scpClient = con.createSCPClient();
                        scpClient.put(fileName, fileUploadPath);
                    } else {
                        log.error(localConfig.getServerCurrentHost() + "->" + config.getServerCurrentHost() + ",连接验证失败");
                    }
                    con.close();
                } catch (IOException e) {
                    log.error(localConfig.getServerCurrentHost() + "->" + config.getServerCurrentHost() + ",文件同步失败:{}", e.getMessage(), e);
                    if (con != null) {
                        con.close();
                    }
                    if (session != null) {
                        session.close();
                    }
                } finally {
                    if (con != null) {
                        con.close();
                    }
                    if (session != null) {
                        session.close();
                    }
                }
            }
        }
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public FileInfo uploadFile(MultipartFile file) {
        checkUploadFile(file);

        // 上传的文件名称
        String uploadFileName = file.getOriginalFilename();
        // 文件路径按日期归类
        String dateDir = UPLOAD_DIR + File.separator +
                DateUtil.thisYear() + File.separator + (DateUtil.thisMonth() + 1) + File.separator + DateUtil.thisDayOfMonth() + File.separator;
        // 保存的文件名称（重命名文件，避免重名覆盖）
        String fileKey = dateDir + UUID.randomUUID().toString() + "_" + uploadFileName;

        // 上传的文件路径
        String fileName = FileProperties.getResourcePath() + File.separator + fileKey;

        File destFile = initFile(fileName);
        try {
            // 将文件从缓存中复制到上传目录
            file.transferTo(destFile);
        } catch (IOException e) {
            log.error("存储文件失败！", e);
            throw new BusinessException("存储文件失败");
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileSize(FileUtil.size(destFile));
        fileInfo.setFileMd5(DigestUtil.md5Hex(destFile));
        fileInfo.setFilename(uploadFileName);
        fileInfo.setFilePath(fileKey);
        fileInfoMapper.insert(fileInfo);
        //同步文件到服务器集群
        syncFile(fileName, dateDir, 1);
        return fileInfo;
    }

    /**
     * 上传公开文件（无需权限）
     *
     * @param file 上传的文件
     * @return 文件信息
     */
    @Override
    public FileInfo uploadPublicFile(MultipartFile file, Long projectId) {
        checkUploadFile(file);

        // 上传的文件名称
        String uploadFileName = file.getOriginalFilename();
        // 文件路径按日期归类
        String dateDir = UPLOAD_DIR + File.separator +
                DateUtil.thisYear() + File.separator + (DateUtil.thisMonth() + 1) + File.separator + DateUtil.thisDayOfMonth() + File.separator;
        // 保存的文件名称（重命名文件，避免重名覆盖）
        String fileKey = dateDir + UUID.randomUUID().toString() + "_" + uploadFileName;

        // 上传的文件路径
        String fileName = FileProperties.getStaticPath() + File.separator + fileKey;

        File destFile = initFile(fileName);
        try {
            // 将文件从缓存中复制到上传目录
            file.transferTo(destFile);
        } catch (IOException e) {
            log.error("存储文件失败！", e);
            throw new BusinessException("存储文件失败");
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileSize(FileUtil.size(destFile));
        fileInfo.setFileMd5(DigestUtil.md5Hex(destFile));
        fileInfo.setFilename(uploadFileName);
        fileInfo.setFilePath(fileKey);
        fileInfo.setProjectId(projectId);
        fileInfoMapper.insert(fileInfo);
        //同步文件到服务器集群
        syncFile(fileName, dateDir, 1);
        return fileInfo;
    }

    @Override
    public String uploadProjectImgFile(MultipartFile file, Long projectId) {
        checkUploadImg(file);

        // 上传的文件名称
        String uploadFileName = file.getOriginalFilename();
        // 文件路径按日期归类
        String projectDir = PROJECT_IMG + "/" +
                projectId + "/";
        // 保存的文件名称（重命名文件，避免重名覆盖）
        String fileKey = projectDir + uploadFileName;

        // 上传的文件路径
        String fileName = FileProperties.getStaticPath() + "/" + fileKey;

        File destFile = initFile(fileName);
        try {
            // 将文件从缓存中复制到上传目录
            file.transferTo(destFile);
        } catch (IOException e) {
            log.error("存储文件失败！", e);
            throw new BusinessException("存储文件失败");
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileSize(FileUtil.size(destFile));
        fileInfo.setFileMd5(DigestUtil.md5Hex(destFile));
        fileInfo.setFilename(uploadFileName);
        fileInfo.setFilePath(fileKey);
        fileInfo.setProjectId(projectId);
        fileInfoMapper.insert(fileInfo);
        return fileKey;
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     * @param response 响应
     * @param request  请求
     */
    @Override
    public void downloadFile(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {

        // 下载的文件路径
        String filePath = FileProperties.getResourcePath() + File.separator + fileName;
        File downloadFile = new File(filePath);
        String realName = downloadFile.getName();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/dto-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + setFileDownloadHeader(request, realName));
        response.setHeader("Access-Control-Expose-Headers",
                "Content-Disposition");
        try {
            writeBytes(filePath, response.getOutputStream());
        } catch (IOException e) {
            throw new BusinessException("文件写入输出流失败");
        }

        if (Boolean.TRUE.equals(delete)) {
            FileUtil.del(filePath);
        }
    }

    /**
     * 获取公开文件的下载信息
     *
     * @param fileName 文件名称
     * @return 文件下载信息
     */
    @Override
    public FileInfo publicFileDownloadInfo(String fileName) {
        FileInfo fileInfo = new FileInfo();
        // 下载的文件路径
        String filePath = FileProperties.getStaticPath() + File.separator + fileName;
        File realFile = new File(filePath);
        String realName = realFile.getName();
        fileName = fileName.replace(File.separator, "/");

        fileInfo.setFileSize(FileUtil.size(realFile));
        fileInfo.setFilename(realName);
        fileInfo.setFilePath(ApplicationInfoProperties.getAppUrl() + FileProperties.getStaticUrl() + fileName);
        return fileInfo;
    }

    /**
     * 获取文件大小
     *
     * @param filePath resource文件路径
     * @return 文件大小
     */
    @Override
    public Long getResourceFileSize(String filePath) {
        String localPath = FileProperties.getResourcePath() + "/" + filePath;
        File localFile = new File(localPath);
        return FileUtil.size(localFile);
    }

    /**
     * 设置文件下载响应头
     *
     * @param request
     * @param fileName
     * @return
     */
    public String setFileDownloadHeader(HttpServletRequest request, String fileName) {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        try {
            if (agent.contains("MSIE")) {
                // IE浏览器
                filename = URLEncoder.encode(filename, "utf-8");
                filename = filename.replace("+", " ");
            } else if (agent.contains("Firefox")) {
                // 火狐浏览器
                filename = new String(fileName.getBytes(), "ISO8859-1");
            } else if (agent.contains("Chrome")) {
                // google浏览器
                filename = URLEncoder.encode(filename, "utf-8");
            } else {
                // 其它浏览器
                filename = URLEncoder.encode(filename, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码失败。", e);
        }
        return filename;
    }
}
