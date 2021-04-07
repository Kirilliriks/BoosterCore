package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.network.NetHandler;

import java.io.*;

public class EntityEgg extends Packet
{

    public EntityEgg()
    {
    }

    public EntityEgg(int i, int j, int k, int l, int i1)
    {
        shake = i;
        xTile = j;
        yTile = k;
        zTile = l;
        inTile = i1;
    }

    public void readPacketData(DataInputStream datainputstream)
    {
        try {
            shake = datainputstream.readInt();
            xTile = datainputstream.readShort();
            yTile = datainputstream.readInt();
            zTile = datainputstream.read();
            inTile = datainputstream.read();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void writePacketData(DataOutputStream dataoutputstream)
    {
        try {
            dataoutputstream.writeInt(shake);
            dataoutputstream.writeShort(xTile);
            dataoutputstream.writeInt(yTile);
            dataoutputstream.write(zTile);
            dataoutputstream.write(inTile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_26501_a(this);
    }

    public int getPacketSize()
    {
        return 12;
    }

    public int shake;
    public int xTile;
    public int yTile;
    public int zTile;
    public int inTile;
}
