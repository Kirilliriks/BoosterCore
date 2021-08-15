package com.booster.core.command;

import com.booster.api.command.Command;
import com.booster.api.command.CommandSender;
import com.booster.api.entity.Entity;
import com.booster.core.BoosterServer;

public class BoosterCommand extends Command {

    private final BoosterServer boosterServer;

    public BoosterCommand(BoosterServer boosterServer) {
        super("booster");
        this.boosterServer = boosterServer;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("Booster version: " + boosterServer.getBoosterVersion());
        if (sender instanceof Entity) sender.sendMessage(((Entity)sender).getPosition().toString());
    }
}
