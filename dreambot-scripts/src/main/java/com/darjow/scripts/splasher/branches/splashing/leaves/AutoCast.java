package com.darjow.scripts.splasher.branches.splashing.leaves;

import com.darjow.framework.decisiontree.components.Leaf;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.magic.Spell;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.utilities.Logger;

public class AutoCast extends Leaf {

    public AutoCast() {}

    @Override
    public boolean validate() {
        Spell spell = Magic.getAutocastSpell();
        int magicLevel = Skills.getRealLevel(Skill.MAGIC);

        return getBestSpell(magicLevel) != spell;
    }

    @Override
    public void execute() {
        int magicLevel = Skills.getRealLevel(Skill.MAGIC);
        Spell spell = getBestSpell(magicLevel);
        Logger.info("Autocasting: " + spell.toString());
        Magic.setAutocastSpell(spell);
    }

    @Override
    public String getStatus() {
        return "[Leaf] Swapping spells.";
    }


    private boolean canCastSpell(int magicLevel, int requiredLevel) {
        return magicLevel >= requiredLevel;
    }
    private Spell getBestSpell(int magicLevel) {
        if (canCastSpell(magicLevel, 13)) {
            return Normal.FIRE_STRIKE;
        } else {
            return Normal.WIND_STRIKE;
        }
    }
}
