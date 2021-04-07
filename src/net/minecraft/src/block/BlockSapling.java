package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.item.ItemArmor;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenBigTree;
import net.minecraft.src.WorldGenerator;

import java.util.Random;

public class BlockSapling extends ItemArmor
{

    protected BlockSapling(int i, int j)
    {
        super(i, j);
        float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        super.updateTick(world, i, j, k, random);
        if(world.getBlockLightValue(i, j + 1, k) >= 9 && random.nextInt(5) == 0)
        {
            int l = world.getBlockMetadata(i, j, k);
            if(l < 15)
            {
                world.setBlockMetadataWithNotify(i, j, k, l + 1);
            } else
            {
                func_21027_b(world, i, j, k, random);
            }
        }
    }

    public void func_21027_b(World world, int i, int j, int k, Random random)
    {
        world.setBlock(i, j, k, 0);
        Object obj = new BlockBloodStone();
        if(random.nextInt(10) == 0)
        {
            obj = new WorldGenBigTree();
        }
        if(!((WorldGenerator) (obj)).generate(world, random, i, j, k))
        {
            world.setBlock(i, j, k, blockID);
        }
    }
}
