package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class Statistic
{

    public Statistic(int i, String s)
    {
        field_26629_d = i;
        field_26628_e = s;
    }

    public Statistic func_26626_c()
    {
        StatList.func_26736_a(this);
        return this;
    }

    public boolean func_25060_a()
    {
        return false;
    }

    public final int field_26629_d;
    public final String field_26628_e;
    public String field_26627_f;
}
