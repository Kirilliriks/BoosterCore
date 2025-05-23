package net.minecraft.src.crafting;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.entity.EntityHuman;
import net.minecraft.src.inventory.IInventory;
import net.minecraft.src.inventory.InventoryPlayer;
import net.minecraft.src.item.ItemStack;

import java.util.*;

public abstract class CraftingInventoryCB
{

    public CraftingInventoryCB()
    {
        inventoryItemStacks = new ArrayList();
        inventorySlots = new ArrayList();
        windowId = 0;
        field_20132_a = 0;
        crafters = new ArrayList();
        field_20131_b = new HashSet();
    }

    protected void addSlot(Slot slot)
    {
        slot.id = inventorySlots.size();
        inventorySlots.add(slot);
        inventoryItemStacks.add(null);
    }

    public void onCraftGuiOpened(ICrafting icrafting)
    {
        crafters.add(icrafting);
        ArrayList arraylist = new ArrayList();
        for(int i = 0; i < inventorySlots.size(); i++)
        {
            arraylist.add(((Slot)inventorySlots.get(i)).getStack());
        }

        icrafting.updateCraftingInventory(this, arraylist);
        updateCraftingMatrix();
    }

    public void updateCraftingMatrix()
    {
        for(int i = 0; i < inventorySlots.size(); i++)
        {
            ItemStack itemstack = ((Slot)inventorySlots.get(i)).getStack();
            ItemStack itemstack1 = (ItemStack)inventoryItemStacks.get(i);
            if(ItemStack.areItemStacksEqual(itemstack1, itemstack))
            {
                continue;
            }
            itemstack1 = itemstack != null ? itemstack.copy() : null;
            inventoryItemStacks.set(i, itemstack1);
            for(int j = 0; j < crafters.size(); j++)
            {
                ((ICrafting)crafters.get(j)).updateCraftingInventorySlot(this, i, itemstack1);
            }

        }

    }

    public Slot func_20127_a(IInventory iinventory, int i)
    {
        for(int j = 0; j < inventorySlots.size(); j++)
        {
            Slot slot = (Slot)inventorySlots.get(j);
            if(slot.isHere(iinventory, i))
            {
                return slot;
            }
        }

        return null;
    }

    public Slot getSlot(int i)
    {
        return (Slot)inventorySlots.get(i);
    }

