package com.darjow.scripts.splasher.branches.splashing.leaves;

import com.darjow.framework.decisiontree.components.Leaf;
import com.darjow.framework.handlers.afk.AFKHandler;
import com.darjow.framework.handlers.afk.DistributionType;
import com.darjow.framework.mouse.FullscreenRectangle;
import com.darjow.framework.mouse.MouseArea;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.wrappers.interactive.Player;

import java.awt.*;
import java.util.Random;

public class GoAfk extends Leaf {

    private AFKHandler handler;


    public GoAfk(AFKHandler handler) {
        super("AFK");
        this.handler = handler;

    }

    @Override
    public boolean validate() {
        Player local = Players.getLocal();

        if (!handler.isAfk()) {
            if (local.isInCombat() && local.isHealthBarVisible()) {
                return true;
            }

        }
        return false;
    }

    @Override
    public void execute() {
        handler.startAfk(90 * 1000, 1250 * 1000, 700 * 1000, DistributionType.LITTLE_SPREAD);
    }

    private void performRandomActionBeforeAfk() {
        switch(Calculations.random(0,5)) {
            case 1:
                Skills.hoverSkill(Skill.MAGIC);
                break;
            case 2:
                Point randomXY = new MouseArea(FullscreenRectangle.getRectangle()).getRandomPoint();
                Mouse.click(randomXY, true);
                Mouse.move(new MouseArea(FullscreenRectangle.getRectangle()).getRandomPoint());
                break;
            default:
                Point random = new MouseArea(FullscreenRectangle.getRectangle()).getRandomPoint();
                Mouse.click(random, true);
        }
        if(new Random().nextInt(15) != 1){
            Mouse.moveOutsideScreen();
        }
    }


}
