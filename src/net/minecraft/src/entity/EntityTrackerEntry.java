package net.minecraft.src.entity;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.block.Block;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.packet.*;
import net.minecraft.src.tileentity.TileEntitySign;

import java.util.*;

public class EntityTrackerEntry
{

    public EntityTrackerEntry(Entity entity, int i, int j, boolean flag)
    {
        updateCounter = 0;
        firstUpdateDone = false;
        playerEntitiesUpdated = false;
        trackedPlayers = new HashSet();
        trackedEntity = entity;
        trackingDistanceThreshold = i;
        field_9234_e = j;
        shouldSendMotionUpdates = flag;
        encodedPosX = MathHelper.floor_double(entity.posX * 32D);
        encodedPosY = MathHelper.floor_double(entity.posY * 32D);
        encodedPosZ = MathHelper.floor_double(entity.posZ * 32D);
        encodedRotationYaw = MathHelper.floor_float((entity.rotationYaw * 256F) / 360F);
        encodedRotationPitch = MathHelper.floor_float((entity.rotationPitch * 256F) / 360F);
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof EntityTrackerEntry)
        {
            return ((EntityTrackerEntry)obj).trackedEntity.entityId == trackedEntity.entityId;
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return trackedEntity.entityId;
    }

    public void updatePlayerList(List list)
    {
        playerEntitiesUpdated = false;
        if(!firstUpdateDone || trackedEntity.getDistanceSq(lastTrackedEntityPosX, lastTrackedEntityPosY, lastTrackedEntityPosZ) > 16D)
        {
            lastTrackedEntityPosX = trackedEntity.posX;
            lastTrackedEntityPosY = trackedEntity.posY;
            lastTrackedEntityPosZ = trackedEntity.posZ;
            firstUpdateDone = true;
            playerEntitiesUpdated = true;
            updatePlayerEntities(list);
        }
        if(++updateCounter % field_9234_e == 0)
        {
            int i = MathHelper.floor_double(trackedEntity.posX * 32D);
            int j = MathHelper.floor_double(trackedEntity.posY * 32D);
            int k = MathHelper.floor_double(trackedEntity.posZ * 32D);
            int l = MathHelper.floor_float((trackedEntity.rotationYaw * 256F) / 360F);
            int i1 = MathHelper.floor_float((trackedEntity.rotationPitch * 256F) / 360F);
            int j1 = i - encodedPosX;
            int k1 = j - encodedPosY;
            int l1 = k - encodedPosZ;
            Object obj = null;
            boolean flag = Math.abs(i) >= 8 || Math.abs(j) >= 8 || Math.abs(k) >= 8;
            boolean flag1 = Math.abs(l - encodedRotationYaw) >= 8 || Math.abs(i1 - encodedRotationPitch) >= 8;
            if(j1 < -128 || j1 >= 128 || k1 < -128 || k1 >= 128 || l1 < -128 || l1 >= 128)
            {
                obj = new Packet34EntityTeleport(trackedEntity.entityId, i, j, k, (byte)l, (byte)i1);
            } else
            if(flag && flag1)
            {
                obj = new Packet33RelEntityMoveLook(trackedEntity.entityId, (byte)j1, (byte)k1, (byte)l1, (byte)l, (byte)i1);
            } else
            if(flag)
            {
                obj = new Packet31RelEntityMove(trackedEntity.entityId, (byte)j1, (byte)k1, (byte)l1);
            } else
            if(flag1)
            {
                obj = new Packet32EntityLook(trackedEntity.entityId, (byte)l, (byte)i1);
            }
            if(shouldSendMotionUpdates)
            {
                double d = trackedEntity.motionX - lastTrackedEntityMotionX;
                double d1 = trackedEntity.motionY - lastTrackedEntityMotionY;
                double d2 = trackedEntity.motionZ - lastTrackedEntityMotionZ;
                double d3 = 0.02D;
                double d4 = d * d + d1 * d1 + d2 * d2;
                if(d4 > d3 * d3 || d4 > 0.0D && trackedEntity.motionX == 0.0D && trackedEntity.motionY == 0.0D && trackedEntity.motionZ == 0.0D)
                {
                    lastTrackedEntityMotionX = trackedEntity.motionX;
                    lastTrackedEntityMotionY = trackedEntity.motionY;
                    lastTrackedEntityMotionZ = trackedEntity.motionZ;
                    sendPacketToTrackedPlayers(new Packet28(trackedEntity.entityId, lastTrackedEntityMotionX, lastTrackedEntityMotionY, lastTrackedEntityMotionZ));
                }
            }
            if(obj != null)
            {
                sendPacketToTrackedPlayers(((Packet) (obj)));
            }
            DataWatcher datawatcher = trackedEntity.func_26572_T();
            if(datawatcher.hasObjectChanged())
            {
                sendPacketToTrackedPlayersAndTrackedEntity(new Packet40(trackedEntity.entityId, datawatcher));
            }
            if(flag)
            {
                encodedPosX = i;
                encodedPosY = j;
                encodedPosZ = k;
            }
            if(flag1)
            {
                encodedRotationYaw = l;
                encodedRotationPitch = i1;
            }
        }
        if(trackedEntity.beenAttacked)
        {
            sendPacketToTrackedPlayersAndTrackedEntity(new Packet28(trackedEntity));
            trackedEntity.beenAttacked = false;
        }
    }

