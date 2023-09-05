package branches.setup.leaves;

import com.darjow.framework.decisiontree.components.Leaf;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.Shop;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;

import static com.darjow.framework.enums.Location.DRAYNOR_VILLAGE_MARKET;
import static util.Constants.ItemIDs.COINS;
import static util.Constants.ItemIDs.CURSED_GOBLIN_STAFF;


public class BuyStaff extends Leaf {

    public BuyStaff() {    }

    @Override
    public boolean validate() {
        if(!DRAYNOR_VILLAGE_MARKET.getArea().contains(Players.getLocal())){
            return false;
        }
        if(Inventory.contains(CURSED_GOBLIN_STAFF)){
            return false;
        }
        Item staff = Equipment.getItemInSlot(EquipmentSlot.WEAPON);

        if(staff != null && staff.isValid() && staff.getID() == CURSED_GOBLIN_STAFF){
            return false;
        }

        Item coins = Inventory.get(COINS);

        if(coins == null || !coins.isValid()){
            Logger.error("Out of coins, can't buy staff.");
            ScriptManager.getScriptManager().stop();
        }
        if(coins.getAmount() < 50){
            Logger.error("Out of coins can't buy staff.");
            ScriptManager.getScriptManager().stop();
        }
        return true;
    }

    @Override
    public void execute() {
        if(!Shop.isOpen()){
            Logger.info("Opening the Diango shop");
            if(Shop.open("Diango")) {
                Sleep.sleepUntil(() -> Shop.isOpen(), Calculations.random(500, 1500));
            }
        }

        if(Shop.isOpen()) {
            Logger.info("Purchasing the staff");
            if(Shop.purchaseOne(CURSED_GOBLIN_STAFF)){
                Shop.close();
            }
        }
    }

    @Override
    public String getStatus() {
        return "[Leaf] Buying a staff.";
    }
}
