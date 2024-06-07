package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/8/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sim_t1p2")
public class SimT1p2 extends BaseDataEntity {

    private String series;

    private String frame1;

    private String frame2;

    private String type1;

    private String label;

    private BigDecimal spend;

    private BigDecimal revenue;

    private BigDecimal roi;

}
