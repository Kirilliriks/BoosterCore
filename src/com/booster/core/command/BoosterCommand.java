package com.booster.core.command;

import com.booster.core.BoosterServer;

public class BoosterCommand extends Command {

    private final BoosterServer boosterServer;

    public BoosterCommand(BoosterServer boosterServer) {
        super("booster");
        this.boosterServer = boosterServer;
    }

    @Override
    public void execute(CommandSender sender) {
        sender.sendMessage("Booster version: " + boosterServer.getBoosterVersion());
    }
}
