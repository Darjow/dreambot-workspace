package com.darjow.framework.decisiontree.components;

import com.darjow.framework.decisiontree.actions.Completable;

public abstract class CompletableLeaf extends Leaf implements Completable {
    protected boolean completed;

    public CompletableLeaf(String status) {
        super(status);
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
