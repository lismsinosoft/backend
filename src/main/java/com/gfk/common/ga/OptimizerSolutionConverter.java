package com.gfk.common.ga;


import com.gfk.business.data.model.bo.OptimizerDataCacheBO;
import com.gfk.business.data.model.bo.OptimizerDataContainerBO;
import com.gfk.common.ga.customized.CustomizedIntegerChromosome;
import com.gfk.common.ga.engine.converters.DefaultSolutionConverter;
import com.gfk.common.ga.engine.core.AbstractSolution;
import com.gfk.common.ga.engine.core.DefaultSolution;
import com.gfk.common.ga.engine.core.EvolvingSolutionInfo;
import io.jenetics.Chromosome;
import io.jenetics.Genotype;
import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OptimizerSolutionConverter extends DefaultSolutionConverter {

    private OptimizerDataContainerBO container;

    public void setDataContainer(OptimizerDataContainerBO dataContainer) {
        this.container = dataContainer;
    }

    @Override
    public AbstractSolution convert2Solution(EvolvingSolutionInfo solutionInfo) {
        Demo1Solution solution = new Demo1Solution();
        solution.setFitnessValues(solutionInfo.getFitnessValues());
        solution.setTarget(this.container.getTarget());

        Genotype<IntegerGene> geneMap = solutionInfo.getMap();

//        for (Chromosome<IntegerGene> integerGenes : geneMap) {
//            solution.getEntities().add(integerGenes.getGene().intValue());
//        }
        List<OptimizerDataCacheBO> dataList = container.getDataContainer();
        for (int i = 0; i < geneMap.length(); i++) {
            Chromosome<IntegerGene> integerGenes = geneMap.get(i);
            OptimizerDataCacheBO cacheBO = dataList.get(i);
            SolutionEntity solutionEntity = new SolutionEntity();
            solutionEntity.setName(cacheBO.getName());
            solutionEntity.setSpending(integerGenes.getGene().longValue());
            Long revenue = cacheBO.getCachedData().computeIfAbsent(solutionEntity.getSpending(), cacheBO::calculateRevenue);
            solutionEntity.setRevenue(revenue);
            solution.getEntities().add(solutionEntity);
        }
//		Chromosome<IntegerGene> chromosome=geneMap.getChromosome();
//
//		for(int idx=0;idx<chromosome.length();idx++) {
//			IntegerGene integerGene=chromosome.getGene(idx);
//			if(integerGene.intValue()==1) {
//				solution.getSelectedIds().add(idx);
//			}
//		}

        return solution;
    }

    @Override
    public Genotype<IntegerGene> loadGenotype() {
        List<OptimizerDataCacheBO> dataList = this.container.getDataContainer();
        IntegerChromosome[] array = dataList.stream()
//                .map(data -> IntegerChromosome.of(data.getMinData().intValue(), data.getMaxData().intValue(), IntRange.of(1)))
                .map(data -> CustomizedIntegerChromosome.customize(data.getMinData().intValue(), data.getMaxData().intValue(), data.getGranularity()))
                .toArray(IntegerChromosome[]::new);
        return dataList.size() > 1
                ? Genotype.of(array[0], Arrays.copyOfRange(array, 1, array.length))
                : Genotype.of(array[0]);
    }

    public double test(AbstractSolution solution) {
        Demo1Solution s = (Demo1Solution) solution;
//        Integer score = s.getSelectedIds().stream().reduce(Integer::sum).orElse(0);
//        List<Integer> selectedIds = s.getEntities();
//        for (int i = 0; i < selectedIds.size(); i++) {
//            OptimizerDataCacheBO cacheBO = dataContainer.get(i);
//            Integer revenue = cacheBO.getCachedData().computeIfAbsent(selectedIds.get(i), cacheBO::calculateRevenue);
//            score += revenue;
//        }
        Long spending = s.getEntities().stream().map(SolutionEntity::getSpending).reduce(Long::sum).orElse(0L);
        Long target = s.getTarget();
        // 超额惩罚
        if (null != target && spending > target) {
            return -1;
        }
        return s.getEntities().stream().map(SolutionEntity::getRevenue).reduce(Long::sum).orElse(0L);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Demo1Solution extends DefaultSolution {

        private List<SolutionEntity> entities = new ArrayList<>();

        private Long target;

        @Override
        public String toString() {
            return entities.stream().map(Object::toString).collect(Collectors.joining(","));
        }
    }

    @Data
    @ToString
    public static class SolutionEntity {
        private String name;

        private Long spending;

        private Long revenue;
    }
}
