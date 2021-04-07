package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.network.NetworkManager;

public class BlockSponge extends Thread
{

    public BlockSponge(NetworkManager networkmanager)
    {
        super();
        field_26663_a = networkmanager;
    }

    public void run()
    {
        try
        {
            Thread.sleep(5000L);
            if(NetworkManager.getReadThread(field_26663_a).isAlive())
            {
                try
                {
                    NetworkManager.getReadThread(field_26663_a).stop();
                }
                catch(Throwable throwable) { }
            }
            if(NetworkManager.getWriteThread(field_26663_a).isAlive())
            {
                try
                {
                    NetworkManager.getWriteThread(field_26663_a).stop();
                }
                catch(Throwable throwable1) { }
            }
        }
        catch(InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }
    }

    final NetworkManager field_26663_a; /* synthetic field */
}
