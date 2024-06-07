package com.gfk.business.system.data.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.ImportBase;
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
@TableName("import_hl")
public class HlImport extends ImportBase {

    @ExcelProperty(value = "Platform")
    private String platform;

    @ExcelProperty(value = "lgroup")
    private String lGroup;

    @ExcelProperty(value = "HL1")
    private BigDecimal hl1;

    @ExcelProperty(value = "l2group")
    private String l2Group;

    @ExcelProperty(value = "HL2")
    private BigDecimal hl2;

}
