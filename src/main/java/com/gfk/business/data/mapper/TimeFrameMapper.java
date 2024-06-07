package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.TimeFrame;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/8/29
 */
public interface TimeFrameMapper extends BaseMapper<TimeFrame> {
    int batchInsert(@Param("saveList") List<TimeFrame> saveList);
}
