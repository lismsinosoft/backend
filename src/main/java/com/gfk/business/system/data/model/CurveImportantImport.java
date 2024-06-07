package com.gfk.business.system.data.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.ImportBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * curve important 导入对象
 *
 * @author wzl
 * @version 1.0 2023/5/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("import_curve_important")
public class CurveImportantImport extends ImportBase {

    @ExcelProperty(value = "model")
    private String product;

    @ExcelProperty(value = "varnm")
    private String name;

    @ExcelProperty(value = "label")
    private String label;

    @ExcelProperty(value = "x")
    private BigDecimal x;

    @ExcelProperty(value = "y")
    private BigDecimal y;

}
