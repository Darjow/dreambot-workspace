package com.darjow.framework.decisiontree.components;


import com.darjow.framework.decisiontree.actions.TreeComponent;

public abstract class Leaf implements TreeComponent {

    private final String status;

    public Leaf(String status){
        this.status = status;
    }

    public String getStatus() {
        return String.format("[%s] - %s", getClass().getSimpleName(), status);
    }
}