package net.minecraft.src.mobspawner;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.block.Block;
import net.minecraft.src.block.BlockBloodStone;
import net.minecraft.src.chunk.ChunkPosition;
import net.minecraft.src.entity.*;

import java.util.*;

public class MobSpawnerBase
{

    protected MobSpawnerBase()
    {
        topBlock = (byte) Block.grass.blockID;
        fillerBlock = (byte)Block.dirt.blockID;
        field_6161_q = 0x4ee031;
        field_25058_r = new ArrayList();
        field_25057_s = new ArrayList();
        field_25056_t = new ArrayList();
        field_25058_r.add(new SpawnListEntry(EntitySpider.class, 10));
        field_25058_r.add(new SpawnListEntry(EntityZombie.class, 10));
        field_25058_r.add(new SpawnListEntry(EntitySkeleton.class, 10));
        field_25058_r.add(new SpawnListEntry(EntityCreeper.class, 10));
        field_25058_r.add(new SpawnListEntry(EntitySlime.class, 10));
        field_25057_s.add(new SpawnListEntry(EntitySheep.class, 12));
        field_25057_s.add(new SpawnListEntry(ChunkPosition.class, 10));
        field_25057_s.add(new SpawnListEntry(PlayerManager.class, 10));
        field_25057_s.add(new SpawnListEntry(EntityCow.class, 8));
        field_25056_t.add(new SpawnListEntry(WorldGenFire.class, 10));
    }

    public static void generateBiomeLookup()
    {
        for(int i = 0; i < 64; i++)
        {
            for(int j = 0; j < 64; j++)
            {
                biomeLookupTable[i + j * 64] = getBiome((float)i / 63F, (float)j / 63F);
            }

        }

        desert.topBlock = desert.fillerBlock = (byte)Block.sand.blockID;
        iceDesert.topBlock = iceDesert.fillerBlock = (byte)Block.sand.blockID;
    }

    public WorldGenerator getRandomWorldGenForTrees(Random random)
    {
        if(random.nextInt(10) == 0)
        {
            return new WorldGenBigTree();
        } else
        {
            return new BlockBloodStone();
        }
    }

    protected MobSpawnerBase doesNothingForMobSpawnerBase()
    {
        return this;
    }

    protected MobSpawnerBase setBiomeName(String s)
    {
        biomeName = s;
        return this;
    }

    protected MobSpawnerBase func_4080_a(int i)
    {
        field_6161_q = i;
        return this;
    }

    protected MobSpawnerBase setColor(int i)
    {
        color = i;
        return this;
    }

    public static MobSpawnerBase getBiomeFromLookup(double d, double d1)
    {
        int i = (int)(d * 63D);
        int j = (int)(d1 * 63D);
        return biomeLookupTable[i + j * 64];
    }

    public static MobSpawnerBase getBiome(float f, float f1)
    {
        f1 *= f;
        if(f < 0.1F)
        {
            return tundra;
        }
        if(f1 < 0.2F)
        {
            if(f < 0.5F)
            {
                return tundra;
            }
            if(f < 0.95F)
            {
                return savanna;
            } else
            {
                return desert;
            }
        }
        if(f1 > 0.5F && f < 0.7F)
        {
            return swampland;
        }
        if(f < 0.5F)
        {
            return taiga;
        }
        if(f < 0.97F)
        {
            if(f1 < 0.35F)
            {
                return shrubland;
            } else
            {
                return forest;
            }
        }
        if(f1 < 0.45F)
        {
            return plains;
        }
        if(f1 < 0.9F)
        {
            return seasonalForest;
        } else
        {
            return rainforest;
        }
    }

    public List func_25055_a(EnumCreatureType enumcreaturetype)
    {
        if(enumcreaturetype == EnumCreatureType.monster)
        {
            return field_25058_r;
        }
        if(enumcreaturetype == EnumCreatureType.creature)
        {
            return field_25057_s;
        }
        if(enumcreaturetype == EnumCreatureType.waterCreature)
        {
            return field_25056_t;
        } else
        {
            return null;
        }
    }

