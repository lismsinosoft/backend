package com.gfk.business.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.user.model.UserProjectPermissionMapping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户项目权限
 *
 * @author wzl
 * @version 1.0 2024/3/6
 */
public interface UserProjectPermissionMappingMapper extends BaseMapper<UserProjectPermissionMapping> {
    int batchInsert(@Param("saveList") List<UserProjectPermissionMapping> saveList);
}
