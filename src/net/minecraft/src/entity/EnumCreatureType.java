package net.minecraft.src.entity;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.Material;

public enum EnumCreatureType
{
    monster(IMobs.class, 70, Material.air, false),
    creature(EntityAnimals.class, 15, Material.air, true),
    waterCreature(EntityWaterMob.class, 5, Material.water, true);
/*
    public static EnumCreatureType[] values()
    {
        return (EnumCreatureType[])field_6155_e.clone();
    }

    public static EnumCreatureType valueOf(String s)
    {
        return (EnumCreatureType)Enum.valueOf(EnumCreatureType.class, s);
    }
*/
    private EnumCreatureType(Class class1, int j, Material material, boolean flag)
    {
        creatureClass = class1;
        maxNumberOfCreature = j;
        creatureMaterial = material;
        field_21106_g = flag;
    }

    public Class getCreatureClass()
    {
        return creatureClass;
    }

    public int getMaxNumberOfCreature()
    {
        return maxNumberOfCreature;
    }

    public Material getCreatureMaterial()
    {
        return creatureMaterial;
    }

    public boolean func_21103_d()
    {
        return field_21106_g;
    }
/*
    public static final EnumCreatureType monster;
    public static final EnumCreatureType creature;
    public static final EnumCreatureType waterCreature;
*/
    private final Class creatureClass;
    private final int maxNumberOfCreature;
    private final Material creatureMaterial;
    private final boolean field_21106_g;
/*
    private static final EnumCreatureType field_6155_e[]; /* synthetic field */
/*
    static 
    {
        monster = new EnumCreatureType("monster", 0, IMobs.class, 70, Material.air, false);
        creature = new EnumCreatureType("creature", 1, EntityAnimals.class, 15, Material.air, true);
        waterCreature = new EnumCreatureType("waterCreature", 2, EntityWaterMob.class, 5, Material.water, true);
        field_6155_e = (new EnumCreatureType[] {
            monster, creature, waterCreature
        });
    }
*/
}
