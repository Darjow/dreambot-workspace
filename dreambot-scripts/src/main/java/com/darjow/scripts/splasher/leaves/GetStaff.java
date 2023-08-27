package com.darjow.scripts.splasher.leaves;

import com.darjow.framework.decisiontree.components.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.items.Item;
import static com.darjow.scripts.splasher.util.Constants.ItemIDs.*;

public class GetStaff extends Leaf {


    @Override
    public boolean validate() {
        Logger.info("Validating GetStaff Leaf");

        if(Inventory.contains(CURSED_GOBLIN_STAFF)){
            return false;
        }
        Item weapon = Equipment.getItemInSlot(EquipmentSlot.WEAPON);
        if(weapon.isValid() && weapon.getID() == CURSED_GOBLIN_STAFF) {
            return false;
        }

        if(!Inventory.get(COINS).isValid()){
            Logger.error("Out of coins, can't buy staff.");
            ScriptManager.getScriptManager().stop();
        }
        if(Inventory.get(COINS).getAmount() < 5000){
            Logger.error("Out of coins can't buy staff.");
            ScriptManager.getScriptManager().stop();
        }

        return true;
    }

    @Override
    public void execute() {
        Logger.info("GetStaff is being executed.");

    }

}
