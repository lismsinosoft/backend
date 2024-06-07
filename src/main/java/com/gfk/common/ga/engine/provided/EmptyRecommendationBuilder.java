package com.gfk.common.ga.engine.provided;

import com.gfk.common.ga.engine.core.builders.RecommendationBuilder;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.HashMap;
import java.util.Map;

public class EmptyRecommendationBuilder implements RecommendationBuilder {
    private static Tuple2<String, String> buildConfigs() {
        return null;
    }

    @Override
    public Tuple2<Map<String, Object>, Tuple2<String, String>> build() {
        Map<String, Object> objConfigs = buildObjectAndConfigs();
        Tuple2<String, String> calcConfigs = buildConfigs();

        if (calcConfigs == null) {
            return null;
        }

        return Tuples.of(objConfigs, calcConfigs);
    }

    private Map<String, Object> buildObjectAndConfigs() {

        Map<String, Object> configs = new HashMap<>();

        return configs;
    }
}
