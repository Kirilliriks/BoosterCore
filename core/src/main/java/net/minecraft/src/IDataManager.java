package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.chunk.IChunkLoader;
import net.minecraft.src.world.WorldInfo;
import net.minecraft.src.world.WorldProvider;

import java.util.List;

public interface IDataManager
{

    public abstract WorldInfo func_22096_c();

    public abstract void func_22091_b();

    public abstract IChunkLoader func_26697_a(WorldProvider worldProvider);

    public abstract void func_22095_a(WorldInfo worldinfo, List list);

    public abstract void func_22094_a(WorldInfo worldinfo);

    public abstract IPlayerFileData func_22090_d();

    public abstract void func_22093_e();
}
