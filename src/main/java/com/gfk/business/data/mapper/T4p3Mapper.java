package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.T4p3;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/7/17
 */
public interface T4p3Mapper extends BaseMapper<T4p3> {
    int batchInsert(@Param("saveList") List<T4p3> saveList);

    List<String> distinctTypes(@Param("projectId") Long projectId, @Param("series") String series);
}
