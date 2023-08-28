package com.darjow.framework.teleportation;

import org.dreambot.api.methods.map.Area;

import java.util.HashMap;

public abstract class TeleportationMethod {


    protected String name;
    protected Area destination;
    protected TeleportationType type;


    public TeleportationMethod(String name, Area destination, TeleportationType type){
        this.name = name;
        this.destination = destination;
        this.type = type;
    }


    public Area getDestination(){
        return destination;
    }
    public String getName(){
        return name;
    }
    public abstract boolean hasTimerActive();
    public TeleportationType getTeleportationType(){return type;}
    public abstract void teleport();
    public abstract boolean canExecute();
    public abstract HashMap<String, Integer> getRequiredItems();



}