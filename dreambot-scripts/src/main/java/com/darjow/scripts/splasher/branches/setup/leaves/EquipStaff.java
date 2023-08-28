package com.darjow.scripts.splasher.branches.setup.leaves;

import com.darjow.framework.decisiontree.components.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.utilities.Logger;

import static com.darjow.scripts.splasher.util.Constants.ItemIDs.CURSED_GOBLIN_STAFF;

public class EquipStaff extends Leaf {

    public EquipStaff() {
        super("Equipping staff");
    }

    @Override
    public boolean validate() {
        return Inventory.contains(CURSED_GOBLIN_STAFF);
    }

    @Override
    public void execute() {
        Logger.info("Equipping staff");
       Inventory.get(CURSED_GOBLIN_STAFF).interact();
    }
}
