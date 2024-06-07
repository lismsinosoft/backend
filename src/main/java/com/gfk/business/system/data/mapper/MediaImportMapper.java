package com.gfk.business.system.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.system.data.model.MediaImport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @Date 2023/3/11
 * @description
 */
public interface MediaImportMapper extends BaseMapper<MediaImport> {

    void truncateImport();

    int saveBatch(@Param("list") List<MediaImport> list);
}
