package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.entity.EntityHuman;
import net.minecraft.src.inventory.IInventory;
import net.minecraft.src.item.ItemStack;

public class NibbleArray
    implements IInventory
{

    public NibbleArray()
    {
        data = new ItemStack[1];
    }

    public int getSizeInventory()
    {
        return 1;
    }

    public ItemStack getStackInSlot(int i)
    {
        return data[i];
    }

    public String getInvName()
    {
        return "Result";
    }

    public ItemStack decrStackSize(int i, int j)
    {
        if(data[i] != null)
        {
            ItemStack itemstack = data[i];
            data[i] = null;
            return itemstack;
        } else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        data[i] = itemstack;
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public void onInventoryChanged()
    {
    }

    public boolean canInteractWith(EntityHuman entityplayer)
    {
        return true;
    }

    private ItemStack data[];
}
