package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.T5p3;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/8/4
 */
public interface T5p3Mapper extends BaseMapper<T5p3> {
    int batchInsert(@Param("saveList") List<T5p3> saveList);
}
