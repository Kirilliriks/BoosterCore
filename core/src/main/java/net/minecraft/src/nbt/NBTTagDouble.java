package net.minecraft.src.nbt;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class NBTTagDouble extends NBTBase
{

    public NBTTagDouble()
    {
    }

    public NBTTagDouble(double d)
    {
        doubleValue = d;
    }

    public void writeTagContents(DataOutput dataoutput) throws IOException
    {
        dataoutput.writeDouble(doubleValue);
    }

    public void readTagContents(DataInput datainput) throws IOException
    {
        doubleValue = datainput.readDouble();
    }

    public byte getType()
    {
        return 6;
    }

    public String toString()
    {
        return (new StringBuilder()).append("").append(doubleValue).toString();
    }

    public double doubleValue;
}
