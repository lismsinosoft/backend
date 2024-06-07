package com.gfk.common.ga.customized;

import io.jenetics.IntegerChromosome;
import io.jenetics.IntegerGene;
import io.jenetics.internal.math.random;
import io.jenetics.util.ISeq;
import io.jenetics.util.IntRange;
import io.jenetics.util.MSeq;

import java.util.Random;

import static io.jenetics.util.RandomRegistry.getRandom;

/**
 * @author wzl
 * @version 1.0 2024/2/19
 */
public class CustomizedIntegerChromosome {
    public static IntegerChromosome customize(final int min,
                                              final int max,
                                              final int granularity) {
        int calMin = min % granularity == 0 ? min / granularity : (min / granularity) + 1;
        int calMax = max % granularity == 0 ? (max / granularity) + 1 : max / granularity;
        final Random r = getRandom();
        IntRange lengthRange = IntRange.of(1);
        final ISeq<IntegerGene> values = MSeq.<IntegerGene>ofLength(random.nextInt(lengthRange, r))
                .fill(() -> IntegerGene.of((r.nextInt(calMax - calMin) + calMin) * granularity, min, max))
                .toISeq();
        return IntegerChromosome.of(values);
    }

    public static void main(String[] args) {
        final Random r = getRandom();
        System.out.println(r.nextInt(100));
    }
}
