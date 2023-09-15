package com.darjow.framework.handlers.core;

import com.darjow.framework.banking.Banker;
import com.darjow.framework.script.taskssystem.Task;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.grandexchange.GrandExchangeItem;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.grandexchange.Status;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;

import java.util.*;

import static constants.ID.COINS;

public class GrandExchangeHandler {

    private final TraversingHandler walker;
    private final BankingHandler banker;

    private boolean hasCalculatedTotalCost = false;
    private HashMap<String, Integer> toBuy = new HashMap<>();

    public GrandExchangeHandler(TraversingHandler walker, BankingHandler banker){
        this.walker = walker;
        this.banker = banker;
    }
    public void handle(Task current, List<Task> upcommingTasks) {
        if(!BankLocation.GRAND_EXCHANGE.getArea(15).contains(Players.getLocal())){
            current.setStatus("Running to grand exchange");
            walker.traverseToGe();
        }
        else if(!Inventory.contains(COINS)){
            current.setStatus("Withdrawing coins");
            Banker.withdrawAll(COINS);
        }
        else if(!GrandExchange.isOpen()){
            current.setStatus("Opening GE");
            GrandExchange.open();
        }
        else if(!hasCalculatedTotalCost){
            current.setStatus("Setting items to buy");
            setItemsToBuy(current, upcommingTasks);
            Logger.warn("done setting items to buy");
        }else{
            if(toBuy.isEmpty()){
                current.setGrandExchangeRequired(false);
            }else {
                current.setStatus("Buying items.");
                Logger.warn(String.format("Need to buy the following items: "));
                toBuy.forEach((k, v) -> {
                    Logger.warn(String.format("%s : %d ", k, v));
                });
                buyItems();
            }
        }

    }

    private void buyItems(){
        for (Map.Entry<String, Integer> item: toBuy.entrySet()){
            buyItem(item.getKey(), item.getValue());
        }
    }
    private void addToBuy(List<Item> items) {
        for (Item item : items) {
            int amount = toBuy.getOrDefault(item.getName(), 0);

            int newAmount = amount + item.getAmount();

            if(newAmount == 0){
                toBuy.remove(item.getName());
            }else{
                toBuy.put(item.getName(), amount + item.getAmount());
            }
        }
    }

    private void setItemsToBuy(Task current, List<Task> upcomming) {
        int coins = Inventory.get(995).getAmount();

        int total = calculateTaskBudget(current);
        addToBuy(current.getRequiredItemsForCompletion());
        addToBuy(current.getRequiredFixedItems());

        for(Item i : getInitialAmount(current.getRequiredItemsForCompletion())){
            total -= calculateItemPrice(i);
            int amount = toBuy.getOrDefault(i.getName(), 0);
            toBuy.put(i.getName(), amount + i.getAmount());
            int totalAmount = amount + i.getAmount();
            if(totalAmount == 0){
                toBuy.remove(i.getName());
            }else {
                toBuy.put(i.getName(), amount + i.getAmount());
            }
        }
        for(Item i : getInitialAmount(current.getRequiredFixedItems())){
            total -= calculateItemPrice(i);
            int amount = toBuy.getOrDefault(i.getName(), 0);
            int totalAmount = amount + i.getAmount();
            if(totalAmount == 0){
                toBuy.remove(i.getName());
            }else {
                toBuy.put(i.getName(), amount + i.getAmount());
            }
        }

        if(total > coins) {
            Logger.log("Out of coins");
            ScriptManager.getScriptManager().stop();
        }

        for(Task upc: upcomming){
            int cost = calculateTaskBudget(upc);
            if(cost > coins - total){
                break;
            }
            total += cost;
            addToBuy(upc.getRequiredItemsForCompletion());
            addToBuy(upc.getRequiredFixedItems());
        }
        hasCalculatedTotalCost = true;

    }
    private int calculateItemPrice(Item e) {
        return (int) (LivePrices.get(e) * Calculations.random(1.2, 1.25)) * e.getAmount();
    }
    private int calculateTaskBudget(Task task) {
        int total =  task.getRequiredItemsForCompletion()
                .stream()
                .map(e -> calculateItemPrice(e))
                .reduce(0, (a, b) -> a + b);

        return task.getRequiredFixedItems()
                .stream()
                .map(e -> calculateItemPrice(e))
                .reduce(total, (a,b) -> a+b);
    }


    private List<Item> getInitialAmount(List<Item> neededItems) {
        List<Item> items = new ArrayList<>();

        for(Item item: neededItems){
            Item bankItem = Bank.get(item.getName());
            Item inventoryItem = Inventory.get(item.getName());

            int totalAmount = 0;
            if (bankItem != null && bankItem.isValid()) {
                totalAmount += bankItem.getAmount();
            }
            if (inventoryItem != null && inventoryItem.isValid()) {
                totalAmount += inventoryItem.getAmount();
            }
            items.add(new Item(item.getID(), totalAmount));
        }

        return items;
    }


    private void buyItem(String name, int quantity){
        int price = (int) (LivePrices.get(name) * Calculations.random(1.2, 1.25));
        GrandExchangeItem geItem = null;
        int slot = GrandExchange.getFirstOpenSlot();
        boolean success = false;


        while(!success) {
            Logger.info("Trying to buy item: " + name);

            if (slot == -1) {
                if (GrandExchange.cancelAll()) {
                    Sleep.sleepUntil(() -> GrandExchange.isReadyToCollect(), 500);
                    GrandExchange.collect();
                } else {
                    if (GrandExchange.isReadyToCollect()) {
                        GrandExchange.collect();
                    }
                }
            } else {
                if (GrandExchange.getItems()[slot].getStatus() == Status.BUY) {
                    geItem = GrandExchange.getItems()[slot];
                    if (GrandExchange.cancelOffer(slot)) {
                        Logger.info("Succesfully canceled item.");
                    }
                }

                if (geItem != null) {
                    Logger.info("Adjusted price from " + name);
                    price = (int) (geItem.getPrice() * 1.2);

                }
                if (GrandExchange.buyItem(name, quantity, price)) {
                    Sleep.sleepUntil(() -> GrandExchange.getItems()[slot].getStatus() == Status.BUY_COLLECT, Calculations.random(758, 1500));
                    if (GrandExchange.isReadyToCollect(slot)) {
                        if (GrandExchange.collect()) {
                            success = true;
                            toBuy.remove(name);
                        }
                    }
                }
            }
        }
    }

}