    public void sendPacketToTrackedPlayers(Packet packet)
    {
        EntityPlayerMP entityplayermp;
        for(Iterator iterator = trackedPlayers.iterator(); iterator.hasNext(); entityplayermp.playerNetServerHandler.sendPacket(packet))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }

    }

    public void sendPacketToTrackedPlayersAndTrackedEntity(Packet packet)
    {
        sendPacketToTrackedPlayers(packet);
        if(trackedEntity instanceof EntityPlayerMP)
        {
            ((EntityPlayerMP)trackedEntity).playerNetServerHandler.sendPacket(packet);
        }
    }

    public void sendDestroyEntityPacketToTrackedPlayers()
    {
        sendPacketToTrackedPlayers(new Packet29DestroyEntity(trackedEntity.entityId));
    }

    public void removeFromTrackedPlayers(EntityPlayerMP entityplayermp)
    {
        if(trackedPlayers.contains(entityplayermp))
        {
            trackedPlayers.remove(entityplayermp);
        }
    }

    public void updatePlayerEntity(EntityPlayerMP entityplayermp)
    {
        if(entityplayermp == trackedEntity)
        {
            return;
        }
        double d = entityplayermp.posX - (double)(encodedPosX / 32);
        double d1 = entityplayermp.posZ - (double)(encodedPosZ / 32);
        if(d >= (double)(-trackingDistanceThreshold) && d <= (double)trackingDistanceThreshold && d1 >= (double)(-trackingDistanceThreshold) && d1 <= (double)trackingDistanceThreshold)
        {
            if(!trackedPlayers.contains(entityplayermp))
            {
                trackedPlayers.add(entityplayermp);
                entityplayermp.playerNetServerHandler.sendPacket(getSpawnPacket());
                if(shouldSendMotionUpdates)
                {
                    entityplayermp.playerNetServerHandler.sendPacket(new Packet28(trackedEntity.entityId, trackedEntity.motionX, trackedEntity.motionY, trackedEntity.motionZ));
                }
                ItemStack aitemstack[] = trackedEntity.getInventory();
                if(aitemstack != null)
                {
                    for(int i = 0; i < aitemstack.length; i++)
                    {
                        entityplayermp.playerNetServerHandler.sendPacket(new Packet5PlayerInventory(trackedEntity.entityId, i, aitemstack[i]));
                    }

                }
            }
        } else
        if(trackedPlayers.contains(entityplayermp))
        {
            trackedPlayers.remove(entityplayermp);
            entityplayermp.playerNetServerHandler.sendPacket(new Packet29DestroyEntity(trackedEntity.entityId));
        }
    }

    public void updatePlayerEntities(List list)
    {
        for(int i = 0; i < list.size(); i++)
        {
            updatePlayerEntity((EntityPlayerMP)list.get(i));
        }

    }

    private Packet getSpawnPacket()
    {
        if(trackedEntity instanceof EntityItem)
        {
            EntityItem entityitem = (EntityItem)trackedEntity;
            Packet21PickupSpawn packet21pickupspawn = new Packet21PickupSpawn(entityitem);
            entityitem.posX = (double)packet21pickupspawn.xPosition / 32D;
            entityitem.posY = (double)packet21pickupspawn.yPosition / 32D;
            entityitem.posZ = (double)packet21pickupspawn.zPosition / 32D;
            return packet21pickupspawn;
        }
        if(trackedEntity instanceof EntityPlayerMP)
        {
            return new Packet20NamedEntitySpawn((EntityPlayer)trackedEntity);
        }
        if(trackedEntity instanceof TileEntitySign)
        {
            TileEntitySign tileentitysign = (TileEntitySign)trackedEntity;
            if(tileentitysign.field_26625_d == 0)
            {
                return new Packet23VehicleSpawn(trackedEntity, 10);
            }
            if(tileentitysign.field_26625_d == 1)
            {
                return new Packet23VehicleSpawn(trackedEntity, 11);
            }
            if(tileentitysign.field_26625_d == 2)
            {
                return new Packet23VehicleSpawn(trackedEntity, 12);
            }
        }
        if(trackedEntity instanceof EntityBoat)
        {
            return new Packet23VehicleSpawn(trackedEntity, 1);
        }
        if(trackedEntity instanceof IAnimals)
        {
            return new Packet24MobSpawn((EntityLiving)trackedEntity);
        }
        if(trackedEntity instanceof EntityFish)
        {
            return new Packet23VehicleSpawn(trackedEntity, 90);
        }
        if(trackedEntity instanceof EntityArrow)
        {
            return new Packet23VehicleSpawn(trackedEntity, 60);
        }
        if(trackedEntity instanceof EntitySnowball)
        {
            return new Packet23VehicleSpawn(trackedEntity, 61);
        }
        if(trackedEntity instanceof Packet25_ButEntity)
        {
            return new Packet23VehicleSpawn(trackedEntity, 62);
        }
        if(trackedEntity instanceof EntityTNTPrimed)
        {
            return new Packet23VehicleSpawn(trackedEntity, 50);
        }
        if(trackedEntity instanceof EntityFallingSand)
        {
            EntityFallingSand entityfallingsand = (EntityFallingSand)trackedEntity;
            if(entityfallingsand.blockID == Block.sand.blockID)
            {
                return new Packet23VehicleSpawn(trackedEntity, 70);
            }
            if(entityfallingsand.blockID == Block.gravel.blockID)
            {
                return new Packet23VehicleSpawn(trackedEntity, 71);
            }
        }
        if(trackedEntity instanceof EntityPainting)
        {
            return new StatBasic((EntityPainting)trackedEntity);
        } else
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Don't know how to add ").append(trackedEntity.getClass()).append("!").toString());
        }
    }

    public void removeTrackedPlayerSymmetric(EntityPlayerMP entityplayermp)
    {
        if(trackedPlayers.contains(entityplayermp))
        {
            trackedPlayers.remove(entityplayermp);
            entityplayermp.playerNetServerHandler.sendPacket(new Packet29DestroyEntity(trackedEntity.entityId));
        }
    }

    public Entity trackedEntity;
    public int trackingDistanceThreshold;
    public int field_9234_e;
    public int encodedPosX;
    public int encodedPosY;
    public int encodedPosZ;
    public int encodedRotationYaw;
    public int encodedRotationPitch;
    public double lastTrackedEntityMotionX;
    public double lastTrackedEntityMotionY;
    public double lastTrackedEntityMotionZ;
    public int updateCounter;
    private double lastTrackedEntityPosX;
    private double lastTrackedEntityPosY;
    private double lastTrackedEntityPosZ;
    private boolean firstUpdateDone;
    private boolean shouldSendMotionUpdates;
    public boolean playerEntitiesUpdated;
    public Set trackedPlayers;
}
