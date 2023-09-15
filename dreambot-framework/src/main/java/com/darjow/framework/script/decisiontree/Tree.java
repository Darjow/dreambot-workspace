package com.darjow.framework.script.decisiontree;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    private List<Branch> branches = new ArrayList<>();
    private Branch currentBranch;
    private Leaf currentLeaf;


    public void addBranch(Branch branch) {
        branches.add(branch);
    }

    public Branch getCurrentBranch() {
        return currentBranch;
    }

    public Leaf getCurrentLeaf() {
        return currentLeaf;
    }

    public void execute() {
        for (Branch branch : branches) {
            if (branch.validate()) {
                currentBranch = branch;
                for (Leaf leaf : branch.getChildren()) {
                    if (leaf.validate()) {
                        currentLeaf = leaf;
                        leaf.execute();
                        break;
                    }
                }
            }
        }
    }
}
