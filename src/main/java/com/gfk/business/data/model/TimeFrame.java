package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzl
 * @version 1.0 2023/8/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("timeframe")
public class TimeFrame extends BaseDataEntity {

    private String frame1;

    private String frame2;

}
