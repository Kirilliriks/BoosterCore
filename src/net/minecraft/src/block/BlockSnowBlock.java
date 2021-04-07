package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import net.minecraft.server.MinecraftServer;

public final class BlockSnowBlock extends WindowAdapter
{

    public BlockSnowBlock(MinecraftServer minecraftserver)
    {
        super();
        field_26742_a = minecraftserver;
    }

    public void windowClosing(WindowEvent windowevent)
    {
        field_26742_a.initiateShutdown();
        while(!field_26742_a.serverStopped) 
        {
            try
            {
                Thread.sleep(100L);
            }
            catch(InterruptedException interruptedexception)
            {
                interruptedexception.printStackTrace();
            }
        }
        System.exit(0);
    }

    final MinecraftServer field_26742_a; /* synthetic field */
}
