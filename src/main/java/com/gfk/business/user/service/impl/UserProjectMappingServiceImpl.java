package com.gfk.business.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gfk.business.system.permission.mapper.PermissionMapper;
import com.gfk.business.system.permission.model.Permission;
import com.gfk.business.system.project.mapper.ProjectMapper;
import com.gfk.business.system.project.model.Project;
import com.gfk.business.user.mapper.UserMapper;
import com.gfk.business.user.mapper.UserProjectMappingMapper;
import com.gfk.business.user.mapper.UserProjectPermissionMappingMapper;
import com.gfk.business.user.model.User;
import com.gfk.business.user.model.UserProjectMapping;
import com.gfk.business.user.model.UserProjectPermissionMapping;
import com.gfk.business.user.model.form.UserProjectMappingAddForm;
import com.gfk.business.user.model.vo.UserProjectMappingVO;
import com.gfk.business.user.service.UserProjectMappingService;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.model.PageRequest;
import com.gfk.common.model.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户项目关联 服务层实现
 *
 * @author wzl
 * @version 1.1 2024/3/6
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserProjectMappingServiceImpl implements UserProjectMappingService {

    private final UserProjectMappingMapper userProjectMappingMapper;

    private final UserMapper userMapper;

    private final ProjectMapper projectMapper;

    private final PermissionMapper permissionMapper;

    private final UserProjectPermissionMappingMapper userProjectPermissionMappingMapper;

    /**
     * 查询用户项目关联列表
     *
     * @param form 查询条件
     * @return 用户项目关联集合
     */
    @Override
    public PageResponse<UserProjectMappingVO> pageList(PageRequest<UserProjectMapping> form) {
        UserProjectMapping filter = Optional.ofNullable(form.getFilter()).orElseGet(UserProjectMapping::new);
        Page<UserProjectMapping> page = new Page<>(form.getPageNo(), form.getPageSize());
        LambdaQueryWrapper<UserProjectMapping> query = Wrappers.<UserProjectMapping>lambdaQuery();
        query.eq(null != filter.getUserId(), UserProjectMapping::getUserId, filter.getUserId());
        query.eq(null != filter.getProjectId(), UserProjectMapping::getProjectId, filter.getProjectId());
        Page<UserProjectMapping> dbPage = userProjectMappingMapper.selectPage(page, query);
        List<UserProjectMapping> records = dbPage.getRecords();
        List<UserProjectMappingVO> voList = this.entityToVO(records);
        return PageResponse.ofCustomizedPage(dbPage, voList);
    }

    @Override
    public List<UserProjectMappingVO> getUserProjectList(Long userId) {
        if (null == userId) {
            return Collections.emptyList();
        }
        List<UserProjectMapping> records = userProjectMappingMapper.selectList(
                Wrappers.<UserProjectMapping>lambdaQuery().eq(UserProjectMapping::getUserId, userId));
        if (CollUtil.isEmpty(records)) {
            return Collections.emptyList();
        }
        return this.entityToVO(records);
    }

    @Override
    public boolean hasProject(String userId, Long projectId) {
        List<UserProjectMapping> records = userProjectMappingMapper.selectList(
                Wrappers.<UserProjectMapping>lambdaQuery().eq(UserProjectMapping::getUserId, userId));
        return records.stream().anyMatch(p -> Objects.equals(p.getProjectId(), projectId));
    }

    /**
     * 新增用户项目关联对象
     *
     * @param addForm 待新增对象
     * @return 用户项目关联对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int add(UserProjectMappingAddForm addForm) {
        if (null == addForm.getUserId()) {
            throw new BusinessException("用户ID为空");
        }
        if (null == addForm.getProjectId()) {
            throw new BusinessException("项目ID为空");
        }
        List<User> user = Optional.ofNullable(userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getId, addForm.getUserId())))
                .orElseGet(Collections::emptyList);
        if (CollUtil.isEmpty(user)) {
            throw new BusinessException("用户id不存在" + addForm.getUserId());
        }
        List<Project> projects = Optional.ofNullable(projectMapper.selectList(Wrappers.<Project>lambdaQuery().in(Project::getId, addForm.getProjectId())))
                .orElseGet(Collections::emptyList);
        if (CollUtil.isEmpty(projects)) {
            throw new BusinessException("项目id不存在" + addForm.getProjectId());
        }
        // 增加用户项目关联
        UserProjectMapping mapping = new UserProjectMapping();
        BeanUtil.copyProperties(addForm, mapping);
        mapping.setIsDeleted(false);
        mapping.setCreateBy("admin");
        mapping.setCreateTime(new Date());
        userProjectMappingMapper.insert(mapping);
        // 初始化用户项目权限
        List<Permission> permissions = permissionMapper.selectList(Wrappers.<Permission>lambdaQuery().eq(Permission::getIsDeleted, false));
        List<UserProjectPermissionMapping> projectPermissionMappingList = permissions.stream().map(permission -> {
            UserProjectPermissionMapping projectPermissionMapping = new UserProjectPermissionMapping();
            projectPermissionMapping.setUserId(addForm.getUserId());
            projectPermissionMapping.setProjectId(addForm.getProjectId());
            projectPermissionMapping.setPermission(permission.getCode());
            projectPermissionMapping.setCreateBy("admin");
            projectPermissionMapping.setCreateTime(new Date());
            projectPermissionMapping.setIsDeleted(false);
            return projectPermissionMapping;
        }).collect(Collectors.toList());
        userProjectPermissionMappingMapper.batchInsert(projectPermissionMappingList);
        return 1;
    }

    /**
     * 删除用户项目关联对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean del(String ids) {
        userProjectMappingMapper.deleteBatchIds(CollectionUtil.toList(ids.split(",")));
        return true;
    }

    @Override
    public boolean deleteByUserIdProjectId(Long userId, Long projectId) {
        if (null == projectId) {
            throw new BusinessException("项目ID为空");
        }
        if (null == userId) {
            throw new BusinessException("用户ID为空");
        }
        userProjectMappingMapper.delete(Wrappers.<UserProjectMapping>lambdaQuery()
                .eq(UserProjectMapping::getUserId, userId)
                .eq(UserProjectMapping::getProjectId, projectId));
        userProjectPermissionMappingMapper.delete(Wrappers.<UserProjectPermissionMapping>lambdaQuery()
                .eq(UserProjectPermissionMapping::getUserId, userId)
                .eq(UserProjectPermissionMapping::getProjectId, projectId));
        return true;
    }

    @Override
    public void deleteProjectPermission(Long projectId, String permission, Long userId) {
        log.info("删除用户项目权限，ProjectID:" + projectId + "，Permission:" + permission + "，UserId" + userId);
        if (null == projectId) {
            throw new BusinessException("项目ID为空");
        }
        if (null == permission) {
            throw new BusinessException("permission为空");
        }
        List<Permission> permissions = permissionMapper.selectList(Wrappers.<Permission>lambdaQuery().eq(Permission::getIsDeleted, false).eq(Permission::getCode, permission));
        if (CollUtil.isEmpty(permissions)) {
            throw new BusinessException("permission code 错误");
        }
        int deletedCount = userProjectPermissionMappingMapper.delete(
                Wrappers.<UserProjectPermissionMapping>lambdaQuery()
                        .eq(UserProjectPermissionMapping::getProjectId, projectId)
                        .eq(UserProjectPermissionMapping::getPermission, permission)
                        .eq(null != userId, UserProjectPermissionMapping::getUserId, userId));
        log.info("共删除" + deletedCount + "条记录");
    }

    @Override
    public void createProjectPermission(Long projectId, String permission, Long userId) {
        log.info("创建用户项目权限，ProjectID:" + projectId + "，Permission:" + permission + "，UserId" + userId);
        if (null == projectId) {
            throw new BusinessException("项目ID为空");
        }
        if (null == permission) {
            throw new BusinessException("permission为空");
        }
        List<Permission> permissions = permissionMapper.selectList(Wrappers.<Permission>lambdaQuery().eq(Permission::getIsDeleted, false).eq(Permission::getCode, permission));
        if (CollUtil.isEmpty(permissions)) {
            throw new BusinessException("permission code 错误");
        }
        // 查询当前项目所有的关联用户
        List<UserProjectMapping> userProjectMappings = userProjectMappingMapper.selectList(
                Wrappers.<UserProjectMapping>lambdaQuery()
                        .eq(UserProjectMapping::getProjectId, projectId)
                        .eq(UserProjectMapping::getIsDeleted, false)
                        .eq(null != userId, UserProjectMapping::getUserId, userId));
        if (CollUtil.isEmpty(userProjectMappings)) {
            log.info("当前项目无关联用户，创建结束");
            return;
        }
        List<Long> allUsers = userProjectMappings.stream().map(UserProjectMapping::getUserId).collect(Collectors.toList());
        // 查询已有权限，补足差集
        List<UserProjectPermissionMapping> existedPermission = userProjectPermissionMappingMapper.selectList(
                Wrappers.<UserProjectPermissionMapping>lambdaQuery()
                        .eq(UserProjectPermissionMapping::getProjectId, projectId)
                        .eq(UserProjectPermissionMapping::getPermission, permission)
                        .in(UserProjectPermissionMapping::getUserId, allUsers)
                        .eq(UserProjectPermissionMapping::getIsDeleted, false));
        Set<Long> existedUserId = existedPermission.stream().map(UserProjectPermissionMapping::getUserId).collect(Collectors.toSet());
        List<UserProjectPermissionMapping> inserList = allUsers.stream().filter(id -> !existedUserId.contains(id)).map(uid -> {
            UserProjectPermissionMapping projectPermissionMapping = new UserProjectPermissionMapping();
            projectPermissionMapping.setUserId(uid);
            projectPermissionMapping.setProjectId(projectId);
            projectPermissionMapping.setPermission(permission);
            projectPermissionMapping.setCreateBy("admin");
            projectPermissionMapping.setCreateTime(new Date());
            projectPermissionMapping.setIsDeleted(false);
            return projectPermissionMapping;
        }).collect(Collectors.toList());
        if (CollUtil.isEmpty(inserList)) {
            log.info("没有需要新增的数据，创建结束");
            return;
        }
        int insertCount = userProjectPermissionMappingMapper.batchInsert(inserList);
        log.info("共插入" + insertCount + "条记录");
    }

    private List<UserProjectMappingVO> entityToVO(List<UserProjectMapping> records) {
        List<UserProjectMappingVO> voList = records.stream().map(po -> {
            UserProjectMappingVO vo = new UserProjectMappingVO();
            vo.setUserId(po.getUserId());
            vo.setProjectId(po.getProjectId());
            return vo;
        }).collect(Collectors.toList());
        if (CollUtil.isEmpty(voList)) {
            return Collections.emptyList();
        }
        Set<Long> projectIds = voList.stream().map(UserProjectMappingVO::getProjectId).collect(Collectors.toSet());
        Set<Long> userIds = voList.stream().map(UserProjectMappingVO::getUserId).collect(Collectors.toSet());
        Map<Long, String> userMap = Optional.ofNullable(userMapper.selectList(Wrappers.<User>lambdaQuery().in(User::getId, userIds)))
                .orElseGet(Collections::emptyList)
                .stream()
                .collect(Collectors.toMap(User::getId, User::getName, (oldVal, newVal) -> newVal));
        Map<Long, Project> projectMap = Optional.ofNullable(projectMapper.selectList(Wrappers.<Project>lambdaQuery().in(Project::getId, projectIds)))
                .orElseGet(Collections::emptyList)
                .stream()
                .collect(Collectors.toMap(Project::getId, Function.identity(), (oldVal, newVal) -> newVal));
        Map<String, List<String>> permissionGroup = Optional.ofNullable(userProjectPermissionMappingMapper.selectList(
                        Wrappers.<UserProjectPermissionMapping>lambdaQuery()
                                .in(UserProjectPermissionMapping::getUserId, userIds)))
                .orElseGet(Collections::emptyList)
                .stream()
                .collect(Collectors.groupingBy(m -> m.getUserId() + "-" + m.getProjectId(), Collectors.mapping(UserProjectPermissionMapping::getPermission, Collectors.toList())));
        voList.forEach(vo -> {
            vo.setUserName(userMap.get(vo.getUserId()));
            Project project = projectMap.get(vo.getProjectId());
            if (null != project) {
                vo.setProjectName(project.getName());
                vo.setProjectNameEn(project.getNameEn());
                vo.setPicUrl(project.getFullPicUrl());
                vo.setPicUrl2(project.getFullPicUrl2());
                vo.setPermissionList(permissionGroup.get(vo.getUserId() + "-" + vo.getProjectId()));
            }
        });
        voList.sort(Comparator.comparing(UserProjectMappingVO::getUserId).thenComparing(UserProjectMappingVO::getProjectId));
        return voList;
    }
}
