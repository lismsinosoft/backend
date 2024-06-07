package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.GroupRelation;
import org.apache.ibatis.annotations.Param;

/**
 * group关联关系 数据层
 *
 * @author wzl
 * @version 1.0 2023/5/3
 */
public interface GroupRelationMapper extends BaseMapper<GroupRelation> {

    /**
     * 提取HL数据中的关联关系
     *
     * @param taskId 项目id
     * @return 成功条数
     */
    int extractRelation(@Param("taskId") String taskId);

    void truncateGroupRelation();
}