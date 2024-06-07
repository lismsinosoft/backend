package com.gfk.business.common.file.service;

import com.gfk.business.common.file.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件服务
 *
 * @author jjh
 * @date 2019/11/19
 */
public interface FileInfoService {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    FileInfo uploadFile(MultipartFile file);

    /**
     * 上传公开文件（无需权限）
     *
     * @param file      上传的文件
     * @param projectId 项目ID
     * @return 文件信息
     */
    FileInfo uploadPublicFile(MultipartFile file, Long projectId);

    /**
     * 上传公开文件（无需权限）
     *
     * @param file      上传的文件
     * @param projectId 项目ID
     * @return 文件信息
     */
    String uploadProjectImgFile(MultipartFile file, Long projectId);

    /**
     * 下载文件
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     * @param response 响应
     * @param request  请求
     */
    void downloadFile(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request);


    /**
     * 获取公开文件的下载信息
     *
     * @param fileName 文件名称
     * @return 文件下载信息
     */
    FileInfo publicFileDownloadInfo(String fileName);


    /**
     * 获取文件大小
     *
     * @param filePath resource文件路径
     * @return 文件大小
     */
    Long getResourceFileSize(String filePath);

}
