package com.gfk.common.ga.engine.provided;

import com.gfk.common.ga.engine.core.builders.ConstraintBuilder;
import io.jenetics.IntegerGene;
import io.jenetics.engine.Constraint;
import io.jenetics.ext.moea.Vec;

public class EmptyConstraintBuilder implements ConstraintBuilder {
    @Override
    public Constraint<IntegerGene, Vec<double[]>> build() {
        EmptyGeneConstraintor constraint = new EmptyGeneConstraintor();
        return constraint;
    }
}
