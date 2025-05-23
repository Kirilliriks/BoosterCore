package net.minecraft.src.nbt;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class NBTTagLong extends NBTBase
{

    public NBTTagLong()
    {
    }

    public NBTTagLong(long l)
    {
        longValue = l;
    }

    public void writeTagContents(DataOutput dataoutput) throws IOException
    {
        dataoutput.writeLong(longValue);
    }

    public void readTagContents(DataInput datainput) throws IOException
    {
        longValue = datainput.readLong();
    }

    public byte getType()
    {
        return 4;
    }

    public String toString()
    {
        return (new StringBuilder()).append("").append(longValue).toString();
    }

    public long longValue;
}
