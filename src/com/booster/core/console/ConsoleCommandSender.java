package com.booster.core.console;

import com.booster.core.BoosterServer;
import com.booster.core.command.CommandSender;

public class ConsoleCommandSender implements CommandSender {

    private final BoosterServer boosterServer;

    public ConsoleCommandSender(BoosterServer boosterServer){
        this.boosterServer = boosterServer;
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }
}
