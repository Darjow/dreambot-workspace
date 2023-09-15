import com.darjow.framework.script.TaskScript;
import com.darjow.framework.script.taskssystem.Task;
import com.darjow.framework.script.taskssystem.TaskManagerBuilder;
import com.darjow.framework.utility.discord.DiscordWebhook;
import com.darjow.framework.utility.time.TimeUtilites;
import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.login.LoginStage;
import org.dreambot.api.methods.login.LoginUtility;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Logger;
import tasks.nodes.LeatherGlovesTask;

import java.awt.*;
import java.io.IOException;



@ScriptManifest(
        version = 1.0,
        category = Category.COMBAT,
        author = "Shock",
        description = "Dynamic jewelry maker in F2P",
        name = "Shock's F2P crafter"
)
public class Main extends TaskScript {

    private final TimeUtilites timeUtilities = new TimeUtilites();

    @Override
    public void onPaint(Graphics g) {
        g.setFont(new Font("Sathu", Font.BOLD, 12));
        g.setColor(Color.WHITE);
        g.drawRect(3, 10, 150, 100);
        g.setColor(Color.BLACK);
        g.fillRect(3,10,150,100);
        g.setColor(Color.WHITE);
        g.drawString("Shock's - F2P crafter", 10, 30);
        g.drawString(String.format("Total runtime: %s", timeUtilities.getTimeRunning()), 10, 50);
        g.drawString(String.format("Exp earned: %s", SkillTracker.getGainedExperience(Skill.CRAFTING)), 10, 70);
        g.drawString(String.format("Crafting Level: %d", Skills.getRealLevel(Skill.CRAFTING)), 10, 90);

        if(manager != null ) {
            g.setColor(Color.white);
            g.drawString("DEBUG", 10, 140);
            Task task = manager.getCurrentTask();
            if (task != null) {
                g.drawString(String.format("[%s] - %s", task.getClass().getSimpleName(), task.getStatus()), 10, 160);
                g.drawString(String.format("Needs grandexchange:  - %s", String.valueOf(task.isGrandExchangeRequired())), 10, 180);
                g.drawString(String.format("Needs banking: - %s", String.valueOf(task.isBankingRequired())), 10, 200);
                g.drawString(String.format("Needs traversing - %s", String.valueOf(task.isTravelRequired())), 10, 220);


            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        LoginUtility.login();
        createTaskManager();
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
    public int onLoop() {
        if(manager != null && manager.getCurrentTask() != null) {
            if (Skills.getRealLevel(Skill.CRAFTING) >= 50) {
                Logger.info("We are 50+ crafting level. Terminating script.");
                getScriptManager().stop();
            } else if (Client.isLoggedIn()) {
                if (manager.hasRequirements()) {
                    manager.executeCurrent();
                } else {
                    Logger.log("Doesnt have the requirements, skipping task.");
                    manager.finishedTask();
                }
            }
        }

        return Calculations.random(750,1400);
    }

    @Override
    protected void createTaskManager() {
        manager = TaskManagerBuilder.newBuilder()
                .addTask(new LeatherGlovesTask(BankLocation.GRAND_EXCHANGE))
                .build();
    }
}