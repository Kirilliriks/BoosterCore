package net.minecraft.src.item;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.world.World;
import net.minecraft.src.block.Block;
import net.minecraft.src.entity.EntityHuman;

public class ItemRecord extends Item
{

    protected ItemRecord(int i, String s)
    {
        super(i);
        recordName = s;
        maxStackSize = 1;
    }

    public boolean onItemUse(ItemStack itemstack, EntityHuman entityplayer, World world, int i, int j, int k, int l)
    {
        if(world.getBlockId(i, j, k) == Block.jukebox.blockID && world.getBlockMetadata(i, j, k) == 0)
        {
            world.setBlockMetadataWithNotify(i, j, k, (shiftedIndex - Item.record13.shiftedIndex) + 1);
            world.playRecord(recordName, i, j, k);
            itemstack.stackSize--;
            return true;
        } else
        {
            return false;
        }
    }

    private String recordName;
}
