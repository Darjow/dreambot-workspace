package com.darjow.framework.script.taskssystem;

import com.darjow.framework.handlers.core.CoreHandler;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TaskManager {

    private List<Task> taskList = new LinkedList<>();
    private List<Task> finishedTask = new ArrayList<>();

    private Task currentTask;

    private final CoreHandler handler;

    public TaskManager(CoreHandler handler){
        this.handler = handler;
    }

    public void executeCurrent(){
        if(handler.shouldExecuteTask(currentTask)){
            Logger.warn("We cant execute the task");
            handler.execute(currentTask, taskList);
        }
        else{
            if(currentTask.validate()){
                Logger.warn("Executing the task.");
                currentTask.execute();
            }else{
                Logger.error("Nothing we can do.");
                ScriptManager.getScriptManager().stop();
            }

        }
    }
    public Task getCurrentTask(){
        return currentTask;
    }

    public void addTask(Task task){
        if (task.maximumLevel > Skills.getRealLevel(task.skill)) {
            this.taskList.add(task);
        }
    }
    public boolean hasRequirements() {
        return currentTask.hasRequiredLevel();
    }
    public void setNewTask(){
        currentTask = taskList.get(0);
        taskList.remove(0);
    }
    public void finishedTask(){
        if(taskList.isEmpty()){
            Logger.info("No more tasks to execute, will log out.");
            Tabs.logout();
            ScriptManager.getScriptManager().stop();
        }else {
            finishedTask.add(currentTask);
            currentTask = taskList.get(0);
            taskList.remove(0);
        }
    }
}