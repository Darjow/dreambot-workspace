package com.darjow.framework.handlers.afk;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.Well19937c;
import org.dreambot.api.methods.Calculations;

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

    public void startAfk(int min, int max){
        NormalDistribution distribution = new NormalDistribution(randomGenerator, (min + max ) / 2, generateRandomSD());
        setTimeOut(distribution);

    }
    public void startAfk(int min, int max, DistributionType type){
        NormalDistribution distribution = new NormalDistribution(randomGenerator, (min + max ) / 2, generateRandomSD(type));
        setTimeOut(distribution);

    }
    public void startAfk(int min, int max, int mean, int sd) {
        NormalDistribution distribution = new NormalDistribution(randomGenerator, mean, sd);
        setTimeOut(distribution);
    }

    public void startAfk(int min, int max, int mean, DistributionType type){
        NormalDistribution distribution = new NormalDistribution(randomGenerator, mean, generateRandomSD(type));
        setTimeOut(distribution);

    }
    public void startAfk(int min, int max, int mean){
        NormalDistribution distribution = new NormalDistribution(randomGenerator, mean, generateRandomSD());
        setTimeOut(distribution);
    }

    private int generateRandomSD(){
        return Calculations.random(1,80);
    }

    private int generateRandomSD(DistributionType type){
        switch(type){
            case UNIFORM: return 30 - Calculations.random(0,5);
            case MOSTLY_SPREAD: return 25 - Calculations.random(0,5);
            case LITTLE_SPREAD: return 5 + Calculations.random(0,20);
            case DENSE: return Calculations.random(1,5);
            default:
                throw new IllegalArgumentException();

        }
    }
    private void setTimeOut(NormalDistribution distribution) {
        int generatedNumber = (int) Math.round(distribution.sample());
        afkUntil = generatedNumber + System.currentTimeMillis();
    }

}
