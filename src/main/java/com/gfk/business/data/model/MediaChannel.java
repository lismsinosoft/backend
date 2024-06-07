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
@TableName("media_channel")
public class MediaChannel extends BaseEntity {

    private Long projectId;

    private String platform;

    private String lGroup;

    private String varLabel;

    private String product;

    private String modelName;

    private String name;

    private String line;

    private BigDecimal pieOverall;


    private BigDecimal pieFirstYear;


    private BigDecimal pieLastYear;


    private BigDecimal spendOverall;


    private BigDecimal spendFirstYear;


    private BigDecimal spendLastYear;


    private BigDecimal valueUsdOverall;


    private BigDecimal valueUsdFirstYear;


    private BigDecimal valueUsdLastYear;


    private BigDecimal drivenOverall;


    private BigDecimal drivenUsdFirstYear;


    private BigDecimal drivenUsdLastYear;


    private BigDecimal roiOverall;


    private BigDecimal roiFirstYear;


    private BigDecimal roiLastYear;

}
