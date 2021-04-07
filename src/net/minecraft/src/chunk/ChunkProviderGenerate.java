package net.minecraft.src.chunk;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.block.Block;

public class ChunkProviderGenerate extends Item
{

    public ChunkProviderGenerate(int i)
    {
        super(i);
        maxStackSize = 1;
        setMaxDamage(64);
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
        int i1 = world.getBlockId(i, j, k);
        if(i1 == 0)
        {
            world.playSoundEffect((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
            world.setBlockWithNotify(i, j, k, Block.fire.blockID);
        }
        itemstack.func_25125_a(1, entityplayer);
        return true;
    }
}