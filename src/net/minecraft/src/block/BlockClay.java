package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.item.Item;
import net.minecraft.src.material.Material;

import java.util.Random;

public class BlockClay extends Block
{

    public BlockClay(int i, int j)
    {
        super(i, j, Material.clay);
    }

    public int idDropped(int i, Random random)
    {
        return Item.clay.shiftedIndex;
    }

    public int quantityDropped(Random random)
    {
        return 4;
    }
}
