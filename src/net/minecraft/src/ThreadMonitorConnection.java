package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


class ThreadMonitorConnection extends Thread
{

    ThreadMonitorConnection(NetworkManager networkmanager, String s)
    {
        super(s);
        netManager = networkmanager;
    }

    public void run()
    {
        synchronized(NetworkManager.threadSyncObject)
        {
            NetworkManager.numReadThreads++;
        }
        while(NetworkManager.isRunning(netManager) && !NetworkManager.isServerTerminating(netManager)) 
        {
            NetworkManager.readNetworkPacket(netManager);
            try
            {
                sleep(0L);
            }
            catch(InterruptedException interruptedexception) { }
        }
        synchronized(NetworkManager.threadSyncObject)
        {
            NetworkManager.numReadThreads--;
        }
        synchronized(NetworkManager.threadSyncObject)
        {
            NetworkManager.numReadThreads--;
        }
    }

    final NetworkManager netManager; /* synthetic field */
}
