package com.gfk.business.data.model.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wzl
 * @version 1.0 2023/5/21
 */
@Data
public class ImpressionBO {
    private Date date;

    private BigDecimal value;
}
