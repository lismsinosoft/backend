package com.gfk.common.ga.engine.provided;

import com.gfk.common.ga.engine.core.builders.ValidatorFilterBuilder;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmptyValidatorFilterBuilder implements ValidatorFilterBuilder {
    @Override
    public Tuple2<Map<String, Object>, List<Tuple2<String, String>>> build() {
        Map<String, Object> objConfigs = buildObjectAndConfigs();
        List<Tuple2<String, String>> fitnessConfigs = buildValidatorFilterConfigs();

        return Tuples.of(objConfigs, fitnessConfigs);
    }

    private Map<String, Object> buildObjectAndConfigs() {

        Map<String, Object> configs = new HashMap<>();

        return configs;
    }

    private List<Tuple2<String, String>> buildValidatorFilterConfigs() {
        List<Tuple2<String, String>> validatorFilters = new ArrayList<>();

        return validatorFilters;
    }
}
