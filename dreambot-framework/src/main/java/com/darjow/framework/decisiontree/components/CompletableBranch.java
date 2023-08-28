package com.darjow.framework.decisiontree.components;

import com.darjow.framework.decisiontree.actions.Completable;
import com.darjow.framework.decisiontree.actions.TreeComponent;

public class CompletableBranch extends Branch implements Completable {
    protected boolean completed;

    public CompletableBranch(String status) {
        super(status);
    }

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
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void setCompleted(boolean b) {
        this.completed = b;
    }
}
