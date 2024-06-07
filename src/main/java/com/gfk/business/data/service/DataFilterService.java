package com.gfk.business.data.service;

import com.gfk.business.data.controller.form.BaseForm;
import com.gfk.business.data.model.vo.ProductGroupFilterVO;

import java.util.List;

/**
 * 筛选条件查询 服务层
 *
 * @author wzl
 * @version 1.0 2023/5/4
 */
public interface DataFilterService {

    /**
     * 查询所有product name
     *
     * @param query 项目ID
     * @return Product Names
     */
    List<String> queryProductNames(BaseForm query);

    /**
     * 查询产品的group
     *
     * @param query 查询条件
     * @return 产品包含的group
     */
    List<ProductGroupFilterVO> queryProductGroups(BaseForm query);

    /**
     * 查询所有Product
     *
     * @param projectId 项目ID
     * @return Product名称list
     */
    List<String> queryProducts(Integer projectId);

    /**
     * 清空本地缓存信息
     *
     * @param key key（可为空）
     */
    void cacheClear(String key);

}
