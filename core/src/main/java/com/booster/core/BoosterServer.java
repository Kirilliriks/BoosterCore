package com.booster.core;

import com.booster.api.Server;
import com.booster.core.command.VersionCommand;
import com.booster.core.command.CommandHandler;
import com.booster.api.command.CommandSender;
import com.booster.core.plugin.PluginManager;
import net.minecraft.server.MinecraftServer;

import java.util.logging.Logger;

public class BoosterServer implements Server {

    public static Logger logger;
    private final MinecraftServer server;
    private final PluginManager pluginManager;

    private final CommandHandler commandHandler;
    private final String boosterVersion = "0.1";

    public BoosterServer(MinecraftServer server) {
        this.server = server;
        logger = MinecraftServer.logger;
        logger.info("This server running on Booster version " + boosterVersion);

        commandHandler = new CommandHandler();
        commandHandler.registerCommand(new VersionCommand(this));
        pluginManager = new PluginManager(this);
    }

    public void stopServer(){
        pluginManager.disablePlugins();
    }

    public boolean dispatchCommand(String command, CommandSender sender){
        return commandHandler.dispatchCommand(command, sender);
    }

    public String getBoosterVersion() {
        return boosterVersion;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
