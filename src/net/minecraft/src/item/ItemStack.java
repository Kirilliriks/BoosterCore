package net.minecraft.src.item;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.nbt.NBTTagCompound;
import net.minecraft.src.StatList;
import net.minecraft.src.world.World;
import net.minecraft.src.block.Block;
import net.minecraft.src.entity.Entity;
import net.minecraft.src.entity.EntityLiving;
import net.minecraft.src.entity.EntityHuman;

public final class ItemStack
{

    public ItemStack(Block block)
    {
        this(block, 1);
    }

    public ItemStack(Block block, int i)
    {
        this(block.blockID, i, 0);
    }

    public ItemStack(Block block, int i, int j)
    {
        this(block.blockID, i, j);
    }

    public ItemStack(Item item)
    {
        this(item.shiftedIndex, 1, 0);
    }

    public ItemStack(Item item, int i)
    {
        this(item.shiftedIndex, i, 0);
    }

    public ItemStack(Item item, int i, int j)
    {
        this(item.shiftedIndex, i, j);
    }

    public ItemStack(int itemID, int stackSize, int itemDamage)
    {
        this.itemID = itemID;
        this.stackSize = stackSize;
        this.itemDamage = itemDamage;
    }

    public ItemStack(NBTTagCompound nbttagcompound)
    {
        stackSize = 0;
        readFromNBT(nbttagcompound);
    }

    public ItemStack splitStack(int i)
    {
        stackSize -= i;
        return new ItemStack(itemID, i, itemDamage);
    }

    public Item getItem()
    {
        return Item.itemsList[itemID];
    }

    public boolean useItem(EntityHuman entityplayer, World world, int i, int j, int k, int l)
    {
        boolean flag = getItem().onItemUse(this, entityplayer, world, i, j, k, l);
        if(flag)
        {
            entityplayer.func_26604_a(StatList.field_25107_A[itemID], 1);
        }
        return flag;
    }

    public float getStrVsBlock(Block block)
    {
        return getItem().getStrVsBlock(this, block);
    }

    public ItemStack useItemRightClick(World world, EntityHuman entityplayer)
    {
        return getItem().onItemRightClick(this, world, entityplayer);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("id", (short)itemID);
        nbttagcompound.setByte("Count", (byte)stackSize);
        nbttagcompound.setShort("Damage", (short)itemDamage);
        return nbttagcompound;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        itemID = nbttagcompound.getShort("id");
        stackSize = nbttagcompound.getByte("Count");
        itemDamage = nbttagcompound.getShort("Damage");
    }

    public int getMaxStackSize()
    {
        return getItem().getItemStackLimit();
    }

    public boolean func_21132_c()
    {
        return getMaxStackSize() > 1 && (!isItemStackDamageable() || !isItemDamaged());
    }

    public boolean isItemStackDamageable()
    {
        return Item.itemsList[itemID].getMaxDamage() > 0;
    }

    public boolean getHasSubtypes()
    {
        return Item.itemsList[itemID].getHasSubtypes();
    }

    public boolean isItemDamaged()
    {
        return isItemStackDamageable() && itemDamage > 0;
    }

    public int getItemDamageForDisplay()
    {
        return itemDamage;
    }

    public int getItemDamage()
    {
        return itemDamage;
    }

    public int getMaxDamage()
    {
        return Item.itemsList[itemID].getMaxDamage();
    }

    public void func_25125_a(int i, Entity entity)
    {
        if(!isItemStackDamageable())
        {
            return;
        }
        itemDamage += i;
        if(itemDamage > getMaxDamage())
        {
            if(entity instanceof EntityHuman)
            {
                ((EntityHuman)entity).func_26604_a(StatList.field_25105_B[itemID], 1);
            }
            stackSize--;
            if(stackSize < 0)
            {
                stackSize = 0;
            }
            itemDamage = 0;
        }
    }

    public void hitEntity(EntityLiving entityliving, EntityHuman entityplayer)
    {
        boolean flag = Item.itemsList[itemID].hitEntity(this, entityliving, entityplayer);
        if(flag)
        {
            entityplayer.func_26604_a(StatList.field_25107_A[itemID], 1);
        }
    }

    public void func_25124_a(int i, int j, int k, int l, EntityHuman entityplayer)
    {
        boolean flag = Item.itemsList[itemID].func_25007_a(this, i, j, k, l, entityplayer);
        if(flag)
        {
            entityplayer.func_26604_a(StatList.field_25107_A[itemID], 1);
        }
    }

    public int getDamageVsEntity(Entity entity)
    {
        return Item.itemsList[itemID].getDamageVsEntity(entity);
    }

    public boolean canHarvestBlock(Block block)
    {
        return Item.itemsList[itemID].canHarvestBlock(block);
    }

    public void func_577_a(EntityHuman entityplayer)
    {
    }

    public void useItemOnEntity(EntityLiving entityliving)
    {
        Item.itemsList[itemID].saddleEntity(this, entityliving);
    }

    public ItemStack copy()
    {
        return new ItemStack(itemID, stackSize, itemDamage);
    }

    public static boolean areItemStacksEqual(ItemStack itemstack, ItemStack itemstack1)
    {
        if(itemstack == null && itemstack1 == null)
        {
            return true;
        }
        if(itemstack == null || itemstack1 == null)
        {
            return false;
        } else
        {
            return itemstack.isItemStackEqual(itemstack1);
        }
    }

    private boolean isItemStackEqual(ItemStack itemstack)
    {
        if(stackSize != itemstack.stackSize)
        {
            return false;
        }
        if(itemID != itemstack.itemID)
        {
            return false;
        }
        return itemDamage == itemstack.itemDamage;
    }

    public boolean isItemEqual(ItemStack itemstack)
    {
        return itemID == itemstack.itemID && itemDamage == itemstack.itemDamage;
    }

    public static ItemStack func_20117_a(ItemStack itemstack)
    {
        return itemstack != null ? itemstack.copy() : null;
    }

    public String toString()
    {
        return (new StringBuilder()).append(stackSize).append("x").append(Item.itemsList[itemID].getItemName()).append("@").append(itemDamage).toString();
    }

    public int stackSize;
    public int animationsToGo;
    public int itemID;
    private int itemDamage;
}
