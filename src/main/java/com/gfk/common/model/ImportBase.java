package com.gfk.common.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author wzl
 * @version 1.0 2023/3/11
 */
@Data
public class ImportBase {
    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Long id;

    @ExcelIgnore
    private String taskId;
    @ExcelIgnore
    private Long projectId;
    @ExcelIgnore
    private Integer seq;
    @ExcelIgnore
    private Date createTime;
}
