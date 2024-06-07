package com.gfk.business.common.file.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 文件信息
 *
 * @author : wzl
 * @version 1.1 : 2023/02/26
 **/
@ApiModel("文件信息")
@Data
@TableName("file")
@EqualsAndHashCode(callSuper = true)
public class FileInfo extends BaseDataEntity {
    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    private String filename;

    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    private String filePath;

    /**
     * 文件内容的md5摘要
     */
    @ApiModelProperty("文件内容的md5摘要")
    private String fileMd5;

    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    private Long fileSize;

}
