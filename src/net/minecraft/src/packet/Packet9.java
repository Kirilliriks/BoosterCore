package net.minecraft.src.packet;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.network.NetHandler;

import java.io.*;

public class Packet9 extends Packet
{

    public Packet9()
    {
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleRespawnPacket(this);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
    }

    public int getPacketSize()
    {
        return 0;
    }
}
