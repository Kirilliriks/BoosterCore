package com.booster.core.command;

import com.booster.api.command.Command;
import com.booster.api.command.CommandSender;
import com.booster.core.BoosterServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class CommandHandler {

    private final Map<String, Command> commands;

    public CommandHandler(){
        commands = new HashMap<>();
    }

    public void registerCommand(Command command){
        commands.put(command.getName(), command);
        BoosterServer.logger.info("Registered command " + command.getName());
    }

    public boolean dispatchCommand(String commandLine, CommandSender sender){
        String[] args = commandLine.split(" ");
        final Command command = commands.get(args[0]);

        if (args.length > 1) args = Arrays.copyOfRange(args, 1, args.length);
        if (command == null) return false;

        command.execute(sender, args);
        return true;
    }
}
