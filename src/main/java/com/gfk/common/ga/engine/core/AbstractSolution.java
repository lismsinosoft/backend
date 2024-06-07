package com.gfk.common.ga.engine.core;

import io.jenetics.ext.moea.Vec;
import lombok.Data;

@Data
public abstract class AbstractSolution {
    private Vec<double[]> fitnessValues;
}
