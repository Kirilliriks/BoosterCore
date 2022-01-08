package net.minecraft.src.packet;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.entity.*;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.nbt.NBTTagCompound;
import net.minecraft.src.world.World;

import java.util.List;

public class Packet25_ButEntity extends Entity
{

    public Packet25_ButEntity(World world)
    {
        super(world);
        xPosition = -1;
        yPosition = -1;
        zPosition = -1;
        direction = 0;
        title = false;
        entityId = 0;
        field_26577_i = 0;
        setSize(0.25F, 0.25F);
    }

    protected void entityInit()
    {
    }

    public Packet25_ButEntity(World world, EntityLiving entityliving)
    {
        super(world);
        xPosition = -1;
        yPosition = -1;
        zPosition = -1;
        direction = 0;
        title = false;
        entityId = 0;
        field_26577_i = 0;
        field_26579_g = entityliving;
        setSize(0.25F, 0.25F);
        setLocationAndAngles(entityliving.posX, entityliving.posY + (double)entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        float f = 0.4F;
        motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f;
        func_26576_a(motionX, motionY, motionZ, 1.5F, 1.0F);
    }

    public Packet25_ButEntity(World world, double d, double d1, double d2)
    {
        super(world);
        xPosition = -1;
        yPosition = -1;
        zPosition = -1;
        direction = 0;
        title = false;
        entityId = 0;
        field_26577_i = 0;
        field_26578_h = 0;
        setSize(0.25F, 0.25F);
        setPosition(d, d1, d2);
        yOffset = 0.0F;
    }

    public void func_26576_a(double d, double d1, double d2, float f, 
            float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
        field_26578_h = 0;
    }

    public void onUpdate()
    {
        lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;
        super.onUpdate();
        if(entityId > 0)
        {
            entityId--;
        }
        if(title)
        {
            int i = worldObj.getBlockId(xPosition, yPosition, zPosition);
            if(i != direction)
            {
                title = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                field_26578_h = 0;
                field_26577_i = 0;
            } else
            {
                field_26578_h++;
                if(field_26578_h == 1200)
                {
                    setEntityDead();
                }
                return;
            }
        } else
        {
            field_26577_i++;
        }
        Vec3D vec3d = Vec3D.createVector(posX, posY, posZ);
        Vec3D vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(vec3d, vec3d1);
        vec3d = Vec3D.createVector(posX, posY, posZ);
        vec3d1 = Vec3D.createVector(posX + motionX, posY + motionY, posZ + motionZ);
        if(movingobjectposition != null)
        {
            vec3d1 = Vec3D.createVector(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        if(!worldObj.singleplayerWorld)
        {
            Entity entity = null;
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
            double d = 0.0D;
            for(int i1 = 0; i1 < list.size(); i1++)
            {
                Entity entity1 = (Entity)list.get(i1);
                if(!entity1.canBeCollidedWith() || entity1 == field_26579_g && field_26577_i < 5)
                {
                    continue;
                }
                float f4 = 0.3F;
                AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f4, f4, f4);
                MovingObjectPosition movingobjectposition1 = axisalignedbb.func_706_a(vec3d, vec3d1);
                if(movingobjectposition1 == null)
                {
                    continue;
                }
                double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
                if(d1 < d || d == 0.0D)
                {
                    entity = entity1;
                    d = d1;
                }
            }

            if(entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);
            }
        }
        if(movingobjectposition != null)
        {
            if(movingobjectposition.entityHit != null)
            {
                if(!movingobjectposition.entityHit.attackEntityFrom(field_26579_g, 0));
            }
            if(!worldObj.singleplayerWorld && rand.nextInt(8) == 0)
            {
                byte byte0 = 1;
                if(rand.nextInt(32) == 0)
                {
                    byte0 = 4;
                }
                for(int k = 0; k < byte0; k++)
                {
                    EntityChicken playermanager = new EntityChicken(worldObj);
                    playermanager.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
                    worldObj.entityJoinedWorld(playermanager);
                }

            }
            for(int j = 0; j < 8; j++)
            {
                worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            }

            setEntityDead();
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        for(rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f1 = 0.99F;
        float f2 = 0.03F;
        if(handleWaterMovement())
        {
            for(int l = 0; l < 4; l++)
            {
                float f3 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f3, posY - motionY * (double)f3, posZ - motionZ * (double)f3, motionX, motionY, motionZ);
            }

            f1 = 0.8F;
        }
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        motionY -= f2;
        setPosition(posX, posY, posZ);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)xPosition);
        nbttagcompound.setShort("yTile", (short)yPosition);
        nbttagcompound.setShort("zTile", (short)zPosition);
        nbttagcompound.setByte("inTile", (byte)direction);
        nbttagcompound.setByte("shake", (byte)entityId);
        nbttagcompound.setByte("inGround", (byte)(title ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        xPosition = nbttagcompound.getShort("xTile");
        yPosition = nbttagcompound.getShort("yTile");
        zPosition = nbttagcompound.getShort("zTile");
        direction = nbttagcompound.getByte("inTile") & 0xff;
        entityId = nbttagcompound.getByte("shake") & 0xff;
        title = nbttagcompound.getByte("inGround") == 1;
    }

    public void onCollideWithPlayer(EntityHuman entityplayer)
    {
        if(title && field_26579_g == entityplayer && entityId <= 0 && entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1)))
        {
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.onItemPickup(this, 1);
            setEntityDead();
        }
    }

    private int xPosition;
    private int yPosition;
    private int zPosition;
    private int direction;
    private boolean title;
    public int entityId;
    private EntityLiving field_26579_g;
    private int field_26578_h;
    private int field_26577_i;
}
