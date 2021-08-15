package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.block.BlockBed;
import net.minecraft.src.chunk.ChunkCoordinate;
import net.minecraft.src.chunk.ChunkCoordinates;
import net.minecraft.src.chunk.ChunkPosition;
import net.minecraft.src.entity.*;
import net.minecraft.src.material.Material;
import net.minecraft.src.biome.MobSpawnerBase;
import net.minecraft.src.world.World;

import java.util.*;

public final class SpawnerCreature {

    public SpawnerCreature() { }

    private static Set<ChunkCoordinate> eligibleChunksForSpawning = new HashSet<>();
    protected static final Class[] field_22213_a;

    static
    {
        field_22213_a = (new Class[] {
                EntitySpider.class, EntityZombie.class, EntitySkeleton.class
        });
    }

    protected static ChunkPosition func_26743_a(World world, int i, int j)
    {
        int k = i + world.rand.nextInt(16);
        int l = world.rand.nextInt(128);
        int i1 = j + world.rand.nextInt(16);
        return new ChunkPosition(k, l, i1);
    }

    public static int performSpawning(World world, boolean flag, boolean flag1) {
        int n;
        ChunkCoordinates chunkcoordinates;
        if(!flag && !flag1) {
            return 0;
        }
        eligibleChunksForSpawning.clear();
        for(int i = 0; i < world.playerEntities.size(); i++) {
            EntityPlayer entityPlayer = (EntityPlayer)world.playerEntities.get(i);
            int k = MathHelper.floor_double(entityPlayer.posX / 16D);
            int l = MathHelper.floor_double(entityPlayer.posZ / 16D);
            byte byte0 = 8;
            for(int k1 = -byte0; k1 <= byte0; k1++) {
                for(int l1 = -byte0; l1 <= byte0; l1++) {
                    eligibleChunksForSpawning.add(new ChunkCoordinate(k1 + k, l1 + l));
                }
            }
        }
        n = 0;
        chunkcoordinates = world.getSpawnPoint();
        for (EnumCreatureType enumCreatureType : EnumCreatureType.values()) {
            if (enumCreatureType.func_21103_d() && !flag1 || !enumCreatureType.func_21103_d() && !flag || world.countEntities(enumCreatureType.getCreatureClass()) > (enumCreatureType.getMaxNumberOfCreature() * eligibleChunksForSpawning.size()) / 256) continue;
            block6: for (ChunkCoordinate chunkCoordinate : eligibleChunksForSpawning) {
                SpawnListEntry biomeMeta2;
                MobSpawnerBase biomeBase = world.func_26662_a().func_26658_a(chunkCoordinate);
                List<SpawnListEntry> list = biomeBase.func_25055_a(enumCreatureType);
                if (list == null || list.isEmpty()) continue;
                int n5 = 0;
                for(Iterator iterator1 = list.iterator(); iterator1.hasNext();) {
                    SpawnListEntry spawnlistentry = (SpawnListEntry)iterator1.next();
                    n5 += spawnlistentry.field_25144_b;
                }
                int n6 = world.rand.nextInt(n5);
                biomeMeta2 = list.get(0);
                for (SpawnListEntry biomeMeta3 : list) {
                    if ((n6 -= biomeMeta3.field_25144_b) >= 0) continue;
                    biomeMeta2 = biomeMeta3;
                    break;
                }
                ChunkPosition chunkPosition = func_26743_a(world, chunkCoordinate.field_26507_a * 16, chunkCoordinate.field_26506_b * 16);
                int n7 = chunkPosition.field_26708_a;
                int n8 = chunkPosition.field_26707_b;
                int n9 = chunkPosition.field_26709_c;
                if (world.isBlockOpaqueCube(n7, n8, n9) || world.getBlockMaterial(n7, n8, n9) != enumCreatureType.getCreatureMaterial()) continue;
                int n10 = 0;
                for (int i = 0; i < 3; ++i) {
                    int n11 = n7;
                    int n12 = n8;
                    int n13 = n9;
                    int n14 = 6;
                    for (int j = 0; j < 4; ++j) {
                        EntityLiving entityLiving;
                        n11 += world.rand.nextInt(n14) - world.rand.nextInt(n14);
                        n12 += world.rand.nextInt(1) - world.rand.nextInt(1);
                        n13 += (world.rand.nextInt(n14) - world.rand.nextInt(n14));
                        if (!func_21167_a(enumCreatureType, world, n11, n12, n13)) continue;
                        float f = (float)n11 + 0.5f;
                        float f1 = (float)n12;
                        float f2 = (float)n13 + 0.5f;
                        if ( world.getClosestPlayer(f, f1, f2, 24.0) != null) continue;
                        float f3 = f - (float)chunkcoordinates.posX;
                        float f4 = f1 - (float)chunkcoordinates.posY;
                        float f5 = f2 - (float)chunkcoordinates.posZ;
                        float f6 = f3 * f3 + f4 * f4 + f5 * f5;
                        if(f6 < 576F)
                        {
                            continue; /* Loop/switch isn't completed */
                        }
                        try {
                            entityLiving = (EntityLiving)biomeMeta2.field_25145_a.getConstructor(World.class).newInstance(world);
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                            return n;
                        }
                        entityLiving.setLocationAndAngles(f, f1, f2, world.rand.nextFloat() * 360.0f, 0.0f);
                        if (entityLiving.getCanSpawnHere()) {
                            world.entityJoinedWorld(entityLiving);
                            func_21166_a(entityLiving, world, f, f1, f2);
                            if (++n10 >= entityLiving.getMaxSpawnedInChunk()) continue block6;
                        }
                        n += n10;
                    }
                }
            }
        }
        return n;
    }

