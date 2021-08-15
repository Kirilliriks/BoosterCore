package net.minecraft.src.entity;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.world.World;
import net.minecraft.src.item.Item;
import net.minecraft.src.nbt.NBTTagCompound;

public class EntityChicken extends EntityAnimals
{

    public EntityChicken(World world)
    {
        super(world);
        players = false;
        playerInstances = 0.0F;
        playerInstancesToUpdate = 0.0F;
        field_26599_h = 1.0F;
        texture = "/mob/chicken.png";
        setSize(0.3F, 0.4F);
        health = 4;
        field_26598_i = rand.nextInt(6000) + 6000;
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        field_26600_g = playerInstances;
        field_26601_f = playerInstancesToUpdate;
        playerInstancesToUpdate += (double)(onGround ? -1 : 4) * 0.29999999999999999D;
        if(playerInstancesToUpdate < 0.0F)
        {
            playerInstancesToUpdate = 0.0F;
        }
        if(playerInstancesToUpdate > 1.0F)
        {
            playerInstancesToUpdate = 1.0F;
        }
        if(!onGround && field_26599_h < 1.0F)
        {
            field_26599_h = 1.0F;
        }
        field_26599_h *= 0.90000000000000002D;
        if(!onGround && motionY < 0.0D)
        {
            motionY *= 0.59999999999999998D;
        }
        playerInstances += field_26599_h * 2.0F;
        if(!worldObj.singleplayerWorld && --field_26598_i <= 0)
        {
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            dropItem(Item.egg.shiftedIndex, 1);
            field_26598_i = rand.nextInt(6000) + 6000;
        }
    }

    protected void fall(float f)
    {
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    protected String getLivingSound()
    {
        return "mob.chicken";
    }

    protected String getHurtSound()
    {
        return "mob.chickenhurt";
    }

    protected String getDeathSound()
    {
        return "mob.chickenhurt";
    }

    protected int getDropItemId()
    {
        return Item.feather.shiftedIndex;
    }

    public boolean players;
    public float playerInstances;
    public float playerInstancesToUpdate;
    public float field_26601_f;
    public float field_26600_g;
    public float field_26599_h;
    public int field_26598_i;
}
