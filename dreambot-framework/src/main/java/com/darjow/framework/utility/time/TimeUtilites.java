package com.darjow.framework.utility.time;

public class TimeUtilites {
    private final long startTime;

    public TimeUtilites(){
        startTime = System.currentTimeMillis();
    }

    public String formatTime(){
        long millis = System.currentTimeMillis() - startTime;
        int sec  = (int)(millis/ 1000) % 60 ;
        int min  = (int)((millis/ (1000*60)) % 60);
        int hr   = (int)((millis/ (1000*60*60)) % 24);

        return String.format("%02d:%02d:%02d", hr, min, sec);
    }
}
