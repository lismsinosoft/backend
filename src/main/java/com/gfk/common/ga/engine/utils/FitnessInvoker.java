package com.gfk.common.ga.engine.utils;

import com.gfk.common.ga.engine.converters.SolutionConverter;
import com.gfk.common.ga.engine.core.AbstractSolution;
import com.gfk.common.ga.engine.core.AdaptiveMutator;
import com.gfk.common.ga.engine.core.AutoDoubleVec;
import com.gfk.common.ga.engine.core.EvolvingSolutionInfo;
import io.jenetics.Genotype;
import io.jenetics.IntegerGene;
import io.jenetics.ext.moea.Vec;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.StringUtils;
import reactor.util.function.Tuple3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FitnessInvoker {
    public static Vec<double[]> invoke(AdaptiveMutator.MutatorUpdator mutatorUpdator, MinMaxScaler minMaxScaler, SolutionConverter solutionConverter, Genotype<IntegerGene> map, Map<String, Object> objConfigs, List<Tuple3<String, String, Double>> fitnessConfigs) {
        List<Double> fitness_list = new ArrayList<>();

        EvolvingSolutionInfo info = EvolvingSolutionInfo.of(map);
        AbstractSolution solution = solutionConverter.convert2Solution(info);

        //增加此处
        for (Tuple3<String, String, Double> item : fitnessConfigs) {
            String objKey = item.getT1();
            String fitnessIdentity = item.getT2();
            double weight = item.getT3();

            double score = 0;
            for (String functionName : fitnessIdentity.split(",")) {
                if (StringUtils.isEmpty(functionName)) {
                    continue;
                }

                functionName = functionName.trim();

                Object obj = objConfigs.get(objKey);
                Method fitnessMethod = null;
                try {
                    fitnessMethod = obj.getClass().getMethod(functionName, AbstractSolution.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                double result = 0;
                try {
                    result = (double) fitnessMethod.invoke(obj, solution);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                score += result;
            }

            double resultValue = score;

            String minMaxScalerId = objKey + "-" + fitnessIdentity;

            minMaxScaler.collect(minMaxScalerId, resultValue);

            resultValue = minMaxScaler.convert(minMaxScalerId, resultValue);

            fitness_list.add(weight * resultValue);
        }


        Double[] fitness_array = new Double[fitness_list.size()];
        fitness_array = fitness_list.toArray(fitness_array);

        Vec<double[]> fitnesses = new AutoDoubleVec(ArrayUtils.toPrimitive(fitness_array));

        mutatorUpdator.tick();

        return fitnesses;
    }
}
