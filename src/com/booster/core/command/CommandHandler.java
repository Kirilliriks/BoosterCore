package com.booster.core.command;

import com.booster.core.BoosterServer;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private final Map<String, Command> commands;

    public CommandHandler(){
        this.commands = new HashMap<>();
    }

    public void registerCommand(Command command){
        this.commands.put(command.getName(), command);
        BoosterServer.logger.info("Registered command " + command.getName());
    }

    public boolean dispatchCommand(String commandName, CommandSender sender){
        Command command = commands.get(commandName);
        if (command == null) return false;
        command.execute(sender);
        return true;
    }
}
