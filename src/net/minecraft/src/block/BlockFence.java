package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.entity.AxisAlignedBB;
import net.minecraft.src.material.Material;
import net.minecraft.src.world.World;

public class BlockFence extends Block
{

    public BlockFence(int i, int j)
    {
        super(i, j, Material.wood);
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j - 1, k) == blockID)
        {
            return false;
        }
        if(!world.getBlockMaterial(i, j - 1, k).isSolid())
        {
            return false;
        } else
        {
            return super.canPlaceBlockAt(world, i, j, k);
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return AxisAlignedBB.getBoundingBoxFromPool(i, j, k, i + 1, (float)j + 1.5F, k + 1);
    }

    public boolean isOpaqueCube()
    {
        return false;
    }
}
