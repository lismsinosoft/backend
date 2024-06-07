package com.gfk.business.data.controller.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author wzl
 * @version 1.0 2023/7/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TypeActForm extends BaseForm {
    @NotBlank
    private String type1;
    @NotBlank
    private String type2;
    @NotBlank
    private String actOn;
}
