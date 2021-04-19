package net.minecraft.src.biome;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.WorldGenerator;
import net.minecraft.src.WorldGenTaiga2;
import net.minecraft.src.entity.EntityWolf;

import java.util.Random;

public class MobSpawnerTaiga extends MobSpawnerBase
{

    public MobSpawnerTaiga()
    {
        field_25057_s.add(new SpawnListEntry(EntityWolf.class, 2));
    }

    public WorldGenerator getRandomWorldGenForTrees(Random random)
    {
        if(random.nextInt(3) == 0)
        {
            return new net.minecraft.src.WorldGenTaiga1();
        } else
        {
            return new WorldGenTaiga2();
        }
    }
}
