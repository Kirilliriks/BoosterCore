package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.logging.*;

public class ConsoleLogManager
{

    public ConsoleLogManager()
    {
    }

    public static void init()
    {
        HashEntry hashentry = new HashEntry();
        logger.setUseParentHandlers(false);
        ConsoleHandler consolehandler = new ConsoleHandler();
        consolehandler.setFormatter(hashentry);
        logger.addHandler(consolehandler);
        try
        {
            FileHandler filehandler = new FileHandler("server.log", true);
            filehandler.setFormatter(hashentry);
            logger.addHandler(filehandler);
        }
        catch(Exception exception)
        {
            logger.log(Level.WARNING, "Failed to log to server.log", exception);
        }
    }

    public static Logger logger = Logger.getLogger("Minecraft");

}