    public ItemStack placeItem(int i, int j, EntityHuman entityplayer)
    {
        ItemStack itemstack = null;
        if(j == 0 || j == 1)
        {
            InventoryPlayer inventoryplayer = entityplayer.inventory;
            if(i == -999)
            {
                if(inventoryplayer.getItemStack() != null && i == -999)
                {
                    if(j == 0)
                    {
                        entityplayer.dropPlayerItem(inventoryplayer.getItemStack());
                        inventoryplayer.setItemStack(null);
                    }
                    if(j == 1)
                    {
                        entityplayer.dropPlayerItem(inventoryplayer.getItemStack().splitStack(1));
                        if(inventoryplayer.getItemStack().stackSize == 0)
                        {
                            inventoryplayer.setItemStack(null);
                        }
                    }
                }
            } else
            {
                Slot slot = (Slot)inventorySlots.get(i);
                if(slot != null)
                {
                    slot.onSlotChanged();
                    ItemStack itemstack1 = slot.getStack();
                    ItemStack itemstack2 = inventoryplayer.getItemStack();
                    if(itemstack1 != null)
                    {
                        itemstack = itemstack1.copy();
                    }
                    if(itemstack1 == null)
                    {
                        if(itemstack2 != null && slot.isItemValid(itemstack2))
                        {
                            int k = j != 0 ? 1 : itemstack2.stackSize;
                            if(k > slot.getSlotStackLimit())
                            {
                                k = slot.getSlotStackLimit();
                            }
                            slot.putStack(itemstack2.splitStack(k));
                            if(itemstack2.stackSize == 0)
                            {
                                inventoryplayer.setItemStack(null);
                            }
                        }
                    } else
                    if(itemstack2 == null)
                    {
                        int l = j != 0 ? (itemstack1.stackSize + 1) / 2 : itemstack1.stackSize;
                        ItemStack itemstack4 = slot.decrStackSize(l);
                        if(itemstack4 != null && slot.func_25003_d())
                        {
                            entityplayer.func_26604_a(StatList.field_25093_z[itemstack4.itemID], itemstack4.stackSize);
                        }
                        inventoryplayer.setItemStack(itemstack4);
                        if(itemstack1.stackSize == 0)
                        {
                            slot.putStack(null);
                        }
                        slot.onPickupFromSlot(inventoryplayer.getItemStack());
                    } else
                    if(slot.isItemValid(itemstack2))
                    {
                        if(itemstack1.itemID != itemstack2.itemID || itemstack1.getHasSubtypes() && itemstack1.getItemDamage() != itemstack2.getItemDamage())
                        {
                            if(itemstack2.stackSize <= slot.getSlotStackLimit())
                            {
                                ItemStack itemstack3 = itemstack1;
                                slot.putStack(itemstack2);
                                inventoryplayer.setItemStack(itemstack3);
                            }
                        } else
                        {
                            int i1 = j != 0 ? 1 : itemstack2.stackSize;
                            if(i1 > slot.getSlotStackLimit() - itemstack1.stackSize)
                            {
                                i1 = slot.getSlotStackLimit() - itemstack1.stackSize;
                            }
                            if(i1 > itemstack2.getMaxStackSize() - itemstack1.stackSize)
                            {
                                i1 = itemstack2.getMaxStackSize() - itemstack1.stackSize;
                            }
                            itemstack2.splitStack(i1);
                            if(itemstack2.stackSize == 0)
                            {
                                inventoryplayer.setItemStack(null);
                            }
                            itemstack1.stackSize += i1;
                        }
                    } else
                    if(itemstack1.itemID == itemstack2.itemID && itemstack2.getMaxStackSize() > 1 && (!itemstack1.getHasSubtypes() || itemstack1.getItemDamage() == itemstack2.getItemDamage()))
                    {
                        int j1 = itemstack1.stackSize;
                        if(j1 > 0 && j1 + itemstack2.stackSize <= itemstack2.getMaxStackSize())
                        {
                            itemstack2.stackSize += j1;
                            ItemStack itemstack5 = itemstack1.splitStack(j1);
                            if(itemstack5 != null && slot.func_25003_d())
                            {
                                entityplayer.func_26604_a(StatList.field_25093_z[itemstack5.itemID], itemstack5.stackSize);
                            }
                            if(itemstack1.stackSize == 0)
                            {
                                slot.putStack(null);
                            }
                            slot.onPickupFromSlot(inventoryplayer.getItemStack());
                        }
                    }
                }
            }
        }
        return itemstack;
    }

    public void onCraftGuiClosed(EntityHuman entityplayer)
    {
        InventoryPlayer inventoryplayer = entityplayer.inventory;
        if(inventoryplayer.getItemStack() != null)
        {
            entityplayer.dropPlayerItem(inventoryplayer.getItemStack());
            inventoryplayer.setItemStack(null);
        }
    }

    public void onCraftMatrixChanged(IInventory iinventory)
    {
        updateCraftingMatrix();
    }

    public boolean getCanCraft(EntityHuman entityplayer)
    {
        return !field_20131_b.contains(entityplayer);
    }

    public void setCanCraft(EntityHuman entityplayer, boolean flag)
    {
        if(flag)
        {
            field_20131_b.remove(entityplayer);
        } else
        {
            field_20131_b.add(entityplayer);
        }
    }

    public abstract boolean canInteractWith(EntityHuman entityplayer);

    public List inventoryItemStacks;
    public List inventorySlots;
    public int windowId;
    private short field_20132_a;
    protected List crafters;
    private Set field_20131_b;
}
