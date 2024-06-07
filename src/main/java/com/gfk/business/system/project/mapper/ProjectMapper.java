package com.gfk.business.system.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.system.project.model.Project;
import org.apache.ibatis.annotations.Param;

/**
 * 项目 数据层
 *
 * @author wzl
 * @date 2023/03/11
 */
public interface ProjectMapper extends BaseMapper<Project> {
    int updateProjectPic(@Param("projectId") Long projectId, @Param("url1") String url1, @Param("url2") String url2);
}