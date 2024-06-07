package com.gfk.common.ga.engine.core.builders;

import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.util.List;
import java.util.Map;

public interface FitnessBuilder {
    Tuple2<Map<String, Object>, List<Tuple3<String, String, Double>>> build();
}
