package com.rbc.Utils;

import java.util.HashMap;

/**
 * Created by yuwang on 5/23/17.
 */
public class RandomNumberGenerator {

    private HashMap<String, Double> distribution;
    private double distSum;

    public RandomNumberGenerator() {
        distribution = new HashMap<>();
    }

    public void addNumber(String value, double distribution) {
        if (this.distribution.get(value) != null) {
            distSum -= this.distribution.get(value);
        }
        this.distribution.put(value, distribution);
        distSum += distribution;
    }

    public String getDistributedRandomNumber() {
        double rand = Math.random();
        double ratio = 1.0f / distSum;
        double tempDist = 0;
        for (String i : distribution.keySet()) {
            tempDist += distribution.get(i);
            if (rand / ratio <= tempDist) {
                return i;
            }
        }
        return null;
    }

}