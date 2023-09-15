package com.darjow.framework.script.taskssystem;


import com.darjow.framework.handlers.core.CoreHandler;

public class TaskManagerBuilder {
    private TaskManager manager;

    public TaskManagerBuilder(){ manager = new TaskManager(new CoreHandler());}

    public TaskManagerBuilder addTask(Task task){
        manager.addTask(task);
        return this;
    }
    public TaskManager build(){
        manager.setNewTask();
        return manager;
    }
    public static TaskManagerBuilder newBuilder(){
        return new TaskManagerBuilder();
    }
}
