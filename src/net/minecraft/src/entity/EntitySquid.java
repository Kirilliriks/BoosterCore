package net.minecraft.src.entity;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.MathHelper;
import net.minecraft.src.world.World;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.material.Material;
import net.minecraft.src.nbt.NBTTagCompound;

public class EntitySquid extends EntityWaterMob
{

    public EntitySquid(World world)
    {
        super(world);
        field_26597_a = 0.0F;
        field_26596_b = 0.0F;
        field_26595_c = 0.0F;
        field_26594_f = 0.0F;
        field_26593_g = 0.0F;
        field_26592_h = 0.0F;
        field_26591_i = 0.0F;
        field_26590_j = 0.0F;
        field_26589_k = 0.0F;
        field_26588_l = 0.0F;
        field_26587_m = 0.0F;
        field_26586_n = 0.0F;
        field_26585_o = 0.0F;
        field_26584_p = 0.0F;
        texture = "/mob/squid.png";
        setSize(0.95F, 0.95F);
        field_26588_l = (1.0F / (rand.nextFloat() + 1.0F)) * 0.2F;
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
        return null;
    }

    protected String getHurtSound()
    {
        return null;
    }

    protected String getDeathSound()
    {
        return null;
    }

    protected float getSoundVolume()
    {
        return 0.4F;
    }

    protected int getDropItemId()
    {
        return 0;
    }

    protected void dropFewItems()
    {
        int i = rand.nextInt(3) + 1;
        for(int j = 0; j < i; j++)
        {
            entityDropItem(new ItemStack(Item.dyePowder, 1, 0), 0.0F);
        }

    }

    public boolean interact(EntityPlayer entityplayer)
    {
        return false;
    }

    public boolean handleWaterMovement()
    {
        return worldObj.handleMaterialAcceleration(boundingBox.expand(0.0D, -0.60000002384185791D, 0.0D), Material.water, this);
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        field_26596_b = field_26597_a;
        field_26594_f = field_26595_c;
        field_26592_h = field_26593_g;
        field_26590_j = field_26591_i;
        field_26593_g += field_26588_l;
        if(field_26593_g > 6.283185F)
        {
            field_26593_g -= 6.283185F;
            if(rand.nextInt(10) == 0)
            {
                field_26588_l = (1.0F / (rand.nextFloat() + 1.0F)) * 0.2F;
            }
        }
        if(handleWaterMovement())
        {
            if(field_26593_g < 3.141593F)
            {
                float f = field_26593_g / 3.141593F;
                field_26591_i = MathHelper.sin(f * f * 3.141593F) * 3.141593F * 0.25F;
                if((double)f > 0.75D)
                {
                    field_26589_k = 1.0F;
                    field_26587_m = 1.0F;
                } else
                {
                    field_26587_m = field_26587_m * 0.8F;
                }
            } else
            {
                field_26591_i = 0.0F;
                field_26589_k = field_26589_k * 0.9F;
                field_26587_m = field_26587_m * 0.99F;
            }
            if(!field_9112_aN)
            {
                motionX = field_26586_n * field_26589_k;
                motionY = field_26585_o * field_26589_k;
                motionZ = field_26584_p * field_26589_k;
            }
            float f1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            renderYawOffset += ((-(float)Math.atan2(motionX, motionZ) * 180F) / 3.141593F - renderYawOffset) * 0.1F;
            rotationYaw = renderYawOffset;
            field_26595_c = field_26595_c + 3.141593F * field_26587_m * 1.5F;
            field_26597_a += ((-(float)Math.atan2(f1, motionY) * 180F) / 3.141593F - field_26597_a) * 0.1F;
        } else
        {
            field_26591_i = MathHelper.abs(MathHelper.sin(field_26593_g)) * 3.141593F * 0.25F;
            if(!field_9112_aN)
            {
                motionX = 0.0D;
                motionY -= 0.080000000000000002D;
                motionY *= 0.98000001907348633D;
                motionZ = 0.0D;
            }
            field_26597_a += (double)(-90F - field_26597_a) * 0.02D;
        }
    }

    public void moveEntityWithHeading(float f, float f1)
    {
        moveEntity(motionX, motionY, motionZ);
    }

    protected void updatePlayerActionState()
    {
        if(rand.nextInt(50) == 0 || !inWater || field_26586_n == 0.0F && field_26585_o == 0.0F && field_26584_p == 0.0F)
        {
            float f = rand.nextFloat() * 3.141593F * 2.0F;
            field_26586_n = MathHelper.cos(f) * 0.2F;
            field_26585_o = -0.1F + rand.nextFloat() * 0.2F;
            field_26584_p = MathHelper.sin(f) * 0.2F;
        }
    }

    public float field_26597_a;
    public float field_26596_b;
    public float field_26595_c;
    public float field_26594_f;
    public float field_26593_g;
    public float field_26592_h;
    public float field_26591_i;
    public float field_26590_j;
    private float field_26589_k;
    private float field_26588_l;
    private float field_26587_m;
    private float field_26586_n;
    private float field_26585_o;
    private float field_26584_p;
}
