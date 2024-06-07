package com.gfk.common.ga.engine.provided;

import com.gfk.common.ga.engine.core.builders.FitnessBuilder;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmptyFitnessBuilder implements FitnessBuilder {
    @Override
    public Tuple2<Map<String, Object>, List<Tuple3<String, String, Double>>> build() {

        Map<String, Object> objConfigs = buildObjectAndConfigs();
        List<Tuple3<String, String, Double>> fitnessConfigs = buildFitnessConfigs();

        return Tuples.of(objConfigs, fitnessConfigs);
    }


    private Map<String, Object> buildObjectAndConfigs() {

        Map<String, Object> configs = new HashMap<>();

        return configs;
    }

    private List<Tuple3<String, String, Double>> buildFitnessConfigs() {
        List<Tuple3<String, String, Double>> fitnessConfigs = new ArrayList<>();

        return fitnessConfigs;
    }
}
