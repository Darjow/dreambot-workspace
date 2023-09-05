package com.darjow.framework.handlers.afk;

import com.darjow.framework.antiban.ABNumberGenerator;

public class AFKHandler {

    private long afkUntil;



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
        setTimeOut(ABNumberGenerator.generateRandomNumber(min, max, mean));
    }
    public void startAfk(int min, int max, int mean, DistributionType spread) {
        setTimeOut(ABNumberGenerator.generateRandomNumber(min, max, mean, spread));
    }



    private void setTimeOut(long number) {
        afkUntil = number  + System.currentTimeMillis();
    }
}

