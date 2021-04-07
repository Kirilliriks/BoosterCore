package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class BlockStep extends Item
{

    public BlockStep(int i, int j, boolean flag)
    {
        super(i);
        field_22027_a = j;
        field_26518_bi = flag;
        maxStackSize = 1;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        itemstack.stackSize--;
        entityplayer.heal(field_22027_a);
        return itemstack;
    }

    public int func_26516_j()
    {
        return field_22027_a;
    }

    public boolean func_26517_k()
    {
        return field_26518_bi;
    }

    private int field_22027_a;
    private boolean field_26518_bi;
}
