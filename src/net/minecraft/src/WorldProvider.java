package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class WorldProvider extends NBTBase
{

    public WorldProvider()
    {
    }

    public WorldProvider(String s)
    {
        worldObj = s;
        if(s == null)
        {
            throw new IllegalArgumentException("Empty string not allowed");
        } else
        {
            return;
        }
    }

    void writeTagContents(DataOutput dataoutput)
    {
        try {
            dataoutput.writeUTF(worldObj);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    void readTagContents(DataInput datainput)
    {
        try {
            worldObj = datainput.readUTF();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public byte getType()
    {
        return 8;
    }

    public String toString()
    {
        return (new StringBuilder()).append("").append(worldObj).toString();
    }

    public String worldObj;
}
