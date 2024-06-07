package com.gfk.business.system.data.model;

import com.alibaba.excel.annotation.ExcelIgnore;
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
@TableName("import_sales_date")
public class SalesDateImport extends ImportBase {

    @ExcelProperty(value = "yearrpt")
    private String year;

    @ExcelProperty(value = "ym")
    private Date yearMonth;

    @ExcelProperty(value = "EndPeriod")
    private Date endPeriod;

    @ExcelProperty(value = "PL")
    private String product;

    @ExcelProperty(value = "channel")
    private String channel;

    @ExcelProperty(value = "units")
    private BigDecimal units;

    @ExcelProperty(value = "value")
    private BigDecimal value;

    @ExcelProperty(value = "price")
    private BigDecimal price;

    @ExcelIgnore
    private Integer type;
}
