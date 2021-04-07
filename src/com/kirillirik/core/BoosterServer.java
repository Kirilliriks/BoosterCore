package com.kirillirik.core;

import net.minecraft.server.MinecraftServer;

import java.util.logging.Logger;

public class BoosterServer {

    public static Logger logger;

    private final MinecraftServer server;
    private final String boosterVersion = "0.1";

    public BoosterServer(MinecraftServer server){
        this.server = server;
        logger = MinecraftServer.logger;
        logger.info( "This server running on Booster version " + boosterVersion);
    }
}
