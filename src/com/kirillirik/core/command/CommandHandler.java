package com.kirillirik.core.command;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private final Map<String, Command> commands;

    public CommandHandler(){
        this.commands = new HashMap<>();
    }

    public void registerCommand(Command command){
        this.commands.put(command.getName(), command);
    }
}
