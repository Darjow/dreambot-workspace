package com.darjow.framework.teleportation;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;

import java.util.HashMap;

public abstract class SpellbookTeleport extends TeleportationMethod {

    private HashMap<String, Integer> runesNeeded;
    private int requiredLevel;

    public SpellbookTeleport(String name, Area destination, int requiredLevel, HashMap<String, Integer> runesNeeded) {
        super(name, destination, TeleportationType.SPELLBOOK);
        this.runesNeeded = runesNeeded;
        this.requiredLevel = requiredLevel;
    }

    public boolean hasRequiredLevel(){
        return Skills.getBoostedLevel(Skill.MAGIC) >= requiredLevel;
    }
}
