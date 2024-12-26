package com.naman.keyvaluestore.commands;

import com.naman.keyvaluestore.storage.Storage;

public class SetCommand extends BaseCommand {

    @Override
    public boolean preExecute(String[] args, Storage storage) throws IllegalArgumentException {
        if (args.length < 2) {
            throw new IllegalArgumentException("SET command requires a key and value.");
        }
        return true;
    }

    @Override
    protected Object executeCore(String[] args, Storage storage) {
        String key = args[0];
        String value = args[1];
        long ttl = args.length == 3 ? Long.parseLong(args[2]) : 0;
        storage.set(key, value, ttl);
        return "OK";
    }

    @Override
    public String getType() {
        return "SET";
    }
}
