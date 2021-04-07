package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.block.Block;
import net.minecraft.src.crafting.CraftingInventoryPlayerCB;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.packet.Packet54;

public class SlotArmor extends Slot
{

    public SlotArmor(CraftingInventoryPlayerCB craftinginventoryplayercb, IInventory iinventory, int i, int j, int k, int l)
    {
        super(iinventory, i, j, k);
        field_20101_b = craftinginventoryplayercb;
        field_20102_a = l;
    }

    public int getSlotStackLimit()
    {
        return 1;
    }

    public boolean isItemValid(ItemStack itemstack)
    {
        if(itemstack.getItem() instanceof Packet54)
        {
            return ((Packet54)itemstack.getItem()).field_26512_bi == field_20102_a;
        }
        if(itemstack.getItem().shiftedIndex == Block.pumpkin.blockID)
        {
            return field_20102_a == 0;
        } else
        {
            return false;
        }
    }

    final int field_20102_a; /* synthetic field */
    final CraftingInventoryPlayerCB field_20101_b; /* synthetic field */
}
