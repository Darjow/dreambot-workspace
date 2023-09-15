package com.darjow.framework.script.taskssystem;

import com.darjow.framework.script.actions.ExecutableTask;
import com.darjow.framework.utility.skills.SkillExperience;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.interactive.Locatable;
import org.dreambot.api.wrappers.items.Item;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Task implements ExecutableTask {

    protected int minimumLevel;
    protected int maximumLevel;
    protected List<Item> requiredItems;
    protected List<Item> requiredFixedItems;

    protected Skill skill;
    protected Locatable location;
    protected double experiencePerAction;
    protected boolean bankingRequired = false;
    protected boolean grandExchangeRequired = false;
    protected boolean travelRequired = false;

    private String status;

    public Task(Skill skill, int minimumLevel, int maximumLevel,double experiencePerAction, Locatable location, List<Item> requiredItems, List<Item> requiredFixedItems) {
        this.skill = skill;
        this.minimumLevel = minimumLevel;
        this.maximumLevel = maximumLevel;
        this.experiencePerAction = experiencePerAction;
        this.location = location;
        this.requiredItems = requiredItems;
        this.requiredFixedItems = requiredFixedItems;
    }

    public void setBankingRequired(boolean bankingRequired) {
        this.bankingRequired = bankingRequired;
    }
    public void setGrandExchangeRequired(boolean grandExchangeRequired) {this.grandExchangeRequired = grandExchangeRequired;}
    public void setTravelRequired(boolean travelRequired) {
        this.travelRequired = travelRequired;
    }

    public Locatable getLocation() {return location;}

    public List<Item> getRequiredItemsForCompletion() {
        int expNeeded = SkillExperience.getExperienceForLevel(Skills.getRealLevel(skill), maximumLevel);
        int total = (int) Math.ceil(expNeeded / experiencePerAction);

        return requiredItems.stream().map(e -> e = new Item(e.getID(), e.getAmount() * total)).collect(Collectors.toList());
    }
    public List<Item> getRequiredFixedItems(){
        return requiredFixedItems;
    }

    public boolean hasRequiredLevel(){
        return Skills.getRealLevel(skill) >= minimumLevel && Skills.getRealLevel(skill) <= maximumLevel;
    }
    public boolean hasRequiredItems() {
        for (Item item : requiredFixedItems) {
            Item inventoryItem = Inventory.get(item.getID());
            if (inventoryItem == null || inventoryItem.getAmount() < item.getAmount()) {
                return false;
            }

        }
        for (Item item : requiredItems) {
            Item inventoryItem = Inventory.get(item.getID());
            if (inventoryItem == null || inventoryItem.getAmount() < item.getAmount()) {
                return false;
            }
        }
        return true;
    }
    public boolean isGrandExchangeRequired() {
        return grandExchangeRequired;
    }
    public boolean isTravelRequired() {
        return travelRequired;
    }
    public boolean isBankingRequired() {
        return bankingRequired;
    }

    public void setStatus(String status){
        Logger.warn(String.format("[%s] - %s", this.getClass().getSimpleName(), status));
        this.status = status;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public boolean validate() {
        return hasRequiredItems() && !isTravelRequired() && !isGrandExchangeRequired();
    }

    public List<Item> getRequiredItems(){
        return requiredItems;
    }

}