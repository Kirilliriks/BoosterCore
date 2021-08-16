package net.minecraft.src.world;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.block.Block;
import net.minecraft.src.block.BlockFluids;
import net.minecraft.src.chunk.*;
import net.minecraft.src.entity.AxisAlignedBB;
import net.minecraft.src.entity.Entity;
import net.minecraft.src.entity.IProgressUpdate;
import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.material.Material;
import net.minecraft.src.tileentity.TileEntity;

import java.util.*;

public class World
    implements IBlockAccess
{

    public boolean scheduledUpdatesAreImmediate;
    private List field_821_y;
    public List loadedEntityList;
    private List unloadedEntityList;
    private TreeSet scheduledTickTreeSet;
    private Set scheduledTickSet;
    public List loadedTileEntityList;
    public List playerEntities;
    private long field_6159_E;
    public int skylightSubtracted;
    protected int distHashCounter;
    protected int DIST_HASH_MAGIC;
    public boolean editingBlocks;
    private long lockTimestamp;
    protected int autosavePeriod;
    public int difficultySetting;
    public Random rand;
    public boolean isNewWorld;
    public final WorldProvider worldProvider;
    protected List worldAccesses;
    protected IChunkProvider chunkProvider;
    protected final IDataManager worldFile;
    protected WorldInfo worldInfo;
    public boolean field_9209_x;
    private boolean allPlayersSleeping;
    private ArrayList field_9207_I;
    private int field_4265_J;
    private boolean spawnHostileMobs;
    private boolean spawnPeacefulMobs;
    static int field_4268_y = 0;
    private Set activeChunkSet;
    private int ambientTickCountdown;
    private List field_778_L;
    public boolean singleplayerWorld;

    public WorldChunkManager func_26662_a()
    {
        return worldProvider.worldChunkManager;
    }

    public World(IDataManager idataManager, String s, long l, WorldProvider worldProvider)
    {
        scheduledUpdatesAreImmediate = false;
        field_821_y = new ArrayList();
        loadedEntityList = new ArrayList();
        unloadedEntityList = new ArrayList();
        scheduledTickTreeSet = new TreeSet();
        scheduledTickSet = new HashSet();
        loadedTileEntityList = new ArrayList();
        playerEntities = new ArrayList();
        field_6159_E = 0xffffffL;
        skylightSubtracted = 0;
        distHashCounter = (new Random()).nextInt();
        DIST_HASH_MAGIC = 0x3c6ef35f;
        editingBlocks = false;
        lockTimestamp = System.currentTimeMillis();
        autosavePeriod = 40;
        rand = new Random();
        isNewWorld = false;
        worldAccesses = new ArrayList();
        field_9207_I = new ArrayList();
        field_4265_J = 0;
        spawnHostileMobs = true;
        spawnPeacefulMobs = true;
        activeChunkSet = new HashSet();
        ambientTickCountdown = rand.nextInt(12000);
        field_778_L = new ArrayList();
        singleplayerWorld = false;
        worldFile = idataManager;
        worldInfo = idataManager.func_22096_c();
        isNewWorld = worldInfo == null;
        if(worldProvider != null)
        {
            this.worldProvider = worldProvider;
        } else
        if(worldInfo != null && worldInfo.getDimension() == -1)
        {
            this.worldProvider = new WorldProviderHell();
        } else
        {
            this.worldProvider = new WorldProvider();
        }
        boolean flag = false;
        if(worldInfo == null)
        {
            worldInfo = new WorldInfo(l, s);
            flag = true;
        } else
        {
            worldInfo.setLevelName(s);
        }
        this.worldProvider.func_26671_a(this);
        chunkProvider = func_22086_b();
        if(flag)
        {
            func_25072_c();
        }
        calculateInitialSkylight();
    }

    protected IChunkProvider func_22086_b()
    {
        IChunkLoader ichunkloader = worldFile.func_26697_a(worldProvider);
        return new ChunkProviderLoadOrGenerate(this, ichunkloader, worldProvider.func_26667_c());
    }

    protected void func_25072_c()
    {
        field_9209_x = true;
        int i = 0;
        byte byte0 = 64;
        int j;
        for(j = 0; !worldProvider.func_26672_a(i, j); j += rand.nextInt(64) - rand.nextInt(64))
        {
            i += rand.nextInt(64) - rand.nextInt(64);
        }

        worldInfo.setSpawnPosition(i, byte0, j);
        field_9209_x = false;
    }

    public int getFirstUncoveredBlock(int i, int j)
    {
        int k;
        for(k = 63; !isAirBlock(i, k + 1, j); k++) { }
        return getBlockId(i, k, j);
    }

    public void func_26660_a(boolean flag, IProgressUpdate entitypigzombie)
    {
        if(!chunkProvider.func_364_b())
        {
            return;
        }
        if(entitypigzombie != null)
        {
            entitypigzombie.func_438_a("Saving level");
        }
        saveLevel();
        if(entitypigzombie != null)
        {
            entitypigzombie.displayLoadingString("Saving chunks");
        }
        chunkProvider.func_26631_a(flag, entitypigzombie);
    }

    private void saveLevel()
    {
        checkSessionLock();
        worldFile.func_22095_a(worldInfo, playerEntities);
    }

    public int getBlockId(int i, int j, int k)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return 0;
        }
        if(j < 0)
        {
            return 0;
        }
        if(j >= 128)
        {
            return 0;
        } else
        {
            return getChunkFromChunkCoords(i >> 4, k >> 4).getBlockID(i & 0xf, j, k & 0xf);
        }
    }

    public boolean isAirBlock(int i, int j, int k)
    {
        return getBlockId(i, j, k) == 0;
    }

    public boolean blockExists(int i, int j, int k)
    {
        if(j < 0 || j >= 128)
        {
            return false;
        } else
        {
            return chunkExists(i >> 4, k >> 4);
        }
    }

    public boolean doChunksNearChunkExist(int i, int j, int k, int l)
    {
        return checkChunksExist(i - l, j - l, k - l, i + l, j + l, k + l);
    }

    public boolean checkChunksExist(int i, int j, int k, int l, int i1, int j1)
    {
        if(i1 < 0 || j >= 128)
        {
            return false;
        }
        i >>= 4;
        j >>= 4;
        k >>= 4;
        l >>= 4;
        i1 >>= 4;
        j1 >>= 4;
        for(int k1 = i; k1 <= l; k1++)
        {
            for(int l1 = k; l1 <= j1; l1++)
            {
                if(!chunkExists(k1, l1))
                {
                    return false;
                }
            }

        }

        return true;
    }

    private boolean chunkExists(int i, int j)
    {
        return chunkProvider.chunkExists(i, j);
    }

    public Chunk getChunkFromBlockCoords(int i, int j)
    {
        return getChunkFromChunkCoords(i >> 4, j >> 4);
    }

    public Chunk getChunkFromChunkCoords(int i, int j)
    {
        return chunkProvider.provideChunk(i, j);
    }

    public boolean setBlockAndMetadata(int i, int j, int k, int l, int i1)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return false;
        }
        if(j < 0)
        {
            return false;
        }
        if(j >= 128)
        {
            return false;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
            return chunk.setBlockIDWithMetadata(i & 0xf, j, k & 0xf, l, i1);
        }
    }

    public boolean setBlock(int i, int j, int k, int l)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return false;
        }
        if(j < 0)
        {
            return false;
        }
        if(j >= 128)
        {
            return false;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
            return chunk.setBlockID(i & 0xf, j, k & 0xf, l);
        }
    }

    public Material getBlockMaterial(int i, int j, int k)
    {
        int l = getBlockId(i, j, k);
        if(l == 0)
        {
            return Material.air;
        } else
        {
            return Block.blocksList[l].blockMaterial;
        }
    }

    public int getBlockMetadata(int i, int j, int k)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return 0;
        }
        if(j < 0)
        {
            return 0;
        }
        if(j >= 128)
        {
            return 0;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
            i &= 0xf;
            k &= 0xf;
            return chunk.getBlockMetadata(i, j, k);
        }
    }

    public void setBlockMetadataWithNotify(int i, int j, int k, int l)
    {
        if(setBlockMetadata(i, j, k, l))
        {
            notifyBlockChange(i, j, k, getBlockId(i, j, k));
        }
    }

    public boolean setBlockMetadata(int i, int j, int k, int l)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return false;
        }
        if(j < 0)
        {
            return false;
        }
        if(j >= 128)
        {
            return false;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
            i &= 0xf;
            k &= 0xf;
            chunk.setBlockMetadata(i, j, k, l);
            return true;
        }
    }

    public boolean setBlockWithNotify(int i, int j, int k, int l)
    {
        if(setBlock(i, j, k, l))
        {
            notifyBlockChange(i, j, k, l);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean setBlockAndMetadataWithNotify(int i, int j, int k, int l, int i1)
    {
        if(setBlockAndMetadata(i, j, k, l, i1))
        {
            notifyBlockChange(i, j, k, l);
            return true;
        } else
        {
            return false;
        }
    }

    public void markBlockNeedsUpdate(int i, int j, int k)
    {
        for(int l = 0; l < worldAccesses.size(); l++)
        {
            ((IWorldAccess)worldAccesses.get(l)).markBlockNeedsUpdate(i, j, k);
        }

    }

    protected void notifyBlockChange(int i, int j, int k, int l)
    {
        markBlockNeedsUpdate(i, j, k);
        notifyBlocksOfNeighborChange(i, j, k, l);
    }

    public void markBlocksDirtyVertical(int i, int j, int k, int l)
    {
        if(k > l)
        {
            int i1 = l;
            l = k;
            k = i1;
        }
        markBlocksDirty(i, k, j, i, l, j);
    }

    public void markBlockAsNeedsUpdate(int i, int j, int k)
    {
        for(int l = 0; l < worldAccesses.size(); l++)
        {
            ((IWorldAccess)worldAccesses.get(l)).markBlockRangeNeedsUpdate(i, j, k, i, j, k);
        }

    }

    public void markBlocksDirty(int i, int j, int k, int l, int i1, int j1)
    {
        for(int k1 = 0; k1 < worldAccesses.size(); k1++)
        {
            ((IWorldAccess)worldAccesses.get(k1)).markBlockRangeNeedsUpdate(i, j, k, l, i1, j1);
        }

    }

    public void notifyBlocksOfNeighborChange(int i, int j, int k, int l)
    {
        notifyBlockOfNeighborChange(i - 1, j, k, l);
        notifyBlockOfNeighborChange(i + 1, j, k, l);
        notifyBlockOfNeighborChange(i, j - 1, k, l);
        notifyBlockOfNeighborChange(i, j + 1, k, l);
        notifyBlockOfNeighborChange(i, j, k - 1, l);
        notifyBlockOfNeighborChange(i, j, k + 1, l);
    }

    private void notifyBlockOfNeighborChange(int i, int j, int k, int l)
    {
        if(editingBlocks || singleplayerWorld)
        {
            return;
        }
        Block block = Block.blocksList[getBlockId(i, j, k)];
        if(block != null)
        {
            block.onNeighborBlockChange(this, i, j, k, l);
        }
    }

    public boolean canBlockSeeTheSky(int i, int j, int k)
    {
        return getChunkFromChunkCoords(i >> 4, k >> 4).canBlockSeeTheSky(i & 0xf, j, k & 0xf);
    }

    public int getBlockLightValue(int i, int j, int k)
    {
        return getBlockLightValue_do(i, j, k, true);
    }

    public int getBlockLightValue_do(int i, int j, int k, boolean flag)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return 15;
        }
        if(flag)
        {
            int l = getBlockId(i, j, k);
            if(l == Block.stairSingle.blockID || l == Block.tilledField.blockID)
            {
                int j1 = getBlockLightValue_do(i, j + 1, k, false);
                int k1 = getBlockLightValue_do(i + 1, j, k, false);
                int l1 = getBlockLightValue_do(i - 1, j, k, false);
                int i2 = getBlockLightValue_do(i, j, k + 1, false);
                int j2 = getBlockLightValue_do(i, j, k - 1, false);
                if(k1 > j1)
                {
                    j1 = k1;
                }
                if(l1 > j1)
                {
                    j1 = l1;
                }
                if(i2 > j1)
                {
                    j1 = i2;
                }
                if(j2 > j1)
                {
                    j1 = j2;
                }
                return j1;
            }
        }
        if(j < 0)
        {
            return 0;
        }
        if(j >= 128)
        {
            int i1 = 15 - skylightSubtracted;
            if(i1 < 0)
            {
                i1 = 0;
            }
            return i1;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
            i &= 0xf;
            k &= 0xf;
            return chunk.getBlockLightValue(i, j, k, skylightSubtracted);
        }
    }

    public boolean canExistingBlockSeeTheSky(int i, int j, int k)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return false;
        }
        if(j < 0)
        {
            return false;
        }
        if(j >= 128)
        {
            return true;
        }
        if(!chunkExists(i >> 4, k >> 4))
        {
            return false;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
            i &= 0xf;
            k &= 0xf;
            return chunk.canBlockSeeTheSky(i, j, k);
        }
    }

    public int getHeightValue(int i, int j)
    {
        if(i < 0xfe17b800 || j < 0xfe17b800 || i >= 0x1e84800 || j > 0x1e84800)
        {
            return 0;
        }
        if(!chunkExists(i >> 4, j >> 4))
        {
            return 0;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(i >> 4, j >> 4);
            return chunk.getHeightValue(i & 0xf, j & 0xf);
        }
    }

    public void neighborLightPropagationChanged(EnumSkyBlock enumskyblock, int i, int j, int k, int l)
    {
        if(worldProvider.field_26678_e && enumskyblock == EnumSkyBlock.Sky)
        {
            return;
        }
        if(!blockExists(i, j, k))
        {
            return;
        }
        if(enumskyblock == EnumSkyBlock.Sky)
        {
            if(canExistingBlockSeeTheSky(i, j, k))
            {
                l = 15;
            }
        } else
        if(enumskyblock == EnumSkyBlock.Block)
        {
            int i1 = getBlockId(i, j, k);
            if(Block.lightValue[i1] > l)
            {
                l = Block.lightValue[i1];
            }
        }
        if(getSavedLightValue(enumskyblock, i, j, k) != l)
        {
            func_483_a(enumskyblock, i, j, k, i, j, k);
        }
    }

    public int getSavedLightValue(EnumSkyBlock enumskyblock, int i, int j, int k)
    {
        if(j < 0 || j >= 128 || i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return enumskyblock.field_984_c;
        }
        int l = i >> 4;
        int i1 = k >> 4;
        if(!chunkExists(l, i1))
        {
            return 0;
        } else
        {
            Chunk chunk = getChunkFromChunkCoords(l, i1);
            return chunk.getSavedLightValue(enumskyblock, i & 0xf, j, k & 0xf);
        }
    }

    public void setLightValue(EnumSkyBlock enumskyblock, int i, int j, int k, int l)
    {
        if(i < 0xfe17b800 || k < 0xfe17b800 || i >= 0x1e84800 || k > 0x1e84800)
        {
            return;
        }
        if(j < 0)
        {
            return;
        }
        if(j >= 128)
        {
            return;
        }
        if(!chunkExists(i >> 4, k >> 4))
        {
            return;
        }
        Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
        chunk.setLightValue(enumskyblock, i & 0xf, j, k & 0xf, l);
        for(int i1 = 0; i1 < worldAccesses.size(); i1++)
        {
            ((IWorldAccess)worldAccesses.get(i1)).markBlockNeedsUpdate(i, j, k);
        }

    }

    public float getLightBrightness(int i, int j, int k)
    {
        return worldProvider.field_26677_f[getBlockLightValue(i, j, k)];
    }

    public boolean isDaytime()
    {
        return skylightSubtracted < 4;
    }

    public MovingObjectPosition rayTraceBlocks(Vec3D vec3d, Vec3D vec3d1)
    {
        return rayTraceBlocks_do(vec3d, vec3d1, false);
    }

    public MovingObjectPosition rayTraceBlocks_do(Vec3D vec3d, Vec3D vec3d1, boolean flag)
    {
        if(Double.isNaN(vec3d.xCoord) || Double.isNaN(vec3d.yCoord) || Double.isNaN(vec3d.zCoord))
        {
            return null;
        }
        if(Double.isNaN(vec3d1.xCoord) || Double.isNaN(vec3d1.yCoord) || Double.isNaN(vec3d1.zCoord))
        {
            return null;
        }
        int i = MathHelper.floor_double(vec3d1.xCoord);
        int j = MathHelper.floor_double(vec3d1.yCoord);
        int k = MathHelper.floor_double(vec3d1.zCoord);
        int l = MathHelper.floor_double(vec3d.xCoord);
        int i1 = MathHelper.floor_double(vec3d.yCoord);
        int j1 = MathHelper.floor_double(vec3d.zCoord);
        for(int k1 = 200; k1-- >= 0;)
        {
            if(Double.isNaN(vec3d.xCoord) || Double.isNaN(vec3d.yCoord) || Double.isNaN(vec3d.zCoord))
            {
                return null;
            }
            if(l == i && i1 == j && j1 == k)
            {
                return null;
            }
            double d = 999D;
            double d1 = 999D;
            double d2 = 999D;
            if(i > l)
            {
                d = (double)l + 1.0D;
            }
            if(i < l)
            {
                d = (double)l + 0.0D;
            }
            if(j > i1)
            {
                d1 = (double)i1 + 1.0D;
            }
            if(j < i1)
            {
                d1 = (double)i1 + 0.0D;
            }
            if(k > j1)
            {
                d2 = (double)j1 + 1.0D;
            }
            if(k < j1)
            {
                d2 = (double)j1 + 0.0D;
            }
            double d3 = 999D;
            double d4 = 999D;
            double d5 = 999D;
            double d6 = vec3d1.xCoord - vec3d.xCoord;
            double d7 = vec3d1.yCoord - vec3d.yCoord;
            double d8 = vec3d1.zCoord - vec3d.zCoord;
            if(d != 999D)
            {
                d3 = (d - vec3d.xCoord) / d6;
            }
            if(d1 != 999D)
            {
                d4 = (d1 - vec3d.yCoord) / d7;
            }
            if(d2 != 999D)
            {
                d5 = (d2 - vec3d.zCoord) / d8;
            }
            byte byte0 = 0;
            if(d3 < d4 && d3 < d5)
            {
                if(i > l)
                {
                    byte0 = 4;
                } else
                {
                    byte0 = 5;
                }
                vec3d.xCoord = d;
                vec3d.yCoord += d7 * d3;
                vec3d.zCoord += d8 * d3;
            } else
            if(d4 < d5)
            {
                if(j > i1)
                {
                    byte0 = 0;
                } else
                {
                    byte0 = 1;
                }
                vec3d.xCoord += d6 * d4;
                vec3d.yCoord = d1;
                vec3d.zCoord += d8 * d4;
            } else
            {
                if(k > j1)
                {
                    byte0 = 2;
                } else
                {
                    byte0 = 3;
                }
                vec3d.xCoord += d6 * d5;
                vec3d.yCoord += d7 * d5;
                vec3d.zCoord = d2;
            }
            Vec3D vec3d2 = Vec3D.createVector(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
            l = (int)(vec3d2.xCoord = MathHelper.floor_double(vec3d.xCoord));
            if(byte0 == 5)
            {
                l--;
                vec3d2.xCoord++;
            }
            i1 = (int)(vec3d2.yCoord = MathHelper.floor_double(vec3d.yCoord));
            if(byte0 == 1)
            {
                i1--;
                vec3d2.yCoord++;
            }
            j1 = (int)(vec3d2.zCoord = MathHelper.floor_double(vec3d.zCoord));
            if(byte0 == 3)
            {
                j1--;
                vec3d2.zCoord++;
            }
            int l1 = getBlockId(l, i1, j1);
            int i2 = getBlockMetadata(l, i1, j1);
            Block block = Block.blocksList[l1];
            if(l1 > 0 && block.canCollideCheck(i2, flag))
            {
                MovingObjectPosition movingobjectposition = block.collisionRayTrace(this, l, i1, j1, vec3d, vec3d1);
                if(movingobjectposition != null)
                {
                    return movingobjectposition;
                }
            }
        }

        return null;
    }

    public void playSoundAtEntity(Entity entity, String s, float f, float f1)
    {
        for(int i = 0; i < worldAccesses.size(); i++)
        {
            ((IWorldAccess)worldAccesses.get(i)).playSound(s, entity.posX, entity.posY - (double)entity.yOffset, entity.posZ, f, f1);
        }

    }

    public void playSoundEffect(double d, double d1, double d2, String s, 
            float f, float f1)
    {
        for(int i = 0; i < worldAccesses.size(); i++)
        {
            ((IWorldAccess)worldAccesses.get(i)).playSound(s, d, d1, d2, f, f1);
        }

    }

    public void playRecord(String s, int i, int j, int k)
    {
        for(int l = 0; l < worldAccesses.size(); l++)
        {
            ((IWorldAccess)worldAccesses.get(l)).playRecord(s, i, j, k);
        }

    }

    public void spawnParticle(String s, double d, double d1, double d2, 
            double d3, double d4, double d5)
    {
        for(int i = 0; i < worldAccesses.size(); i++)
        {
            ((IWorldAccess)worldAccesses.get(i)).spawnParticle(s, d, d1, d2, d3, d4, d5);
        }

    }

    public boolean entityJoinedWorld(Entity entity)
    {
        int i = MathHelper.floor_double(entity.posX / 16D);
        int j = MathHelper.floor_double(entity.posZ / 16D);
        boolean flag = false;
        if(entity instanceof EntityPlayer)
        {
            flag = true;
        }
        if(flag || chunkExists(i, j))
        {
            if(entity instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entity;
                playerEntities.add(entityplayer);
                updateAllPlayersSleepingFlag();
            }
            getChunkFromChunkCoords(i, j).addEntity(entity);
            loadedEntityList.add(entity);
            obtainEntitySkin(entity);
            return true;
        } else
        {
            return false;
        }
    }

    protected void obtainEntitySkin(Entity entity)
    {
        for(int i = 0; i < worldAccesses.size(); i++)
        {
            ((IWorldAccess)worldAccesses.get(i)).obtainEntitySkin(entity);
        }

    }

    protected void releaseEntitySkin(Entity entity)
    {
        for(int i = 0; i < worldAccesses.size(); i++)
        {
            ((IWorldAccess)worldAccesses.get(i)).releaseEntitySkin(entity);
        }

    }

    public void func_22085_d(Entity entity)
    {
        if(entity.riddenByEntity != null)
        {
            entity.riddenByEntity.mountEntity(null);
        }
        if(entity.ridingEntity != null)
        {
            entity.mountEntity(null);
        }
        entity.setEntityDead();
        if(entity instanceof EntityPlayer)
        {
            playerEntities.remove((EntityPlayer)entity);
            updateAllPlayersSleepingFlag();
        }
    }

    public void removePlayer(Entity entity)
    {
        entity.setEntityDead();
        if(entity instanceof EntityPlayer)
        {
            playerEntities.remove((EntityPlayer)entity);
            updateAllPlayersSleepingFlag();
        }
        int i = entity.chunkCoordX;
        int j = entity.chunkCoordZ;
        if(entity.addedToChunk && chunkExists(i, j))
        {
            getChunkFromChunkCoords(i, j).removeEntity(entity);
        }
        loadedEntityList.remove(entity);
        releaseEntitySkin(entity);
    }

    public void addWorldAccess(IWorldAccess iworldaccess)
    {
        worldAccesses.add(iworldaccess);
    }

    public List getCollidingBoundingBoxes(Entity entity, AxisAlignedBB axisalignedbb)
    {
        field_9207_I.clear();
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for(int k1 = i; k1 < j; k1++)
        {
            for(int l1 = i1; l1 < j1; l1++)
            {
                if(!blockExists(k1, 64, l1))
                {
                    continue;
                }
                for(int i2 = k - 1; i2 < l; i2++)
                {
                    Block block = Block.blocksList[getBlockId(k1, i2, l1)];
                    if(block != null)
                    {
                        block.getCollidingBoundingBoxes(this, k1, i2, l1, axisalignedbb, field_9207_I);
                    }
                }

            }

        }

        double d = 0.25D;
        List list = getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb.expand(d, d, d));
        for(int j2 = 0; j2 < list.size(); j2++)
        {
            AxisAlignedBB axisalignedbb1 = ((Entity)list.get(j2)).getBoundingBox();
            if(axisalignedbb1 != null && axisalignedbb1.intersectsWith(axisalignedbb))
            {
                field_9207_I.add(axisalignedbb1);
            }
            axisalignedbb1 = entity.func_89_d((Entity)list.get(j2));
            if(axisalignedbb1 != null && axisalignedbb1.intersectsWith(axisalignedbb))
            {
                field_9207_I.add(axisalignedbb1);
            }
        }

        return field_9207_I;
    }

    public int calculateSkylightSubtracted(float f)
    {
        float f1 = getCelestialAngle(f);
        float f2 = 1.0F - (MathHelper.cos(f1 * 3.141593F * 2.0F) * 2.0F + 0.5F);
        if(f2 < 0.0F)
        {
            f2 = 0.0F;
        }
        if(f2 > 1.0F)
        {
            f2 = 1.0F;
        }
        return (int)(f2 * 11F);
    }

    public float getCelestialAngle(float f)
    {
        return worldProvider.func_26668_a(worldInfo.getWorldTime(), f);
    }

    public int findTopSolidBlock(int i, int j)
    {
        Chunk chunk = getChunkFromBlockCoords(i, j);
        int k;
        for(k = 127; getBlockMaterial(i, k, j).getIsSolid() && k > 0; k--) { }
        i &= 0xf;
        j &= 0xf;
        while(k > 0) 
        {
            int l = chunk.getBlockID(i, k, j);
            if(l == 0 || !Block.blocksList[l].blockMaterial.getIsSolid() && !Block.blocksList[l].blockMaterial.getIsLiquid())
            {
                k--;
            } else
            {
                return k + 1;
            }
        }
        return -1;
    }

    public void scheduleUpdateTick(int i, int j, int k, int l, int i1)
    {
        NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, l);
        byte byte0 = 8;
        if(scheduledUpdatesAreImmediate)
        {
            if(checkChunksExist(nextticklistentry.xCoord - byte0, nextticklistentry.yCoord - byte0, nextticklistentry.zCoord - byte0, nextticklistentry.xCoord + byte0, nextticklistentry.yCoord + byte0, nextticklistentry.zCoord + byte0))
            {
                int j1 = getBlockId(nextticklistentry.xCoord, nextticklistentry.yCoord, nextticklistentry.zCoord);
                if(j1 == nextticklistentry.blockID && j1 > 0)
                {
                    Block.blocksList[j1].updateTick(this, nextticklistentry.xCoord, nextticklistentry.yCoord, nextticklistentry.zCoord, rand);
                }
            }
            return;
        }
        if(checkChunksExist(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0))
        {
            if(l > 0)
            {
                nextticklistentry.setScheduledTime((long)i1 + worldInfo.getWorldTime());
            }
            if(!scheduledTickSet.contains(nextticklistentry))
            {
                scheduledTickSet.add(nextticklistentry);
                scheduledTickTreeSet.add(nextticklistentry);
            }
        }
    }

    public void updateEntities()
    {
        loadedEntityList.removeAll(unloadedEntityList);
        for(int i = 0; i < unloadedEntityList.size(); i++)
        {
            Entity entity = (Entity)unloadedEntityList.get(i);
            int i1 = entity.chunkCoordX;
            int k1 = entity.chunkCoordZ;
            if(entity.addedToChunk && chunkExists(i1, k1))
            {
                getChunkFromChunkCoords(i1, k1).removeEntity(entity);
            }
        }

        for(int j = 0; j < unloadedEntityList.size(); j++)
        {
            releaseEntitySkin((Entity)unloadedEntityList.get(j));
        }

        unloadedEntityList.clear();
        for(int k = 0; k < loadedEntityList.size(); k++)
        {
            Entity entity1 = (Entity)loadedEntityList.get(k);
            if(entity1.ridingEntity != null)
            {
                if(!entity1.ridingEntity.isDead && entity1.ridingEntity.riddenByEntity == entity1)
                {
                    continue;
                }
                entity1.ridingEntity.riddenByEntity = null;
                entity1.ridingEntity = null;
            }
            if(!entity1.isDead)
            {
                updateEntity(entity1);
            }
            if(!entity1.isDead)
            {
                continue;
            }
            int j1 = entity1.chunkCoordX;
            int l1 = entity1.chunkCoordZ;
            if(entity1.addedToChunk && chunkExists(j1, l1))
            {
                getChunkFromChunkCoords(j1, l1).removeEntity(entity1);
            }
            loadedEntityList.remove(k--);
            releaseEntitySkin(entity1);
        }

        for(int l = 0; l < loadedTileEntityList.size(); l++)
        {
            TileEntity tileentity = (TileEntity)loadedTileEntityList.get(l);
            tileentity.updateEntity();
        }

    }

    public void updateEntity(Entity entity)
    {
        updateEntityWithOptionalForce(entity, true);
    }

    public void updateEntityWithOptionalForce(Entity entity, boolean flag)
    {
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posZ);
        byte byte0 = 32;
        if(flag && !checkChunksExist(i - byte0, 0, j - byte0, i + byte0, 128, j + byte0))
        {
            return;
        }
        entity.lastTickPosX = entity.posX;
        entity.lastTickPosY = entity.posY;
        entity.lastTickPosZ = entity.posZ;
        entity.prevRotationYaw = entity.rotationYaw;
        entity.prevRotationPitch = entity.rotationPitch;
        if(flag && entity.addedToChunk)
        {
            if(entity.ridingEntity != null)
            {
                entity.updateRidden();
            } else
            {
                entity.onUpdate();
            }
        }
        if(Double.isNaN(entity.posX) || Double.isInfinite(entity.posX))
        {
            entity.posX = entity.lastTickPosX;
        }
        if(Double.isNaN(entity.posY) || Double.isInfinite(entity.posY))
        {
            entity.posY = entity.lastTickPosY;
        }
        if(Double.isNaN(entity.posZ) || Double.isInfinite(entity.posZ))
        {
            entity.posZ = entity.lastTickPosZ;
        }
        if(Double.isNaN(entity.rotationPitch) || Double.isInfinite(entity.rotationPitch))
        {
            entity.rotationPitch = entity.prevRotationPitch;
        }
        if(Double.isNaN(entity.rotationYaw) || Double.isInfinite(entity.rotationYaw))
        {
            entity.rotationYaw = entity.prevRotationYaw;
        }
        int k = MathHelper.floor_double(entity.posX / 16D);
        int l = MathHelper.floor_double(entity.posY / 16D);
        int i1 = MathHelper.floor_double(entity.posZ / 16D);
        if(!entity.addedToChunk || entity.chunkCoordX != k || entity.chunkCoordY != l || entity.chunkCoordZ != i1)
        {
            if(entity.addedToChunk && chunkExists(entity.chunkCoordX, entity.chunkCoordZ))
            {
                getChunkFromChunkCoords(entity.chunkCoordX, entity.chunkCoordZ).removeEntityAtIndex(entity, entity.chunkCoordY);
            }
            if(chunkExists(k, i1))
            {
                entity.addedToChunk = true;
                getChunkFromChunkCoords(k, i1).addEntity(entity);
            } else
            {
                entity.addedToChunk = false;
            }
        }
        if(flag && entity.addedToChunk && entity.riddenByEntity != null)
        {
            if(entity.riddenByEntity.isDead || entity.riddenByEntity.ridingEntity != entity)
            {
                entity.riddenByEntity.ridingEntity = null;
                entity.riddenByEntity = null;
            } else
            {
                updateEntity(entity.riddenByEntity);
            }
        }
    }

    public boolean checkIfAABBIsClear(AxisAlignedBB axisalignedbb)
    {
        List list = getEntitiesWithinAABBExcludingEntity(null, axisalignedbb);
        for(int i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity)list.get(i);
            if(!entity.isDead && entity.preventEntitySpawning)
            {
                return false;
            }
        }

        return true;
    }

    public boolean getIsAnyLiquid(AxisAlignedBB axisalignedbb)
    {
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        if(axisalignedbb.minX < 0.0D)
        {
            i--;
        }
        if(axisalignedbb.minY < 0.0D)
        {
            k--;
        }
        if(axisalignedbb.minZ < 0.0D)
        {
            i1--;
        }
        for(int k1 = i; k1 < j; k1++)
        {
            for(int l1 = k; l1 < l; l1++)
            {
                for(int i2 = i1; i2 < j1; i2++)
                {
                    Block block = Block.blocksList[getBlockId(k1, l1, i2)];
                    if(block != null && block.blockMaterial.getIsLiquid())
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public boolean isBoundingBoxBurning(AxisAlignedBB axisalignedbb)
    {
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        if(checkChunksExist(i, k, i1, j, l, j1))
        {
            for(int k1 = i; k1 < j; k1++)
            {
                for(int l1 = k; l1 < l; l1++)
                {
                    for(int i2 = i1; i2 < j1; i2++)
                    {
                        int j2 = getBlockId(k1, l1, i2);
                        if(j2 == Block.fire.blockID || j2 == Block.lavaMoving.blockID || j2 == Block.lavaStill.blockID)
                        {
                            return true;
                        }
                    }

                }

            }

        }
        return false;
    }

    public boolean handleMaterialAcceleration(AxisAlignedBB axisalignedbb, Material material, Entity entity)
    {
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        if(!checkChunksExist(i, k, i1, j, l, j1))
        {
            return false;
        }
        boolean flag = false;
        Vec3D vec3d = Vec3D.createVector(0.0D, 0.0D, 0.0D);
        for(int k1 = i; k1 < j; k1++)
        {
            for(int l1 = k; l1 < l; l1++)
            {
                for(int i2 = i1; i2 < j1; i2++)
                {
                    Block block = Block.blocksList[getBlockId(k1, l1, i2)];
                    if(block == null || block.blockMaterial != material)
                    {
                        continue;
                    }
                    double d1 = (float)(l1 + 1) - BlockFluids.setFluidHeight(getBlockMetadata(k1, l1, i2));
                    if((double)l >= d1)
                    {
                        flag = true;
                        block.velocityToAddToEntity(this, k1, l1, i2, entity, vec3d);
                    }
                }

            }

        }

        if(vec3d.lengthVector() > 0.0D)
        {
            vec3d = vec3d.normalize();
            double d = 0.0040000000000000001D;
            entity.motionX += vec3d.xCoord * d;
            entity.motionY += vec3d.yCoord * d;
            entity.motionZ += vec3d.zCoord * d;
        }
        return flag;
    }

    public boolean isMaterialInBB(AxisAlignedBB axisalignedbb, Material material)
    {
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for(int k1 = i; k1 < j; k1++)
        {
            for(int l1 = k; l1 < l; l1++)
            {
                for(int i2 = i1; i2 < j1; i2++)
                {
                    Block block = Block.blocksList[getBlockId(k1, l1, i2)];
                    if(block != null && block.blockMaterial == material)
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public boolean isAABBInMaterial(AxisAlignedBB axisalignedbb, Material material)
    {
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for(int k1 = i; k1 < j; k1++)
        {
            for(int l1 = k; l1 < l; l1++)
            {
                for(int i2 = i1; i2 < j1; i2++)
                {
                    Block block = Block.blocksList[getBlockId(k1, l1, i2)];
                    if(block == null || block.blockMaterial != material)
                    {
                        continue;
                    }
                    int j2 = getBlockMetadata(k1, l1, i2);
                    double d = l1 + 1;
                    if(j2 < 8)
                    {
                        d = (double)(l1 + 1) - (double)j2 / 8D;
                    }
                    if(d >= axisalignedbb.minY)
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public Explosion createExplosion(Entity entity, double d, double d1, double d2, 
            float f)
    {
        return newExplosion(entity, d, d1, d2, f, false);
    }

    public Explosion newExplosion(Entity entity, double d, double d1, double d2, 
            float f, boolean flag)
    {
        Explosion explosion = new Explosion(this, entity, d, d1, d2, f);
        explosion.isFlaming = flag;
        explosion.doExplosion();
        explosion.doEffects();
        return explosion;
    }

    public float func_494_a(Vec3D vec3d, AxisAlignedBB axisalignedbb)
    {
        double d = 1.0D / ((axisalignedbb.maxX - axisalignedbb.minX) * 2D + 1.0D);
        double d1 = 1.0D / ((axisalignedbb.maxY - axisalignedbb.minY) * 2D + 1.0D);
        double d2 = 1.0D / ((axisalignedbb.maxZ - axisalignedbb.minZ) * 2D + 1.0D);
        int i = 0;
        int j = 0;
        for(float f = 0.0F; f <= 1.0F; f = (float)((double)f + d))
        {
            for(float f1 = 0.0F; f1 <= 1.0F; f1 = (float)((double)f1 + d1))
            {
                for(float f2 = 0.0F; f2 <= 1.0F; f2 = (float)((double)f2 + d2))
                {
                    double d3 = axisalignedbb.minX + (axisalignedbb.maxX - axisalignedbb.minX) * (double)f;
                    double d4 = axisalignedbb.minY + (axisalignedbb.maxY - axisalignedbb.minY) * (double)f1;
                    double d5 = axisalignedbb.minZ + (axisalignedbb.maxZ - axisalignedbb.minZ) * (double)f2;
                    if(rayTraceBlocks(Vec3D.createVector(d3, d4, d5), vec3d) == null)
                    {
                        i++;
                    }
                    j++;
                }

            }

        }

        return (float)i / (float)j;
    }

    public TileEntity getBlockTileEntity(int i, int j, int k)
    {
        Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
        if(chunk != null)
        {
            return chunk.getChunkBlockTileEntity(i & 0xf, j, k & 0xf);
        } else
        {
            return null;
        }
    }

    public void setBlockTileEntity(int i, int j, int k, TileEntity tileentity)
    {
        Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
        if(chunk != null)
        {
            chunk.setChunkBlockTileEntity(i & 0xf, j, k & 0xf, tileentity);
        }
    }

    public void removeBlockTileEntity(int i, int j, int k)
    {
        Chunk chunk = getChunkFromChunkCoords(i >> 4, k >> 4);
        if(chunk != null)
        {
            chunk.removeChunkBlockTileEntity(i & 0xf, j, k & 0xf);
        }
    }

    public boolean isBlockOpaqueCube(int i, int j, int k)
    {
        Block block = Block.blocksList[getBlockId(i, j, k)];
        if(block == null)
        {
            return false;
        } else
        {
            return block.isOpaqueCube();
        }
    }

    public boolean func_6156_d()
    {
        if(field_4265_J >= 50)
        {
            return false;
        }
        field_4265_J++;
        try
        {
            int i = 500;
            for(; field_821_y.size() > 0; ((MetadataChunkBlock)field_821_y.remove(field_821_y.size() - 1)).func_4107_a(this))
            {
                if(--i <= 0)
                {
                    boolean flag = true;
                    return flag;
                }
            }

            boolean flag1 = false;
            return flag1;
        }
        finally
        {
            field_4265_J--;
        }
    }

    public void func_483_a(EnumSkyBlock enumskyblock, int i, int j, int k, int l, int i1, int j1)
    {
        func_484_a(enumskyblock, i, j, k, l, i1, j1, true);
    }

    public void func_484_a(EnumSkyBlock enumskyblock, int i, int j, int k, int l, int i1, int j1, 
            boolean flag)
    {
        if(worldProvider.field_26678_e && enumskyblock == EnumSkyBlock.Sky)
        {
            return;
        }
        field_4268_y++;
        if(field_4268_y == 50)
        {
            field_4268_y--;
            return;
        }
        int k1 = (l + i) / 2;
        int l1 = (j1 + k) / 2;
        if(!blockExists(k1, 64, l1))
        {
            field_4268_y--;
            return;
        }
        if(getChunkFromBlockCoords(k1, l1).func_21101_g())
        {
            return;
        }
        int i2 = field_821_y.size();
        if(flag)
        {
            int j2 = 5;
            if(j2 > i2)
            {
                j2 = i2;
            }
            for(int l2 = 0; l2 < j2; l2++)
            {
                MetadataChunkBlock metadatachunkblock = (MetadataChunkBlock)field_821_y.get(field_821_y.size() - l2 - 1);
                if(metadatachunkblock.field_957_a == enumskyblock && metadatachunkblock.func_692_a(i, j, k, l, i1, j1))
                {
                    field_4268_y--;
                    return;
                }
            }

        }
        field_821_y.add(new MetadataChunkBlock(enumskyblock, i, j, k, l, i1, j1));
        int k2 = 0xf4240;
        if(field_821_y.size() > 0xf4240)
        {
            System.out.println((new StringBuilder()).append("More than ").append(k2).append(" updates, aborting lighting updates").toString());
            field_821_y.clear();
        }
        field_4268_y--;
    }

    public void calculateInitialSkylight()
    {
        int i = calculateSkylightSubtracted(1.0F);
        if(i != skylightSubtracted)
        {
            skylightSubtracted = i;
        }
    }

    public void setAllowedSpawnTypes(boolean flag, boolean flag1)
    {
        spawnHostileMobs = flag;
        spawnPeacefulMobs = flag1;
    }

    public void tick()
    {
        if(isAllPlayersFullyAsleep())
        {
            boolean flag = false;
            if(spawnHostileMobs && difficultySetting >= 1)
            {
                flag = SpawnerCreature.performSleepSpawning(this, playerEntities);
            }
            if(!flag)
            {
                long l = worldInfo.getWorldTime() + 24000L;
                worldInfo.setWorldTime(l - l % 24000L);
                wakeUpAllPlayers();
            }
        }
        SpawnerCreature.performSpawning(this, spawnHostileMobs, spawnPeacefulMobs);
        chunkProvider.func_361_a();
        int i = calculateSkylightSubtracted(1.0F);
        if(i != skylightSubtracted)
        {
            skylightSubtracted = i;
            for(int j = 0; j < worldAccesses.size(); j++)
            {
                ((IWorldAccess)worldAccesses.get(j)).updateAllRenderers();
            }

        }
        long l1 = worldInfo.getWorldTime() + 1L;
        if(l1 % (long)autosavePeriod == 0L)
        {
            func_26660_a(false, null);
        }
        worldInfo.setWorldTime(l1);
        TickUpdates(false);
        doRandomUpdateTicks();
    }

    protected void doRandomUpdateTicks()
    {
        activeChunkSet.clear();
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayer entityplayer = (EntityPlayer)playerEntities.get(i);
            int j = MathHelper.floor_double(entityplayer.posX / 16D);
            int l = MathHelper.floor_double(entityplayer.posZ / 16D);
            byte byte0 = 9;
            for(int j1 = -byte0; j1 <= byte0; j1++)
            {
                for(int i2 = -byte0; i2 <= byte0; i2++)
                {
                    activeChunkSet.add(new ChunkCoordinate(j1 + j, i2 + l));
                }

            }

        }

        if(ambientTickCountdown > 0)
        {
            ambientTickCountdown--;
        }
        for(Iterator iterator = activeChunkSet.iterator(); iterator.hasNext();)
        {
            ChunkCoordinate slotcrafting = (ChunkCoordinate)iterator.next();
            int k = slotcrafting.field_26507_a * 16;
            int i1 = slotcrafting.field_26506_b * 16;
            Chunk chunk = getChunkFromChunkCoords(slotcrafting.field_26507_a, slotcrafting.field_26506_b);
            if(ambientTickCountdown == 0)
            {
                distHashCounter = distHashCounter * 3 + DIST_HASH_MAGIC;
                int k1 = distHashCounter >> 2;
                int j2 = k1 & 0xf;
                int l2 = k1 >> 8 & 0xf;
                int j3 = k1 >> 16 & 0x7f;
                int l3 = chunk.getBlockID(j2, j3, l2);
                j2 += k;
                l2 += i1;
                if(l3 == 0 && getBlockLightValue(j2, j3, l2) <= rand.nextInt(8) && getSavedLightValue(EnumSkyBlock.Sky, j2, j3, l2) <= 0)
                {
                    EntityPlayer entityplayer1 = getClosestPlayer((double)j2 + 0.5D, (double)j3 + 0.5D, (double)l2 + 0.5D, 8D);
                    if(entityplayer1 != null && entityplayer1.getDistanceSq((double)j2 + 0.5D, (double)j3 + 0.5D, (double)l2 + 0.5D) > 4D)
                    {
                        playSoundEffect((double)j2 + 0.5D, (double)j3 + 0.5D, (double)l2 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + rand.nextFloat() * 0.2F);
                        ambientTickCountdown = rand.nextInt(12000) + 6000;
                    }
                }
            }
            int l1 = 0;
            while(l1 < 80) 
            {
                distHashCounter = distHashCounter * 3 + DIST_HASH_MAGIC;
                int k2 = distHashCounter >> 2;
                int i3 = k2 & 0xf;
                int k3 = k2 >> 8 & 0xf;
                int i4 = k2 >> 16 & 0x7f;
                int j4 = chunk.blocks[i3 << 11 | k3 << 7 | i4] & 0xff;
                if(Block.tickOnLoad[j4])
                {
                    Block.blocksList[j4].updateTick(this, i3 + k, i4, k3 + i1, rand);
                }
                l1++;
            }
        }

    }

    public boolean TickUpdates(boolean flag)
    {
        int i = scheduledTickTreeSet.size();
        if(i != scheduledTickSet.size())
        {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if(i > 1000)
        {
            i = 1000;
        }
        for(int j = 0; j < i; j++)
        {
            NextTickListEntry nextticklistentry = (NextTickListEntry)scheduledTickTreeSet.first();
            if(!flag && nextticklistentry.scheduledTime > worldInfo.getWorldTime())
            {
                break;
            }
            scheduledTickTreeSet.remove(nextticklistentry);
            scheduledTickSet.remove(nextticklistentry);
            byte byte0 = 8;
            if(!checkChunksExist(nextticklistentry.xCoord - byte0, nextticklistentry.yCoord - byte0, nextticklistentry.zCoord - byte0, nextticklistentry.xCoord + byte0, nextticklistentry.yCoord + byte0, nextticklistentry.zCoord + byte0))
            {
                continue;
            }
            int k = getBlockId(nextticklistentry.xCoord, nextticklistentry.yCoord, nextticklistentry.zCoord);
            if(k == nextticklistentry.blockID && k > 0)
            {
                Block.blocksList[k].updateTick(this, nextticklistentry.xCoord, nextticklistentry.yCoord, nextticklistentry.zCoord, rand);
            }
        }

        return scheduledTickTreeSet.size() != 0;
    }

    public List getEntitiesWithinAABBExcludingEntity(Entity entity, AxisAlignedBB axisalignedbb)
    {
        field_778_L.clear();
        int i = MathHelper.floor_double((axisalignedbb.minX - 2D) / 16D);
        int j = MathHelper.floor_double((axisalignedbb.maxX + 2D) / 16D);
        int k = MathHelper.floor_double((axisalignedbb.minZ - 2D) / 16D);
        int l = MathHelper.floor_double((axisalignedbb.maxZ + 2D) / 16D);
        for(int i1 = i; i1 <= j; i1++)
        {
            for(int j1 = k; j1 <= l; j1++)
            {
                if(chunkExists(i1, j1))
                {
                    getChunkFromChunkCoords(i1, j1).getEntitiesWithinAABBForEntity(entity, axisalignedbb, field_778_L);
                }
            }

        }

        return field_778_L;
    }

    public List getEntitiesWithinAABB(Class class1, AxisAlignedBB axisalignedbb)
    {
        int i = MathHelper.floor_double((axisalignedbb.minX - 2D) / 16D);
        int j = MathHelper.floor_double((axisalignedbb.maxX + 2D) / 16D);
        int k = MathHelper.floor_double((axisalignedbb.minZ - 2D) / 16D);
        int l = MathHelper.floor_double((axisalignedbb.maxZ + 2D) / 16D);
        ArrayList arraylist = new ArrayList();
        for(int i1 = i; i1 <= j; i1++)
        {
            for(int j1 = k; j1 <= l; j1++)
            {
                if(chunkExists(i1, j1))
                {
                    getChunkFromChunkCoords(i1, j1).getEntitiesOfTypeWithinAAAB(class1, axisalignedbb, arraylist);
                }
            }

        }

        return arraylist;
    }

    public void func_515_b(int i, int j, int k, TileEntity tileentity)
    {
        if(blockExists(i, j, k))
        {
            getChunkFromBlockCoords(i, k).setChunkModified();
        }
        for(int l = 0; l < worldAccesses.size(); l++)
        {
            ((IWorldAccess)worldAccesses.get(l)).doNothingWithTileEntity(i, j, k, tileentity);
        }

    }

    public int countEntities(Class class1)
    {
        int i = 0;
        for(int j = 0; j < loadedEntityList.size(); j++)
        {
            Entity entity = (Entity)loadedEntityList.get(j);
            if(class1.isAssignableFrom(entity.getClass()))
            {
                i++;
            }
        }

        return i;
    }

    public void func_464_a(List list)
    {
        loadedEntityList.addAll(list);
        for(int i = 0; i < list.size(); i++)
        {
            obtainEntitySkin((Entity)list.get(i));
        }

    }

    public void func_461_b(List list)
    {
        unloadedEntityList.addAll(list);
    }

    public boolean canBlockBePlacedAt(int i, int j, int k, int l, boolean flag)
    {
        int i1 = getBlockId(j, k, l);
        Block block = Block.blocksList[i1];
        Block block1 = Block.blocksList[i];
        AxisAlignedBB axisalignedbb = block1.getCollisionBoundingBoxFromPool(this, j, k, l);
        if(flag)
        {
            axisalignedbb = null;
        }
        if(axisalignedbb != null && !checkIfAABBIsClear(axisalignedbb))
        {
            return false;
        }
        if(block == Block.waterMoving || block == Block.waterStill || block == Block.lavaMoving || block == Block.lavaStill || block == Block.fire || block == Block.snow)
        {
            return true;
        }
        return i > 0 && block == null && block1.canPlaceBlockAt(this, j, k, l);
    }

    public PathEntity getPathToEntity(Entity entity, Entity entity1, float f)
    {
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);
        int l = (int)(f + 16F);
        int i1 = i - l;
        int j1 = j - l;
        int k1 = k - l;
        int l1 = i + l;
        int i2 = j + l;
        int j2 = k + l;
        ChunkCache chunkcache = new ChunkCache(this, i1, j1, k1, l1, i2, j2);
        return (new Pathfinder(chunkcache)).createEntityPathTo(entity, entity1, f);
    }

    public PathEntity getEntityPathToXYZ(Entity entity, int i, int j, int k, float f)
    {
        int l = MathHelper.floor_double(entity.posX);
        int i1 = MathHelper.floor_double(entity.posY);
        int j1 = MathHelper.floor_double(entity.posZ);
        int k1 = (int)(f + 8F);
        int l1 = l - k1;
        int i2 = i1 - k1;
        int j2 = j1 - k1;
        int k2 = l + k1;
        int l2 = i1 + k1;
        int i3 = j1 + k1;
        ChunkCache chunkcache = new ChunkCache(this, l1, i2, j2, k2, l2, i3);
        return (new Pathfinder(chunkcache)).createEntityPathTo(entity, i, j, k, f);
    }

    public boolean isBlockProvidingPowerTo(int i, int j, int k, int l)
    {
        int i1 = getBlockId(i, j, k);
        if(i1 == 0)
        {
            return false;
        } else
        {
            return Block.blocksList[i1].isIndirectlyPoweringTo(this, i, j, k, l);
        }
    }

    public boolean isBlockGettingPowered(int i, int j, int k)
    {
        if(isBlockProvidingPowerTo(i, j - 1, k, 0))
        {
            return true;
        }
        if(isBlockProvidingPowerTo(i, j + 1, k, 1))
        {
            return true;
        }
        if(isBlockProvidingPowerTo(i, j, k - 1, 2))
        {
            return true;
        }
        if(isBlockProvidingPowerTo(i, j, k + 1, 3))
        {
            return true;
        }
        if(isBlockProvidingPowerTo(i - 1, j, k, 4))
        {
            return true;
        }
        return isBlockProvidingPowerTo(i + 1, j, k, 5);
    }

    public boolean isBlockIndirectlyProvidingPowerTo(int i, int j, int k, int l)
    {
        if(isBlockOpaqueCube(i, j, k))
        {
            return isBlockGettingPowered(i, j, k);
        }
        int i1 = getBlockId(i, j, k);
        if(i1 == 0)
        {
            return false;
        } else
        {
            return Block.blocksList[i1].func_26520_b(this, i, j, k, l);
        }
    }

    public boolean isBlockIndirectlyGettingPowered(int i, int j, int k)
    {
        if(isBlockIndirectlyProvidingPowerTo(i, j - 1, k, 0))
        {
            return true;
        }
        if(isBlockIndirectlyProvidingPowerTo(i, j + 1, k, 1))
        {
            return true;
        }
        if(isBlockIndirectlyProvidingPowerTo(i, j, k - 1, 2))
        {
            return true;
        }
        if(isBlockIndirectlyProvidingPowerTo(i, j, k + 1, 3))
        {
            return true;
        }
        if(isBlockIndirectlyProvidingPowerTo(i - 1, j, k, 4))
        {
            return true;
        }
        return isBlockIndirectlyProvidingPowerTo(i + 1, j, k, 5);
    }

    public EntityPlayer getClosestPlayerToEntity(Entity entity, double d)
    {
        return getClosestPlayer(entity.posX, entity.posY, entity.posZ, d);
    }

    public EntityPlayer getClosestPlayer(double d, double d1, double d2, double d3)
    {
        double d4 = -1D;
        EntityPlayer entityplayer = null;
        for(int i = 0; i < playerEntities.size(); i++)
        {
            EntityPlayer entityplayer1 = (EntityPlayer)playerEntities.get(i);
            double d5 = entityplayer1.getDistanceSq(d, d1, d2);
            if((d3 < 0.0D || d5 < d3 * d3) && (d4 == -1D || d5 < d4))
            {
                d4 = d5;
                entityplayer = entityplayer1;
            }
        }

        return entityplayer;
    }

    public EntityPlayer getPlayerEntityByName(String s)
    {
        for(int i = 0; i < playerEntities.size(); i++)
        {
            if(s.equals(((EntityPlayer)playerEntities.get(i)).username))
            {
                return (EntityPlayer)playerEntities.get(i);
            }
        }

        return null;
    }

    public byte[] getChunkData(int i, int j, int k, int l, int i1, int j1)
    {
        byte abyte0[] = new byte[(l * i1 * j1 * 5) / 2];
        int k1 = i >> 4;
        int l1 = k >> 4;
        int i2 = (i + l) - 1 >> 4;
        int j2 = (k + j1) - 1 >> 4;
        int k2 = 0;
        int l2 = j;
        int i3 = j + i1;
        if(l2 < 0)
        {
            l2 = 0;
        }
        if(i3 > 128)
        {
            i3 = 128;
        }
        for(int j3 = k1; j3 <= i2; j3++)
        {
            int k3 = i - j3 * 16;
            int l3 = (i + l) - j3 * 16;
            if(k3 < 0)
            {
                k3 = 0;
            }
            if(l3 > 16)
            {
                l3 = 16;
            }
            for(int i4 = l1; i4 <= j2; i4++)
            {
                int j4 = k - i4 * 16;
                int k4 = (k + j1) - i4 * 16;
                if(j4 < 0)
                {
                    j4 = 0;
                }
                if(k4 > 16)
                {
                    k4 = 16;
                }
                k2 = getChunkFromChunkCoords(j3, i4).getChunkData(abyte0, k3, l2, j4, l3, i3, k4, k2);
            }

        }

        return abyte0;
    }

    public void checkSessionLock()
    {
        worldFile.func_22091_b();
    }

    public void setWorldTime(long l)
    {
        worldInfo.setWorldTime(l);
    }

    public long func_22079_j()
    {
        return worldInfo.getRandomSeed();
    }

    public long getWorldTime()
    {
        return worldInfo.getWorldTime();
    }

    public ChunkCoordinates getSpawnPoint()
    {
        return new ChunkCoordinates(worldInfo.getSpawnX(), worldInfo.getSpawnY(), worldInfo.getSpawnZ());
    }

    public boolean canMineBlock(EntityPlayer entityplayer, int i, int j, int k)
    {
        return true;
    }

    public void func_9206_a(Entity entity, byte byte0)
    {
    }

    public IChunkProvider func_25073_n()
    {
        return chunkProvider;
    }

    public void playNoteAt(int i, int j, int k, int l, int i1)
    {
        int j1 = getBlockId(i, j, k);
        if(j1 > 0)
        {
            Block.blocksList[j1].playBlock(this, i, j, k, l, i1);
        }
    }

    public IDataManager func_26661_o()
    {
        return worldFile;
    }

    public WorldInfo getWorldInfo()
    {
        return worldInfo;
    }

    public void updateAllPlayersSleepingFlag()
    {
        allPlayersSleeping = !playerEntities.isEmpty();
        Iterator iterator = playerEntities.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                break;
            }
            EntityPlayer entityplayer = (EntityPlayer)iterator.next();
            if(entityplayer.isPlayerSleeping())
            {
                continue;
            }
            allPlayersSleeping = false;
            break;
        } while(true);
    }

    protected void wakeUpAllPlayers()
    {
        allPlayersSleeping = false;
        Iterator iterator = playerEntities.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                break;
            }
            EntityPlayer entityplayer = (EntityPlayer)iterator.next();
            if(entityplayer.isPlayerSleeping())
            {
                entityplayer.wakeUpPlayer(false, false, false);
            }
        } while(true);
    }

    public boolean isAllPlayersFullyAsleep()
    {
        if(allPlayersSleeping && !singleplayerWorld)
        {
            for(Iterator iterator = playerEntities.iterator(); iterator.hasNext();)
            {
                EntityPlayer entityplayer = (EntityPlayer)iterator.next();
                if(!entityplayer.isPlayerFullyAsleep())
                {
                    return false;
                }
            }

            return true;
        } else
        {
            return false;
        }
    }
}
