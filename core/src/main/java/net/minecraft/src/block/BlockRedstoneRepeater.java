package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.material.Material;
import net.minecraft.src.world.World;

import java.util.Random;

public class BlockRedstoneRepeater extends Block
{

    protected BlockRedstoneRepeater(int i)
    {
        super(i, Material.ground);
        blockIndexInTexture = 3;
        func_26519_a(true);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(world.singleplayerWorld)
        {
            return;
        }
        if(world.getBlockLightValue(i, j + 1, k) < 4 && world.getBlockMaterial(i, j + 1, k).getCanBlockGrass())
        {
            if(random.nextInt(4) != 0)
            {
                return;
            }
            world.setBlockWithNotify(i, j, k, Block.dirt.blockID);
        } else
        if(world.getBlockLightValue(i, j + 1, k) >= 9)
        {
            int l = (i + random.nextInt(3)) - 1;
            int i1 = (j + random.nextInt(5)) - 3;
            int j1 = (k + random.nextInt(3)) - 1;
            if(world.getBlockId(l, i1, j1) == Block.dirt.blockID && world.getBlockLightValue(l, i1 + 1, j1) >= 4 && !world.getBlockMaterial(l, i1 + 1, j1).getCanBlockGrass())
            {
                world.setBlockWithNotify(l, i1, j1, Block.grass.blockID);
            }
        }
    }

    public int idDropped(int i, Random random)
    {
        return Block.dirt.idDropped(0, random);
    }
}
