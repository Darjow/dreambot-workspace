package com.darjow.framework.script;

import com.darjow.framework.tasks.decisiontree.Tree;
import org.dreambot.api.script.AbstractScript;

public abstract class DecisionTreeScript extends AbstractScript {
    protected Tree decisionTree;

    public void onStart(){
        super.onStart();
        decisionTree = createDecisionTree();
    }

    protected abstract Tree createDecisionTree();



}
