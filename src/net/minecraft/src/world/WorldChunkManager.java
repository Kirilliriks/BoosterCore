package net.minecraft.src.world;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.NoiseGeneratorOctaves2;
import net.minecraft.src.biome.MobSpawnerBase;
import net.minecraft.src.chunk.ChunkCoordinate;

import java.util.Random;

public class WorldChunkManager
{

    protected WorldChunkManager()
    {
    }

    public WorldChunkManager(World world)
    {
        field_4262_e = new NoiseGeneratorOctaves2(new Random(world.func_22079_j() * 9871L), 4);
        field_4261_f = new NoiseGeneratorOctaves2(new Random(world.func_22079_j() * 39811L), 4);
        field_4260_g = new NoiseGeneratorOctaves2(new Random(world.func_22079_j() * 0x84a59L), 2);
    }

    public MobSpawnerBase func_26658_a(ChunkCoordinate chunkCoordinate)
    {
        return func_4067_a(chunkCoordinate.field_26507_a << 4, chunkCoordinate.field_26506_b << 4);
    }

    public MobSpawnerBase func_4067_a(int i, int j)
    {
        return func_4065_a(i, j, 1, 1)[0];
    }

    public MobSpawnerBase[] func_4065_a(int i, int j, int k, int l)
    {
        updateTime = loadBlockGeneratorData(updateTime, i, j, k, l);
        return updateTime;
    }

    public double[] getTemperatures(double ad[], int i, int j, int k, int l)
    {
        if(ad == null || ad.length < k * l)
        {
            ad = new double[k * l];
        }
        ad = field_4262_e.func_4101_a(ad, i, j, k, l, 0.02500000037252903D, 0.02500000037252903D, 0.25D);
        z = field_4260_g.func_4101_a(z, i, j, k, l, 0.25D, 0.25D, 0.58823529411764708D);
        int i1 = 0;
        for(int j1 = 0; j1 < k; j1++)
        {
            for(int k1 = 0; k1 < l; k1++)
            {
                double d = z[i1] * 1.1000000000000001D + 0.5D;
                double d1 = 0.01D;
                double d2 = 1.0D - d1;
                double d3 = (ad[i1] * 0.14999999999999999D + 0.69999999999999996D) * d2 + d * d1;
                d3 = 1.0D - (1.0D - d3) * (1.0D - d3);
                if(d3 < 0.0D)
                {
                    d3 = 0.0D;
                }
                if(d3 > 1.0D)
                {
                    d3 = 1.0D;
                }
                ad[i1] = d3;
                i1++;
            }

        }

        return ad;
    }

    public MobSpawnerBase[] loadBlockGeneratorData(MobSpawnerBase amobspawnerbase[], int i, int j, int k, int l)
    {
        if(amobspawnerbase == null || amobspawnerbase.length < k * l)
        {
            amobspawnerbase = new MobSpawnerBase[k * l];
        }
        x = field_4262_e.func_4101_a(x, i, j, k, k, 0.02500000037252903D, 0.02500000037252903D, 0.25D);
        y = field_4261_f.func_4101_a(y, i, j, k, k, 0.05000000074505806D, 0.05000000074505806D, 0.33333333333333331D);
        z = field_4260_g.func_4101_a(z, i, j, k, k, 0.25D, 0.25D, 0.58823529411764708D);
        int i1 = 0;
        for(int j1 = 0; j1 < k; j1++)
        {
            for(int k1 = 0; k1 < l; k1++)
            {
                double d = z[i1] * 1.1000000000000001D + 0.5D;
                double d1 = 0.01D;
                double d2 = 1.0D - d1;
                double d3 = (x[i1] * 0.14999999999999999D + 0.69999999999999996D) * d2 + d * d1;
                d1 = 0.002D;
                d2 = 1.0D - d1;
                double d4 = (y[i1] * 0.14999999999999999D + 0.5D) * d2 + d * d1;
                d3 = 1.0D - (1.0D - d3) * (1.0D - d3);
                if(d3 < 0.0D)
                {
                    d3 = 0.0D;
                }
                if(d4 < 0.0D)
                {
                    d4 = 0.0D;
                }
                if(d3 > 1.0D)
                {
                    d3 = 1.0D;
                }
                if(d4 > 1.0D)
                {
                    d4 = 1.0D;
                }
                x[i1] = d3;
                y[i1] = d4;
                amobspawnerbase[i1++] = MobSpawnerBase.getBiomeFromLookup(d3, d4);
            }

        }

        return amobspawnerbase;
    }

    private NoiseGeneratorOctaves2 field_4262_e;
    private NoiseGeneratorOctaves2 field_4261_f;
    private NoiseGeneratorOctaves2 field_4260_g;
    public double x[];
    public double y[];
    public double z[];
    public MobSpawnerBase updateTime[];
}
