package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public interface IPlayerFileData
{

    public abstract void writePlayerData(EntityPlayer entityplayer);

    public abstract void readPlayerData(EntityPlayer entityplayer);
}
