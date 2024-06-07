package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.T3p1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/7/25
 */
public interface T3p1Mapper extends BaseMapper<T3p1> {
    int batchInsert(@Param("saveList") List<T3p1> saveList);
}
