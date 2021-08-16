package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;

import net.minecraft.src.block.Block;
import net.minecraft.src.chunk.ChunkCoordinate;
import net.minecraft.src.packet.Packet52MultiBlockChange;
import net.minecraft.src.entity.EntityPlayerMP;
import net.minecraft.src.packet.Packet;
import net.minecraft.src.packet.Packet50PreChunk;
import net.minecraft.src.packet.Packet51MapChunk;
import net.minecraft.src.packet.Packet53BlockChange;
import net.minecraft.src.tileentity.TileEntity;

class PlayerInstance {
    private List players;
    private int chunkX;
    private int chunkY;
    private ChunkCoordinate currentChunk;
    private short blocksToUpdate[];
    private int numBlocksToUpdate;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int minZ;
    private int maxZ;
    final ISaveHandler playerManager; /* synthetic field */

    public PlayerInstance(ISaveHandler isavehandler, int i, int j) {
        super();
        playerManager = isavehandler;
        players = new ArrayList();
        blocksToUpdate = new short[10];
        numBlocksToUpdate = 0;
        chunkX = i;
        chunkY = j;
        currentChunk = new ChunkCoordinate(i, j);
        ISaveHandler.func_26686_a(isavehandler).worldManager.chunkProvider.loadChunk(i, j);
    }

    public void addPlayer(EntityPlayerMP entityplayermp)
    {
        if(players.contains(entityplayermp))
        {
            throw new IllegalStateException((new StringBuilder()).append("Failed to add player. ").append(entityplayermp).append(" already is in chunk ").append(chunkX).append(", ").append(chunkY).toString());
        } else
        {
            entityplayermp.field_420_ah.add(currentChunk);
            entityplayermp.playerNetServerHandler.sendPacket(new Packet50PreChunk(currentChunk.field_26507_a, currentChunk.field_26506_b, true));
            players.add(entityplayermp);
            entityplayermp.loadedChunks.add(currentChunk);
            return;
        }
    }

    public void removePlayer(EntityPlayerMP entityplayermp)
    {
        if(!players.contains(entityplayermp))
        {
            (new IllegalStateException((new StringBuilder()).append("Failed to remove player. ").append(entityplayermp).append(" isn't in chunk ").append(chunkX).append(", ").append(chunkY).toString())).printStackTrace();
            return;
        }
        players.remove(entityplayermp);
        if(players.size() == 0)
        {
            long l = (long)chunkX + 0x7fffffffL | (long)chunkY + 0x7fffffffL << 32;
            ISaveHandler.func_26684_b(playerManager).remove(l);
            if(numBlocksToUpdate > 0)
            {
                ISaveHandler.func_26690_c(playerManager).remove(this);
            }
            ISaveHandler.func_26686_a(playerManager).worldManager.chunkProvider.func_374_c(chunkX, chunkY);
        }
        entityplayermp.loadedChunks.remove(currentChunk);
        if(entityplayermp.field_420_ah.contains(currentChunk))
        {
            entityplayermp.playerNetServerHandler.sendPacket(new Packet50PreChunk(chunkX, chunkY, false));
        }
    }

    public void markBlockNeedsUpdate(int i, int j, int k)
    {
        if(numBlocksToUpdate == 0)
        {
            ISaveHandler.func_26690_c(playerManager).add(this);
            minX = maxX = i;
            minY = maxY = j;
            minZ = maxZ = k;
        }
        if(minX > i)
        {
            minX = i;
        }
        if(maxX < i)
        {
            maxX = i;
        }
        if(minY > j)
        {
            minY = j;
        }
        if(maxY < j)
        {
            maxY = j;
        }
        if(minZ > k)
        {
            minZ = k;
        }
        if(maxZ < k)
        {
            maxZ = k;
        }
        if(numBlocksToUpdate < 10)
        {
            short word0 = (short)(i << 12 | k << 8 | j);
            for(int l = 0; l < numBlocksToUpdate; l++)
            {
                if(blocksToUpdate[l] == word0)
                {
                    return;
                }
            }

            blocksToUpdate[numBlocksToUpdate++] = word0;
        }
    }

    public void sendPacketToPlayersInInstance(Packet packet)
    {
        for(int i = 0; i < players.size(); i++)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)players.get(i);
            if(entityplayermp.field_420_ah.contains(currentChunk))
            {
                entityplayermp.playerNetServerHandler.sendPacket(packet);
            }
        }

    }

    public void onUpdate()
    {
        if(numBlocksToUpdate == 0)
        {
            return;
        }
        if(numBlocksToUpdate == 1)
        {
            int i = chunkX * 16 + minX;
            int l = minY;
            int k1 = chunkY * 16 + minZ;
            sendPacketToPlayersInInstance(new Packet53BlockChange(i, l, k1, ISaveHandler.func_26686_a(playerManager).worldManager));
            if(Block.isBlockContainer[ISaveHandler.func_26686_a(playerManager).worldManager.getBlockId(i, l, k1)])
            {
                updateTileEntity(ISaveHandler.func_26686_a(playerManager).worldManager.getBlockTileEntity(i, l, k1));
            }
        } else
        if(numBlocksToUpdate == 10)
        {
            minY = (minY / 2) * 2;
            maxY = (maxY / 2 + 1) * 2;
            int j = minX + chunkX * 16;
            int i1 = minY;
            int l1 = minZ + chunkY * 16;
            int j2 = (maxX - minX) + 1;
            int l2 = (maxY - minY) + 2;
            int i3 = (maxZ - minZ) + 1;
            sendPacketToPlayersInInstance(new Packet51MapChunk(j, i1, l1, j2, l2, i3, ISaveHandler.func_26686_a(playerManager).worldManager));
            List list = ISaveHandler.func_26686_a(playerManager).worldManager.getTileEntityList(j, i1, l1, j + j2, i1 + l2, l1 + i3);
            for(int j3 = 0; j3 < list.size(); j3++)
            {
                updateTileEntity((TileEntity)list.get(j3));
            }

        } else
        {
            sendPacketToPlayersInInstance(new Packet52MultiBlockChange(chunkX, chunkY, blocksToUpdate, numBlocksToUpdate, ISaveHandler.func_26686_a(playerManager).worldManager));
            for(int k = 0; k < numBlocksToUpdate; k++)
            {
                int j1 = chunkX * 16 + (numBlocksToUpdate >> 12 & 0xf);
                int i2 = numBlocksToUpdate & 0xff;
                int k2 = chunkY * 16 + (numBlocksToUpdate >> 8 & 0xf);
                if(Block.isBlockContainer[ISaveHandler.func_26686_a(playerManager).worldManager.getBlockId(j1, i2, k2)])
                {
                    System.out.println("Sending!");
                    updateTileEntity(ISaveHandler.func_26686_a(playerManager).worldManager.getBlockTileEntity(j1, i2, k2));
                }
            }

        }
        numBlocksToUpdate = 0;
    }

    private void updateTileEntity(TileEntity tileentity)
    {
        if(tileentity != null)
        {
            Packet packet = tileentity.getDescriptionPacket();
            if(packet != null)
            {
                sendPacketToPlayersInInstance(packet);
            }
        }
    }
}
