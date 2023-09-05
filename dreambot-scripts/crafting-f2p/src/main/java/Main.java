import com.darjow.framework.decisiontree.components.Branch;
import com.darjow.framework.decisiontree.components.Leaf;
import com.darjow.framework.decisiontree.components.Tree;
import com.darjow.framework.decisiontree.components.TreeBuilder;
import com.darjow.framework.handlers.afk.AFKHandler;
import com.darjow.framework.script.DecisionTreeScript;
import com.darjow.framework.utility.discord.DiscordWebhook;
import com.darjow.framework.utility.time.TimeUtilites;
import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.login.LoginStage;
import org.dreambot.api.methods.login.LoginUtility;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;

import java.awt.*;
import java.io.IOException;

@ScriptManifest(
        version = 1.0,
        category = Category.COMBAT,
        author = "Shock",
        description = "Dynamic jewelry maker in F2P",
        name = "Shock's F2P crafter"
)
public class Main extends DecisionTreeScript {

    private final TimeUtilites timeUtilities = new TimeUtilites();
    private AFKHandler handler = new AFKHandler();

    @Override
    public void onPaint(Graphics g) {
        g.setFont(new Font("Sathu", Font.BOLD, 12));
        g.drawString("Shock's - F2P crafter", 40, 30);

        if(decisionTree != null ) {
            Leaf leaf = decisionTree.getCurrentLeaf();

            if (leaf != null) {
                g.drawString(decisionTree.getCurrentBranch().getStatus(), 40, 50);
                g.drawString(decisionTree.getCurrentLeaf().getStatus(), 40, 70);
            }
        }
        g.drawString(String.format("Total runtime: %s", timeUtilities.getTimeRunning()), 40, 90);
        g.drawString(String.format("Exp earned: %s", SkillTracker.getGainedExperience(Skill.MAGIC)), 40, 110);
        g.drawString(String.format("Crafting Level: %d", Skills.getRealLevel(Skill.MAGIC)), 40, 130);
    }

    @Override
    public void onStart() {
        super.onStart();
        LoginUtility.login();
        sleepUntil(() -> Client.isLoggedIn() && LoginUtility.getStage() != LoginStage.LOGIN_SCREEN_PLAY_NOW && Players.getLocal().isOnScreen(), 5000, 7);
        createDecisionTree();
        SkillTracker.start(Skill.CRAFTING);
    }


    @Override
    public void onExit() {
        super.onExit();
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/1148707408668004483/e-2JkzqbeKCnic8aJTfr5QI9D5_jCVvJXrCoUXc6J11ykOn6VXLULQs0lQBTFKIMYPtG");
        webhook.setTts(true);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setColor(Color.white)
                .setDescription(LoginUtility.getLoginEmail())
                .addField("Exp earned: ", String.valueOf(SkillTracker.getGainedExperience(Skill.CRAFTING)), true)
                .addField("Crafting level: ", String.valueOf(Skills.getRealLevel(Skill.CRAFTING)), true)
                .addField("Total Runtime: " ,timeUtilities.getTimeRunning(), false)
                .setColor(Color.RED));

        try {
            webhook.execute();
        } catch (IOException e) {
            Logger.error("Discordwebhook failed on start: " + e.getMessage());
        }
    }

    @Override
    protected void createDecisionTree() {

    }

    @Override
    public int onLoop() {
        if (Skills.getRealLevel(Skill.CRAFTING) >= 50) {
            Logger.info("We are 50+ crafting level. Terminating script.");
            getScriptManager().stop();
        }
        else{
            decisionTree.execute();
        }

        return Calculations.random(750,2480);
    }

}