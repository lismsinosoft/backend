package com.gfk.business.data.controller.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GroupForm extends BaseForm {
    private String group;
}
