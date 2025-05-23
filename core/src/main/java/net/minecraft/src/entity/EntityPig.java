package net.minecraft.src.entity;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.item.Item;
import net.minecraft.src.nbt.NBTTagCompound;
import net.minecraft.src.world.World;

public class EntityPig extends EntityAnimals
{

    public EntityPig(World world)
    {
        super(world);
        texture = "/mob/pig.png";
        setSize(0.9F, 0.9F);
    }

    protected void entityInit()
    {
        dataWatcher.addObject(16, (byte) 0);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", func_26603_v());
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        func_26602_a(nbttagcompound.getBoolean("Saddle"));
    }

    protected String getLivingSound()
    {
        return "mob.pig";
    }

    protected String getHurtSound()
    {
        return "mob.pig";
    }

    protected String getDeathSound()
    {
        return "mob.pigdeath";
    }

    public boolean interact(EntityHuman entityplayer)
    {
        if(func_26603_v() && !worldObj.singleplayerWorld && (riddenByEntity == null || riddenByEntity == entityplayer))
        {
            entityplayer.mountEntity(this);
            return true;
        } else
        {
            return false;
        }
    }

    protected int getDropItemId()
    {
        return Item.porkRaw.shiftedIndex;
    }

    public boolean func_26603_v()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void func_26602_a(boolean flag)
    {
        if(flag)
        {
            dataWatcher.updateObject(16, (byte) 1);
        } else
        {
            dataWatcher.updateObject(16, (byte) 0);
        }
    }
}
