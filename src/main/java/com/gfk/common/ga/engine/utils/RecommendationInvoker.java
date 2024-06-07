package com.gfk.common.ga.engine.utils;

import com.gfk.common.ga.engine.core.AbstractSolution;
import reactor.util.function.Tuple2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class RecommendationInvoker {
    public static double invoke(AbstractSolution solution, Tuple2<Map<String, Object>, Tuple2<String, String>> recommendationInfo) {
        Map<String, Object> objs = recommendationInfo.getT1();
        Tuple2<String, String> definations = recommendationInfo.getT2();
        Object obj = objs.get(definations.getT1());
        String methodId = definations.getT2();

        Method mtd = null;
        try {
            mtd = obj.getClass().getMethod(methodId, AbstractSolution.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        double result = 0;
        try {
            result = (double) mtd.invoke(obj, solution);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
