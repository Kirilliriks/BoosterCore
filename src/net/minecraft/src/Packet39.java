package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.network.NetHandler;

import java.io.*;

public class Packet39 extends Packet
{

    public Packet39()
    {
    }

    public Packet39(Entity entity, Entity entity1)
    {
        entityId = entity.entityId;
        vehicleEntityId = entity1 == null ? -1 : entity1.entityId;
    }

    public int getPacketSize()
    {
        return 8;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        entityId = datainputstream.readInt();
        vehicleEntityId = datainputstream.readInt();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(entityId);
        dataoutputstream.writeInt(vehicleEntityId);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_6003_a(this);
    }

    public int entityId;
    public int vehicleEntityId;
}
