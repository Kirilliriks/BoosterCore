package net.minecraft.src.packet;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.entity.Entity;
import net.minecraft.src.network.NetHandler;

import java.io.*;

public class Packet28 extends Packet
{

    public Packet28()
    {
    }

    public Packet28(Entity entity)
    {
        this(entity.entityId, entity.motionX, entity.motionY, entity.motionZ);
    }

    public Packet28(int i, double d, double d1, double d2)
    {
        entityId = i;
        double d3 = 3.8999999999999999D;
        if(d < -d3)
        {
            d = -d3;
        }
        if(d1 < -d3)
        {
            d1 = -d3;
        }
        if(d2 < -d3)
        {
            d2 = -d3;
        }
        if(d > d3)
        {
            d = d3;
        }
        if(d1 > d3)
        {
            d1 = d3;
        }
        if(d2 > d3)
        {
            d2 = d3;
        }
        motionX = (int)(d * 8000D);
        motionY = (int)(d1 * 8000D);
        motionZ = (int)(d2 * 8000D);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        entityId = datainputstream.readInt();
        motionX = datainputstream.readShort();
        motionY = datainputstream.readShort();
        motionZ = datainputstream.readShort();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(entityId);
        dataoutputstream.writeShort(motionX);
        dataoutputstream.writeShort(motionY);
        dataoutputstream.writeShort(motionZ);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_6002_a(this);
    }

    public int getPacketSize()
    {
        return 10;
    }

    public int entityId;
    public int motionX;
    public int motionY;
    public int motionZ;
}
