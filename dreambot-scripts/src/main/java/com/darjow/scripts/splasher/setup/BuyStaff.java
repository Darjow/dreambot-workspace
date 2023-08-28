package com.darjow.scripts.splasher.setup;

import com.darjow.framework.decisiontree.components.CompletableLeaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.items.Item;

import static com.darjow.framework.locations.Location.DRAYNOR_VILLAGE_MARKET;
import static com.darjow.scripts.splasher.util.Constants.ItemIDs.COINS;

public class BuyStaff extends CompletableLeaf {

    public BuyStaff() {
        super("Buying a staff");
    }

    @Override
    public boolean validate() {
        if(completed){
            return false;
        }

        if(!DRAYNOR_VILLAGE_MARKET.getArea().contains(Players.getLocal())){
            return false;
        }
        Item coins = Inventory.get(COINS);

        if(coins == null || !coins.isValid()){
            Logger.error("Out of coins, can't buy staff.");
            ScriptManager.getScriptManager().stop();
        }
        if(coins.getAmount() < 5000){
            Logger.error("Out of coins can't buy staff.");
            ScriptManager.getScriptManager().stop();
        }
        return true;
    }

    @Override
    public void execute() {

        // ...



    }
}
