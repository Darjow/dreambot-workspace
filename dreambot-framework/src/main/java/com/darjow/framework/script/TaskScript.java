package com.darjow.framework.script;

import com.darjow.framework.script.taskssystem.TaskManager;
import org.dreambot.api.script.AbstractScript;

public abstract class TaskScript extends AbstractScript {
    protected TaskManager manager;

    protected abstract void createTaskManager();
}
