package com.gfk.business.system.project.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gfk.business.system.project.model.Project;
import com.gfk.business.system.project.model.vo.LanguageTextVo;
import com.gfk.common.model.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 项目 服务层
 *
 * @author wzl
 * @date 2023/03/11
 */
public interface ProjectService {

    /**
     * 查询项目列表
     *
     * @param form 查询条件
     * @return 项目集合
     */
    Page<Project> pageList(PageRequest<Void> form);

    Project findById(Long projectId);

    /**
     * 新增项目对象
     *
     * @param entity 待新增对象
     * @return 项目对象
     */
    @Transactional(rollbackFor = Exception.class)
    Project add(Project entity);

    /**
     * 新增项目对象
     *
     * @param entity 待新增对象
     * @return 项目对象
     */
    @Transactional(rollbackFor = Exception.class)
    String addWithPic(Project entity, MultipartFile picFile1, MultipartFile picFile2);


    /**
     * 更新项目对象
     *
     * @param entity 待更新对象
     * @return 项目对象
     */
    @Transactional(rollbackFor = Exception.class)
    Project update(Project entity);

    /**
     * 更新项目对象
     *
     * @param entity 待更新对象
     * @return 项目对象
     */
    @Transactional(rollbackFor = Exception.class)
    Project update(Project entity, MultipartFile picFile1, MultipartFile picFile2);


    /**
     * 删除项目对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    boolean del(String ids);

    List<LanguageTextVo> getProjectLanguageText(Long projectId, String language);

}
