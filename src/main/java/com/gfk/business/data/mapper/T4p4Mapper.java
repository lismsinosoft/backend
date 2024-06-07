package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.T4p4;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/7/17
 */
public interface T4p4Mapper extends BaseMapper<T4p4> {
    int batchInsert(@Param("saveList") List<T4p4> saveList);
}
