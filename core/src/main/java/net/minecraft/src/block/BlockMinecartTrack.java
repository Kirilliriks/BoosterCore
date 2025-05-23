package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.entity.AxisAlignedBB;
import net.minecraft.src.material.Material;
import net.minecraft.src.world.World;

import java.util.Random;

public class BlockMinecartTrack extends Block
{

    protected BlockMinecartTrack(int i, int j)
    {
        super(i, j, Material.circuits);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1)
    {
        func_26521_a(world, i, j, k);
        return super.collisionRayTrace(world, i, j, k, vec3d, vec3d1);
    }

    public void func_26521_a(IBlockAccess worldgenliquids, int i, int j, int k)
    {
        int l = worldgenliquids.getBlockMetadata(i, j, k);
        if(l >= 2 && l <= 5)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        } else
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        }
    }

    public int func_22009_a(int i, int j)
    {
        if(j >= 6)
        {
            return blockIndexInTexture - 16;
        } else
        {
            return blockIndexInTexture;
        }
    }

    public int quantityDropped(Random random)
    {
        return 1;
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        return world.isBlockOpaqueCube(i, j - 1, k);
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
        if(!world.singleplayerWorld)
        {
            world.setBlockMetadataWithNotify(i, j, k, 15);
            func_4038_g(world, i, j, k);
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(world.singleplayerWorld)
        {
            return;
        }
        int i1 = world.getBlockMetadata(i, j, k);
        boolean flag = false;
        if(!world.isBlockOpaqueCube(i, j - 1, k))
        {
            flag = true;
        }
        if(i1 == 2 && !world.isBlockOpaqueCube(i + 1, j, k))
        {
            flag = true;
        }
        if(i1 == 3 && !world.isBlockOpaqueCube(i - 1, j, k))
        {
            flag = true;
        }
        if(i1 == 4 && !world.isBlockOpaqueCube(i, j, k - 1))
        {
            flag = true;
        }
        if(i1 == 5 && !world.isBlockOpaqueCube(i, j, k + 1))
        {
            flag = true;
        }
        if(flag)
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
        } else
        if(l > 0 && Block.blocksList[l].canProvidePower() && BlockTorch.func_26712_a(new BlockTorch(this, world, i, j, k)) == 3)
        {
            func_4038_g(world, i, j, k);
        }
    }

    private void func_4038_g(World world, int i, int j, int k)
    {
        if(world.singleplayerWorld)
        {
            return;
        } else
        {
            (new BlockTorch(this, world, i, j, k)).func_26718_a(world.isBlockIndirectlyGettingPowered(i, j, k));
            return;
        }
    }
}
