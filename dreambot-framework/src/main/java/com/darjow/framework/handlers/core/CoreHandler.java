package com.darjow.framework.handlers.core;


import com.darjow.framework.script.taskssystem.Task;

import java.util.List;

public class CoreHandler {

    private TraversingHandler traversingHandler;
    private GrandExchangeHandler geHandler;
    private BankingHandler bankingHandler;


    public CoreHandler() {
        this.traversingHandler = new TraversingHandler();
        this.bankingHandler = new BankingHandler();
        this.geHandler = new GrandExchangeHandler(traversingHandler, bankingHandler);
    }
    public void execute(Task current, List<Task> upcomming) {
        if(current.isGrandExchangeRequired()){
            geHandler.handle(current, upcomming);
        }
        else if(current.isBankingRequired()){
            bankingHandler.handle(current);
        }
        else if(current.isTravelRequired()){
            traversingHandler.traverse(current.getLocation());
        }


    }

    public boolean shouldExecuteTask(Task task){
        return !task.validate() || task.isTravelRequired() || task.isGrandExchangeRequired();
    }
}
