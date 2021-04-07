package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.entity.Entity;

public class WorldManager
    implements IWorldAccess
{

    public WorldManager(MinecraftServer minecraftserver)
    {
        mcServer = minecraftserver;
    }

    public void spawnParticle(String s, double d, double d1, double d2, 
            double d3, double d4, double d5)
    {
    }

    public void obtainEntitySkin(Entity entity)
    {
        mcServer.entityTracker.trackEntity(entity);
    }

    public void releaseEntitySkin(Entity entity)
    {
        mcServer.entityTracker.untrackEntity(entity);
    }

    public void playSound(String s, double d, double d1, double d2, 
            float f, float f1)
    {
    }

    public void markBlockRangeNeedsUpdate(int i, int j, int k, int l, int i1, int j1)
    {
    }

    public void updateAllRenderers()
    {
    }

    public void markBlockNeedsUpdate(int i, int j, int k)
    {
        mcServer.configManager.markBlockNeedsUpdate(i, j, k);
    }

    public void playRecord(String s, int i, int j, int k)
    {
    }

    public void doNothingWithTileEntity(int i, int j, int k, TileEntity tileentity)
    {
        mcServer.configManager.sentTileEntityToPlayer(i, j, k, tileentity);
    }

    private MinecraftServer mcServer;
}
