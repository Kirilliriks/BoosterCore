package net.minecraft.src.packet;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.item.ItemStack;
import net.minecraft.src.network.NetHandler;

import java.io.*;

public class Packet103 extends Packet
{

    public Packet103()
    {
    }

    public Packet103(int i, int j, ItemStack itemstack)
    {
        windowId = i;
        itemSlot = j;
        myItemStack = itemstack != null ? itemstack.copy() : itemstack;
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_20003_a(this);
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        windowId = datainputstream.readByte();
        itemSlot = datainputstream.readShort();
        short word0 = datainputstream.readShort();
        if(word0 >= 0)
        {
            byte byte0 = datainputstream.readByte();
            short word1 = datainputstream.readShort();
            myItemStack = new ItemStack(word0, byte0, word1);
        } else
        {
            myItemStack = null;
        }
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeByte(windowId);
        dataoutputstream.writeShort(itemSlot);
        if(myItemStack == null)
        {
            dataoutputstream.writeShort(-1);
        } else
        {
            dataoutputstream.writeShort(myItemStack.itemID);
            dataoutputstream.writeByte(myItemStack.stackSize);
            dataoutputstream.writeShort(myItemStack.getItemDamage());
        }
    }

    public int getPacketSize()
    {
        return 8;
    }

    public int windowId;
    public int itemSlot;
    public ItemStack myItemStack;
}
