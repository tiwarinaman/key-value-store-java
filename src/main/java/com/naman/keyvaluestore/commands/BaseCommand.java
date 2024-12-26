package com.naman.keyvaluestore.commands;

import com.naman.keyvaluestore.storage.Storage;

public abstract class BaseCommand {

    public boolean preExecute(String[] args, Storage storage) throws IllegalArgumentException {
        return true;
    }

    protected abstract Object executeCore(String[] args, Storage storage);

    public void postExecute(String[] args, Object result) {
        System.out.println("Command: " + this.getType() + ", Args: " + String.join(" ", args) + ", Result: " + result);
    }

    public Object execute(String[] args, Storage storage) {
        try {
            if (!preExecute(args, storage)) {
                return "ERROR: Validation failed.";
            }
            Object result = executeCore(args, storage);
            postExecute(args, result);
            return result;
        } catch (IllegalArgumentException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    public abstract String getType();

}
