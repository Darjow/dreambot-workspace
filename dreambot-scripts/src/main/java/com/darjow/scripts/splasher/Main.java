package com.darjow.scripts.splasher;

import com.darjow.framework.utility.discord.DiscordWebhook;
import com.darjow.framework.utility.time.TimeUtilites;
import org.dreambot.api.methods.login.LoginUtility;
import org.dreambot.api.script.AbstractScript;
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
public class Main extends AbstractScript{

    private final TimeUtilites timeUtilities = new TimeUtilites();

    @Override
    public void onStart() {
        DiscordWebhook webhook = new DiscordWebhook("insert webhook");
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
        DiscordWebhook webhook = new DiscordWebhook("insert webhook");
        webhook.setTts(true);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setColor(Color.white)
                .setDescription(LoginUtility.getLoginEmail())
                .addField("Total Runtime: " ,String.valueOf(timeUtilities), true)
                .setColor(Color.RED));

        try {
            webhook.execute();
        } catch (IOException e) {
            Logger.error("Discordwebhook failed on start: " + e.getMessage());
        }
    }

    @Override
    public int onLoop() {
        return 0;
    }

}