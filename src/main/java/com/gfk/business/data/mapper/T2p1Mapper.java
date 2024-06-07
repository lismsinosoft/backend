package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.T2p1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/7/17
 */
public interface T2p1Mapper extends BaseMapper<T2p1> {
    int batchInsert(@Param("saveList") List<T2p1> saveList);
}
