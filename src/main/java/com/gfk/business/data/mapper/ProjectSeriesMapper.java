package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.ProjectSeries;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/7/17
 */
public interface ProjectSeriesMapper extends BaseMapper<ProjectSeries> {
    int batchInsert(@Param("saveList") List<ProjectSeries> saveList);
}
