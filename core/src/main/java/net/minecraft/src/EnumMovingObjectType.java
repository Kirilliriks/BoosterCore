package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public enum EnumMovingObjectType
{
    TILE,
    ENTITY;
/*
    public static EnumMovingObjectType[] values()
    {
        return (EnumMovingObjectType[])field_21124_c.clone();
    }

    public static EnumMovingObjectType valueOf(String s)
    {
        return (EnumMovingObjectType)Enum.valueOf(EnumMovingObjectType.class, s);
    }

    private EnumMovingObjectType(String s, int i)
    {
        super(s, i);
    }

    public static final EnumMovingObjectType TILE;
    public static final EnumMovingObjectType ENTITY;
    private static final EnumMovingObjectType field_21124_c[]; /* synthetic field */
/*
    static 
    {
        TILE = new EnumMovingObjectType("TILE", 0);
        ENTITY = new EnumMovingObjectType("ENTITY", 1);
        field_21124_c = (new EnumMovingObjectType[] {
            TILE, ENTITY
        });
    }
*/
}
