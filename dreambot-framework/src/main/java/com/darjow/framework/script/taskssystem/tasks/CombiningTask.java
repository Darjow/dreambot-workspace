package com.darjow.framework.script.taskssystem.tasks;

import com.darjow.framework.antiban.ABNumberGenerator;
import com.darjow.framework.handlers.afk.DistributionType;
import com.darjow.framework.script.taskssystem.Task;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.widget.helpers.ItemProcessing;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.Locatable;
import org.dreambot.api.wrappers.items.Item;

import java.util.List;



public abstract class CombiningTask extends Task {

    private int itemAId;
    private int itemBId;
    private String finalProduct;



    public CombiningTask(Skill skill, int minimumLevel, int maximumLevel, double experiencePerAction, Locatable location, List<Item> requiredItems, List<Item> requiredFixedItems, int itemAId, int itemBId, String finalProduct) {
        super(skill, minimumLevel, maximumLevel, experiencePerAction, location, requiredItems, requiredFixedItems);
        this.itemAId = itemAId;
        this.itemBId = itemBId;
        this.finalProduct = finalProduct;
    }

    @Override
    public boolean validate() {
        if(Skills.getRealLevel(skill) < minimumLevel || Skills.getRealLevel(skill) > maximumLevel) {
            return false;
        }
        if(!hasRequiredItems()){
            setStatus("Executing - BankingHandler");
            setBankingRequired(true);
            return false;
        }

        if(location.distance(Players.getLocal().getTile()) > 10){
            setStatus("Executing - TraverseHandler");
            setTravelRequired(true);
            return false;
        }
        return true;
    }

    @Override
    public void execute() {
        if(ItemProcessing.isOpen()){
            setStatus("Clicking the menu item");
            if(ItemProcessing.makeAll(finalProduct)){
                setStatus("Sleeping");
                Sleep.sleepUntil(() -> !hasRequiredItems() || Dialogues.canContinue(),50000, ABNumberGenerator.generateRandomNumber(1500, 5000, 2500));
                Logger.warn("WE DONE SLEEPING");
            }
        }
        else if (!Inventory.isItemSelected() && !Players.getLocal().isAnimating()) {
            Logger.warn("No item selected, interacting with one.");
            setStatus(String.format("Interacting with: %d", itemAId));
            Inventory.interact(itemAId);
        }
        else if (Inventory.isItemSelected()){
            int selected = Inventory.getSelectedItemId();
            if (selected != itemAId && selected != itemBId) {
                Logger.warn("Random item was selected, deselecting it.");
                Inventory.deselect();
            } else {
                int next = getNextInteract(selected);
                Logger.warn("Interacting with next item.");
                setStatus(String.format("Interacting with: %d", next));
                if(Inventory.interact(next)){
                    Sleep.sleepUntil(() -> ItemProcessing.isOpen(), 500,2);
                }
            }
        }
    }

    public int getNextInteract(int selected){
        if(selected == itemAId){
            return itemBId;
        }else{
            return itemAId;
        }
    }
}
