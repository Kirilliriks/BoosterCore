package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.entity.EntityHuman;
import net.minecraft.src.material.Material;
import net.minecraft.src.world.World;
import net.minecraft.src.achievement.AchievementList;

import java.util.Random;

public class BlockLog extends Block
{

    protected BlockLog(int i)
    {
        super(i, Material.wood);
        blockIndexInTexture = 20;
    }

    public int quantityDropped(Random random)
    {
        return 1;
    }

    public int idDropped(int i, Random random)
    {
        return Block.wood.blockID;
    }

    public void harvestBlock(World world, EntityHuman entityplayer, int i, int j, int k, int l)
    {
        super.harvestBlock(world, entityplayer, i, j, k, l);
        entityplayer.func_26604_a(AchievementList.field_25131_c, 1);
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        byte byte0 = 4;
        int l = byte0 + 1;
        if(world.checkChunksExist(i - l, j - l, k - l, i + l, j + l, k + l))
        {
            for(int i1 = -byte0; i1 <= byte0; i1++)
            {
                for(int j1 = -byte0; j1 <= byte0; j1++)
                {
                    for(int k1 = -byte0; k1 <= byte0; k1++)
                    {
                        int l1 = world.getBlockId(i + i1, j + j1, k + k1);
                        if(l1 != Block.leaves.blockID)
                        {
                            continue;
                        }
                        int i2 = world.getBlockMetadata(i + i1, j + j1, k + k1);
                        if((i2 & 4) == 0)
                        {
                            world.setBlockMetadata(i + i1, j + j1, k + k1, i2 | 4);
                        }
                    }

                }

            }

        }
    }

    public int func_22009_a(int i, int j)
    {
        if(i == 1)
        {
            return 21;
        }
        if(i == 0)
        {
            return 21;
        }
        if(j == 1)
        {
            return 116;
        }
        return j != 2 ? 20 : 117;
    }

    protected int damageDropped(int i)
    {
        return i;
    }
}
