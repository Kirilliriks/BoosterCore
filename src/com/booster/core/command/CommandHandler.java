package com.booster.core.command;

import com.booster.core.BoosterServer;

import java.util.Arrays;
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

    public boolean dispatchCommand(String commandLine, CommandSender sender){
        String[] args = commandLine.split(" ");
        Command command = commands.get(args[0]);
        if (args.length > 1) args = Arrays.copyOfRange(args, 1, args.length);
        if (command == null) return false;
        command.execute(args, sender);
        return true;
    }
}
