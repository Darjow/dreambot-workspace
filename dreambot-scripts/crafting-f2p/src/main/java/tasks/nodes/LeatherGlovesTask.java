package tasks.nodes;

import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.wrappers.interactive.Locatable;
import org.dreambot.api.wrappers.items.Item;
import com.darjow.framework.script.taskssystem.tasks.CombiningTask;

import java.util.ArrayList;
import java.util.Arrays;

import static constants.ID.*;


public class LeatherGlovesTask extends CombiningTask {

    public LeatherGlovesTask(Locatable location) {
        super(Skill.CRAFTING,
                1,
                6,
                13.8,
                location,
                new ArrayList(Arrays.asList(new Item(LEATHER, 1))),
                new ArrayList(Arrays.asList(new Item(NEEDLE, 1), new Item(THREAD, 10))),
                LEATHER,
                NEEDLE,
                "Leather gloves");
    }
}
