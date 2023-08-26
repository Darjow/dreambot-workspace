package com.darjow.framework.decisiontree;

import java.util.ArrayList;
import java.util.List;

public abstract class Branch implements TreeComponent {

    private List<TreeComponent> children = new ArrayList<>();

    public void addComponent(TreeComponent component) {
        children.add(component);
    }

    @Override
    public void execute() {
        for (TreeComponent child: children){
            if(child.validate()){
                child.execute();
            }
        }
    }
}
