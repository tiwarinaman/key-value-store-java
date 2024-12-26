package com.naman.keyvaluestore.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static final CommandFactory INSTANCE = new CommandFactory();
    private final Map<String, BaseCommand> commandMap;

    private CommandFactory() {
        commandMap = new HashMap<>();
        commandMap.put("SET", new SetCommand());
        commandMap.put("GET", new GetCommand());
    }

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    public BaseCommand getCommand(String commandName) {
        return commandMap.get(commandName.toUpperCase());
    }

}
