package com.gfk.common.ga.engine.utils;

import java.util.HashMap;
import java.util.Map;

public class MinMaxScaler {
    private double maxValue;
    private boolean enabled = true;
    private Map<String, Double> collectedMaxData = new HashMap<>();

    public MinMaxScaler(double max, boolean enabled) {
        this.maxValue = max;
        this.enabled = enabled;
    }

    public MinMaxScaler(double max) {
        this.maxValue = max;
        this.enabled = true;
    }

    public synchronized void collect(String minMaxScalerId, double resultValue) {
        if (collectedMaxData.containsKey(minMaxScalerId)) {
            if (resultValue > collectedMaxData.get(minMaxScalerId)) {
                collectedMaxData.put(minMaxScalerId, resultValue);
            }
        } else {
            collectedMaxData.put(minMaxScalerId, resultValue);
        }
    }

    public synchronized double convert(String minMaxScalerId, double resultValue) {
        if (!enabled) {
            return resultValue;
        }

        double maxValue = collectedMaxData.get(minMaxScalerId);
        return this.maxValue * (resultValue / maxValue);
    }
}
