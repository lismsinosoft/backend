package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.SimT1p1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/8/29
 */
public interface SimT1p1Mapper extends BaseMapper<SimT1p1> {
    int batchInsert(@Param("saveList") List<SimT1p1> saveList);

    List<SimT1p1> getTypeHierarchy(@Param("projectId") Long projectId);
}
