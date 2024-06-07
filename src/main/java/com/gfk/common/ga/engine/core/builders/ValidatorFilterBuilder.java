package com.gfk.common.ga.engine.core.builders;

import reactor.util.function.Tuple2;

import java.util.List;
import java.util.Map;

public interface ValidatorFilterBuilder {
    Tuple2<Map<String, Object>, List<Tuple2<String, String>>> build();
}
