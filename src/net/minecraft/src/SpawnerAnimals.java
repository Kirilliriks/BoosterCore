package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.block.BlockBed;
import net.minecraft.src.chunk.ChunkCoordinates;
import net.minecraft.src.entity.*;

import java.util.*;

public final class SpawnerAnimals
{

    public SpawnerAnimals()
    {
    }

    protected static WorldProviderHell func_26743_a(World world, int i, int j)
    {
        int k = i + world.rand.nextInt(16);
        int l = world.rand.nextInt(128);
        int i1 = j + world.rand.nextInt(16);
        return new WorldProviderHell(k, l, i1);
    }

    public static final int performSpawning(World world, boolean flag, boolean flag1)
    {
        return 0;
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

    private static Set eligibleChunksForSpawning = new HashSet();
    protected static final Class field_22213_a[];

    static 
    {
        field_22213_a = (new Class[] {
            EntitySpider.class, EntityZombie.class, EntitySkeleton.class
        });
    }
}
