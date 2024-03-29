package net.minecraft.src.packet;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.network.NetHandler;

import java.io.*;

public class Packet7 extends Packet
{

    public Packet7()
    {
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        playerEntityId = datainputstream.readInt();
        targetEntity = datainputstream.readInt();
        isLeftClick = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(playerEntityId);
        dataoutputstream.writeInt(targetEntity);
        dataoutputstream.writeByte(isLeftClick);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_6006_a(this);
    }

    public int getPacketSize()
    {
        return 9;
    }

    public int playerEntityId;
    public int targetEntity;
    public int isLeftClick;
}
