package com.darjow.framework.handlers.core;


import com.darjow.framework.banking.Banker;
import com.darjow.framework.script.taskssystem.Task;
import org.dreambot.api.methods.container.impl.bank.Bank;

public class BankingHandler {

    public BankingHandler(){
        Bank.setUseBankHistoryCache(true);
    }


    public void handle(Task task) {
        if(Banker.depositNotNeeded(task)){
            if(Banker.fetchTaskItems(task)){
                if(Banker.ensureBankIsClosed()){
                    task.setBankingRequired(false);
                }
            }
        }
    }

}
