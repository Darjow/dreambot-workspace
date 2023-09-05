import branches.setup.SetupBranch;
import branches.setup.leaves.BuyStaff;
import branches.setup.leaves.EquipStaff;
import branches.setup.leaves.GoToMarket;
import branches.setup.leaves.RunToSpot;
import branches.splashing.SplashingBranch;
import branches.splashing.leaves.AttackSeagull;
import branches.splashing.leaves.AutoCast;
import branches.splashing.leaves.GoAfk;
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
        description = "Fetches all items and performs splashing until target level has reached",
        name = "Shock's Splasher"
)
public class Main extends DecisionTreeScript {

    private final TimeUtilites timeUtilities = new TimeUtilites();
    private AFKHandler handler = new AFKHandler();

    @Override
    public void onPaint(Graphics g) {
        g.setFont(new Font("Sathu", Font.BOLD, 12));
        g.drawString("Shock's - Splasher", 40, 30);

        if(decisionTree != null ) {
            Leaf leaf = decisionTree.getCurrentLeaf();

            if (leaf != null) {
                g.drawString(decisionTree.getCurrentBranch().getStatus(), 40, 50);
                g.drawString(decisionTree.getCurrentLeaf().getStatus(), 40, 70);
            }
        }
        g.drawString(String.format("Total runtime: %s", timeUtilities.getTimeRunning()), 40, 90);
        g.drawString(String.format("Exp earned: %s", SkillTracker.getGainedExperience(Skill.MAGIC)), 40, 110);
        g.drawString(String.format("Magic Level: %d", Skills.getRealLevel(Skill.MAGIC)), 40, 130);
    }

    @Override
    public void onStart() {
        super.onStart();
        LoginUtility.login();
        sleepUntil(() -> Client.isLoggedIn() && Players.getLocal().isOnScreen(), 2500, 7);
        createDecisionTree();
        getRandomManager().disableSolver(RandomEvent.LOGIN);
        SkillTracker.start(Skill.MAGIC);

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
                    .addLeaf(new GoAfk(handler))

                .build();

        this.decisionTree = decisionTree;
    }

    @Override
    public int onLoop() {
        if(!Client.isLoggedIn()){
            if(!handler.isAfk()){
                getRandomManager().enableSolver(RandomEvent.LOGIN);
            }
        }
        else if (Skills.getRealLevel(Skill.MAGIC) >= 50) {
            Logger.info("We are 50+ magic level. Terminating script.");
            getScriptManager().stop();
        }
        else if(Client.isLoggedIn()){
            if(handler.isAfk()){
                getRandomManager().disableSolver(RandomEvent.LOGIN);
                Sleep.sleepUntil(() -> !handler.isAfk() || Skills.getRealLevel(Skill.MAGIC) >= 50, 1000, 2500);
            }
            else{
                decisionTree.execute();
            }
        }
        return Calculations.random(250,1250);
    }

}