package net.minecraft.src.chunk;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.entity.EntityPigZombie;

public class ChunkProviderLoadOrGenerate
    implements IChunkProvider
{

    public ChunkProviderLoadOrGenerate(World world, IChunkLoader ichunkloader, IChunkProvider ichunkprovider)
    {
        chunks = new Chunk[1024];
        lastQueriedChunkXPos = 0xc4653601;
        lastQueriedChunkZPos = 0xc4653601;
        blankChunk = new EmptyChunk(world, new byte[32768], 0, 0);
        worldObj = world;
        chunkLoader = ichunkloader;
        chunkProvider = ichunkprovider;
    }

    public boolean func_21111_c(int i, int j)
    {
        byte byte0 = 15;
        return i >= field_21113_i - byte0 && j >= field_21112_j - byte0 && i <= field_21113_i + byte0 && j <= field_21112_j + byte0;
    }

    public boolean chunkExists(int i, int j)
    {
        if(!func_21111_c(i, j))
        {
            return false;
        }
        if(i == lastQueriedChunkXPos && j == lastQueriedChunkZPos && lastQueriedChunk != null)
        {
            return true;
        } else
        {
            int k = i & 0x1f;
            int l = j & 0x1f;
            int i1 = k + l * 32;
            return chunks[i1] != null && (chunks[i1] == blankChunk || chunks[i1].isAtLocation(i, j));
        }
    }

    public Chunk loadChunk(int i, int j)
    {
        return provideChunk(i, j);
    }

    public Chunk provideChunk(int i, int j)
    {
        if(i == lastQueriedChunkXPos && j == lastQueriedChunkZPos && lastQueriedChunk != null)
        {
            return lastQueriedChunk;
        }
        if(!worldObj.field_9209_x && !func_21111_c(i, j))
        {
            return blankChunk;
        }
        int k = i & 0x1f;
        int l = j & 0x1f;
        int i1 = k + l * 32;
        if(!chunkExists(i, j))
        {
            if(chunks[i1] != null)
            {
                chunks[i1].onChunkUnload();
                saveChunk(chunks[i1]);
                saveExtraChunkData(chunks[i1]);
            }
            Chunk chunk = func_4059_c(i, j);
            if(chunk == null)
            {
                if(chunkProvider == null)
                {
                    chunk = blankChunk;
                } else
                {
                    chunk = chunkProvider.provideChunk(i, j);
                }
            }
            chunks[i1] = chunk;
            chunk.func_4053_c();
            if(chunks[i1] != null)
            {
                chunks[i1].onChunkLoad();
            }
            if(!chunks[i1].isTerrainPopulated && chunkExists(i + 1, j + 1) && chunkExists(i, j + 1) && chunkExists(i + 1, j))
            {
                populate(this, i, j);
            }
            if(chunkExists(i - 1, j) && !provideChunk(i - 1, j).isTerrainPopulated && chunkExists(i - 1, j + 1) && chunkExists(i, j + 1) && chunkExists(i - 1, j))
            {
                populate(this, i - 1, j);
            }
            if(chunkExists(i, j - 1) && !provideChunk(i, j - 1).isTerrainPopulated && chunkExists(i + 1, j - 1) && chunkExists(i, j - 1) && chunkExists(i + 1, j))
            {
                populate(this, i, j - 1);
            }
            if(chunkExists(i - 1, j - 1) && !provideChunk(i - 1, j - 1).isTerrainPopulated && chunkExists(i - 1, j - 1) && chunkExists(i, j - 1) && chunkExists(i - 1, j))
            {
                populate(this, i - 1, j - 1);
            }
        }
        lastQueriedChunkXPos = i;
        lastQueriedChunkZPos = j;
        lastQueriedChunk = chunks[i1];
        return chunks[i1];
    }

    private Chunk func_4059_c(int i, int j)
    {
        if(chunkLoader == null)
        {
            return blankChunk;
        }
        try
        {
            Chunk chunk = chunkLoader.loadChunk(worldObj, i, j);
            if(chunk != null)
            {
                chunk.lastSaveTime = worldObj.getWorldTime();
            }
            return chunk;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return blankChunk;
    }

    private void saveExtraChunkData(Chunk chunk)
    {
        if(chunkLoader == null)
        {
            return;
        }
        try
        {
            chunkLoader.saveExtraChunkData(worldObj, chunk);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void saveChunk(Chunk chunk)
    {
        if(chunkLoader == null)
        {
            return;
        }
//        try
//        {
            chunk.lastSaveTime = worldObj.getWorldTime();
            chunkLoader.saveChunk(worldObj, chunk);
//        }
//        catch(IOException ioexception)
//        {
//            ioexception.printStackTrace();
//        }
    }

    public void populate(IChunkProvider ichunkprovider, int i, int j)
    {
        Chunk chunk = provideChunk(i, j);
        if(!chunk.isTerrainPopulated)
        {
            chunk.isTerrainPopulated = true;
            if(chunkProvider != null)
            {
                chunkProvider.populate(ichunkprovider, i, j);
                chunk.setChunkModified();
            }
        }
    }

    public boolean func_26631_a(boolean flag, EntityPigZombie entitypigzombie)
    {
        int i = 0;
        int j = 0;
        if(entitypigzombie != null)
        {
            for(int k = 0; k < chunks.length; k++)
            {
                if(chunks[k] != null && chunks[k].needsSaving(flag))
                {
                    j++;
                }
            }

        }
        int l = 0;
        for(int i1 = 0; i1 < chunks.length; i1++)
        {
            if(chunks[i1] == null)
            {
                continue;
            }
            if(flag && !chunks[i1].neverSave)
            {
                saveExtraChunkData(chunks[i1]);
            }
            if(!chunks[i1].needsSaving(flag))
            {
                continue;
            }
            saveChunk(chunks[i1]);
            chunks[i1].isModified = false;
            if(++i == 2 && !flag)
            {
                return false;
            }
            if(entitypigzombie != null && ++l % 10 == 0)
            {
                entitypigzombie.setLoadingProgress((l * 100) / j);
            }
        }

        if(flag)
        {
            if(chunkLoader == null)
            {
                return true;
            }
            chunkLoader.saveExtraData();
        }
        return true;
    }

    public boolean func_361_a()
    {
        if(chunkLoader != null)
        {
            chunkLoader.func_661_a();
        }
        return chunkProvider.func_361_a();
    }

    public boolean func_364_b()
    {
        return true;
    }

    private Chunk blankChunk;
    private IChunkProvider chunkProvider;
    private IChunkLoader chunkLoader;
    private Chunk chunks[];
    private World worldObj;
    int lastQueriedChunkXPos;
    int lastQueriedChunkZPos;
    private Chunk lastQueriedChunk;
    private int field_21113_i;
    private int field_21112_j;
}
