package com.darjow.framework.script.taskssystem.tasks;

import com.darjow.framework.script.taskssystem.Task;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.wrappers.interactive.Locatable;
import org.dreambot.api.wrappers.items.Item;

import java.util.List;

public abstract class CraftingTask extends Task {

    public CraftingTask(Skill skill, int minimumLevel, int maximumLevel, int experiencePerAction, Locatable location, List<Item> requiredItems, List<Item> fixedRequired) {
        super(skill, minimumLevel, maximumLevel, experiencePerAction, location, requiredItems, fixedRequired);

    }

    @Override
    public boolean validate() {
        if(Skills.getRealLevel(skill) < minimumLevel || Skills.getRealLevel(skill) > maximumLevel) {
            return false;
        }

        for(Item fixed: requiredFixedItems){
            if(!Inventory.contains(fixed.getName())){
                setBankingRequired(true);
                return false;
            }
        }
        for(Item fixed: requiredItems) {
            if (!Inventory.contains(fixed.getName())) {
                setBankingRequired(true);
                return false;
            }
        }
        if(location.distance(Players.getLocal().getTile()) > 10) {
            setTravelRequired(true);
            return false;
        }

            return true;
    }

    @Override
    public void execute() {


    }

    public String getStatus() {
        return null;
    }
}
