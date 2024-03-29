package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.entity.AxisAlignedBB;
import net.minecraft.src.entity.EntityItem;
import net.minecraft.src.entity.EntityHuman;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.material.Material;
import net.minecraft.src.world.World;

import java.util.Random;

public class BlockSnow extends Block
{

    protected BlockSnow(int i, int j)
    {
        super(i, j, Material.snow);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        func_26519_a(true);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        int l = world.getBlockId(i, j - 1, k);
        if(l == 0 || !Block.blocksList[l].isOpaqueCube())
        {
            return false;
        } else
        {
            return world.getBlockMaterial(i, j - 1, k).getIsSolid();
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        func_275_g(world, i, j, k);
    }

    private boolean func_275_g(World world, int i, int j, int k)
    {
        if(!canPlaceBlockAt(world, i, j, k))
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
            return false;
        } else
        {
            return true;
        }
    }

    public void harvestBlock(World world, EntityHuman entityplayer, int i, int j, int k, int l)
    {
        int i1 = Item.snowball.shiftedIndex;
        float f = 0.7F;
        double d = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        EntityItem entityitem = new EntityItem(world, (double)i + d, (double)j + d1, (double)k + d2, new ItemStack(i1, 1, 0));
        entityitem.delayBeforeCanPickup = 10;
        world.entityJoinedWorld(entityitem);
        world.setBlockWithNotify(i, j, k, 0);
        entityplayer.func_26604_a(StatList.field_25094_y[blockID], 1);
    }

    public int idDropped(int i, Random random)
    {
        return Item.snowball.shiftedIndex;
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(world.getSavedLightValue(EnumSkyBlock.Block, i, j, k) > 11)
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    public boolean func_26522_a(IBlockAccess worldgenliquids, int i, int j, int k, int l)
    {
        Material material = worldgenliquids.getBlockMaterial(i, j, k);
        if(l == 1)
        {
            return true;
        }
        if(material == blockMaterial)
        {
            return false;
        } else
        {
            return super.func_26522_a(worldgenliquids, i, j, k, l);
        }
    }
}
