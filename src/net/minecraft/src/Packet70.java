package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.network.NetHandler;

import java.io.*;

public class Packet70 extends Packet
{

    public Packet70()
    {
    }

    public Packet70(int i)
    {
        field_25015_b = i;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        field_25015_b = datainputstream.readByte();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeByte(field_25015_b);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_25001_a(this);
    }

    public int getPacketSize()
    {
        return 1;
    }

    public static final String field_25016_a[] = {
        "tile.bed.notValid"
    };
    public int field_25015_b;

}
