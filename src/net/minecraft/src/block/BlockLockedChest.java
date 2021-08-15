package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.material.Material;
import net.minecraft.src.world.World;

public class BlockLockedChest extends Block
{

    protected BlockLockedChest(int i)
    {
        super(i, Material.wood);
        blockIndexInTexture = 26;
    }

    public int getBlockTextureFromSide(int i)
    {
        if(i == 1)
        {
            return blockIndexInTexture - 1;
        }
        if(i == 0)
        {
            return blockIndexInTexture - 1;
        }
        if(i == 3)
        {
            return blockIndexInTexture + 1;
        } else
        {
            return blockIndexInTexture;
        }
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        int l = 0;
        if(world.getBlockId(i - 1, j, k) == blockID)
        {
            l++;
        }
        if(world.getBlockId(i + 1, j, k) == blockID)
        {
            l++;
        }
        if(world.getBlockId(i, j, k - 1) == blockID)
        {
            l++;
        }
        if(world.getBlockId(i, j, k + 1) == blockID)
        {
            l++;
        }
        if(l > 1)
        {
            return false;
        }
        if(func_26543_g(world, i - 1, j, k))
        {
            return false;
        }
        if(func_26543_g(world, i + 1, j, k))
        {
            return false;
        }
        if(func_26543_g(world, i, j, k - 1))
        {
            return false;
        }
        return !func_26543_g(world, i, j, k + 1);
    }

    private boolean func_26543_g(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j, k) != blockID)
        {
            return false;
        }
        if(world.getBlockId(i - 1, j, k) == blockID)
        {
            return true;
        }
        if(world.getBlockId(i + 1, j, k) == blockID)
        {
            return true;
        }
        if(world.getBlockId(i, j, k - 1) == blockID)
        {
            return true;
        }
        return world.getBlockId(i, j, k + 1) == blockID;
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        entityplayer.func_26605_J();
        return true;
    }
}
