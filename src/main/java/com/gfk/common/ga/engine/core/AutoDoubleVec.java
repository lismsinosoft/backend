package com.gfk.common.ga.engine.core;

import io.jenetics.ext.moea.ElementComparator;
import io.jenetics.ext.moea.ElementDistance;
import io.jenetics.ext.moea.Vec;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

public class AutoDoubleVec implements Vec<double[]>, Serializable {
    private static final long serialVersionUID = 1L;

    private final double[] _data;

    public AutoDoubleVec(final double[] data) {
        _data = data;
    }

    @Override
    public double[] data() {
        return _data;
    }

    @Override
    public int length() {
        return _data.length;
    }

    @Override
    public ElementComparator<double[]> comparator() {
        return (u, v, i) -> Double.compare(u[i], v[i]);
    }

    @Override
    public ElementDistance<double[]> distance() {
        return (u, v, i) -> u[i] - v[i];
    }

    @Override
    public Comparator<double[]> dominance() {
        return Vec::dominance;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(_data);
    }

    @Override
    public boolean equals(final Object obj) {
        return obj == this ||
                obj instanceof AutoDoubleVec &&
                        Arrays.equals(((AutoDoubleVec) obj)._data, _data);
    }

    @Override
    public String toString() {
        return Arrays.toString(_data);
    }

}
