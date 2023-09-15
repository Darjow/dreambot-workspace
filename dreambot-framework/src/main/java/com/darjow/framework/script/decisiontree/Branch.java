package com.darjow.framework.script.decisiontree;

import com.darjow.framework.script.actions.ExecutableTask;

import java.util.ArrayList;
import java.util.List;

public abstract class Branch implements ExecutableTask {

    protected List<Leaf> children = new ArrayList<>();
    protected List<Branch> dependencies = new ArrayList<>(); //branches to be executed before the branch.

    protected ExecutableTask current = null;

    public List<Leaf> getChildren(){
        return children;
    }

    public void addComponent(Leaf component) {
        children.add(component);
    }
    public void addDependency(Branch branch){dependencies.add(branch);}


    @Override
    public void execute() {
        if(current != null){
            current.execute();
        }
    }

    @Override
    public boolean validate() {
        if(!dependenciesCompleted()){
            return false;
        }
        for (ExecutableTask child : children) {
            if (child.validate()) {
                current = child;
                return true;
            }
        }
        return false;
    }

    protected boolean dependenciesCompleted() {
        for (Branch dependency : dependencies) {
            if (dependency.validate()) {
                return false;
            }
        }
        return true;
    }
}
