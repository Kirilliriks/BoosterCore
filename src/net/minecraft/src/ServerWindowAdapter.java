package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.block.Block;

import java.util.Random;

public class ServerWindowAdapter extends Block
{

    public ServerWindowAdapter(int i, int j)
    {
        super(i, j, Material.builtSnow);
        func_26519_a(true);
    }

    public int idDropped(int i, Random random)
    {
        return Item.snowball.shiftedIndex;
    }

    public int quantityDropped(Random random)
    {
        return 4;
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(world.getSavedLightValue(EnumSkyBlock.Block, i, j, k) > 11)
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
        }
    }
}
