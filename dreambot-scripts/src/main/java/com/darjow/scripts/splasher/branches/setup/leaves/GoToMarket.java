package com.darjow.scripts.splasher.branches.setup.leaves;

import com.darjow.framework.decisiontree.components.Leaf;
import com.darjow.framework.teleportation.TeleportationMethod;
import com.darjow.framework.teleportation.implementation.LumbridgeTeleport;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.items.Item;

import static com.darjow.framework.locations.Location.DRAYNOR_VILLAGE_MARKET;
import static com.darjow.scripts.splasher.util.Constants.ItemIDs.CURSED_GOBLIN_STAFF;

public class GoToMarket extends Leaf {

    private TeleportationMethod lumbridgeTp;

    public GoToMarket(){
        super("Going to draynor village");
        this.lumbridgeTp = new LumbridgeTeleport();
    }

    @Override
    public boolean validate() {
        if(DRAYNOR_VILLAGE_MARKET.getArea().contains(Players.getLocal())){
            return false;
        }

        if(Inventory.contains(CURSED_GOBLIN_STAFF)){
            return false;
        }

        Item staff = Equipment.getItemInSlot(EquipmentSlot.WEAPON);

        if(staff != null && staff.isValid() && staff.getID() == CURSED_GOBLIN_STAFF){
            return false;
        }

        return true;
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
    }
}
