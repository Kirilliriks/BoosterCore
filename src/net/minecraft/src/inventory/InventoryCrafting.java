package net.minecraft.src.inventory;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.crafting.CraftingInventoryCB;
import net.minecraft.src.entity.EntityHuman;
import net.minecraft.src.item.ItemStack;

public class InventoryCrafting
    implements IInventory
{

    public InventoryCrafting(CraftingInventoryCB craftinginventorycb, int i, int j)
    {
        int k = i * j;
        stackList = new ItemStack[k];
        eventHandler = craftinginventorycb;
        field_21085_b = i;
    }

    public int getSizeInventory()
    {
        return stackList.length;
    }

    public ItemStack getStackInSlot(int i)
    {
        if(i >= getSizeInventory())
        {
            return null;
        } else
        {
            return stackList[i];
        }
    }

    public ItemStack func_21084_a(int i, int j)
    {
        if(i < 0 || i >= field_21085_b)
        {
            return null;
        } else
        {
            int k = i + j * field_21085_b;
            return getStackInSlot(k);
        }
    }

    public String getInvName()
    {
        return "Crafting";
    }

    public ItemStack decrStackSize(int i, int j)
    {
        if(stackList[i] != null)
        {
            if(stackList[i].stackSize <= j)
            {
                ItemStack itemstack = stackList[i];
                stackList[i] = null;
                eventHandler.onCraftMatrixChanged(this);
                return itemstack;
            }
            ItemStack itemstack1 = stackList[i].splitStack(j);
            if(stackList[i].stackSize == 0)
            {
                stackList[i] = null;
            }
            eventHandler.onCraftMatrixChanged(this);
            return itemstack1;
        } else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        stackList[i] = itemstack;
        eventHandler.onCraftMatrixChanged(this);
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

    private ItemStack stackList[];
    private int field_21085_b;
    private CraftingInventoryCB eventHandler;
}
