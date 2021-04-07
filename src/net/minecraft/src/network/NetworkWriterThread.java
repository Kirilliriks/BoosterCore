package net.minecraft.src.network;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.block.Block;

import java.util.Random;

public class NetworkWriterThread extends WorldGenerator
{

    public NetworkWriterThread(int i)
    {
        netManager = i;
    }

    public boolean generate(World world, Random random, int i, int j, int k)
    {
        if(world.getBlockId(i, j + 1, k) != Block.stone.blockID)
        {
            return false;
        }
        if(world.getBlockId(i, j - 1, k) != Block.stone.blockID)
        {
            return false;
        }
        if(world.getBlockId(i, j, k) != 0 && world.getBlockId(i, j, k) != Block.stone.blockID)
        {
            return false;
        }
        int l = 0;
        if(world.getBlockId(i - 1, j, k) == Block.stone.blockID)
        {
            l++;
        }
        if(world.getBlockId(i + 1, j, k) == Block.stone.blockID)
        {
            l++;
        }
        if(world.getBlockId(i, j, k - 1) == Block.stone.blockID)
        {
            l++;
        }
        if(world.getBlockId(i, j, k + 1) == Block.stone.blockID)
        {
            l++;
        }
        int i1 = 0;
        if(world.isAirBlock(i - 1, j, k))
        {
            i1++;
        }
        if(world.isAirBlock(i + 1, j, k))
        {
            i1++;
        }
        if(world.isAirBlock(i, j, k - 1))
        {
            i1++;
        }
        if(world.isAirBlock(i, j, k + 1))
        {
            i1++;
        }
        if(l == 3 && i1 == 1)
        {
            world.setBlockWithNotify(i, j, k, netManager);
            world.scheduledUpdatesAreImmediate = true;
            Block.blocksList[netManager].updateTick(world, i, j, k, random);
            world.scheduledUpdatesAreImmediate = false;
        }
        return true;
    }

    private int netManager;
}
