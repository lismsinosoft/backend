package com.gfk.business.data.controller.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzl
 * @version 1.0 2023/7/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TypeForm extends BaseForm {
    private String type2;
}
