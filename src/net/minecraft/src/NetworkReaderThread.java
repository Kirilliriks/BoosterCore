package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


class NetworkReaderThread extends Thread
{

    NetworkReaderThread(NetworkManager networkmanager, String s)
    {
        super(s);
        netManager = networkmanager;
    }

    public void run()
    {
        synchronized(NetworkManager.threadSyncObject)
        {
            NetworkManager.numWriteThreads++;
        }
        for(; NetworkManager.isRunning(netManager); NetworkManager.sendNetworkPacket(netManager)) { }
        synchronized(NetworkManager.threadSyncObject)
        {
            NetworkManager.numWriteThreads--;
        }
        synchronized(NetworkManager.threadSyncObject)
        {
            NetworkManager.numWriteThreads--;
        }
    }

    final NetworkManager netManager; /* synthetic field */
}
