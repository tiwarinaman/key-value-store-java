package com.naman.keyvaluestore.commands;

import com.naman.keyvaluestore.storage.Storage;

public class GetCommand extends BaseCommand {

    @Override
    public boolean preExecute(String[] args, Storage storage) throws IllegalArgumentException {
        if (args.length != 1) {
            throw new IllegalArgumentException("GET command requires a key.");
        }
        return true;
    }

    @Override
    protected Object executeCore(String[] args, Storage storage) {
        String key = args[0];
        Object value = storage.get(key);
        return value != null ? value : "(nil)";
    }

    @Override
    public String getType() {
        return "GET";
    }
}
