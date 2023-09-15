package branches.splashing.leaves;

import com.darjow.framework.script.decisiontree.Leaf;
import com.darjow.framework.widgets.Spellbook;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.magic.Spell;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.ScriptManager;
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
        if(Spellbook.canCast(spell)) {
            Magic.setAutocastSpell(spell);
        }else{
            Logger.error("Out of runes stopping the script.");
            ScriptManager.getScriptManager().stop();
        }
    }

    @Override
    public String getStatus() {
        return "[Leaf] Swapping spells.";
    }


    private boolean hasLevelRequirement(int magicLevel, int requiredLevel) {
        return magicLevel >= requiredLevel;
    }

    private Spell getBestSpell(int magicLevel) {
        if(hasLevelRequirement(magicLevel, 35)){
            return Normal.FIRE_BOLT;
        }
        else if (hasLevelRequirement(magicLevel, 13)) {
            return Normal.FIRE_STRIKE;
        } else {
            return Normal.WIND_STRIKE;
        }
    }
}
