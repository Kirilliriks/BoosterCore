package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.material.Material;
import net.minecraft.src.tileentity.TileEntity;
import net.minecraft.src.tileentity.TileEntityMobSpawner;

import java.util.Random;

public class BlockMobSpawner extends BlockContainer
{

    protected BlockMobSpawner(int i, int j)
    {
        super(i, j, Material.rock);
    }

    protected TileEntity getBlockEntity()
    {
        return new TileEntityMobSpawner();
    }

    public int idDropped(int i, Random random)
    {
        return 0;
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }
}
