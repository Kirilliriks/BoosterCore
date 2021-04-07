package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ItemFood extends Slot
{

    public ItemFood(EntityPlayer entityplayer, IInventory iinventory, IInventory iinventory1, int i, int j, int k)
    {
        super(iinventory1, i, j, k);
        field_26508_e = entityplayer;
        field_26509_d = iinventory;
    }

    public boolean isItemValid(ItemStack itemstack)
    {
        return false;
    }

    public void onPickupFromSlot(ItemStack itemstack)
    {
        field_26508_e.func_26604_a(StatList.field_25093_z[itemstack.itemID], 1);
        if(itemstack.itemID == Block.workbench.blockID)
        {
            field_26508_e.func_26604_a(AchievementList.field_25130_d, 1);
        }
        for(int i = 0; i < field_26509_d.getSizeInventory(); i++)
        {
            ItemStack itemstack1 = field_26509_d.getStackInSlot(i);
            if(itemstack1 == null)
            {
                continue;
            }
            field_26509_d.decrStackSize(i, 1);
            if(itemstack1.getItem().hasContainerItem())
            {
                field_26509_d.setInventorySlotContents(i, new ItemStack(itemstack1.getItem().getContainerItem()));
            }
        }

    }

    public boolean func_25003_d()
    {
        return true;
    }

    private final IInventory field_26509_d;
    private EntityPlayer field_26508_e;
}
