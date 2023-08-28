package com.darjow.framework.decisiontree.components;

import com.darjow.framework.decisiontree.actions.TreeComponent;

import java.util.ArrayList;
import java.util.List;

public class Branch implements TreeComponent {

    private final String status;

    public Branch(String status){
        this.status = status;
    }
    protected List<Leaf> children = new ArrayList<>();
    protected List<Branch> dependencies = new ArrayList<>(); //branches to be executed before the branch.

    protected TreeComponent current = null;

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
        for (TreeComponent child : children) {
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

    public String getStatus() {
        return String.format("[%s] - %s", getClass().getSimpleName(), status);
    }
}