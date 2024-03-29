package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.world.World;
import net.minecraft.src.world.WorldGenerator;

import java.util.Random;

public class BlockLever extends WorldGenerator
{

    public BlockLever()
    {
    }

    public boolean generate(World world, Random random, int i, int j, int k)
    {
        for(int l = 0; l < 64; l++)
        {
            int i1 = (i + random.nextInt(8)) - random.nextInt(8);
            int j1 = (j + random.nextInt(4)) - random.nextInt(4);
            int k1 = (k + random.nextInt(8)) - random.nextInt(8);
            if(world.isAirBlock(i1, j1, k1) && world.getBlockId(i1, j1 - 1, k1) == Block.bloodStone.blockID)
            {
                world.setBlockWithNotify(i1, j1, k1, Block.fire.blockID);
            }
        }

        return true;
    }
}
