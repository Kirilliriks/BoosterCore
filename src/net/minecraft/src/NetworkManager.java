package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;
import java.net.*;
import java.util.*;

public class NetworkManager
{

    public NetworkManager(Socket socket, String s, NetHandler nethandler) throws IOException
    {
        sendQueueLock = new Object();
        isRunning = true;
        readPackets = Collections.synchronizedList(new ArrayList());
        dataPackets = Collections.synchronizedList(new ArrayList());
        chunkDataPackets = Collections.synchronizedList(new ArrayList());
        isServerTerminating = false;
        isTerminating = false;
        terminationReason = "";
        timeSinceLastRead = 0;
        sendQueueByteLength = 0;
        chunkDataSendCounter = 0;
        field_20175_w = 50;
        networkSocket = socket;
        remoteSocketAddress = socket.getRemoteSocketAddress();
        netHandler = nethandler;
        try
        {
            socket.setTrafficClass(24);
        }
        catch(SocketException socketexception)
        {
            System.err.println(socketexception.getMessage());
        }
        socketInputStream = new DataInputStream(socket.getInputStream());
        socketOutputStream = new DataOutputStream(socket.getOutputStream());
        readThread = new ThreadMonitorConnection(this, (new StringBuilder()).append(s).append(" read thread").toString());
        writeThread = new NetworkReaderThread(this, (new StringBuilder()).append(s).append(" write thread").toString());
        readThread.start();
        writeThread.start();
    }

    public void setNetHandler(NetHandler nethandler)
    {
        netHandler = nethandler;
    }

    public void addToSendQueue(Packet packet)
    {
        if(isServerTerminating)
        {
            return;
        }
        synchronized(sendQueueLock)
        {
            sendQueueByteLength += packet.getPacketSize() + 1;
            if(packet.isChunkDataPacket)
            {
                chunkDataPackets.add(packet);
            } else
            {
                dataPackets.add(packet);
            }
        }
    }

    private void sendPacket()
    {
        try
        {
            boolean flag = true;
            if(!dataPackets.isEmpty() && (chunkDataSendCounter == 0 || System.currentTimeMillis() - ((Packet)dataPackets.get(0)).creationTimeMillis >= (long)chunkDataSendCounter))
            {
                flag = false;
                Packet packet;
                synchronized(sendQueueLock)
                {
                    packet = (Packet)dataPackets.remove(0);
                    sendQueueByteLength -= packet.getPacketSize() + 1;
                }
                Packet.writePacket(packet, socketOutputStream);
            }
            if((flag || field_20175_w-- <= 0) && !chunkDataPackets.isEmpty() && (chunkDataSendCounter == 0 || System.currentTimeMillis() - ((Packet)chunkDataPackets.get(0)).creationTimeMillis >= (long)chunkDataSendCounter))
            {
                flag = false;
                Packet packet1;
                synchronized(sendQueueLock)
                {
                    packet1 = (Packet)chunkDataPackets.remove(0);
                    sendQueueByteLength -= packet1.getPacketSize() + 1;
                }
                Packet.writePacket(packet1, socketOutputStream);
                field_20175_w = 50;
            }
            if(flag)
            {
                Thread.sleep(10L);
            }
        }
        catch(InterruptedException interruptedexception) { }
        catch(Exception exception)
        {
            if(!isTerminating)
            {
                onNetworkError(exception);
            }
        }
    }

    private void readPacket()
    {
        try
        {
            Packet packet = Packet.readPacket(socketInputStream);
            if(packet != null)
            {
                readPackets.add(packet);
            } else
            {
                networkShutdown("disconnect.endOfStream", new Object[0]);
            }
        }
        catch(Exception exception)
        {
            if(!isTerminating)
            {
                onNetworkError(exception);
            }
        }
    }

    private void onNetworkError(Exception exception)
    {
        exception.printStackTrace();
        networkShutdown("disconnect.genericReason", new Object[] {
            (new StringBuilder()).append("Internal exception: ").append(exception.toString()).toString()
        });
    }

    public void networkShutdown(String s, Object aobj[])
    {
        if(!isRunning)
        {
            return;
        }
        isTerminating = true;
        terminationReason = s;
        field_20176_t = aobj;
        (new BlockSponge(this)).start();
        isRunning = false;
        try
        {
            socketInputStream.close();
            socketInputStream = null;
        }
        catch(Throwable throwable) { }
        try
        {
            socketOutputStream.close();
            socketOutputStream = null;
        }
        catch(Throwable throwable1) { }
        try
        {
            networkSocket.close();
            networkSocket = null;
        }
        catch(Throwable throwable2) { }
    }

    public void processReadPackets()
    {
        if(sendQueueByteLength > 0x100000)
        {
            networkShutdown("disconnect.overflow", new Object[0]);
        }
        if(readPackets.isEmpty())
        {
            if(timeSinceLastRead++ == 1200)
            {
                networkShutdown("disconnect.timeout", new Object[0]);
            }
        } else
        {
            timeSinceLastRead = 0;
        }
        Packet packet;
        for(int i = 100; !readPackets.isEmpty() && i-- >= 0; packet.processPacket(netHandler))
        {
            packet = (Packet)readPackets.remove(0);
        }

        if(isTerminating && readPackets.isEmpty())
        {
            netHandler.handleErrorMessage(terminationReason, field_20176_t);
        }
    }

    public SocketAddress getRemoteAddress()
    {
        return remoteSocketAddress;
    }

    public void serverShutdown()
    {
        isServerTerminating = true;
        readThread.interrupt();
        (new NetworkMasterThread(this)).start();
    }

    public int getNumChunkDataPackets()
    {
        return chunkDataPackets.size();
    }

    static boolean isRunning(NetworkManager networkmanager)
    {
        return networkmanager.isRunning;
    }

    static boolean isServerTerminating(NetworkManager networkmanager)
    {
        return networkmanager.isServerTerminating;
    }

    static void readNetworkPacket(NetworkManager networkmanager)
    {
        networkmanager.readPacket();
    }

    static void sendNetworkPacket(NetworkManager networkmanager)
    {
        networkmanager.sendPacket();
    }

    static Thread getReadThread(NetworkManager networkmanager)
    {
        return networkmanager.readThread;
    }

    static Thread getWriteThread(NetworkManager networkmanager)
    {
        return networkmanager.writeThread;
    }

    public static final Object threadSyncObject = new Object();
    public static int numReadThreads;
    public static int numWriteThreads;
    private Object sendQueueLock;
    private Socket networkSocket;
    private final SocketAddress remoteSocketAddress;
    private DataInputStream socketInputStream;
    private DataOutputStream socketOutputStream;
    private boolean isRunning;
    private List readPackets;
    private List dataPackets;
    private List chunkDataPackets;
    private NetHandler netHandler;
    private boolean isServerTerminating;
    private Thread writeThread;
    private Thread readThread;
    private boolean isTerminating;
    private String terminationReason;
    private Object field_20176_t[];
    private int timeSinceLastRead;
    private int sendQueueByteLength;
    public int chunkDataSendCounter;
    private int field_20175_w;

}
