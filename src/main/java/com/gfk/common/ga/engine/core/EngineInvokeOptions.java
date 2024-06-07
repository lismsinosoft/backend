package com.gfk.common.ga.engine.core;

import com.gfk.common.ga.engine.core.builders.ConstraintBuilder;
import com.gfk.common.ga.engine.core.builders.FitnessBuilder;
import com.gfk.common.ga.engine.core.builders.RecommendationBuilder;
import com.gfk.common.ga.engine.core.builders.ValidatorFilterBuilder;
import com.gfk.common.ga.engine.provided.EmptyConstraintBuilder;
import com.gfk.common.ga.engine.provided.EmptyFitnessBuilder;
import com.gfk.common.ga.engine.provided.EmptyRecommendationBuilder;
import com.gfk.common.ga.engine.provided.EmptyValidatorFilterBuilder;
import lombok.Data;


@Data
public class EngineInvokeOptions {
    private double mutation;
    private boolean enabledAdaptiveMutation;
    private double cross;
    private boolean enableSteadyFitness;
    private int steadyFitnessValue;
    private boolean enableDurationSeconds;
    private int maxDurationSeconds;
    private boolean enableMaxLimits;
    private int maxLimits;
    private int paretoSetRangeFrom;
    private int paretoSetRangeTo;
    private int tournamentSelectorSampleSize;
    private int populationSize;

    private FitnessBuilder fitnessBuilder = new EmptyFitnessBuilder();
    private ConstraintBuilder constraintBuilder = new EmptyConstraintBuilder();
    private RecommendationBuilder recommendationBuilder = new EmptyRecommendationBuilder();
    private ValidatorFilterBuilder validatorFilterBuilder = new EmptyValidatorFilterBuilder();

    public EngineInvokeOptions() {
        this.setEnabledAdaptiveMutation(false);
        this.setEnableDurationSeconds(false);
        this.setEnableSteadyFitness(false);
        this.setEnableMaxLimits(false);
        this.setCross(0.1);
        this.setMutation(0.01);
        this.setTournamentSelectorSampleSize(200);
        this.setParetoSetRangeFrom(1);
        this.setParetoSetRangeTo(20);
    }
}
