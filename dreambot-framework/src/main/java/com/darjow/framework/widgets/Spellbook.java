package com.darjow.framework.widgets;

import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.utilities.Sleep;

public class Spellbook {

    public static void open(){
        while(Tabs.getOpen() != Tab.MAGIC){
            if(Tabs.open(Tab.MAGIC)){
                Sleep.sleepUntil(() -> Tabs.getOpen() == Tab.MAGIC, 500);
            }
        }
    }
}
