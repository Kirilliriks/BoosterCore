package net.minecraft.src.world;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.biome.MobSpawnerBase;
import net.minecraft.src.block.Block;
import net.minecraft.src.chunk.ChunkProviderHell;
import net.minecraft.src.chunk.IChunkProvider;

public class WorldProviderHell extends WorldProvider
{

    public WorldProviderHell() { }

    public void func_26669_a()
    {
        field_26673_b = new WorldChunkManagerHell(MobSpawnerBase.hell, 1.0D, 0.0D);
        field_26680_c = true;
        field_26679_d = true;
        field_26678_e = true;
        field_26676_g = -1;
    }

    protected void func_26666_b()
    {
        float f = 0.1F;
        for(int i = 0; i <= 15; i++)
        {
            float f1 = 1.0F - (float)i / 15F;
            field_26677_f[i] = ((1.0F - f1) / (f1 * 3F + 1.0F)) * (1.0F - f) + f;
        }

    }

    public IChunkProvider func_26667_c()
    {
        return new ChunkProviderHell(field_26674_a, field_26674_a.func_22079_j());
    }

    public boolean func_26672_a(int i, int j)
    {
        int k = field_26674_a.getFirstUncoveredBlock(i, j);
        if(k == Block.bedrock.blockID)
        {
            return false;
        }
        if(k == 0)
        {
            return false;
        }
        return Block.opaqueCubeLookup[k];
    }

    public float func_26668_a(long l, float f)
    {
        return 0.5F;
    }
}
