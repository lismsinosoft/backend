package com.gfk.business.data.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wzl
 * @version 1.0 2023/3/11
 */
@Data
public class InvestmentDetailBO {

    /**
     * 分类(平台)
     */
    @ApiModelProperty("分类(平台)")
    private String type2;

    /**
     * 影响方式
     */
    private String effect;

    /**
     * 线上线下
     */
    private String line;

    /**
     * Sales Contribution 前一年数据
     */
    private BigDecimal salesFirst;

    /**
     * Sales Contribution 后一年数据
     */
    private BigDecimal salesLast;

    /**
     * Sales Contribution K USD前一年数据
     */
    private BigDecimal salesUsdFirst;

    /**
     * Sales Contribution K USD后一年数据
     */
    private BigDecimal salesUsdLast;

    public static InvestmentDetailBO zeroIdentity() {
        InvestmentDetailBO bo = new InvestmentDetailBO();
        bo.setSalesFirst(BigDecimal.ZERO);
        bo.setSalesLast(BigDecimal.ZERO);
        bo.setSalesUsdFirst(BigDecimal.ZERO);
        bo.setSalesUsdLast(BigDecimal.ZERO);
        return bo;
    }
}
