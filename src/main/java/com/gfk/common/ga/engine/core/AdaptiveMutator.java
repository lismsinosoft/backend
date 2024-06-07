package com.gfk.common.ga.engine.core;


import io.jenetics.*;
import io.jenetics.internal.math.probability;
import io.jenetics.util.ISeq;
import io.jenetics.util.RandomRegistry;
import io.jenetics.util.Seq;

import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.String.format;

public class AdaptiveMutator<
        G extends Gene<?, G>,
        C extends Comparable<? super C>
        >
        implements Alterer<G, C> {
    private MutatorUpdator mutatorUpdator;

    public AdaptiveMutator(MutatorUpdator mutatorUpdator) {
        this.mutatorUpdator = mutatorUpdator;
    }

    /**
     * Concrete implementation of the alter method. It uses the following
     * mutation methods: {@link #mutate(Phenotype, long, double, Random)},
     * {@link #mutate(Genotype, double, Random)},
     * {@link #mutate(Chromosome, double, Random)}, {@link #mutate(Gene, Random)},
     * in this specific order.
     *
     * @see #mutate(Phenotype, long, double, Random)
     * @see #mutate(Genotype, double, Random)
     * @see #mutate(Chromosome, double, Random)
     * @see #mutate(Gene, Random)
     */
    @Override
    public AltererResult<G, C> alter(
            final Seq<Phenotype<G, C>> population,
            final long generation
    ) {
        assert population != null : "Not null is guaranteed from base class.";

        final Random random = RandomRegistry.getRandom();
        final double p = pow(mutatorUpdator.getProbability(), 1.0 / 3.0);
        final int P = probability.toInt(p);

        final Seq<MutatorResult<Phenotype<G, C>>> result = population
                .map(pt -> random.nextInt() < P
                        ? mutate(pt, generation, p, random)
                        : MutatorResult.of(pt));

        return AltererResult.of(
                result.map(MutatorResult::getResult).asISeq(),
                result.stream().mapToInt(MutatorResult::getMutations).sum()
        );
    }

    /**
     * Mutates the given phenotype.
     *
     * @param phenotype  the phenotype to mutate
     * @param generation the actual generation
     * @param p          the mutation probability for the underlying genetic objects
     * @param random     the random engine used for the phenotype mutation
     * @return the mutation result
     * @see #mutate(Genotype, double, Random)
     * @see #mutate(Chromosome, double, Random)
     * @see #mutate(Gene, Random)
     */
    protected MutatorResult<Phenotype<G, C>> mutate(
            final Phenotype<G, C> phenotype,
            final long generation,
            final double p,
            final Random random
    ) {
        MutatorResult<Genotype<G>> mutate = mutate(phenotype.getGenotype(), p, random);
        return MutatorResult.of(Phenotype.of(mutate.getResult(), generation), mutate.getMutations());
    }

    /**
     * Mutates the given genotype.
     *
     * @param genotype the genotype to mutate
     * @param p        the mutation probability for the underlying genetic objects
     * @param random   the random engine used for the genotype mutation
     * @return the mutation result
     * @see #mutate(Chromosome, double, Random)
     * @see #mutate(Gene, Random)
     */
    protected MutatorResult<Genotype<G>> mutate(
            final Genotype<G> genotype,
            final double p,
            final Random random
    ) {
        final int P = probability.toInt(p);
        final ISeq<MutatorResult<Chromosome<G>>> result = genotype.toSeq()
                .map(gt -> random.nextInt() < P
                        ? mutate(gt, p, random)
                        : MutatorResult.of(gt));

        return MutatorResult.of(
                Genotype.of(result.map(MutatorResult::getResult)),
                result.stream().mapToInt(MutatorResult::getMutations).sum()
        );
    }

    /**
     * Mutates the given chromosome.
     *
     * @param chromosome the chromosome to mutate
     * @param p          the mutation probability for the underlying genetic objects
     * @param random     the random engine used for the genotype mutation
     * @return the mutation result
     * @see #mutate(Gene, Random)
     */
    protected MutatorResult<Chromosome<G>> mutate(
            final Chromosome<G> chromosome,
            final double p,
            final Random random
    ) {
        final int P = probability.toInt(p);
        final ISeq<MutatorResult<G>> result = chromosome.stream()
                .map(gene -> random.nextInt() < P
                        ? MutatorResult.of(mutate(gene, random), 1)
                        : MutatorResult.of(gene))
                .collect(ISeq.toISeq());

        return MutatorResult.of(
                chromosome.newInstance(result.map(MutatorResult::getResult)),
                result.stream().mapToInt(MutatorResult::getMutations).sum()
        );
    }

    /**
     * Mutates the given gene.
     *
     * @param gene   the gene to mutate
     * @param random the random engine used for the genotype mutation
     * @return the mutation result
     */
    protected G mutate(final G gene, final Random random) {
        return gene.newInstance();
    }

    @Override
    public String toString() {
        return format("%s[p=%f]", getClass().getSimpleName(), mutatorUpdator.getProbability());
    }

    public static class MutatorUpdator {
        private double probability;
        private double probability_old;
        private long cycleTicks = 0;
        private boolean enabled;

        public MutatorUpdator(double probability) {
            this(false, probability);
        }

        public MutatorUpdator(boolean enabled, double probability) {
            this.probability = probability;
            this.probability_old = probability;
            this.enabled = enabled;
        }

        public double getProbability() {
            if (!this.enabled) {
                return this.probability_old;
            }

            return this.probability;
        }

        public void tick() {
            if (!this.enabled) {
                return;
            }
            this.cycleTicks++;
            if (this.cycleTicks == 10000) {
                this.cycleTicks = 0;
                this.probability -= 0.01;
                if (this.probability <= 0) {
                    this.probability = this.probability_old;
                }
            }

            if (cycleTicks % 1000 == 0) {
                System.out.println("MutatorUpdator: " + probability);
            }
        }
    }
}
