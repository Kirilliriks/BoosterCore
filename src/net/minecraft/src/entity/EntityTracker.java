package net.minecraft.src.entity;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import net.minecraft.src.packet.Packet;
import net.minecraft.src.packet.Packet25_ButEntity;

public class EntityTracker
{

    public EntityTracker(MinecraftServer minecraftserver)
    {
        trackedEntitySet = new HashSet();
        trackedEntityHashTable = new MCHashTable();
        mcServer = minecraftserver;
        maxTrackingDistanceThreshold = minecraftserver.configManager.getMaxTrackingDistance();
    }

    public void trackEntity(Entity entity)
    {
        if(entity instanceof EntityPlayerMP)
        {
            trackEntity(entity, 512, 2);
            EntityPlayerMP entityplayermp = (EntityPlayerMP)entity;
            Iterator iterator = trackedEntitySet.iterator();
            do
            {
                if(!iterator.hasNext())
                {
                    break;
                }
                EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
                if(entitytrackerentry.trackedEntity != entityplayermp)
                {
                    entitytrackerentry.updatePlayerEntity(entityplayermp);
                }
            } while(true);
        } else
        if(entity instanceof EntityFish)
        {
            trackEntity(entity, 64, 5, true);
        } else
        if(entity instanceof EntityArrow)
        {
            trackEntity(entity, 64, 5, true);
        } else
        if(entity instanceof EntitySnowball)
        {
            trackEntity(entity, 64, 5, true);
        } else
        if(entity instanceof Packet25_ButEntity)
        {
            trackEntity(entity, 64, 5, true);
        } else
        if(entity instanceof EntityItem)
        {
            trackEntity(entity, 64, 20, true);
        } else
        if(entity instanceof TileEntitySign)
        {
            trackEntity(entity, 160, 5, true);
        } else
        if(entity instanceof EntityBoat)
        {
            trackEntity(entity, 160, 5, true);
        } else
        if(entity instanceof EntitySquid)
        {
            trackEntity(entity, 160, 3, true);
        } else
        if(entity instanceof IAnimals)
        {
            trackEntity(entity, 160, 3);
        } else
        if(entity instanceof EntityTNTPrimed)
        {
            trackEntity(entity, 160, 10, true);
        } else
        if(entity instanceof EntityFallingSand)
        {
            trackEntity(entity, 160, 20, true);
        } else
        if(entity instanceof EntityPainting)
        {
            trackEntity(entity, 160, 0x7fffffff, false);
        }
    }

    public void trackEntity(Entity entity, int i, int j)
    {
        trackEntity(entity, i, j, false);
    }

    public void trackEntity(Entity entity, int i, int j, boolean flag)
    {
        if(i > maxTrackingDistanceThreshold)
        {
            i = maxTrackingDistanceThreshold;
        }
        if(trackedEntityHashTable.containsItem(entity.entityId))
        {
            throw new IllegalStateException("Entity is already tracked!");
        } else
        {
            EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entity, i, j, flag);
            trackedEntitySet.add(entitytrackerentry);
            trackedEntityHashTable.addKey(entity.entityId, entitytrackerentry);
            entitytrackerentry.updatePlayerEntities(mcServer.worldManager.playerEntities);
            return;
        }
    }

    public void untrackEntity(Entity entity)
    {
        if(entity instanceof EntityPlayerMP)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)entity;
            EntityTrackerEntry entitytrackerentry1;
            for(Iterator iterator = trackedEntitySet.iterator(); iterator.hasNext(); entitytrackerentry1.removeFromTrackedPlayers(entityplayermp))
            {
                entitytrackerentry1 = (EntityTrackerEntry)iterator.next();
            }

        }
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)trackedEntityHashTable.removeObject(entity.entityId);
        if(entitytrackerentry != null)
        {
            trackedEntitySet.remove(entitytrackerentry);
            entitytrackerentry.sendDestroyEntityPacketToTrackedPlayers();
        }
    }

    public void updateTrackedEntities()
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = trackedEntitySet.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                break;
            }
            EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
            entitytrackerentry.updatePlayerList(mcServer.worldManager.playerEntities);
            if(entitytrackerentry.playerEntitiesUpdated && (entitytrackerentry.trackedEntity instanceof EntityPlayerMP))
            {
                arraylist.add((EntityPlayerMP)entitytrackerentry.trackedEntity);
            }
        } while(true);
label0:
        for(int i = 0; i < arraylist.size(); i++)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)arraylist.get(i);
            Iterator iterator1 = trackedEntitySet.iterator();
            do
            {
                if(!iterator1.hasNext())
                {
                    continue label0;
                }
                EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)iterator1.next();
                if(entitytrackerentry1.trackedEntity != entityplayermp)
                {
                    entitytrackerentry1.updatePlayerEntity(entityplayermp);
                }
            } while(true);
        }

    }

    public void sendPacketToTrackedPlayers(Entity entity, Packet packet)
    {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)trackedEntityHashTable.lookup(entity.entityId);
        if(entitytrackerentry != null)
        {
            entitytrackerentry.sendPacketToTrackedPlayers(packet);
        }
    }

    public void sendPacketToTrackedPlayersAndTrackedEntity(Entity entity, Packet packet)
    {
        EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)trackedEntityHashTable.lookup(entity.entityId);
        if(entitytrackerentry != null)
        {
            entitytrackerentry.sendPacketToTrackedPlayersAndTrackedEntity(packet);
        }
    }

    public void removeTrackedPlayerSymmetric(EntityPlayerMP entityplayermp)
    {
        EntityTrackerEntry entitytrackerentry;
        for(Iterator iterator = trackedEntitySet.iterator(); iterator.hasNext(); entitytrackerentry.removeTrackedPlayerSymmetric(entityplayermp))
        {
            entitytrackerentry = (EntityTrackerEntry)iterator.next();
        }

    }

    private Set trackedEntitySet;
    private MCHashTable trackedEntityHashTable;
    private MinecraftServer mcServer;
    private int maxTrackingDistanceThreshold;
}
