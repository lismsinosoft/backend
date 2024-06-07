package com.gfk.common.ga.engine.provided;

import io.jenetics.IntegerGene;
import io.jenetics.Phenotype;
import io.jenetics.engine.Constraint;
import io.jenetics.ext.moea.Vec;


public class EmptyGeneConstraintor implements Constraint<IntegerGene, Vec<double[]>> {
    @Override
    public boolean test(Phenotype<IntegerGene, Vec<double[]>> individual) {
        return true;
    }


    @Override
    public Phenotype<IntegerGene, Vec<double[]>> repair(Phenotype<IntegerGene, Vec<double[]>> individual, long generation) {
        return null;
    }
}
