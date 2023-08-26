package com.darjow.framework.script;

import com.darjow.framework.decisiontree.Tree;
import org.dreambot.api.script.AbstractScript;

public abstract class DecisionTreeScript extends AbstractScript {
    protected Tree decisionTree;

    public void onStart(){
        super.onStart();
        createDecisionTree();
    }

    protected abstract void createDecisionTree();



}
