package com.gfk.business.system.data.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.ImportBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wzl
 * @Date 2023/3/11
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("import_media")
public class MediaImport extends ImportBase {

    @ExcelProperty(value = "varnm")
    private String name;

    @ExcelProperty(value = "Platform")
    private String platform;

    @ExcelProperty(value = "media_PL")
    private String product;

    @ExcelProperty(value = "lgroup")
    private String lGroup;

    @ExcelProperty(value = "var_metrics")
    private String varMetrics;

    @ExcelProperty(value = "yearrpt")
    private String year;

    @ExcelProperty(value = "ym")
    private Date yearMonth;

    @ExcelProperty(value = "EndPeriod")
    private Date endPeriod;

    @ExcelProperty(value = "value")
    private BigDecimal value;
}
