package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.SimT1p2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/8/31
 */
public interface SimT1p2Mapper extends BaseMapper<SimT1p2> {
    int batchInsert(@Param("saveList") List<SimT1p2> saveList);
}
