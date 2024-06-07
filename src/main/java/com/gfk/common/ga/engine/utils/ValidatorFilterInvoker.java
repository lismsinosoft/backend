package com.gfk.common.ga.engine.utils;

import com.gfk.common.ga.engine.core.AbstractSolution;
import com.gfk.common.ga.engine.core.ValidationResult;
import reactor.util.function.Tuple2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValidatorFilterInvoker {

    public static List<ValidationResult> invoke(AbstractSolution solution, Tuple2<Map<String, Object>, List<Tuple2<String, String>>> validatorFilters) {
        List<ValidationResult> validationResults = new ArrayList<>();

        Map<String, Object> objConfigs = validatorFilters.getT1();

        //增加此处
        for (Tuple2<String, String> item : validatorFilters.getT2()) {
            String objKey = item.getT1();
            String validatorIdentity = item.getT2();

            Object obj = objConfigs.get(objKey);
            Method validatorMethod = null;
            try {
                validatorMethod = obj.getClass().getMethod(validatorIdentity, AbstractSolution.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            ValidationResult result = null;
            try {
                result = (ValidationResult) validatorMethod.invoke(obj, solution);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            validationResults.add(result);
        }

        return validationResults;
    }
}
