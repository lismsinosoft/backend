package com.gfk.business.data.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gfk.business.data.controller.form.BaseForm;
import com.gfk.business.data.mapper.CurveMapper;
import com.gfk.business.data.mapper.MediaChannelMapper;
import com.gfk.business.data.model.bo.ProductGroupFilterBO;
import com.gfk.business.data.model.vo.ProductGroupFilterVO;
import com.gfk.business.data.service.DataFilterService;
import com.gfk.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 筛选条件查询 服务层实现
 *
 * @author wzl
 * @version 1.0 2023/5/4
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataFilterServiceImpl implements DataFilterService {

    private final ConcurrentHashMap<String, String> LOCAL_CACHE = new ConcurrentHashMap<>();

    private final CurveMapper curveMapper;

    private final MediaChannelMapper mediaChannelMapper;

    /**
     * 查询所有product name
     *
     * @param query 项目ID
     * @return Product Names
     */
    @Override
    public List<String> queryProductNames(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        String key = "product:name:" + query.getProjectId();
        String valueStr = LOCAL_CACHE.get(key);
        if (StrUtil.isNotBlank(valueStr)) {
            try {
                List<String> strings = JSON.parseObject(valueStr, new TypeReference<List<String>>() {
                });
                if (CollUtil.isNotEmpty(strings)) {
                    return strings;
                }
            } catch (Exception e) {
                log.error("缓存类型转换失败", e);
            }
        }
        List<String> names = Optional.ofNullable(curveMapper.queryAllNames(query.getProjectId())).orElseGet(Collections::emptyList);
        String jsonString = JSON.toJSONString(names);
        LOCAL_CACHE.put(key, jsonString);
        return names;
    }

    /**
     * 查询产品的group
     *
     * @param query 查询条件
     * @return 产品包含的group
     */
    @Override
    public List<ProductGroupFilterVO> queryProductGroups(BaseForm query) {
        if (null == query.getProjectId()) {
            throw new BusinessException("项目ID不得为空");
        }
        String key = "product:group:" + query.getProjectId();
        String valueStr = LOCAL_CACHE.get(key);
        if (StrUtil.isNotBlank(valueStr)) {
            try {
                List<ProductGroupFilterVO> groupFilter = JSON.parseObject(valueStr, new TypeReference<List<ProductGroupFilterVO>>() {
                });
                if (CollUtil.isNotEmpty(groupFilter)) {
                    if (StrUtil.isNotBlank(query.getProduct())) {
                        groupFilter = groupFilter.stream()
                                .filter(f -> StrUtil.isNotBlank(query.getProduct()) && query.getProduct().equalsIgnoreCase(f.getProduct()))
                                .collect(Collectors.toList());
                    }
                    return groupFilter;
                }
            } catch (Exception e) {
                log.error("缓存类型转换失败", e);
            }
        }
        List<ProductGroupFilterBO> filters = Optional.ofNullable(mediaChannelMapper.selectProductGroupFilter(query.getProjectId())).orElseGet(Collections::emptyList);
        List<ProductGroupFilterVO> vos = filters.stream()
                .collect(Collectors.groupingBy(ProductGroupFilterBO::getProduct))
                .entrySet().stream()
                .map(entry -> {
                    ProductGroupFilterVO filterVO = new ProductGroupFilterVO();
                    filterVO.setProduct(entry.getKey());
                    filterVO.setGroups(entry.getValue().stream()
                            .map(ProductGroupFilterBO::getLGroup)
                            .collect(Collectors.toList()));
                    return filterVO;
                })
                .collect(Collectors.toList());
        LOCAL_CACHE.put(key, JSON.toJSONString(vos));
        if (StrUtil.isNotBlank(query.getProduct())) {
            vos = vos.stream()
                    .filter(f -> StrUtil.isNotBlank(query.getProduct()) && query.getProduct().equalsIgnoreCase(f.getProduct()))
                    .collect(Collectors.toList());
        }
        return vos;
    }

    @Override
    public List<String> queryProducts(Integer projectId) {
        return mediaChannelMapper.selectAllProducts(projectId);
    }

    @Override
    public void cacheClear(String key) {
        if (StrUtil.isNotBlank(key)) {
            LOCAL_CACHE.remove(key);
        } else {
            LOCAL_CACHE.clear();
        }
    }
}
