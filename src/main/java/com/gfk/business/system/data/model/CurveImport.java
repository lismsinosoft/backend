package com.gfk.business.system.data.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.ImportBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * curve 导入对象
 *
 * @author wzl
 * @version 1.0 2023/5/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("import_curve")
public class CurveImport extends ImportBase {

    @ExcelProperty(value = "model")
    private String product;

    @ExcelProperty(value = "varnm")
    private String name;

    @ExcelProperty(value = "x")
    private BigDecimal x;

    @ExcelProperty(value = "y")
    private BigDecimal y;

}
