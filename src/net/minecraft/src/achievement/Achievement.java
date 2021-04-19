package net.minecraft.src.achievement;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.Statistic;

public class Achievement extends Statistic
{

    public Achievement(int i, String s, int j, int k, Achievement achievement)
    {
        super(i, s);
        field_25067_a = j + 46;
        field_25066_b = k + 23;
        field_25068_c = achievement;
    }

    public boolean func_25060_a()
    {
        return true;
    }

    public final int field_25067_a;
    public final int field_25066_b;
    public final Achievement field_25068_c;
}
