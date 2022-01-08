package net.minecraft.src.item;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.world.World;
import net.minecraft.src.block.BlockStep;
import net.minecraft.src.entity.EntityHuman;

public class ItemSoup extends BlockStep
{

    public ItemSoup(int i, int j)
    {
        super(i, j, false);
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityHuman entityplayer)
    {
        super.onItemRightClick(itemstack, world, entityplayer);
        return new ItemStack(Item.bowlEmpty);
    }
}
