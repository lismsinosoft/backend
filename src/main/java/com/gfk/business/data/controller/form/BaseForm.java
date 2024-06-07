package com.gfk.business.data.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wzl
 * @Date 2023/3/11
 * @description
 */
@Data
public class BaseForm {
    @NotNull(message = "项目ID不得为空")
    private Long projectId;

    private String product;
}
