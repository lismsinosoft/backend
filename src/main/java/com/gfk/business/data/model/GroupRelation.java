package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzl
 * @version 1.0 2023/5/3
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("group_relation")
public class GroupRelation extends BaseEntity {
    private String platform;
}
