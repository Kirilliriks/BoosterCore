package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.item.Item;

import java.util.Random;

public class BlockGravel extends BlockSand
{

    public BlockGravel(int i, int j)
    {
        super(i, j);
    }

    public int idDropped(int i, Random random)
    {
        if(random.nextInt(10) == 0)
        {
            return Item.flint.shiftedIndex;
        } else
        {
            return blockID;
        }
    }
}
