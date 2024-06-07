package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.Hl;
import org.apache.ibatis.annotations.Param;

/**
 * Hl 数据层
 *
 * @author wzl
 * @version 1.0 2023/5/3
 */
public interface HlMapper extends BaseMapper<Hl> {

    /**
     * 导入中间数据转换业务数据
     *
     * @param taskId 任务id
     * @return 成功条数
     */
    int transformImport(@Param("taskId") String taskId);

    void truncateHl();
}