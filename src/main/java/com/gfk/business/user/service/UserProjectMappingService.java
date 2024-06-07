package com.gfk.business.user.service;

import com.gfk.business.user.model.UserProjectMapping;
import com.gfk.business.user.model.form.UserProjectMappingAddForm;
import com.gfk.business.user.model.vo.UserProjectMappingVO;
import com.gfk.common.model.PageRequest;
import com.gfk.common.model.PageResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户项目关联 服务层
 *
 * @author wzl
 * @date 2023/03/11
 */
public interface UserProjectMappingService {

    /**
     * 查询用户项目关联列表
     *
     * @param form 查询条件
     * @return 用户项目关联集合
     */
    PageResponse<UserProjectMappingVO> pageList(PageRequest<UserProjectMapping> form);

    List<UserProjectMappingVO> getUserProjectList(Long userId);

    boolean hasProject(String userId, Long projectId);

    /**
     * 新增用户项目关联对象
     *
     * @param entity 待新增对象
     * @return 用户项目关联对象
     */
    @Transactional(rollbackFor = Exception.class)
    int add(UserProjectMappingAddForm entity);


    /**
     * 删除用户项目关联对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    boolean del(String ids);

    /**
     * 删除用户项目关联对象
     *
     * @param userId    用户ID
     * @param projectId 项目ID
     * @return 是否删除
     */
    @Transactional(rollbackFor = Exception.class)
    boolean deleteByUserIdProjectId(Long userId, Long projectId);

    void deleteProjectPermission(Long projectId, String permission, Long userId);

    void createProjectPermission(Long projectId, String permission, Long userId);

}
