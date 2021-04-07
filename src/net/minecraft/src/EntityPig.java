package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class EntityPig
{

    public EntityPig(int i)
    {
        field_26706_a = new byte[i >> 1];
    }

    public EntityPig(byte abyte0[])
    {
        field_26706_a = abyte0;
    }

    public int func_26705_a(int i, int j, int k)
    {
        int l = i << 11 | k << 7 | j;
        int i1 = l >> 1;
        int j1 = l & 1;
        if(j1 == 0)
        {
            return field_26706_a[i1] & 0xf;
        } else
        {
            return field_26706_a[i1] >> 4 & 0xf;
        }
    }

    public void func_26704_a(int i, int j, int k, int l)
    {
        int i1 = i << 11 | k << 7 | j;
        int j1 = i1 >> 1;
        int k1 = i1 & 1;
        if(k1 == 0)
        {
            field_26706_a[j1] = (byte)(field_26706_a[j1] & 0xf0 | l & 0xf);
        } else
        {
            field_26706_a[j1] = (byte)(field_26706_a[j1] & 0xf | (l & 0xf) << 4);
        }
    }

    public boolean func_26703_a()
    {
        return field_26706_a != null;
    }

    public final byte field_26706_a[];
}
