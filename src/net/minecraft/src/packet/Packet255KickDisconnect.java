package net.minecraft.src.packet;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.material.Material;
import net.minecraft.src.World;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.block.Block;

import java.util.Random;

public class Packet255KickDisconnect extends Block
{

    public Packet255KickDisconnect(int i, boolean flag)
    {
        super(i, 6, Material.rock);
        field_26544_b = flag;
        if(!flag)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
        setLightOpacity(255);
    }

    public int func_22009_a(int i, int j)
    {
        if(j == 0)
        {
            return i > 1 ? 5 : 6;
        }
        if(j == 1)
        {
            if(i == 0)
            {
                return 208;
            }
            return i != 1 ? 192 : 176;
        }
        if(j == 2)
        {
            return 4;
        }
        return j != 3 ? 6 : 16;
    }

    public int getBlockTextureFromSide(int i)
    {
        return func_22009_a(i, 0);
    }

    public boolean isOpaqueCube()
    {
        return field_26544_b;
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
        if(this != Block.stairSingle)
        {
            super.onBlockAdded(world, i, j, k);
        }
        int l = world.getBlockId(i, j - 1, k);
        int i1 = world.getBlockMetadata(i, j, k);
        int j1 = world.getBlockMetadata(i, j - 1, k);
        if(i1 != j1)
        {
            return;
        }
        if(l == stairSingle.blockID)
        {
            world.setBlockWithNotify(i, j, k, 0);
            world.setBlockAndMetadataWithNotify(i, j - 1, k, Block.stairDouble.blockID, i1);
        }
    }

    public int idDropped(int i, Random random)
    {
        return Block.stairSingle.blockID;
    }

    public int quantityDropped(Random random)
    {
        return !field_26544_b ? 1 : 2;
    }

    protected int damageDropped(int i)
    {
        return i;
    }

    public boolean func_26522_a(IBlockAccess worldgenliquids, int i, int j, int k, int l)
    {
        if(this != Block.stairSingle)
        {
            super.func_26522_a(worldgenliquids, i, j, k, l);
        }
        if(l == 1)
        {
            return true;
        }
        if(!super.func_26522_a(worldgenliquids, i, j, k, l))
        {
            return false;
        }
        if(l == 0)
        {
            return true;
        } else
        {
            return worldgenliquids.getBlockId(i, j, k) != blockID;
        }
    }

    public static final String reason[] = {
        "stone", "sand", "wood", "cobble"
    };
    private boolean field_26544_b;

}
