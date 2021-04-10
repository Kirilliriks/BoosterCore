package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.inventory.IInventory;
import net.minecraft.src.item.ItemStack;

public class Slot
{

    public Slot(IInventory iinventory, int i, int j, int k)
    {
        inventory = iinventory;
        slotIndex = i;
        xDisplayPosition = j;
        yDisplayPosition = k;
    }

    public void onPickupFromSlot(ItemStack itemstack)
    {
        onSlotChanged();
    }

    public boolean isItemValid(ItemStack itemstack)
    {
        return true;
    }

    public ItemStack getStack()
    {
        return inventory.getStackInSlot(slotIndex);
    }

    public void putStack(ItemStack itemstack)
    {
        inventory.setInventorySlotContents(slotIndex, itemstack);
        onSlotChanged();
    }

    public void onSlotChanged()
    {
        inventory.onInventoryChanged();
    }

    public int getSlotStackLimit()
    {
        return inventory.getInventoryStackLimit();
    }

    public ItemStack decrStackSize(int i)
    {
        return inventory.decrStackSize(slotIndex, i);
    }

    public boolean isHere(IInventory iinventory, int i)
    {
        return iinventory == inventory && i == slotIndex;
    }

    public boolean func_25003_d()
    {
        return false;
    }

    private final int slotIndex;
    private final IInventory inventory;
    public int id;
    public int xDisplayPosition;
    public int yDisplayPosition;
}
