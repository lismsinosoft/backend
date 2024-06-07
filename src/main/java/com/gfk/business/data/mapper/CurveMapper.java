package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.Curve;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Curve
 *
 * @author wzl
 * @version 1.0 2023/5/17
 */
public interface CurveMapper extends BaseMapper<Curve> {

    List<String> queryAllNames(@Param("projectId") Long projectId);

    /**
     * 导入中间数据转换业务数据
     *
     * @param taskId 任务id
     * @return 成功条数
     */
    int transformImport(@Param("taskId") String taskId);


    int transformImportantImport(@Param("taskId") String taskId);

    void truncateCurve();
}