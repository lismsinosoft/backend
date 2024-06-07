package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.T2p2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/7/17
 */
public interface T2p2Mapper extends BaseMapper<T2p2> {
    int batchInsert(@Param("saveList") List<T2p2> saveList);
}
