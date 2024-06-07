package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.T5p1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/8/4
 */
public interface T5p1Mapper extends BaseMapper<T5p1> {
    int batchInsert(@Param("saveList") List<T5p1> saveList);

    List<T5p1> queryFilter(@Param("projectId") Long projectId, @Param("product") String product);
}
