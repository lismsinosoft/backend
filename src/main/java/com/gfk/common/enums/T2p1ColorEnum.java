package com.gfk.common.enums;

/**
 * @author wzl
 * @version 1.0 2024/3/18
 */
public enum T2p1ColorEnum {
    COLOR1(0, new String[]{"ATL"}, "#0077AD"),
    COLOR2(1, new String[]{"BTL"}, "#FFA52F"),
    COLOR3(2, new String[]{"BHT", "organic search"}, "#D2335F"),
    COLOR4(3, new String[]{""}, "#54B9B1"),
    COLOR5(4, new String[]{""}, "#3E62A4"),
    BASE(-1, new String[]{"Base"}, "#909AA0");

    T2p1ColorEnum(int sort, String[] specName, String color) {
        this.sort = sort;
        this.specNames = specName;
        this.color = color;
    }

    public final int sort;

    public final String[] specNames;

    public final String color;

    public static T2p1ColorEnum getByNameAndSort(String name, int sort) {
        for (T2p1ColorEnum value : values()) {
            //先使用名字
            for (String specName : value.specNames) {
                if (specName.equalsIgnoreCase(name)) {
                    return value;
                }
            }
        }
        for (T2p1ColorEnum value : values()) {
            //再使用位置
            if (value.sort == sort) {
                return value;
            }
        }
        return BASE;
    }
}
