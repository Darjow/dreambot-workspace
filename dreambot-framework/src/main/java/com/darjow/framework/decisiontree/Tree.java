package com.darjow.framework.decisiontree;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    private List<Branch> branches = new ArrayList<>();

    public Tree() {

    }

    public void addBranch(Branch branch) {
        branches.add(branch);
    }

    public void execute() {
        for (Branch branch : branches) {
            if (branch.validate()) {
                branch.execute();
            }
        }
    }
}
