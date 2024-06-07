package com.gfk.business.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gfk.business.data.model.LanguageText;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/12/13
 */
public interface LanguageTextMapper extends BaseMapper<LanguageText> {
    int batchInsert(@Param("saveList") List<LanguageText> saveList);
}
