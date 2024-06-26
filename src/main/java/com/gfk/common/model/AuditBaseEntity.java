package com.gfk.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务对象基础类（包含审计功能）
 *
 * JPA Audit
 * https://www.jianshu.com/p/14cb69646195
 * 首先申明实体类，需要在类上加上注解@EntityListeners(AuditingEntityListener.class)，
 * 其次在application启动类中加上注解EnableJpaAuditing，
 * 同时在需要的字段上加上@CreatedDate、@CreatedBy、@LastModifiedDate、@LastModifiedBy等注解。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AuditBaseEntity extends BaseEntity {

    @ApiModelProperty("[base]创建者")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty("[base]更新者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty("[base]逻辑删除")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Boolean isDeleted;


}
