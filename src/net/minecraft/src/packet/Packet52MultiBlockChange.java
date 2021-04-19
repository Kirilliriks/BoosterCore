package net.minecraft.src.packet;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.World;
import net.minecraft.src.chunk.Chunk;
import net.minecraft.src.network.NetHandler;

import java.io.*;


public class Packet52MultiBlockChange extends Packet
{

    public Packet52MultiBlockChange()
    {
        isChunkDataPacket = true;
    }

    public Packet52MultiBlockChange(int i, int j, short aword0[], int k, World world)
    {
        isChunkDataPacket = true;
        field_392_a = i;
        field_391_b = j;
        field_394_ae = k;
        field_395_ad = new short[k];
        field_26546_d = new byte[k];
        field_26545_e = new byte[k];
        Chunk chunk = world.getChunkFromChunkCoords(i, j);
        for(int l = 0; l < k; l++)
        {
            int i1 = aword0[l] >> 12 & 0xf;
            int j1 = aword0[l] >> 8 & 0xf;
            int k1 = aword0[l] & 0xff;
            field_395_ad[l] = aword0[l];
            field_26546_d[l] = (byte)chunk.getBlockID(i1, k1, j1);
            field_26545_e[l] = (byte)chunk.getBlockMetadata(i1, k1, j1);
        }

    }

    public void readPacketData(DataInputStream datainputstream)
    {
        try {
            field_392_a = datainputstream.readInt();
            field_391_b = datainputstream.readInt();
            field_394_ae = datainputstream.readShort() & 0xffff;
            field_395_ad = new short[field_394_ae];
            field_26546_d = new byte[field_394_ae];
            field_26545_e = new byte[field_394_ae];
            for(int i = 0; i < field_394_ae; i++)
            {
                field_395_ad[i] = datainputstream.readShort();
            }

            datainputstream.readFully(field_26546_d);
            datainputstream.readFully(field_26545_e);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void writePacketData(DataOutputStream dataoutputstream)
    {
        try {
            dataoutputstream.writeInt(field_392_a);
            dataoutputstream.writeInt(field_391_b);
            dataoutputstream.writeShort((short)field_394_ae);
            for(int i = 0; i < field_394_ae; i++)
            {
                dataoutputstream.writeShort(field_395_ad[i]);
            }

            dataoutputstream.write(field_26546_d);
            dataoutputstream.write(field_26545_e);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_26504_a(this);
    }

    public int getPacketSize()
    {
        return 10 + field_394_ae * 4;
    }

    public int field_392_a;
    public int field_391_b;
    public short field_395_ad[];
    public byte field_26546_d[];
    public byte field_26545_e[];
    public int field_394_ae;
}
