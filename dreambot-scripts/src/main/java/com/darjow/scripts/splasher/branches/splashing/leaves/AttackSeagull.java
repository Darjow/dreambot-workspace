package com.darjow.scripts.splasher.branches.splashing.leaves;

import com.darjow.framework.decisiontree.components.Leaf;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.core.Instance;

import java.util.Objects;

import static com.darjow.framework.locations.Location.PORT_SARIM_SEAGULLS;

public class AttackSeagull extends Leaf {

    public AttackSeagull() {
        super("Attacking a seagull");
    }

    @Override
    public boolean validate() {
        Player player = Players.getLocal();
        Area seagullsArea = PORT_SARIM_SEAGULLS.getArea();

        if(seagullsArea.contains(player)){
            if(!player.isInCombat()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute() {
        Logger.info("Attacking seagull");
        NPC seagull = NPCs.closest(e -> !e.isInCombat() && e.getName().equals("Seagull") && !e.isHealthBarVisible());

        if(seagull == null || !seagull.exists()){
            Logger.error("Seagull was null when trying to attack one.");
            ScriptManager.getScriptManager().stop();
        }

        if(seagull.interact()){
            Logger.info("Attacked a seagull, waiting to be in combat.");
            Sleep.sleepUntil(() -> Players.getLocal().isInCombat() || Players.getLocal().isHealthBarVisible(), 500,3);
        }else {
            Logger.info("Failed to attack seagull.");
        }

    }
}
