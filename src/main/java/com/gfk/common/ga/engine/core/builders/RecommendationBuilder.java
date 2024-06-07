package com.gfk.common.ga.engine.core.builders;

import reactor.util.function.Tuple2;

import java.util.Map;

public interface RecommendationBuilder {
    Tuple2<Map<String, Object>, Tuple2<String, String>> build();
}
