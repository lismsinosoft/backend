package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzl
 * @version 1.0 2023/7/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("project_series")
public class ProjectSeries extends BaseDataEntity {
    private String name;
}
