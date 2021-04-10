package net.minecraft.src.mobspawner;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.SpawnListEntry;
import net.minecraft.src.WorldChunkManager;
import net.minecraft.src.entity.EntityGhast;

public class MobSpawnerHell extends MobSpawnerBase
{

    public MobSpawnerHell()
    {
        field_25058_r.clear();
        field_25057_s.clear();
        field_25056_t.clear();
        field_25058_r.add(new SpawnListEntry(EntityGhast.class, 10));
        field_25058_r.add(new SpawnListEntry(WorldChunkManager.class, 10));
    }
}
