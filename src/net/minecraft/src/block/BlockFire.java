package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.*;

public class BlockFire
{

    public BlockFire(World world)
    {
        field_26566_c = 0.0F;
        abilityToCatchFire = world;
    }

    public void func_26553_a()
    {
        field_26561_h++;
        if(field_26560_i)
        {
            int i = field_26561_h - field_26556_m;
            int j = abilityToCatchFire.getBlockId(field_26559_j, field_26558_k, field_26557_l);
            if(j != 0)
            {
                Block block = Block.blocksList[j];
                float f = block.blockStrength(chanceToEncourageFire) * (float)(i + 1);
                if(f >= 1.0F)
                {
                    field_26560_i = false;
                    func_26551_d(field_26559_j, field_26558_k, field_26557_l);
                }
            } else
            {
                field_26560_i = false;
            }
        }
    }

    public void setBurnRate(int i, int j, int k)
    {
        field_26565_d = field_26561_h;
        int l = abilityToCatchFire.getBlockId(i, j, k);
        if(l > 0)
        {
            Block.blocksList[l].onBlockClicked(abilityToCatchFire, i, j, k, chanceToEncourageFire);
        }
        if(l > 0 && Block.blocksList[l].blockStrength(chanceToEncourageFire) >= 1.0F)
        {
            func_26551_d(i, j, k);
        } else
        {
            field_26564_e = i;
            field_26563_f = j;
            field_26562_g = k;
        }
    }

    public void func_26552_b(int i, int j, int k)
    {
        if(i == field_26564_e && j == field_26563_f && k == field_26562_g)
        {
            int l = field_26561_h - field_26565_d;
            int i1 = abilityToCatchFire.getBlockId(i, j, k);
            if(i1 != 0)
            {
                Block block = Block.blocksList[i1];
                float f = block.blockStrength(chanceToEncourageFire) * (float)(l + 1);
                if(f >= 1.0F)
                {
                    func_26551_d(i, j, k);
                } else
                if(!field_26560_i)
                {
                    field_26560_i = true;
                    field_26559_j = i;
                    field_26558_k = j;
                    field_26557_l = k;
                    field_26556_m = field_26565_d;
                }
            }
        }
        field_26566_c = 0.0F;
    }

    public boolean func_26550_c(int i, int j, int k)
    {
        Block block = Block.blocksList[abilityToCatchFire.getBlockId(i, j, k)];
        int l = abilityToCatchFire.getBlockMetadata(i, j, k);
        boolean flag = abilityToCatchFire.setBlockWithNotify(i, j, k, 0);
        if(block != null && flag)
        {
            block.onBlockDestroyedByPlayer(abilityToCatchFire, i, j, k, l);
        }
        return flag;
    }

    public boolean func_26551_d(int i, int j, int k)
    {
        int l = abilityToCatchFire.getBlockId(i, j, k);
        int i1 = abilityToCatchFire.getBlockMetadata(i, j, k);
        boolean flag = func_26550_c(i, j, k);
        ItemStack itemstack = chanceToEncourageFire.getCurrentEquippedItem();
        if(itemstack != null)
        {
            itemstack.func_25124_a(l, i, j, k, chanceToEncourageFire);
            if(itemstack.stackSize == 0)
            {
                itemstack.func_577_a(chanceToEncourageFire);
                chanceToEncourageFire.destroyCurrentEquippedItem();
            }
        }
        if(flag && chanceToEncourageFire.canHarvestBlock(Block.blocksList[l]))
        {
            Block.blocksList[l].harvestBlock(abilityToCatchFire, chanceToEncourageFire, i, j, k, i1);
            ((EntityPlayerMP)chanceToEncourageFire).playerNetServerHandler.sendPacket(new Packet53BlockChange(i, j, k, abilityToCatchFire));
        }
        return flag;
    }

    public boolean func_26554_a(EntityPlayer entityplayer, World world, ItemStack itemstack)
    {
        int i = itemstack.stackSize;
        ItemStack itemstack1 = itemstack.useItemRightClick(world, entityplayer);
        if(itemstack1 != itemstack || itemstack1 != null && itemstack1.stackSize != i)
        {
            entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = itemstack1;
            if(itemstack1.stackSize == 0)
            {
                entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = null;
            }
            return true;
        } else
        {
            return false;
        }
    }

    public boolean func_26555_a(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l)
    {
        int i1 = world.getBlockId(i, j, k);
        if(i1 > 0 && Block.blocksList[i1].blockActivated(world, i, j, k, entityplayer))
        {
            return true;
        }
        if(itemstack == null)
        {
            return false;
        } else
        {
            return itemstack.useItem(entityplayer, world, i, j, k, l);
        }
    }

    private World abilityToCatchFire;
    public EntityPlayer chanceToEncourageFire;
    private float field_26566_c;
    private int field_26565_d;
    private int field_26564_e;
    private int field_26563_f;
    private int field_26562_g;
    private int field_26561_h;
    private boolean field_26560_i;
    private int field_26559_j;
    private int field_26558_k;
    private int field_26557_l;
    private int field_26556_m;
}
