package com.gfk.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wzl
 * @version 1.0 2023/5/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue {
    private String key;
    private Object value;
}
