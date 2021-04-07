package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public class ConvertProgressUpdater
    implements EntityPigZombie
{

    public ConvertProgressUpdater(MinecraftServer minecraftserver)
    {
    	super();
        mcServer = minecraftserver;
        lastTimeMillis = System.currentTimeMillis();
    }

    public void func_438_a(String s)
    {
    }

    public void setLoadingProgress(int i)
    {
        if(System.currentTimeMillis() - lastTimeMillis >= 1000L)
        {
            lastTimeMillis = System.currentTimeMillis();
            MinecraftServer.logger.info((new StringBuilder()).append("Converting... ").append(i).append("%").toString());
        }
    }

    public void displayLoadingString(String s)
    {
    }

    private long lastTimeMillis;
    final MinecraftServer mcServer; /* synthetic field */
}
