package com.gfk.business.system.project.model;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.AuditBaseEntity;
import com.gfk.framework.properties.ApplicationInfoProperties;
import com.gfk.framework.properties.FileProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author wzl
 * @Date 2023/3/11
 * @description
 */
@ApiModel("项目记录")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("project")
@NoArgsConstructor
public class Project extends AuditBaseEntity {

    /**
     * 项目code
     */
    @ApiModelProperty("项目code")
    @NotNull(message = "项目code不得为空")
    private String code;
    /**
     * 项目名称
     */
    @ApiModelProperty("项目名称")
    @NotNull(message = "项目名不得为空")
    private String name;
    /**
     * 项目英文名称
     */
    @ApiModelProperty("项目英文名称")
    private String nameEn;
    /**
     * 项目图片地址
     */
    @ApiModelProperty("项目图片地址")
    private String picUrl;
    /**
     * 项目图片地址2
     */
    @ApiModelProperty("项目图片地址2")
    private String picUrl2;

    public Project(String code, String name, String nameEn) {
        this.code = code;
        this.name = name;
        this.nameEn = nameEn;
    }


    public String getFullPicUrl() {
        if (StrUtil.isBlank(this.picUrl)) {
            return this.picUrl;
        }
        boolean url = StrUtil.startWith(this.picUrl, "http://", true)
                || StrUtil.startWith(this.picUrl, "https://", true);
        return url ? this.picUrl : ApplicationInfoProperties.getAppUrl() + FileProperties.getStaticUrl() + this.picUrl;
    }

    public String getFullPicUrl2() {
        if (StrUtil.isBlank(this.picUrl2)) {
            return this.picUrl2;
        }
        boolean url = StrUtil.startWith(this.picUrl2, "http://", true)
                || StrUtil.startWith(this.picUrl2, "https://", true);
        return url ? this.picUrl2 : ApplicationInfoProperties.getAppUrl() + FileProperties.getStaticUrl() + this.picUrl2;
    }
}
