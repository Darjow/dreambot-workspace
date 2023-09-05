package branches.splashing.leaves;

import com.darjow.framework.decisiontree.components.Leaf;
import com.darjow.framework.handlers.afk.AFKHandler;
import com.darjow.framework.input.camera.CameraMovements;
import com.darjow.framework.input.mouse.FullscreenRectangle;
import com.darjow.framework.input.mouse.MouseArea;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.wrappers.interactive.Player;

import java.awt.*;
import java.util.Random;

public class GoAfk extends Leaf {

    private AFKHandler handler;

    public GoAfk(AFKHandler handler) {
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
        handler.startAfk(90000, 1800000, 700000);

        int random = Calculations.random(1,10);
        boolean rightclick = false;

        if(random == 1) {
            Skills.hoverSkill(Skill.MAGIC);
            rightclick = true;
        }
        else if (random <= 4){
            CameraMovements.randomSwipe();
        }
        else if (random <= 10) {
            rightclick = true;
        }

        if(rightclick){
            Rectangle fullScreen= FullscreenRectangle.getRectangle();
            Mouse.click(new MouseArea(fullScreen).getRandomPoint(), true);
        }

        if(new Random().nextInt(15) != 1){
            Mouse.moveOutsideScreen();
        }

    }

    @Override
    public String getStatus() {
        return String.format("[Leaf] Afk for: %d more seconds.", ((handler.getAfkUntil() - System.currentTimeMillis()) / 1000));
    }
}
