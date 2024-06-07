package com.gfk.common.ga.engine.converters;

import com.gfk.common.ga.engine.core.AbstractSolution;
import com.gfk.common.ga.engine.core.EvolvingSolutionInfo;
import io.jenetics.Chromosome;
import io.jenetics.Genotype;
import io.jenetics.IntegerGene;
import io.jenetics.Phenotype;
import io.jenetics.ext.moea.Vec;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.Map;


public interface SolutionConverter {
    AbstractSolution convert2Solution(EvolvingSolutionInfo solutionInfo);

    List<AbstractSolution> filterSolutions(List<Phenotype<IntegerGene, Vec<double[]>>> algo_results, Tuple2<Map<String, Object>, List<Tuple2<String, String>>> validatorFilters);

    AbstractSolution chooseSolution(List<AbstractSolution> solutions, Tuple2<Map<String, Object>, Tuple2<String, String>> recommendationInfo);

    Phenotype<IntegerGene, Vec<double[]>> convert2Individual(int geneSize, int totalBoardSize, AbstractSolution solution, long generation, Chromosome<IntegerGene> boardChromosome);

    Genotype<IntegerGene> loadGenotype();
}
