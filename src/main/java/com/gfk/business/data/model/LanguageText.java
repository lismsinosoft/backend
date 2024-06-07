package com.gfk.business.data.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gfk.common.model.BaseDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzl
 * @version 1.0 2023/12/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("language_text")
public class LanguageText extends BaseDataEntity {

    private String textKey;

    private String textValue;

    private String language;
}
