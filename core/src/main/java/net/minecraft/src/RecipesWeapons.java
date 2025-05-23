package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.block.Block;
import net.minecraft.src.material.Material;
import net.minecraft.src.world.World;

public class RecipesWeapons extends Block
{

    public RecipesWeapons(int i)
    {
        super(i, Material.sponge);
        blockIndexInTexture = 48;
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
        byte byte0 = 2;
        for(int l = i - byte0; l <= i + byte0; l++)
        {
            for(int i1 = j - byte0; i1 <= j + byte0; i1++)
            {
                for(int j1 = k - byte0; j1 <= k + byte0; j1++)
                {
                    if(world.getBlockMaterial(l, i1, j1) != Material.water);
                }

            }

        }

    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        byte byte0 = 2;
        for(int l = i - byte0; l <= i + byte0; l++)
        {
            for(int i1 = j - byte0; i1 <= j + byte0; i1++)
            {
                for(int j1 = k - byte0; j1 <= k + byte0; j1++)
                {
                    world.notifyBlocksOfNeighborChange(l, i1, j1, world.getBlockId(l, i1, j1));
                }

            }

        }

    }
}
