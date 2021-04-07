package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class Packet54 extends Item
{

    public Packet54(int i, int j, int k, int l)
    {
        super(i);
        xLocation = j;
        field_26512_bi = l;
        field_26510_bk = k;
        field_26511_bj = field_26514_bl[l];
        setMaxDamage(field_26513_bm[l] * 3 << j);
        maxStackSize = 1;
    }

    private static final int field_26514_bl[] = {
        3, 8, 6, 3
    };
    private static final int field_26513_bm[] = {
        11, 16, 15, 13
    };
    public final int xLocation;
    public final int field_26512_bi;
    public final int field_26511_bj;
    public final int field_26510_bk;

}
