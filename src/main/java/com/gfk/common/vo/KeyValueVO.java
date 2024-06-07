package com.gfk.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

/**
 * 简单键值表现层对象
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@ApiModel("简单键值表现层对象")
@AllArgsConstructor
public class KeyValueVO {

    @ApiModelProperty(value = "键")
    private Object key;

    @ApiModelProperty(value = "值")
    private Object value;

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
