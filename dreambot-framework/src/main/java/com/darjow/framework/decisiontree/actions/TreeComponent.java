package com.darjow.framework.decisiontree.actions;

public interface TreeComponent {
    boolean validate();
    void execute();
    String getStatus();
}
