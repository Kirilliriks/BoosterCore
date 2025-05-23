package net.minecraft.src.packet;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


class PacketCounter
{

    private PacketCounter()
    {
    }

    public void addPacket(int i)
    {
        totalPackets++;
        totalBytes += i;
    }

    PacketCounter(Empty1 empty1)
    {
        this();
    }

    private int totalPackets;
    private long totalBytes;
}
