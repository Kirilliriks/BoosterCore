package net.minecraft.src.chunk;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.world.World;

public interface IChunkLoader
{

    public abstract Chunk loadChunk(World world, int i, int j);

    public abstract void saveChunk(World world, Chunk chunk);

    public abstract void saveExtraChunkData(World world, Chunk chunk);

    public abstract void func_661_a();

    public abstract void saveExtraData();
}
