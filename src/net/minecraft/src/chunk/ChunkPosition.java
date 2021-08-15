package net.minecraft.src.chunk;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ChunkPosition
{

    public ChunkPosition(int i, int j, int k)
    {
        field_26708_a = i;
        field_26707_b = j;
        field_26709_c = k;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof ChunkPosition)
        {
            ChunkPosition worldproviderhell = (ChunkPosition)obj;
            return worldproviderhell.field_26708_a == field_26708_a && worldproviderhell.field_26707_b == field_26707_b && worldproviderhell.field_26709_c == field_26709_c;
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return field_26708_a * 0x88f9fa + field_26707_b * 0xef88b + field_26709_c;
    }

    public final int field_26708_a;
    public final int field_26707_b;
    public final int field_26709_c;
}
