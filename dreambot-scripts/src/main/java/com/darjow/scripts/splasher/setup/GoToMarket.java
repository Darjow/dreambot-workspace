package com.darjow.scripts.splasher.setup;

import com.darjow.framework.decisiontree.components.CompletableLeaf;
import com.darjow.framework.teleportation.TeleportationMethod;
import com.darjow.framework.teleportation.implementation.LumbridgeTeleport;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;

import static com.darjow.framework.locations.Location.DRAYNOR_VILLAGE_MARKET;

public class GoToMarket extends CompletableLeaf {

    private TeleportationMethod lumbridgeTp;

    public GoToMarket(){
        super("Going to draynor village");
        this.lumbridgeTp = new LumbridgeTeleport();
    }

    @Override
    public boolean validate() {
        if(completed){
            return false;
        }

        return !DRAYNOR_VILLAGE_MARKET.getArea().contains(Players.getLocal());
    }

    @Override
    public void execute() {
        Logger.info("Running to the market.");

        if(Walking.walk(DRAYNOR_VILLAGE_MARKET.getArea())) {
            Logger.info("Walking to market");
        }else{
            Logger.info("We can't reach the market from our position.");

            if(lumbridgeTp.canExecute()){
                Logger.info("Executing lumbridge teleport.");
                lumbridgeTp.teleport();
            }else{
                Logger.error("We can't get to the destination ... script ending. ");
                ScriptManager.getScriptManager().stop();
            }
        }

        setCompleted(DRAYNOR_VILLAGE_MARKET.getArea().contains(Players.getLocal()));
    }
}
