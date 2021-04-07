package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class ItemInWorldManager extends Packet
{

    public ItemInWorldManager()
    {
    }

    public ItemInWorldManager(String s)
    {
        thisPlayer = s;
    }

    public void readPacketData(DataInputStream datainputstream)
    {
        try {
            thisPlayer = datainputstream.readUTF();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void writePacketData(DataOutputStream dataoutputstream)
    {
        try {
            dataoutputstream.writeUTF(thisPlayer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_26503_a(this);
    }

    public int getPacketSize()
    {
        return thisPlayer.length();
    }

    public String thisPlayer;
}
