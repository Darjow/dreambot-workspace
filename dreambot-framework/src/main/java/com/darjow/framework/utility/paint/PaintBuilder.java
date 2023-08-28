package com.darjow.framework.utility.paint;

import com.darjow.framework.decisiontree.components.Branch;
import com.darjow.framework.decisiontree.components.Leaf;
import com.darjow.framework.utility.time.TimeUtilites;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;

import java.awt.*;

public class PaintBuilder {

    private Branch currentBranch;
    private Leaf currentLeaf;

    private final TimeUtilites timeUtilites;

    public PaintBuilder(TimeUtilites timeUtilites){
        this.timeUtilites = timeUtilites;
    }


    public PaintBuilder currentBranch(Branch currentBranch) {
        this.currentBranch = currentBranch;
        return this;
    }

    public PaintBuilder currentLeaf(Leaf currentLeaf) {
        this.currentLeaf = currentLeaf;
        return this;
    }

    public void build(Graphics g) {
        g.setFont(new Font("Sathu", Font.BOLD, 12));
        g.drawString("Shock's - Splasher", 40, 160);
        g.drawString(currentBranch.getStatus(), 40, 180);
        g.drawString(currentLeaf.getStatus(), 40, 200);
        g.drawString(String.format("Total exp: %d", SkillTracker.getGainedExperience(Skill.MAGIC)), 40,220);
        g.drawString(String.format("Total runtime: %s", timeUtilites.formatTime()), 40, 240);
    }

}
