package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class StatCrafting extends Statistic
{

    public StatCrafting(int i, String s, int j)
    {
        super(i, s);
        field_25065_a = j;
    }

    public int func_25064_b()
    {
        return field_25065_a;
    }

    private final int field_25065_a;
}
