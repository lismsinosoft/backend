package com.gfk.business.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.system.project.model.Project;
import com.gfk.business.user.model.UserProjectMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @Date 2023/3/11
 * @description
 */
public interface UserProjectMappingMapper extends BaseMapper<UserProjectMapping> {
    int batchInsert(@Param("saveList") List<UserProjectMapping> saveList);

    /**
     * 登录后获取用户项目权限
     *
     * @param userId 用户id
     * @return 用户权限dto
     */
    List<Project> getUserProject(@Param("userId") Long userId);
}
