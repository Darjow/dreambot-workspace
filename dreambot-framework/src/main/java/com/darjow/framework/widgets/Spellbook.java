package com.darjow.framework.widgets;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.magic.Spell;
import org.dreambot.api.methods.magic.cost.Rune;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;


public class Spellbook {

    public static void open(){
        while(Tabs.getOpen() != Tab.MAGIC){
            if(Tabs.open(Tab.MAGIC)){
                Sleep.sleepUntil(() -> Tabs.getOpen() == Tab.MAGIC, 500);
            }
        }
    }
    public static boolean canCast(Spell spell)
    {
        if (spell == null)
        {
            return false;
        }

        if (Skills.getRealLevel(Skill.MAGIC) < spell.getLevel())
        {
            return false;
        }

        for (Rune rune : spell.getCost())
        {
            int amount = rune.getAmount();
            String fullRuneName = rune.getName();
            Item runesInInv = Inventory.get(fullRuneName);
            String classifier = fullRuneName.split(" ")[0];

            if ((runesInInv == null || runesInInv.getAmount() < amount) &&
                    Equipment.slotContains(
                            EquipmentSlot.WEAPON,
                            item -> item != null && item.getName().toLowerCase().contains(classifier)))
            {
                return false;
            }
        }

        return true;
    }

}
