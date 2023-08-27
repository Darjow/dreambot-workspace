package com.darjow.framework.script;

import com.darjow.framework.decisiontree.components.Tree;
import org.dreambot.api.script.AbstractScript;

public abstract class DecisionTreeScript extends AbstractScript {
    protected Tree decisionTree;

    protected abstract void createDecisionTree();



}
