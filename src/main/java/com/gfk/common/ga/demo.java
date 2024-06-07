package com.gfk.common.ga;

import com.gfk.common.ga.engine.GAEngine;
import com.gfk.common.ga.engine.core.EngineInvokeOptions;
import com.gfk.common.ga.engine.utils.MinMaxScaler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class demo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        OptimizerSolutionConverter optimizerSolutionConverter = new OptimizerSolutionConverter();
        OptimizerFitnessBuilder fitnessBuilder = new OptimizerFitnessBuilder();

        EngineInvokeOptions options = new EngineInvokeOptions();
        options.setFitnessBuilder(fitnessBuilder);
        options.setEnableMaxLimits(true);
        options.setMaxLimits(1200);
//        options.setEnableSteadyFitness(true);
//        options.setSteadyFitnessValue(1000);


        MinMaxScaler minMaxScaler = new MinMaxScaler(100, false);

        GAEngine gaEngine = new GAEngine(100, executorService);

        OptimizerSolutionConverter.Demo1Solution solution = (OptimizerSolutionConverter.Demo1Solution) gaEngine.generate(optimizerSolutionConverter, minMaxScaler, options);
        System.out.println(solution.toString());


        executorService.shutdown();
    }
}
