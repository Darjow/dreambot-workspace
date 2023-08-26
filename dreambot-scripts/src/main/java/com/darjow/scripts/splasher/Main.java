package com.darjow.scripts.splasher;

import com.darjow.framework.decisiontree.Tree;
import com.darjow.framework.decisiontree.TreeBuilder;
import com.darjow.framework.script.DecisionTreeScript;
import com.darjow.framework.utility.discord.DiscordWebhook;
import com.darjow.framework.utility.time.TimeUtilites;
import com.darjow.scripts.splasher.actions.branch.ExecutableBranch;
import com.darjow.scripts.splasher.actions.branch.TestFalseBranch;
import com.darjow.scripts.splasher.actions.leaves.LeaveOne;
import com.darjow.scripts.splasher.actions.leaves.LeaveOneFalse;
import com.darjow.scripts.splasher.actions.leaves.LeaveTwo;
import com.darjow.scripts.splasher.actions.leaves.LeaveTwoFalse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.login.LoginUtility;
import org.dreambot.api.script.Category;
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
    public void onStart() {
        super.onStart();
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
    protected void createDecisionTree() {
        Tree decisionTree = TreeBuilder.newBuilder()
                .addBranch(new ExecutableBranch())
                        .addComponent(new LeaveOne())
                        .addComponent(new LeaveOneFalse())
                .addBranch(new TestFalseBranch())
                    .addComponent(new LeaveTwo())
                    .addComponent(new LeaveTwoFalse())
                .build();

        this.decisionTree = decisionTree;
    }

    @Override
    public void onExit() {
        super.onExit();
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/1144906671769006110/sJrsomZcOw7rZbtjHFzq_eIkU_ToA27L5xjLVMFc6j6rT0fv8FpK1BKBE4Rwn6BzDTrn");
        webhook.setTts(true);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setColor(Color.white)
                .setDescription(LoginUtility.getLoginEmail())
                .addField("Total Runtime: " ,timeUtilities.formatTime(), true)
                .setColor(Color.RED));

        try {
            webhook.execute();
        } catch (IOException e) {
            Logger.error("Discordwebhook failed on start: " + e.getMessage());
        }
    }

    @Override
    public int onLoop() {
        //decisionTree.execute();
        return Calculations.random(250,1250);
    }

}