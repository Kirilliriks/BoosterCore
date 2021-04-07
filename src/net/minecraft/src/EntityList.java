package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class EntityList
{

    public EntityList()
    {
    }

    private static void addMapping(Class class1, String s, int i)
    {
        stringToClassMapping.put(s, class1);
        classToStringMapping.put(class1, s);
        IDtoClassMapping.put(Integer.valueOf(i), class1);
        classToIDMapping.put(class1, Integer.valueOf(i));
    }

    public static Entity createEntityInWorld(String s, World world)
    {
        Entity entity = null;
        try
        {
            Class class1 = (Class)stringToClassMapping.get(s);
            if(class1 != null)
            {
                entity = (Entity)class1.getConstructor(new Class[] {
                    World.class
                }).newInstance(new Object[] {
                    world
                });
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return entity;
    }

    public static Entity createEntityFromNBT(NBTTagCompound nbttagcompound, World world)
    {
        Entity entity = null;
        try
        {
            Class class1 = (Class)stringToClassMapping.get(nbttagcompound.getString("id"));
            if(class1 != null)
            {
                entity = (Entity)class1.getConstructor(new Class[] {
                    World.class
                }).newInstance(new Object[] {
                    world
                });
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        if(entity != null)
        {
            entity.readFromNBT(nbttagcompound);
        } else
        {
            System.out.println((new StringBuilder()).append("Skipping Entity with id ").append(nbttagcompound.getString("id")).toString());
        }
        return entity;
    }

    public static int getEntityID(Entity entity)
    {
        return ((Integer)classToIDMapping.get(entity.getClass())).intValue();
    }

    public static String getEntityString(Entity entity)
    {
        return (String)classToStringMapping.get(entity.getClass());
    }

    private static Map stringToClassMapping = new HashMap();
    private static Map classToStringMapping = new HashMap();
    private static Map IDtoClassMapping = new HashMap();
    private static Map classToIDMapping = new HashMap();

    static 
    {
        addMapping(EntityArrow.class, "Arrow", 10);
        addMapping(EntitySnowball.class, "Snowball", 11);
        addMapping(EntityItem.class, "Item", 1);
        addMapping(EntityPainting.class, "Painting", 9);
        addMapping(EntityLiving.class, "Mob", 48);
        addMapping(EntityMobs.class, "Monster", 49);
        addMapping(EntityCreeper.class, "Creeper", 50);
        addMapping(EntitySkeleton.class, "Skeleton", 51);
        addMapping(EntitySpider.class, "Spider", 52);
        addMapping(EntityZombieSimple.class, "Giant", 53);
        addMapping(EntityZombie.class, "Zombie", 54);
        addMapping(EntitySlime.class, "Slime", 55);
        addMapping(EntityGhast.class, "Ghast", 56);
        addMapping(WorldChunkManager.class, "PigZombie", 57);
        addMapping(ChunkPosition.class, "Pig", 90);
        addMapping(EntitySheep.class, "Sheep", 91);
        addMapping(EntityCow.class, "Cow", 92);
        addMapping(PlayerManager.class, "Chicken", 93);
        addMapping(WorldGenFire.class, "Squid", 94);
        addMapping(EntityWolf.class, "Wolf", 95);
        addMapping(EntityTNTPrimed.class, "PrimedTnt", 20);
        addMapping(EntityFallingSand.class, "FallingSand", 21);
        addMapping(TileEntitySign.class, "Minecart", 40);
        addMapping(EntityBoat.class, "Boat", 41);
    }
}
