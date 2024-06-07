package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzl
 * @version 1.0 2023/7/16
 */
@TableName("project_years")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectYears extends BaseDataEntity {

    private Integer tableNo;

    private Integer year;
}