    public static MobSpawnerBase rainforest = (new StatTime()).setColor(0x8fa36).setBiomeName("Rainforest").func_4080_a(0x1ff458);
    public static MobSpawnerBase swampland = (new MobSpawnerSwamp()).setColor(0x7f9b2).setBiomeName("Swampland").func_4080_a(0x8baf48);
    public static MobSpawnerBase seasonalForest = (new MobSpawnerBase()).setColor(0x9be023).setBiomeName("Seasonal Forest");
    public static MobSpawnerBase forest = (new MobSpawnerForest()).setColor(0x56621).setBiomeName("Forest").func_4080_a(0x4eba31);
    public static MobSpawnerBase savanna = (new MobSpawnerDesert()).setColor(0xd9e023).setBiomeName("Savanna");
    public static MobSpawnerBase shrubland = (new MobSpawnerBase()).setColor(0xa1ad20).setBiomeName("Shrubland");
    public static MobSpawnerBase taiga = (new MobSpawnerTaiga()).setColor(0x2eb153).setBiomeName("Taiga").doesNothingForMobSpawnerBase().func_4080_a(0x7bb731);
    public static MobSpawnerBase desert = (new MobSpawnerDesert()).setColor(0xfa9418).setBiomeName("Desert");
    public static MobSpawnerBase plains = (new MobSpawnerDesert()).setColor(0xffd910).setBiomeName("Plains");
    public static MobSpawnerBase iceDesert = (new MobSpawnerDesert()).setColor(0xffed93).setBiomeName("Ice Desert").doesNothingForMobSpawnerBase().func_4080_a(0xc4d339);
    public static MobSpawnerBase tundra = (new MobSpawnerBase()).setColor(0x57ebf9).setBiomeName("Tundra").doesNothingForMobSpawnerBase().func_4080_a(0xc4d339);
    public static MobSpawnerBase hell = (new MobSpawnerHell()).setColor(0xff0000).setBiomeName("Hell");
    public String biomeName;
    public int color;
    public byte topBlock;
    public byte fillerBlock;
    public int field_6161_q;
    protected List field_25058_r;
    protected List field_25057_s;
    protected List field_25056_t;
    private static MobSpawnerBase biomeLookupTable[] = new MobSpawnerBase[4096];

    static 
    {
        rainforest = (new StatTime()).setColor(0x8fa36).setBiomeName("Rainforest").func_4080_a(0x1ff458);
        swampland = (new MobSpawnerSwamp()).setColor(0x7f9b2).setBiomeName("Swampland").func_4080_a(0x8baf48);
        seasonalForest = (new MobSpawnerBase()).setColor(0x9be023).setBiomeName("Seasonal Forest");
        forest = (new MobSpawnerForest()).setColor(0x56621).setBiomeName("Forest").func_4080_a(0x4eba31);
        savanna = (new MobSpawnerDesert()).setColor(0xd9e023).setBiomeName("Savanna");
        shrubland = (new MobSpawnerBase()).setColor(0xa1ad20).setBiomeName("Shrubland");
        taiga = (new MobSpawnerTaiga()).setColor(0x2eb153).setBiomeName("Taiga").doesNothingForMobSpawnerBase().func_4080_a(0x7bb731);
        desert = (new MobSpawnerDesert()).setColor(0xfa9418).setBiomeName("Desert");
        plains = (new MobSpawnerDesert()).setColor(0xffd910).setBiomeName("Plains");
        iceDesert = (new MobSpawnerDesert()).setColor(0xffed93).setBiomeName("Ice Desert").doesNothingForMobSpawnerBase().func_4080_a(0xc4d339);
        tundra = (new MobSpawnerBase()).setColor(0x57ebf9).setBiomeName("Tundra").doesNothingForMobSpawnerBase().func_4080_a(0xc4d339);
        hell = (new MobSpawnerHell()).setColor(0xff0000).setBiomeName("Hell");
        generateBiomeLookup();
    }
}
