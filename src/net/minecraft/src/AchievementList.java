package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;
import java.util.List;

public class AchievementList
{

    public AchievementList()
    {
    }

    public static List field_25129_a = new ArrayList();
    public static Achievement field_25128_b;
    public static Achievement field_25131_c;
    public static Achievement field_25130_d;

    static 
    {
        field_25128_b = new Achievement(0x500000, StatCollector.func_25136_a("achievement.openInventory"), 0, 0, null);
        field_25131_c = new Achievement(0x500001, StatCollector.func_25136_a("achievement.mineWood"), 4, 1, field_25128_b);
        field_25130_d = new Achievement(0x500001, StatCollector.func_25136_a("achievement.buildWorkBench"), 8, -1, field_25131_c);
    }
}
