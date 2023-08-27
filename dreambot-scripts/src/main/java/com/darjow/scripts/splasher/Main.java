package com.darjow.scripts.splasher;

import com.darjow.framework.decisiontree.components.Branch;
import com.darjow.framework.decisiontree.components.CompletableBranch;
import com.darjow.framework.decisiontree.components.Tree;
import com.darjow.framework.decisiontree.components.TreeBuilder;
import com.darjow.framework.script.DecisionTreeScript;
import com.darjow.framework.utility.discord.DiscordWebhook;
import com.darjow.framework.utility.time.TimeUtilites;
import com.darjow.scripts.splasher.leaves.GetStaff;
import com.darjow.scripts.splasher.leaves.RunToSpot;
import com.darjow.scripts.splasher.leaves.Splash;
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
    protected void createDecisionTree() {
        //dependancy
        CompletableBranch setupBranch = new CompletableBranch();

        Tree decisionTree = TreeBuilder.newBuilder()
                .addBranch(setupBranch)
                    .addComponent(new GetStaff())
                    .addComponent(new RunToSpot())

                .addBranch(new Branch())
                    .addDependancy(setupBranch)
                    .addComponent(new Splash())

                .build();

        this.decisionTree = decisionTree;
    }

    @Override
    public int onLoop() {
        decisionTree.execute();
        return Calculations.random(250,1250);
    }

}