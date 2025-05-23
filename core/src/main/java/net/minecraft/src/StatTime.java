package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.block.WorldGenTrees;
import net.minecraft.src.biome.MobSpawnerBase;
import net.minecraft.src.world.WorldGenBigTree;
import net.minecraft.src.world.WorldGenerator;

import java.util.Random;

public class StatTime extends MobSpawnerBase
{

    public StatTime()
    {
    }

    public WorldGenerator getRandomWorldGenForTrees(Random random)
    {
        if(random.nextInt(3) == 0)
        {
            return new WorldGenBigTree();
        } else
        {
            return new WorldGenTrees();
        }
    }
}
