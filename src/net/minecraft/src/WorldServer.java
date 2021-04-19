package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.chunk.ChunkProviderServer;
import net.minecraft.src.chunk.IChunkLoader;
import net.minecraft.src.chunk.IChunkProvider;
import net.minecraft.src.entity.*;
import net.minecraft.src.packet.Packet38;
import net.minecraft.src.packet.Packet60;

public class WorldServer extends World
{

    public WorldServer(MinecraftServer minecraftserver, IDataManager worldgentaiga2, String s, int i, long l)
    {
        super(worldgentaiga2, s, l, WorldProvider.func_26670_a(i));
        field_819_z = false;
        field_20912_E = new MCHashTable();
        mcServer = minecraftserver;
    }

    public void updateEntityWithOptionalForce(Entity entity, boolean flag)
    {
        if(!mcServer.spawnPeacefulMobs && ((entity instanceof EntityAnimals) || (entity instanceof EntityWaterMob)))
        {
            entity.setEntityDead();
        }
        if(entity.riddenByEntity == null || !(entity.riddenByEntity instanceof EntityPlayer))
        {
            super.updateEntityWithOptionalForce(entity, flag);
        }
    }

    public void func_12017_b(Entity entity, boolean flag)
    {
        super.updateEntityWithOptionalForce(entity, flag);
    }

    protected IChunkProvider func_22086_b()
    {
        IChunkLoader ichunkloader = worldFile.func_26697_a(worldProvider);
        chunkProvider = new ChunkProviderServer(this, ichunkloader, worldProvider.func_26667_c());
        return chunkProvider;
    }

    public List getTileEntityList(int i, int j, int k, int l, int i1, int j1)
    {
        ArrayList arraylist = new ArrayList();
        for(int k1 = 0; k1 < loadedTileEntityList.size(); k1++)
        {
            TileEntity tileentity = (TileEntity)loadedTileEntityList.get(k1);
            if(tileentity.xCoord >= i && tileentity.yCoord >= j && tileentity.zCoord >= k && tileentity.xCoord < l && tileentity.yCoord < i1 && tileentity.zCoord < j1)
            {
                arraylist.add(tileentity);
            }
        }

        return arraylist;
    }

    public boolean canMineBlock(EntityPlayer entityplayer, int i, int j, int k)
    {
        int l = (int)MathHelper.abs(i - worldInfo.getSpawnX());
        int i1 = (int)MathHelper.abs(k - worldInfo.getSpawnZ());
        if(l > i1)
        {
            i1 = l;
        }
        return i1 > 16 || mcServer.configManager.isOp(entityplayer.username);
    }

    protected void obtainEntitySkin(Entity entity)
    {
        super.obtainEntitySkin(entity);
        field_20912_E.addKey(entity.entityId, entity);
    }

    protected void releaseEntitySkin(Entity entity)
    {
        super.releaseEntitySkin(entity);
        field_20912_E.removeObject(entity.entityId);
    }

    public Entity func_6158_a(int i)
    {
        return (Entity)field_20912_E.lookup(i);
    }

    public void func_9206_a(Entity entity, byte byte0)
    {
        Packet38 packet38 = new Packet38(entity.entityId, byte0);
        mcServer.entityTracker.sendPacketToTrackedPlayersAndTrackedEntity(entity, packet38);
    }

    public Explosion newExplosion(Entity entity, double d, double d1, double d2, 
            float f, boolean flag)
    {
        Explosion explosion = super.newExplosion(entity, d, d1, d2, f, flag);
        mcServer.configManager.sendPacketToPlayersAroundPoint(d, d1, d2, 64D, new Packet60(d, d1, d2, f, explosion.destroyedBlockPositions));
        return explosion;
    }

    public void playNoteAt(int i, int j, int k, int l, int i1)
    {
        super.playNoteAt(i, j, k, l, i1);
        mcServer.configManager.sendPacketToPlayersAroundPoint(i, j, k, 64D, new EntityEgg(i, j, k, l, i1));
    }

    public void func_22088_r()
    {
        worldFile.func_22093_e();
    }

    public ChunkProviderServer chunkProvider;
    public boolean field_819_z;
    public boolean levelSaving;
    private MinecraftServer mcServer;
    private MCHashTable field_20912_E;
}
