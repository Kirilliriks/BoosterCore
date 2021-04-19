package net.minecraft.src.biome;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.WorldGenBigTree;
import net.minecraft.src.WorldGenForest;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.block.BlockBloodStone;
import net.minecraft.src.entity.EntityWolf;

import java.util.Random;

public class MobSpawnerForest extends MobSpawnerBase
{

    public MobSpawnerForest()
    {
        field_25057_s.add(new SpawnListEntry(EntityWolf.class, 2));
    }

    public WorldGenerator getRandomWorldGenForTrees(Random random)
    {
        if(random.nextInt(5) == 0)
        {
            return new WorldGenForest();
        }
        if(random.nextInt(3) == 0)
        {
            return new WorldGenBigTree();
        } else
        {
            return new BlockBloodStone();
        }
    }
}
