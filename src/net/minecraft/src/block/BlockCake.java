package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.entity.EntityPlayer;

import java.util.Random;

public class BlockCake extends Block
{

    protected BlockCake(int i, int j)
    {
        super(i, j, Material.cakeMaterial);
        func_26519_a(true);
    }

    public void func_26521_a(WorldGenLiquids worldgenliquids, int i, int j, int k)
    {
        int l = worldgenliquids.getBlockMetadata(i, j, k);
        float f = 0.0625F;
        float f1 = (float)(1 + l * 2) / 16F;
        float f2 = 0.5F;
        setBlockBounds(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        int l = world.getBlockMetadata(i, j, k);
        float f = 0.0625F;
        float f1 = (float)(1 + l * 2) / 16F;
        float f2 = 0.5F;
        return AxisAlignedBB.getBoundingBoxFromPool((float)i + f1, j, (float)k + f, (float)(i + 1) - f, ((float)j + f2) - f, (float)(k + 1) - f);
    }

    public int func_22009_a(int i, int j)
    {
        if(i == 1)
        {
            return blockIndexInTexture;
        }
        if(i == 0)
        {
            return blockIndexInTexture + 3;
        }
        if(j > 0 && i == 4)
        {
            return blockIndexInTexture + 2;
        } else
        {
            return blockIndexInTexture + 1;
        }
    }

    public int getBlockTextureFromSide(int i)
    {
        if(i == 1)
        {
            return blockIndexInTexture;
        }
        if(i == 0)
        {
            return blockIndexInTexture + 3;
        } else
        {
            return blockIndexInTexture + 1;
        }
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        eatCakeSlice(world, i, j, k, entityplayer);
        return true;
    }

    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        eatCakeSlice(world, i, j, k, entityplayer);
    }

    private void eatCakeSlice(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(entityplayer.health < 20)
        {
            entityplayer.heal(3);
            int l = world.getBlockMetadata(i, j, k) + 1;
            if(l >= 6)
            {
                world.setBlockWithNotify(i, j, k, 0);
            } else
            {
                world.setBlockMetadataWithNotify(i, j, k, l);
                world.markBlockAsNeedsUpdate(i, j, k);
            }
        }
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        if(!super.canPlaceBlockAt(world, i, j, k))
        {
            return false;
        } else
        {
            return canBlockStay(world, i, j, k);
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(!canBlockStay(world, i, j, k))
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    public boolean canBlockStay(World world, int i, int j, int k)
    {
        return world.getBlockMaterial(i, j - 1, k).isSolid();
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }

    public int idDropped(int i, Random random)
    {
        return 0;
    }
}
