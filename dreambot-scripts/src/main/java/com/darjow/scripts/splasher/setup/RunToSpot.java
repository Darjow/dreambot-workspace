package com.darjow.scripts.splasher.setup;

import com.darjow.framework.decisiontree.components.Leaf;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Logger;

import static com.darjow.framework.locations.Location.PORT_SARIM_SEAGULLS;


public class RunToSpot extends Leaf {

    public RunToSpot() {
        super("Running to port sarim");
    }

    @Override
    public boolean validate() {
        if(PORT_SARIM_SEAGULLS.getArea().contains(Players.getLocal())){
            return false;
        }
        return true;
    }

    @Override
    public void execute() {
        Logger.log("Executing RunToSpot");
        Walking.walk(PORT_SARIM_SEAGULLS.getArea().getRandomTile());

    }


}
