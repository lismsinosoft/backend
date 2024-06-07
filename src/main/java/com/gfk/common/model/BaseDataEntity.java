package com.gfk.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务基础实体
 */
@ApiModel("业务基础实体")
@Data
public abstract class BaseDataEntity implements Serializable {

    /**
     * 唯一编号（ID）
     */
    @ApiModelProperty("[base]唯一编号（ID）")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("[base]项目ID")
    private Long projectId;

    /**
     * 创建日期（yyyy-MM-dd HH:mm:ss）
     */
    @ApiModelProperty("[base]创建日期（yyyy-MM-dd HH:mm:ss）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新日期（yyyy-MM-dd HH:mm:ss）
     */
    @ApiModelProperty("[base]更新日期（yyyy-MM-dd HH:mm:ss）")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
