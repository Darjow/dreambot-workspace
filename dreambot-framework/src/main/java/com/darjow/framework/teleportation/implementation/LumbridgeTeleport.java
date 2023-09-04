package com.darjow.framework.teleportation.implementation;

import com.darjow.framework.enums.Location;
import com.darjow.framework.teleportation.SpellbookTeleport;
import com.darjow.framework.widgets.Spellbook;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;

import java.util.HashMap;

public class LumbridgeTeleport extends SpellbookTeleport {

    private long timestampNoTimer = System.currentTimeMillis();

    public LumbridgeTeleport() {
        super("Lumbridge Teleport", Location.LUMBRIDGE_TELEPORT.getArea(), 0,  new HashMap());
    }
    @Override
    public boolean hasTimerActive() {
        return System.currentTimeMillis() < timestampNoTimer;
    }


    @Override
    public void teleport() {
        if (canExecute()) {
            Spellbook.open();
            Magic.castSpell(Normal.HOME_TELEPORT);
            Sleep.sleepUntil(() -> Players.getLocal().isAnimating(), 3000);

            if(!Players.getLocal().isAnimating()){
                timestampNoTimer = System.currentTimeMillis() + 180000;
            }else{
                Sleep.sleepUntil(() -> Location.LUMBRIDGE_TELEPORT.getArea().contains(Players.getLocal()) || !canExecute(), 20000, 2);
            }

            if (!Location.LUMBRIDGE_TELEPORT.getArea().contains(Players.getLocal())) {
                Logger.error("Failed to teleport to lumbridge");
            }
        }
    }

    @Override
    public boolean canExecute() {
        return (!Combat.isPoisoned() || !Players.getLocal().isInCombat() || !Players.getLocal().isHealthBarVisible()) && !hasTimerActive();
    }

    @Override
    public HashMap<String, Integer> getRequiredItems() {
        return null;
    }
}
