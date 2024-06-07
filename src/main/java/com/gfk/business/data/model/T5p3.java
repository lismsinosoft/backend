package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/8/4
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t5p3")
public class T5p3 extends BaseDataEntity {

    private String series;

    private String type1;

    private String type2;

    private String actOn;

    private BigDecimal x;

    private BigDecimal y;

    private Boolean isImportant;

    private String label;
}
