package com.gfk.common.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 数组数学操作
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
public class MathArrayUtils {

    /**
     * Discription: 笛卡尔乘积算法
     *
     * @param dimensionValue
     *              原List
     *
     * @return 通过乘积转化后的数组
     */
    public static<T> List<List<T>> descartes(List<List<T>> dimensionValue) {
        List result = new LinkedList<>();
        descartes(dimensionValue, result, 0 , new LinkedList<T>());
        return result;
    }

    /**
     * https://blog.csdn.net/qq_33475202/article/details/91970636
     * Discription: 笛卡尔乘积算法
     * 把一个List{[1,2],[A,B],[a,b]} 转化成
     * List{[1,A,a],[1,A,b],[1,B,a],[1,B,b],[2,A,a],[2,A,b],[2,B,a],[2,B,b]} 数组输出
     *
     * @param dimensionValue
     *              原List
     * @param result
     *              通过乘积转化后的数组
     * @param layer
     *              中间参数
     * @param currentList
     *               中间参数
     */
    public static<T> void descartes(List<List<T>> dimensionValue, List<List<T>> result, int layer, List<T> currentList) {
        if (layer < dimensionValue.size() - 1) {
            if (dimensionValue.get(layer).size() == 0) {
                descartes(dimensionValue, result, layer + 1, currentList);
            } else {
                for (int i = 0; i < dimensionValue.get(layer).size(); i++) {
                    List<T> list = new ArrayList<T>(currentList);
                    list.add(dimensionValue.get(layer).get(i));
                    descartes(dimensionValue, result, layer + 1, list);
                }
            }
        } else if (layer == dimensionValue.size() - 1) {
            if (dimensionValue.get(layer).size() == 0) {
                result.add(currentList);
            } else {
                for (int i = 0; i < dimensionValue.get(layer).size(); i++) {
                    List<T> list = new ArrayList<T>(currentList);
                    list.add(dimensionValue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }

}
