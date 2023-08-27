package com.darjow.framework.decisiontree.components;

import com.darjow.framework.decisiontree.actions.Completable;
import com.darjow.framework.decisiontree.actions.TreeComponent;

public class CompletableBranch extends Branch implements Completable {
    protected boolean completed;

    @Override
    public boolean validate() {
        if(completed || !dependenciesCompleted()){
            return false;
        };
        for (TreeComponent child: children){
            if(child.validate()){
                current = child;
                return true;
            }
        }
        return false;
    }

    @Override
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

}
