package com.booster.core;

import com.booster.api.Server;
import com.booster.core.command.BoosterCommand;
import com.booster.core.command.CommandHandler;
import com.booster.core.command.CommandSender;
import com.booster.core.plugin.PluginManager;
import net.minecraft.server.MinecraftServer;

import java.util.logging.Logger;

public class BoosterServer implements Server {

    public static Logger logger;
    private final MinecraftServer server;
    private final PluginManager pluginManager;

    private final CommandHandler commandHandler;
    private final String boosterVersion = "0.1";

    public BoosterServer(MinecraftServer server){
        this.server = server;
        logger = MinecraftServer.logger;

        this.pluginManager = new PluginManager();
        this.commandHandler = new CommandHandler();
        this.commandHandler.registerCommand(new BoosterCommand(this));

        logger.info( "This server running on Booster version " + boosterVersion);
    }

    public boolean dispatchCommand(String command, CommandSender sender){
        return commandHandler.dispatchCommand(command, sender);
    }

    public String getBoosterVersion() {
        return boosterVersion;
    }
}
