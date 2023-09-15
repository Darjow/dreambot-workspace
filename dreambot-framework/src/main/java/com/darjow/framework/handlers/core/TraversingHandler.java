package com.darjow.framework.handlers.core;

import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.interactive.Locatable;

public class TraversingHandler {

    private boolean isTraversing;
    private Locatable destination;

    public boolean isTraversing(){return this.isTraversing;}
    public boolean isAtDestination() {
        return this.destination.getZ() == Players.getLocal().getTile().getZ() && destination.walkingDistance(Players.getLocal().getTile()) < 10;
    }

    public void traverseToBank(){
        Logger.info("Going to bank.");
        destination = BankLocation.getNearest().getArea(10);
        traverse();
    }

    public void traverseToGe() {
        Logger.info("Going to GE.");
        destination = BankLocation.GRAND_EXCHANGE.getArea(15);
        traverse();
    }
    public void traverse(Locatable destination){
        this.destination = destination;
        traverse();
    }

    private void traverse(){
        Logger.info("Traversing ... ");
        isTraversing = true;
        Walking.walk(destination);

        if(isAtDestination()){
            isTraversing = false;
        }
    }

}
