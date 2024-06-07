package com.gfk.business.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.user.model.UserPermissionMapping;
import com.gfk.business.user.model.vo.UserPermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户权限关联 数据层
 *
 * @author wzl
 * @date 2023/02/26
 */
public interface UserPermissionMappingMapper extends BaseMapper<UserPermissionMapping> {
    /**
     * 登录后获取用户界面权限
     *
     * @param userId 用户id
     * @return 用户权限dto
     */
    List<UserPermissionVO> getUserPermission(@Param("userId") Long userId);

    int batchInsert(@Param("saveList") List<UserPermissionMapping> saveList);
}