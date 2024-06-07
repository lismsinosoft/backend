package com.gfk.common.ga.engine.converters;

import com.gfk.common.ga.engine.core.AbstractSolution;
import com.gfk.common.ga.engine.core.DefaultSolution;
import com.gfk.common.ga.engine.core.EvolvingSolutionInfo;
import com.gfk.common.ga.engine.core.ValidationResult;
import com.gfk.common.ga.engine.utils.RecommendationInvoker;
import com.gfk.common.ga.engine.utils.ValidatorFilterInvoker;
import io.jenetics.Chromosome;
import io.jenetics.IntegerGene;
import io.jenetics.Phenotype;
import io.jenetics.ext.moea.Vec;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class DefaultSolutionConverter implements SolutionConverter {
    @Override
    public AbstractSolution convert2Solution(EvolvingSolutionInfo solutionInfo) {
        DefaultSolution solution = new DefaultSolution();
        solution.setFitnessValues(solutionInfo.getFitnessValues());
        return solution;
    }

    @Override
    public Phenotype<IntegerGene, Vec<double[]>> convert2Individual(int geneSize, int totalBoardSize, AbstractSolution solution, long generation, Chromosome<IntegerGene> boardChromosome) {
        return null;
    }

    @Override
    public List<AbstractSolution> filterSolutions(List<Phenotype<IntegerGene, Vec<double[]>>> algo_results, Tuple2<Map<String, Object>, List<Tuple2<String, String>>> validatorFilters) {
        List<AbstractSolution> solutions = new ArrayList<>();
        if (algo_results == null || algo_results.size() == 0) {
            return solutions;
        }

        for (int resultIndex = 0; resultIndex < algo_results.size(); resultIndex++) {
            EvolvingSolutionInfo solutionInfo = EvolvingSolutionInfo.of(algo_results.get(resultIndex));

            AbstractSolution solution = convert2Solution(solutionInfo);

            solutions.add(solution);
        }


        List<AbstractSolution> finalSolutions = new ArrayList<>();

        for (AbstractSolution solution : solutions) {
            List<ValidationResult> validationResults = ValidatorFilterInvoker.invoke(solution, validatorFilters);

            if (failExists(validationResults)) {
                continue;
            }

            finalSolutions.add(solution);
        }

        return finalSolutions;
    }

    private boolean failExists(List<ValidationResult> validationResults) {
        if (validationResults == null || validationResults.size() == 0) {
            return false;
        }

        long errorCount = validationResults.stream().filter(validationResult -> !validationResult.isValid()).count();

        if (errorCount > 0) {
            return true;
        }

        return false;
    }

    @Override
    public AbstractSolution chooseSolution(List<AbstractSolution> solutions, Tuple2<Map<String, Object>, Tuple2<String, String>> recommendationInfo) {
        if (solutions == null || solutions.size() == 0) {
            return null;
        }

        AbstractSolution solution = null;

        if (recommendationInfo == null) {
            solution = solutions.get(0);
        } else {
            double minScore = 999999;
            for (AbstractSolution sl : solutions) {
                double currentScore = RecommendationInvoker.invoke(sl, recommendationInfo);
                if (currentScore < minScore) {
                    minScore = currentScore;
                    solution = sl;
                }
            }
        }

        return solution;
    }
}
