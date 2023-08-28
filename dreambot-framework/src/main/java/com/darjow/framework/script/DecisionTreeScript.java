package com.darjow.framework.script;

import com.darjow.framework.decisiontree.components.Tree;
import com.darjow.framework.utility.paint.PaintBuilder;
import com.darjow.framework.utility.time.TimeUtilites;
import org.dreambot.api.script.AbstractScript;

import java.awt.*;

public abstract class DecisionTreeScript extends AbstractScript {
    protected Tree decisionTree;
    private final TimeUtilites timeUtilities = new TimeUtilites();
    private final PaintBuilder paintBuilder = new PaintBuilder(timeUtilities);

    protected abstract void createDecisionTree();

    @Override
    public void onPaint(Graphics g) {
        paintBuilder.build(g);
        paintBuilder.currentBranch(decisionTree.getCurrentBranch());
        paintBuilder.currentLeaf(decisionTree.getCurrentLeaf());
    }
}
