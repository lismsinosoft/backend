package com.gfk.business.system.project.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gfk.business.common.file.service.FileInfoService;
import com.gfk.business.data.mapper.LanguageTextMapper;
import com.gfk.business.data.model.LanguageText;
import com.gfk.business.system.project.mapper.ProjectMapper;
import com.gfk.business.system.project.model.Project;
import com.gfk.business.system.project.model.vo.LanguageTextVo;
import com.gfk.business.system.project.service.ProjectService;
import com.gfk.common.exception.BusinessException;
import com.gfk.common.model.PageRequest;
import com.gfk.common.util.PojoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 项目 服务层实现
 *
 * @author wzl
 * @date 2023/03/11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;

    private final FileInfoService fileInfoService;

    private final LanguageTextMapper languageTextMapper;


    /**
     * 查询项目列表
     *
     * @param form 查询条件
     * @return 项目集合
     */
    @Override
    public Page<Project> pageList(PageRequest<Void> form) {
        Page<Project> page = new Page<>(1, 1000);
        LambdaQueryWrapper<Project> query = Wrappers.<Project>lambdaQuery();
        return projectMapper.selectPage(page, query);
    }

    /**
     * 根据ID查询项目
     *
     * @param projectId 项目ID
     * @return 项目对象
     */
    @Override
    public Project findById(Long projectId) {
        if (null == projectId) {
            throw new BusinessException("项目ID为空");
        }
        Project project = projectMapper.selectById(projectId);
        if (null == project) {
            throw new BusinessException("项目ID错误");
        }
        return project;
    }

    /**
     * 新增项目对象
     *
     * @param entity 待新增对象
     * @return 项目对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Project add(Project entity) {
        List<Project> projects = projectMapper.selectList(Wrappers.<Project>lambdaQuery().eq(Project::getCode, entity.getCode()));
        if (CollUtil.isNotEmpty(projects)) {
            throw new BusinessException("项目已存在!");
        }
        entity.setIsDeleted(false);
        projectMapper.insert(entity);
        return entity;
    }

    /**
     * 新增项目对象
     *
     * @param entity 待新增对象
     * @return 项目对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String addWithPic(Project entity, MultipartFile picFile1, MultipartFile picFile2) {
        List<Project> projects = projectMapper.selectList(Wrappers.<Project>lambdaQuery().eq(Project::getCode, entity.getCode()));
        if (CollUtil.isNotEmpty(projects)) {
            throw new BusinessException("项目已存在!");
        }
        entity.setIsDeleted(false);
        projectMapper.insert(entity);
        String picUrl1 = fileInfoService.uploadProjectImgFile(picFile1, entity.getId());
        String picUrl2 = fileInfoService.uploadProjectImgFile(picFile2, entity.getId());
        projectMapper.updateProjectPic(entity.getId(), picUrl1, picUrl2);
        return "已创建项目" + entity.getName() + ",项目ID：" + entity.getId();
    }

    /**
     * 更新项目对象
     *
     * @param entity 待更新对象
     * @return 项目对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Project update(Project entity) {
        Project oldEntity = projectMapper.selectById(entity.getId());
        if (oldEntity == null) {
            throw new BusinessException("对象不存在，请检查");
        }
        //对象属性复制
        PojoUtils.copyPropertiesIgnoreNull(entity, oldEntity);
        projectMapper.updateById(oldEntity);
        return oldEntity;
    }

    /**
     * 更新项目对象
     *
     * @param entity 待更新对象
     * @return 项目对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Project update(Project entity, MultipartFile picFile1, MultipartFile picFile2) {
        Project oldEntity = projectMapper.selectById(entity.getId());
        if (oldEntity == null) {
            throw new BusinessException("对象不存在，请检查");
        }
        if (null != picFile1) {
            entity.setPicUrl(fileInfoService.uploadProjectImgFile(picFile1, entity.getId()));
        }
        if (null != picFile2) {
            entity.setPicUrl2(fileInfoService.uploadProjectImgFile(picFile2, entity.getId()));
        }
        //对象属性复制
        PojoUtils.copyPropertiesIgnoreNull(entity, oldEntity);
        projectMapper.updateById(oldEntity);
        return oldEntity;
    }

    /**
     * 删除项目对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean del(String ids) {
        projectMapper.deleteBatchIds(CollectionUtil.toList(ids.split(",")));
        return true;
    }

    @Override
    public List<LanguageTextVo> getProjectLanguageText(Long projectId, String language) {
        if (null == projectId) {
            throw new BusinessException("项目ID为空");
        }
        List<LanguageText> defaultTexts = Optional.ofNullable(languageTextMapper.selectList(
                Wrappers.<LanguageText>lambdaQuery()
                        .eq(LanguageText::getProjectId, 0))
        ).orElseGet(Collections::emptyList);
        Map<String, String> textMap = defaultTexts.stream().collect(Collectors.toMap(LanguageText::getTextKey, LanguageText::getTextValue, (oldVal, newVal) -> newVal));
        List<LanguageText> projectTexts = Optional.ofNullable(languageTextMapper.selectList(
                Wrappers.<LanguageText>lambdaQuery()
                        .eq(LanguageText::getProjectId, projectId)
                        .eq(null != language, LanguageText::getLanguage, language))
        ).orElseGet(Collections::emptyList);
        projectTexts.forEach(text -> textMap.put(text.getTextKey(), text.getTextValue()));
        return textMap.entrySet().stream().map(entry -> {
            LanguageTextVo vo = new LanguageTextVo();
            vo.setKey(entry.getKey());
            vo.setText(entry.getValue());
            return vo;
        }).collect(Collectors.toList());
    }

}
