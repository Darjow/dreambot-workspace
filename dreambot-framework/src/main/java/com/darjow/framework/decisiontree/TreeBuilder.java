package com.darjow.framework.decisiontree;

public class TreeBuilder {
    private Tree tree;
    private Branch currentBranch;


    public TreeBuilder() {
        tree = new Tree();
    }

    public TreeBuilder addBranch(Branch branch) {
        tree.addBranch(branch);
        currentBranch = branch;
        return this;
    }
    public TreeBuilder addComponent(TreeComponent component){
        if(currentBranch != null){
            currentBranch.addComponent(component);
        }
        return this;
    }

    public Tree build() {
        return tree;
    }

    public static TreeBuilder newBuilder() {
        return new TreeBuilder();
    }
}
