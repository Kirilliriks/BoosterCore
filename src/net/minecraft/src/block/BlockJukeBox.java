package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.entity.EntityItem;
import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.material.Material;

public class BlockJukeBox extends Block
{

    protected BlockJukeBox(int i, int j)
    {
        super(i, j, Material.wood);
    }

    public int getBlockTextureFromSide(int i)
    {
        return blockIndexInTexture + (i != 1 ? 0 : 1);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        int l = world.getBlockMetadata(i, j, k);
        if(l > 0)
        {
            ejectRecord(world, i, j, k, l);
            return true;
        } else
        {
            return false;
        }
    }

    public void ejectRecord(World world, int i, int j, int k, int l)
    {
        world.playRecord(null, i, j, k);
        world.setBlockMetadataWithNotify(i, j, k, 0);
        int i1 = (Item.record13.shiftedIndex + l) - 1;
        float f = 0.7F;
        double d = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.20000000000000001D + 0.59999999999999998D;
        double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
        EntityItem entityitem = new EntityItem(world, (double)i + d, (double)j + d1, (double)k + d2, new ItemStack(i1, 1, 0));
        entityitem.delayBeforeCanPickup = 10;
        world.entityJoinedWorld(entityitem);
    }

    public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f)
    {
        if(world.singleplayerWorld)
        {
            return;
        }
        if(l > 0)
        {
            ejectRecord(world, i, j, k, l);
        }
        super.dropBlockAsItemWithChance(world, i, j, k, l, f);
    }
}
