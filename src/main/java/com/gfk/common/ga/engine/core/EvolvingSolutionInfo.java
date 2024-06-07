package com.gfk.common.ga.engine.core;

import io.jenetics.Genotype;
import io.jenetics.IntegerGene;
import io.jenetics.Phenotype;
import io.jenetics.ext.moea.Vec;

public class EvolvingSolutionInfo {

    private Genotype<IntegerGene> map;
    private Vec<double[]> fitnessValues;

    private EvolvingSolutionInfo(Phenotype<IntegerGene, Vec<double[]>> singleSolution) {
        this.map = singleSolution.getGenotype();
        this.fitnessValues = singleSolution.getFitness();
    }

    private EvolvingSolutionInfo(Genotype<IntegerGene> map) {
        this.map = map;
    }

    public static EvolvingSolutionInfo of(Genotype<IntegerGene> map) {
        return new EvolvingSolutionInfo(map);
    }

    public static EvolvingSolutionInfo of(Phenotype<IntegerGene, Vec<double[]>> singleSolution) {
        return new EvolvingSolutionInfo(singleSolution);
    }

    public Vec<double[]> getFitnessValues() {
        return this.fitnessValues;
    }

    public Genotype<IntegerGene> getMap() {
        return this.map;
    }
}
