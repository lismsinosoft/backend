package com.gfk.business.system.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.system.data.model.HlImport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @Date 2023/3/11
 * @description
 */
public interface HlImportMapper extends BaseMapper<HlImport> {
    void truncateImport();

    int saveBatch(@Param("list") List<HlImport> list);
}
