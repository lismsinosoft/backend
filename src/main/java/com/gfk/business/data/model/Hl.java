package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author wzl
 * @Date 2023/3/11
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hl")
public class Hl extends BaseEntity {

    private Long projectId;

    private String platform;

    private String lGroup;

    private BigDecimal hl1;

    private String l2Group;

    private BigDecimal hl2;
}
