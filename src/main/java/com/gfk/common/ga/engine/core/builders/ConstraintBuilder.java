package com.gfk.common.ga.engine.core.builders;

import io.jenetics.IntegerGene;
import io.jenetics.engine.Constraint;
import io.jenetics.ext.moea.Vec;

public interface ConstraintBuilder {
    Constraint<IntegerGene, Vec<double[]>> build();
}
