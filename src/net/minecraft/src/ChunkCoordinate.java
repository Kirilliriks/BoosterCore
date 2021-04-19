package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ChunkCoordinate
{

    public ChunkCoordinate(int i, int j)
    {
        field_26507_a = i;
        field_26506_b = j;
    }

    public static int func_26505_a(int i, int j)
    {
        return (i >= 0 ? 0 : 0x80000000) | (i & 0x7fff) << 16 | (j >= 0 ? 0 : 0x8000) | j & 0x7fff;
    }

    public int hashCode()
    {
        return func_26505_a(field_26507_a, field_26506_b);
    }

    public boolean equals(Object obj)
    {
        ChunkCoordinate chunkCoordinate = (ChunkCoordinate)obj;
        return chunkCoordinate.field_26507_a == field_26507_a && chunkCoordinate.field_26506_b == field_26506_b;
    }

    public final int field_26507_a;
    public final int field_26506_b;
}
