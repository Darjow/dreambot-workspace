package com.darjow.scripts.splasher;

import com.darjow.framework.decisiontree.components.Branch;
import com.darjow.framework.decisiontree.components.Tree;
import com.darjow.framework.decisiontree.components.TreeBuilder;
import com.darjow.framework.script.DecisionTreeScript;
import com.darjow.framework.utility.discord.DiscordWebhook;
import com.darjow.framework.utility.time.TimeUtilites;
import com.darjow.scripts.splasher.branches.setup.SetupBranch;
import com.darjow.scripts.splasher.branches.setup.leaves.BuyStaff;
import com.darjow.scripts.splasher.branches.setup.leaves.EquipStaff;
import com.darjow.scripts.splasher.branches.setup.leaves.GoToMarket;
import com.darjow.scripts.splasher.branches.setup.leaves.RunToSpot;
import com.darjow.scripts.splasher.branches.splashing.SplashingBranch;
import com.darjow.scripts.splasher.branches.splashing.leaves.AttackSeagull;
import com.darjow.scripts.splasher.branches.splashing.leaves.AutoCast;
import com.darjow.scripts.splasher.branches.splashing.leaves.GoAfk;
import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.login.LoginUtility;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Logger;

import java.awt.*;
import java.io.IOException;

@ScriptManifest(
        version = 1.0,
        category = Category.COMBAT,
        author = "Shock",
        description = "Fetches all items and performs splashing until target level has reached",
        name = "Shock's Splasher"
)
public class Main extends DecisionTreeScript {

    private final TimeUtilites timeUtilities = new TimeUtilites();


    @Override
    public void onPaint(Graphics g) {
        g.setFont(new Font("Sathu", Font.BOLD, 12));
        g.drawString("Shock's - Splasher", 40, 160);
        g.drawString(decisionTree.getCurrentBranch().getStatus(), 40, 180);
        g.drawString(decisionTree.getCurrentLeaf().getStatus(), 40, 200);
        g.drawString(String.format("Total runtime: %s", timeUtilities.getTimeRunning()), 40, 240);
        g.drawString(String.format("Magic Level: %d", Skills.getRealLevel(Skill.MAGIC)), 40, 260);
    }

    @Override
    public void onStart() {
        createDecisionTree();
        SkillTracker.start(Skill.MAGIC);
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/1144906518001623110/K3oxBYKaprKHRVdOkSAXCw593M1oZLGUfnt-PP84-6QiYLTKM55Dvw9NEkX_hJlQhfYk");
        webhook.setTts(true);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setDescription(LoginUtility.getLoginEmail())
                .setColor(Color.GREEN));
        try {
            webhook.execute();
        } catch (IOException e) {
            Logger.error("Discordwebhook failed on start: " + e.getMessage());
        }
    }

    @Override
    public void onExit() {
        super.onExit();
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/1144906671769006110/sJrsomZcOw7rZbtjHFzq_eIkU_ToA27L5xjLVMFc6j6rT0fv8FpK1BKBE4Rwn6BzDTrn");
        webhook.setTts(true);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setColor(Color.white)
                .setDescription(LoginUtility.getLoginEmail())
                .addField("Exp earned: ", String.valueOf(SkillTracker.getGainedExperience(Skill.MAGIC)), true)
                .addField("Magic level: ", String.valueOf(Skills.getRealLevel(Skill.MAGIC)), true)
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
        //dependancy
        Branch setupBranch = new SetupBranch();


        Tree decisionTree = TreeBuilder.newBuilder()
                .addBranch(setupBranch)
                    .addLeaf(new GoToMarket())
                    .addLeaf(new BuyStaff())
                    .addLeaf(new EquipStaff())
                    .addLeaf(new RunToSpot())

                .addBranch(new SplashingBranch())
                    .addDependancy(setupBranch)
                    .addLeaf(new AutoCast())
                    .addLeaf(new AttackSeagull())
                    .addLeaf(new GoAfk())

                .build();

        this.decisionTree = decisionTree;
    }

    @Override
    public int onLoop() {
        if(Skills.getRealLevel(Skill.MAGIC) >= 50){ //will add script arguments later
            ScriptManager.getScriptManager().stop();
        }
        if(Client.isLoggedIn()){
            decisionTree.execute();
        }
        return Calculations.random(250,1250);
    }

}