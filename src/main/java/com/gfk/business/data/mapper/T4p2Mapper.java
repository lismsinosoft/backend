package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.T4p2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/7/17
 */
public interface T4p2Mapper extends BaseMapper<T4p2> {
    int batchInsert(@Param("saveList") List<T4p2> saveList);
}