    private static boolean func_21167_a(EnumCreatureType enumcreaturetype, World world, int i, int j, int k)
    {
        if(enumcreaturetype.getCreatureMaterial() == Material.water)
        {
            return world.getBlockMaterial(i, j, k).getIsLiquid() && !world.isBlockOpaqueCube(i, j + 1, k);
        } else
        {
            return world.isBlockOpaqueCube(i, j - 1, k) && !world.isBlockOpaqueCube(i, j, k) && !world.getBlockMaterial(i, j, k).getIsLiquid() && !world.isBlockOpaqueCube(i, j + 1, k);
        }
    }

    private static void func_21166_a(EntityLiving entityliving, World world, float f, float f1, float f2)
    {
        if((entityliving instanceof EntitySpider) && world.rand.nextInt(100) == 0)
        {
            EntitySkeleton entityskeleton = new EntitySkeleton(world);
            entityskeleton.setLocationAndAngles(f, f1, f2, entityliving.rotationYaw, 0.0F);
            world.entityJoinedWorld(entityskeleton);
            entityskeleton.mountEntity(entityliving);
        } else
        if(entityliving instanceof EntitySheep)
        {
            ((EntitySheep)entityliving).setFleeceColor(EntitySheep.func_21066_a(world.rand));
        }
    }

    public static boolean performSleepSpawning(World world, List list)
    {
        boolean flag = false;
        Pathfinder pathfinder = new Pathfinder(world);
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                break;
            }
            EntityPlayer entityplayer = (EntityPlayer)iterator.next();
            Class aclass[] = field_22213_a;
            if(aclass != null && aclass.length != 0)
            {
                boolean flag1 = false;
                int i = 0;
                while(i < 20 && !flag1) 
                {
                    int j = (MathHelper.floor_double(entityplayer.posX) + world.rand.nextInt(32)) - world.rand.nextInt(32);
                    int k = (MathHelper.floor_double(entityplayer.posZ) + world.rand.nextInt(32)) - world.rand.nextInt(32);
                    int l = (MathHelper.floor_double(entityplayer.posY) + world.rand.nextInt(16)) - world.rand.nextInt(16);
                    if(l < 1)
                    {
                        l = 1;
                    } else
                    if(l > 128)
                    {
                        l = 128;
                    }
                    int i1 = world.rand.nextInt(aclass.length);
                    int j1;
                    for(j1 = l; j1 > 2 && !world.isBlockOpaqueCube(j, j1 - 1, k); j1--) { }
                    for(; !func_21167_a(EnumCreatureType.monster, world, j, j1, k) && j1 < l + 16 && j1 < 128; j1++) { }
                    if(j1 >= l + 16 || j1 >= 128)
                    {
                        j1 = l;
                    } else
                    {
                        float f = (float)j + 0.5F;
                        float f1 = j1;
                        float f2 = (float)k + 0.5F;
                        EntityLiving entityliving;
                        try
                        {
                            entityliving = (EntityLiving)aclass[i1].getConstructor(new Class[] {
                                World.class
                            }).newInstance(new Object[] {
                                world
                            });
                        }
                        catch(Exception exception)
                        {
                            exception.printStackTrace();
                            return flag;
                        }
                        entityliving.setLocationAndAngles(f, f1, f2, world.rand.nextFloat() * 360F, 0.0F);
                        if(entityliving.getCanSpawnHere())
                        {
                            PathEntity pathentity = pathfinder.createEntityPathTo(entityliving, entityplayer, 32F);
                            if(pathentity != null && pathentity.pathLength > 1)
                            {
                                PathPoint pathpoint = pathentity.func_22211_c();
                                if(Math.abs((double)pathpoint.xCoord - entityplayer.posX) < 1.5D && Math.abs((double)pathpoint.zCoord - entityplayer.posZ) < 1.5D && Math.abs((double)pathpoint.yCoord - entityplayer.posY) < 1.5D)
                                {
                                    ChunkCoordinates chunkcoordinates = BlockBed.func_22021_g(world, MathHelper.floor_double(entityplayer.posX), MathHelper.floor_double(entityplayer.posY), MathHelper.floor_double(entityplayer.posZ), 1);
                                    if(chunkcoordinates == null)
                                    {
                                        chunkcoordinates = new ChunkCoordinates(j, j1 + 1, k);
                                    }
                                    entityliving.setLocationAndAngles((float)chunkcoordinates.posX + 0.5F, chunkcoordinates.posY, (float)chunkcoordinates.posZ + 0.5F, 0.0F, 0.0F);
                                    world.entityJoinedWorld(entityliving);
                                    func_21166_a(entityliving, world, (float)chunkcoordinates.posX + 0.5F, chunkcoordinates.posY, (float)chunkcoordinates.posZ + 0.5F);
                                    entityplayer.wakeUpPlayer(true, false, false);
                                    entityliving.onEntityUpdate();
                                    flag = true;
                                    flag1 = true;
                                }
                            }
                        }
                    }
                    i++;
                }
            }
        } while(true);
        return flag;
    }
}
