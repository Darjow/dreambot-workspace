package com.darjow.framework.handlers.afk;

import java.util.SortedMap;
import java.util.TreeMap;

public class Test {
    public static void main(String[] args){
        var x = new AFKHandler();
        SortedMap<Integer, Integer> map = new TreeMap();


        for (int i = 0; i < 1000000; i++){
            int min = 120_000;
            int max = 1500_000;

            x.startAfk(min, max, 700_000, min /4 );
            int afkTime = (int) (x.getAfkUntil() - System.currentTimeMillis());
            int count = map.getOrDefault(afkTime, 0);
            map.put(afkTime, count +1);
        }

        map.forEach((k,v) -> System.out.printf("%d: %d%n", k/1000,v));
    }
}
