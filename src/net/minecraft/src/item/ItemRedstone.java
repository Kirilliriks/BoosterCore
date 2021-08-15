package net.minecraft.src.item;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.world.World;
import net.minecraft.src.block.Block;
import net.minecraft.src.entity.EntityPlayer;

public class ItemRedstone extends Item
{

    public ItemRedstone(int i)
    {
        super(i);
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if(l == 0)
        {
            j--;
        }
        if(l == 1)
        {
            j++;
        }
        if(l == 2)
        {
            k--;
        }
        if(l == 3)
        {
            k++;
        }
        if(l == 4)
        {
            i--;
        }
        if(l == 5)
        {
            i++;
        }
        if(!world.isAirBlock(i, j, k))
        {
            return false;
        }
        if(Block.redstoneWire.canPlaceBlockAt(world, i, j, k))
        {
            itemstack.stackSize--;
            world.setBlockWithNotify(i, j, k, Block.redstoneWire.blockID);
        }
        return true;
    }
}
