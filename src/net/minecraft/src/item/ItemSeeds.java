package net.minecraft.src.item;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.world.World;
import net.minecraft.src.block.Block;
import net.minecraft.src.entity.EntityHuman;

public class ItemSeeds extends Item
{

    public ItemSeeds(int i, int j)
    {
        super(i);
        field_271_a = j;
    }

    public boolean onItemUse(ItemStack itemstack, EntityHuman entityplayer, World world, int i, int j, int k, int l)
    {
        if(l != 1)
        {
            return false;
        }
        int i1 = world.getBlockId(i, j, k);
        if(i1 == Block.tilledField.blockID && world.isAirBlock(i, j + 1, k))
        {
            world.setBlockWithNotify(i, j + 1, k, field_271_a);
            itemstack.stackSize--;
            return true;
        } else
        {
            return false;
        }
    }

    private int field_271_a;
}
