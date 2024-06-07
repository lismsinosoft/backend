package com.gfk.business.data.controller.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wzl
 * @version 1.0 2023/9/3
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SimulatorForm extends BaseForm {
    private String frame1;
    private String frame2;
    private String type1;
}
