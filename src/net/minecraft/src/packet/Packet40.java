package net.minecraft.src.packet;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.DataWatcher;
import net.minecraft.src.network.NetHandler;

import java.io.*;
import java.util.List;

public class Packet40 extends Packet
{

    public Packet40()
    {
    }

    public Packet40(int i, DataWatcher datawatcher)
    {
        entityId = i;
        field_21018_b = datawatcher.getChangedObjects();
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        entityId = datainputstream.readInt();
        field_21018_b = DataWatcher.readWatchableObjects(datainputstream);
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(entityId);
        DataWatcher.writeObjectsInListToStream(field_21018_b, dataoutputstream);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_21002_a(this);
    }

    public int getPacketSize()
    {
        return 5;
    }

    public int entityId;
    private List field_21018_b;
}
