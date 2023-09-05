package branches.setup.leaves;

import com.darjow.framework.decisiontree.components.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.utilities.Logger;

import static util.Constants.ItemIDs.CURSED_GOBLIN_STAFF;

public class EquipStaff extends Leaf {

    public EquipStaff() {}

    @Override
    public boolean validate() {
        return Inventory.contains(CURSED_GOBLIN_STAFF);
    }

    @Override
    public void execute() {
        Logger.info("Equipping staff");
       Inventory.get(CURSED_GOBLIN_STAFF).interact();
    }

    @Override
    public String getStatus() {
        return "[Leaf] Equipping staff.";
    }
}
