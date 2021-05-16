package com.booster.core.command;

public abstract class Command {

    private final String name;

    public Command (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void execute(CommandSender sender, String[] args);
}
