package com.darjow.framework.antiban;

import com.darjow.framework.handlers.afk.DistributionType;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.dreambot.api.utilities.Logger;

public class ABNumberGenerator {

    private static RandomGenerator randomGenerator = new Well19937c();

    private static double generateSkewFactor(DistributionType spread) {
        switch (spread) {
            case LEFT_SIDED:
                return Math.random() * 0.5;
            case RIGHT_SIDED:
                return 0.5 + Math.random() * 0.5;
            default:
                return 0.0;
        }
    }

    private static double generateSD(int min, int max, DistributionType spread) {
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

    public static long generateRandomNumber(int min, int max, int mean, DistributionType spread){
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

        long generatedNumber = 0;
    do {
        Logger.warn("yoooooo");
        generatedNumber = Math.round(distribution.sample());
    } while (generatedNumber < min || generatedNumber > max);

        return generatedNumber;
    }

    public static long generateRandomNumber(int min, int max, int mean) {
        return generateRandomNumber(min, max, mean, DistributionType.UNIFORM);
    }

    public static int generateHumanLikeBuyOrderQuantity(int quantityNeeded){
        int[] roundingValues = {50, 100, 250, 500, 1000, 2500};
        int roundedNumber = roundUpToRandom(quantityNeeded, roundingValues);
        return roundedNumber;
    }

    private static int roundUpToRandom(int number, int[] roundingValues) {
        int oneTenth = number / 10;
        int closestValue = Integer.MAX_VALUE;

        for (int i = roundingValues.length - 1; i >= 0; i--) {
            if (roundingValues[i] <= oneTenth) {
                closestValue = roundingValues[i];
                break;
            }
        }

        if (closestValue != Integer.MAX_VALUE) {
            return (number + closestValue) / closestValue * closestValue;
        }

        return number;
    }

}
