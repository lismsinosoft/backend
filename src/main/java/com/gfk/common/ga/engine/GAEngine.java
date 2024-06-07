package com.gfk.common.ga.engine;

import com.gfk.common.ga.engine.converters.SolutionConverter;
import com.gfk.common.ga.engine.core.AbstractSolution;
import com.gfk.common.ga.engine.core.AdaptiveMutator;
import com.gfk.common.ga.engine.core.EngineInvokeOptions;
import com.gfk.common.ga.engine.core.builders.ConstraintBuilder;
import com.gfk.common.ga.engine.core.builders.FitnessBuilder;
import com.gfk.common.ga.engine.core.builders.RecommendationBuilder;
import com.gfk.common.ga.engine.core.builders.ValidatorFilterBuilder;
import com.gfk.common.ga.engine.utils.FitnessInvoker;
import com.gfk.common.ga.engine.utils.MinMaxScaler;
import io.jenetics.*;
import io.jenetics.engine.Constraint;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStream;
import io.jenetics.engine.Limits;
import io.jenetics.ext.moea.MOEA;
import io.jenetics.ext.moea.UFTournamentSelector;
import io.jenetics.ext.moea.Vec;
import io.jenetics.util.ISeq;
import io.jenetics.util.IntRange;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;


public class GAEngine {
    private final int POPULATION_SIZE;

    private ExecutorService executor = null;

    public GAEngine(int POPULATION_SIZE, ExecutorService executor) {
        this.POPULATION_SIZE = POPULATION_SIZE;
        this.executor = executor;
    }

    public AbstractSolution generate(SolutionConverter solutionConverter,
                                     MinMaxScaler minMaxScaler,
                                     EngineInvokeOptions options) {
        Genotype<IntegerGene> genotype = solutionConverter.loadGenotype();
        FitnessBuilder fitnessBuilder = options.getFitnessBuilder();
        ValidatorFilterBuilder validatorFilterBuilder = options.getValidatorFilterBuilder();
        RecommendationBuilder recommendationBuilder = options.getRecommendationBuilder();
        ConstraintBuilder constraintBuilder = options.getConstraintBuilder();

        Tuple2<Map<String, Object>, List<Tuple3<String, String, Double>>> fitnessInfo = fitnessBuilder.build();
        Tuple2<Map<String, Object>, List<Tuple2<String, String>>> validatorFilters = validatorFilterBuilder.build();
        Tuple2<Map<String, Object>, Tuple2<String, String>> recommendationInfo = recommendationBuilder.build();
        Constraint<IntegerGene, Vec<double[]>> phenotypeConstraint = constraintBuilder.build();

        AdaptiveMutator.MutatorUpdator mutatorUpdator = new AdaptiveMutator.MutatorUpdator(options.isEnabledAdaptiveMutation(), options.getMutation());

        final Engine<IntegerGene, Vec<double[]>> engine =
                Engine.builder(v -> FitnessInvoker.invoke(mutatorUpdator, minMaxScaler, solutionConverter, v, fitnessInfo.getT1(), fitnessInfo.getT2()), genotype)
                        .populationSize(POPULATION_SIZE)
                        .alterers(
                                new AdaptiveMutator<>(mutatorUpdator),
                                new UniformCrossover<>(options.getCross())
                        )
                        .offspringSelector(new TournamentSelector<>(options.getTournamentSelectorSampleSize()))
                        .survivorsSelector(UFTournamentSelector.ofVec())
                        .optimize(Optimize.MAXIMUM)
                        .constraint(phenotypeConstraint)
                        .executor(executor)
                        .build();


        EvolutionStream<IntegerGene, Vec<double[]>> result = engine.stream();

        if (options.isEnableSteadyFitness()) {
            result = result.limit(Limits.bySteadyFitness(options.getSteadyFitnessValue()));
        }
        if (options.isEnableDurationSeconds()) {
            result = result.limit(Limits.byExecutionTime(Duration.ofSeconds(options.getMaxDurationSeconds())));
        }

        ISeq<Phenotype<IntegerGene, Vec<double[]>>> paretoSet;
        if (options.isEnableMaxLimits()) {
            paretoSet = result.limit(options.getMaxLimits()).collect(MOEA.toParetoSet(IntRange.of(options.getParetoSetRangeFrom(), options.getParetoSetRangeTo())));
        } else {
            paretoSet = result.collect(MOEA.toParetoSet(IntRange.of(options.getParetoSetRangeFrom(), options.getParetoSetRangeTo())));
        }
        List<Phenotype<IntegerGene, Vec<double[]>>> algo_results = paretoSet.stream().collect(Collectors.toList());

        //转换到业务表示
        List<AbstractSolution> solutions = solutionConverter.filterSolutions(algo_results, validatorFilters);
        AbstractSolution solution = solutionConverter.chooseSolution(solutions, recommendationInfo);

        return solution;
    }
}
