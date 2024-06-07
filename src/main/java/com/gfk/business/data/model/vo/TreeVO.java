package com.gfk.business.data.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wzl
 * @version 1.0 2023/8/4
 */
@Data
public class TreeVO implements Comparable<TreeVO> {
    private String name;

    private List<TreeVO> child;

    @Override
    public int compareTo(TreeVO o) {
        if (null == this.getName()) {
            return 1;
        }
        if (null == o || null == o.getName()) {
            return -1;
        }
        if ("Total".equalsIgnoreCase(this.getName())) {
            return -1;
        }
        if ("Total".equalsIgnoreCase(o.getName())) {
            return 1;
        }
        return this.getName().compareTo(o.getName());
    }
}
