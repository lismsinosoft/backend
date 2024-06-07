package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.T1p3;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/7/17
 */
public interface T1p3Mapper extends BaseMapper<T1p3> {
    int batchInsert(@Param("saveList") List<T1p3> saveList);
}
