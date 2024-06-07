package com.gfk.business.common.file.controller;

import com.gfk.business.common.file.service.FileInfoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 文件处理
 *
 * @author : wzl
 * @version : 1.0 2023/02/26
 **/
@Api(tags = "[a]文件处理")
@Controller
@RequestMapping("/api/common")
@RequiredArgsConstructor
public class FileInfoController {

    private final FileInfoService fileInfoService;

//    /**
//     * 上传文件
//     * @param file 上传的文件
//     */
//    @ApiOperation(value = "文件上传",notes = "通用上传请求")
//    @PostMapping("/upload")
//    @ResponseBody
//    public SimpleResponseForm<S3FileVO> uploadFile(MultipartFile file) {
//        S3FileVO vo = new S3FileVO();
//        return success(vo);
//    }
//

//    /**
//     * 上传文件（无需权限的公开文件）
//     *
//     * @param file 上传的文件
//     */
//    @ApiOperation(value = "无需权限的公开文件", notes = "通用上传请求")
//    @PostMapping("/upload/public")
//    @ResponseBody
//    public Result<FileInfo> uploadPublicFile(MultipartFile file, Long projectId) {
//        FileInfo userFileInfo = fileInfoService.uploadPublicFile(file, projectId);
//        FileInfo result = new FileInfo();
//        BeanUtil.copyProperties(userFileInfo, result);
//        return Result.success(result);
//    }

}
