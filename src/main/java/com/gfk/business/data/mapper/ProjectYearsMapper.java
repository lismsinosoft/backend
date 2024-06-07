package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.ProjectYears;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/7/17
 */
public interface ProjectYearsMapper extends BaseMapper<ProjectYears> {
    int batchInsert(@Param("saveList") List<ProjectYears> saveList);
}
