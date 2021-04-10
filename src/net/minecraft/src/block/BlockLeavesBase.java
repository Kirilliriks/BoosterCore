package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.material.Material;
import net.minecraft.src.WorldGenLiquids;

public class BlockLeavesBase extends Block
{

    protected BlockLeavesBase(int i, int j, Material material, boolean flag)
    {
        super(i, j, material);
        graphicsLevel = flag;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean func_26522_a(WorldGenLiquids worldgenliquids, int i, int j, int k, int l)
    {
        int i1 = worldgenliquids.getBlockId(i, j, k);
        if(!graphicsLevel && i1 == blockID)
        {
            return false;
        } else
        {
            return super.func_26522_a(worldgenliquids, i, j, k, l);
        }
    }

    protected boolean graphicsLevel;
}
