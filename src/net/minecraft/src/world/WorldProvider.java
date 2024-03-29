package net.minecraft.src.world;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.block.Block;
import net.minecraft.src.chunk.ChunkProviderGenerate;
import net.minecraft.src.chunk.IChunkProvider;

public class WorldProvider {

    public World world;
    public WorldChunkManager worldChunkManager;
    public boolean field_26680_c;
    public boolean field_26679_d;
    public boolean field_26678_e;
    public float field_26677_f[];
    public int field_26676_g;
    private float field_26675_h[];

    public WorldProvider() {
        field_26680_c = false;
        field_26679_d = false;
        field_26678_e = false;
        field_26677_f = new float[16];
        field_26676_g = 0;
        field_26675_h = new float[4];
    }

    public final void func_26671_a(World world)
    {
        this.world = world;
        func_26669_a();
        func_26666_b();
    }

    protected void func_26666_b()
    {
        float f = 0.05F;
        for(int i = 0; i <= 15; i++)
        {
            float f1 = 1.0F - (float)i / 15F;
            field_26677_f[i] = ((1.0F - f1) / (f1 * 3F + 1.0F)) * (1.0F - f) + f;
        }

    }

    protected void func_26669_a()
    {
        worldChunkManager = new WorldChunkManager(world);
    }

    public IChunkProvider func_26667_c()
    {
        return new ChunkProviderGenerate(world, world.func_22079_j());
    }

    public boolean func_26672_a(int i, int j)
    {
        int k = world.getFirstUncoveredBlock(i, j);
        return k == Block.sand.blockID;
    }

    public float func_26668_a(long l, float f)
    {
        int i = (int)(l % 24000L);
        float f1 = ((float)i + f) / 24000F - 0.25F;
        if(f1 < 0.0F)
        {
            f1++;
        }
        if(f1 > 1.0F)
        {
            f1--;
        }
        float f2 = f1;
        f1 = 1.0F - (float)((Math.cos((double)f1 * 3.1415926535897931D) + 1.0D) / 2D);
        f1 = f2 + (f1 - f2) / 3F;
        return f1;
    }

    public static WorldProvider func_26670_a(int i)
    {
        if(i == 0)
        {
            return new WorldProvider();
        }
        if(i == -1)
        {
            return new WorldProviderHell();
        } else
        {
            return null;
        }
    }
}
