package com.darjow.framework.banking;

import com.darjow.framework.script.taskssystem.Task;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Banker {

    private static boolean ensureBankIsOpen() {
        if (!Bank.isOpen()) {
            Logger.log("Opening bank.");
            if (Bank.open()) {
                Sleep.sleepUntil(() -> Bank.isOpen(), 500, 3);
            }
        }

        return Bank.isOpen();
    }

    public static boolean ensureBankIsClosed() {
        if (Bank.isOpen()) {
            Logger.log("Closing bank.");
            if (Bank.close()) {
                Sleep.sleepUntil(() -> !Bank.isOpen(), 500, 3);
            }
        }
        return !Bank.isOpen();
    }

    public static boolean withdrawItem(int id, int quantity) {
        if (ensureBankIsOpen()) {
            Logger.log(String.format("Withdrawing item: %s. %nQuantity: %d", id, quantity));
            if (Bank.getWithdrawMode() == BankMode.NOTE) {
                Bank.setWithdrawMode(BankMode.ITEM);
            }
            else if (Bank.withdraw(id, quantity)) {
                return true;
            }
        }
        Logger.warn(String.format("Withdrawing failed: %s. %nQuantity: %d", id, quantity));
        return false;
    }

    public static boolean withdrawItem(String name, int quantity) {
        if (ensureBankIsOpen()) {
            Logger.log(String.format("Withdrawing item: %s. %nQuantity: %d", name, quantity));
            if (Bank.getWithdrawMode() == BankMode.NOTE) {
                Bank.setWithdrawMode(BankMode.ITEM);
            }
            else if (Bank.withdraw(name, quantity)) {
                return true;
            }
        }
        Logger.warn(String.format("Withdrawing failed: %s. %nQuantity: %d", name, quantity));
        return false;
    }
    public static boolean withdrawAll(int id){
        if (ensureBankIsOpen()) {
            Logger.log(String.format("Withdrawing all with id: %d", id));
            if (Bank.getWithdrawMode() == BankMode.NOTE) {
                Bank.setWithdrawMode(BankMode.ITEM);
            }
            else if (Bank.withdrawAll(id)) {
                return true;
            }
        }
        Logger.warn(String.format("Withdrawing all failed"));
        return false;
    }

    public static boolean withdrawNoted(int id, int quantity) {
        if (ensureBankIsOpen()) {
            Logger.log(String.format("Withdrawing item: %s. %nQuantity: %d", id, quantity));
            if(Bank.getWithdrawMode() != BankMode.NOTE) {
                Bank.setWithdrawMode(BankMode.NOTE);
            }
            else{
                if (Bank.withdraw(id, quantity)) {
                    return true;
                }
            }
        }
        Logger.warn(String.format("Withdrawing failed: %s. %nQuantity: %d", id, quantity));

        return false;
    }

    public static boolean depositItem(String name, int quantity) {
        if (ensureBankIsOpen()) {
            Logger.log(String.format("Depositing item: %s. %nQuantity: %d", name, quantity));
            if (Bank.deposit(name, quantity)) {
                return true;
            }
        }
        Logger.warn(String.format("Depositing item failed!", name, quantity));
        return false;
    }

    public static boolean depositAll(String name) {
        if (ensureBankIsOpen()) {
            Logger.log(String.format("Depositing all items: %s.", name));
            if (Bank.depositAll(name)) {
                return true;
            }
        }
        Logger.warn("Depositing all items failed!");
        return false;
    }

    public static boolean depositNotNeeded(Task task) {
        task.setStatus("Depositing not needed items.");
        List<Integer> required = task.getRequiredFixedItems().stream().map(e -> e.getID()).collect(Collectors.toList());

        if (task.getRequiredItems().size() == 1) {
            required.add(task.getRequiredItems().get(0).getID());
        }

        if (ensureBankIsOpen()) {
            if (Inventory.contains(e -> !required.contains(e.getID()))) {
                Logger.log("We need to deposit some items");
                if (Bank.depositAllExcept(e -> required.contains(e.getID()))) {
                    Logger.debug("Successfully done so");
                    return true;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    private static List<Item> getInventorySetup(List<Item> desolvable, List<Item> fixed){
        List<Item> returnValue = new ArrayList<>();
        int inventorySlotsNeededForOneCraft = desolvable.stream().map(e -> e.isStackable()? 1: e.getAmount()).reduce(0, (a,b) -> a + b);
        int inventorySlotsNeededForFixedPlaceholders = fixed.stream().map(e -> e.isStackable()? 1: e.getAmount()).reduce(0, (a,b) -> a + b);

        Logger.warn(String.format("Inv slots needed for fixed items: %d", inventorySlotsNeededForFixedPlaceholders));
        Logger.warn(String.format("Inv slots needed for nonfixed items: %d", inventorySlotsNeededForOneCraft));

        int multiplier = (int) Math.floor((28 - inventorySlotsNeededForFixedPlaceholders)  / inventorySlotsNeededForOneCraft);
        Logger.warn(String.format("Inside our inventory, we can add %d actions.", multiplier));

        for(Item i: fixed){
            if(i.isStackable() && i.getAmount() == 1){
                returnValue.add(i);
                continue;
            }
            returnValue.add(new Item(i.getID(), i.getAmount() * multiplier));
        }
        for(Item i: desolvable){
            if(i.isStackable() && i.getAmount() == 1){
                returnValue.add(i);
                continue;
            }
            returnValue.add(new Item(i.getID(), i.getAmount() * multiplier));
        }

        Logger.warn("IDEAL INVENTORY SETUP:");
        returnValue.stream().forEach(e -> Logger.warn(String.format("    -%s: %d ", e.getName(), e.getAmount())));
        return returnValue;
    }
    public static boolean fetchTaskItems(Task task) {
        task.setStatus("Fetching all task items");
        List<Item> needed = getInventorySetup(task.getRequiredItems(), task.getRequiredFixedItems());


        for(Item i: needed) {
            Item inventoryItem = Inventory.get(i.getID());
            Item bankItem = Bank.get(i.getID());

            int amountNeeded = i.getAmount();

            if (inventoryItem != null) {
                if (i.isStackable()) {
                    if (inventoryItem.getAmount() > i.getAmount()) {
                        continue;
                    }
                } else {
                    int has = inventoryItem.getAmount();
                    if (has > amountNeeded) {
                        if (!depositItem(i.getName(), has - amountNeeded)) {
                            return false;
                        }
                    } else {
                        amountNeeded -= has;
                    }
                }
            }
            if (amountNeeded <= 0) {
                continue;
            }

            if (bankItem == null || bankItem.getAmount() < amountNeeded) {
                task.setGrandExchangeRequired(true);
                Logger.warn(String.format("No %s in bank.", i.getName()));
                Logger.warn("Grand exchange activated");
                return false;
            } else if (i.isStackable()) {
                if (withdrawAll(i.getID())) {
                    continue;
                }
                return false;
            } else {
                if (withdrawItem(i.getID(), amountNeeded)) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

}
