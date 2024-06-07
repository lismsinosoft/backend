package com.gfk.business.system.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.system.data.model.CurveImport;
import com.gfk.business.system.data.model.CurveImportantImport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * curve 导入 dao
 *
 * @author wzl
 * @version 1.0 2023/5/17
 */
public interface CurveImportMapper extends BaseMapper<CurveImport> {
    void truncateImport();

    void truncateImportantImport();

    int saveBatch(@Param("saveList") List<CurveImport> saveList);

    int saveImportantBatch(@Param("saveList") List<CurveImportantImport> saveList);
}
