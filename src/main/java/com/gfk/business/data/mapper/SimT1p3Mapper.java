package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.SimT1p3;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/9/2
 */
public interface SimT1p3Mapper extends BaseMapper<SimT1p3> {
    int batchInsert(@Param("saveList") List<SimT1p3> saveList);
}
