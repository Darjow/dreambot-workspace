package com.darjow.framework.script.actions;

public interface ExecutableTask {
    boolean validate();
    void execute();
    String getStatus();
}
