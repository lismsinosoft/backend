package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Curve 实体类
 *
 * @author wzl
 * @version 1.0 2023/5/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("curve")
public class Curve extends BaseEntity {

    private Long projectId;

    private String product;

    private String name;

    private BigDecimal x;

    private BigDecimal y;

    private Boolean isImportant;

    private String importantLabel;
}
