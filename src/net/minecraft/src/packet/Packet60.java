package net.minecraft.src.packet;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.chunk.ChunkPosition;
import net.minecraft.src.network.NetHandler;

import java.io.*;
import java.util.*;

public class Packet60 extends Packet
{

    public Packet60()
    {
    }

    public Packet60(double d, double d1, double d2, float f, 
            Set set)
    {
        explosionX = d;
        explosionY = d1;
        explosionZ = d2;
        explosionSize = f;
        destroyedBlockPositions = new HashSet(set);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        explosionX = datainputstream.readDouble();
        explosionY = datainputstream.readDouble();
        explosionZ = datainputstream.readDouble();
        explosionSize = datainputstream.readFloat();
        int i = datainputstream.readInt();
        destroyedBlockPositions = new HashSet();
        int j = (int)explosionX;
        int k = (int)explosionY;
        int l = (int)explosionZ;
        for(int i1 = 0; i1 < i; i1++)
        {
            int j1 = datainputstream.readByte() + j;
            int k1 = datainputstream.readByte() + k;
            int l1 = datainputstream.readByte() + l;
            destroyedBlockPositions.add(new ChunkPosition(j1, k1, l1));
        }

    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeDouble(explosionX);
        dataoutputstream.writeDouble(explosionY);
        dataoutputstream.writeDouble(explosionZ);
        dataoutputstream.writeFloat(explosionSize);
        dataoutputstream.writeInt(destroyedBlockPositions.size());
        int i = (int)explosionX;
        int j = (int)explosionY;
        int k = (int)explosionZ;
        int j1;
        for(Iterator iterator = destroyedBlockPositions.iterator(); iterator.hasNext(); dataoutputstream.writeByte(j1))
        {
            ChunkPosition worldproviderhell = (ChunkPosition)iterator.next();
            int l = worldproviderhell.field_26708_a - i;
            int i1 = worldproviderhell.field_26707_b - j;
            j1 = worldproviderhell.field_26709_c - k;
            dataoutputstream.writeByte(l);
            dataoutputstream.writeByte(i1);
        }

    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_12001_a(this);
    }

    public int getPacketSize()
    {
        return 32 + destroyedBlockPositions.size() * 3;
    }

    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public float explosionSize;
    public Set destroyedBlockPositions;
}
