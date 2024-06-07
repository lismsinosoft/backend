package com.gfk.business.system.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.system.data.model.MediaChannelImport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @Date 2023/3/11
 * @description
 */
public interface MediaChannelImportMapper extends BaseMapper<MediaChannelImport> {
    void truncateImport();

    int saveBatch(@Param("list") List<MediaChannelImport> list);
}
