package com.darjow.framework.handlers.afk;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;

public class AFKHandler {

    private long afkUntil;


    private RandomGenerator randomGenerator = new Well19937c();

    public AFKHandler(){
        this.afkUntil = System.currentTimeMillis();
    }


    public boolean isAfk(){
        return afkUntil > System.currentTimeMillis();
    }

    public long getAfkUntil(){
        return afkUntil;
    }

    public void startAfk(int min, int max, int mean){
        startAfk(min, max, mean, DistributionType.UNIFORM);
    }
    public void startAfk(int min, int max, int mean, DistributionType spread) {
        double adjustedMean = mean;
        double skewFactor = generateSkewFactor(spread);

        switch (spread) {
            case LEFT_SIDED:
                adjustedMean = mean - skewFactor * (mean - min);
                break;
            case RIGHT_SIDED:
                adjustedMean = mean + skewFactor * (max - mean);
                break;
            default:
        }

        NormalDistribution distribution = new NormalDistribution(randomGenerator, adjustedMean, generateSD(min, max, spread));
        setTimeOut(distribution, min, max);
    }

    private double generateSkewFactor(DistributionType spread) {
        switch (spread) {
            case LEFT_SIDED:
                return Math.random() * 0.5;
            case RIGHT_SIDED:
                return 0.5 + Math.random() * 0.5;
            default:
                return 0.0;
        }
    }

    private double generateSD(int min, int max, DistributionType spread) {
        double variance;
        double skewFactor = generateSkewFactor(spread);

        switch (spread) {
            case LEFT_SIDED:
                variance = Math.pow(max - min, 2) * (1 - skewFactor) / 12;
                break;
            case RIGHT_SIDED:
                variance = Math.pow(max - min, 2) * skewFactor / 12;
                break;
            default:
                variance = Math.pow(max - min, 2) / 12;
        }

        return Math.sqrt(variance);
    }

    private void setTimeOut(NormalDistribution distribution, int min, int max) {
        int generatedNumber;

        do {
            generatedNumber = (int) Math.round(distribution.sample());
        } while (generatedNumber < min || generatedNumber > max);

        afkUntil = generatedNumber * 1000 + System.currentTimeMillis();
    }
}

