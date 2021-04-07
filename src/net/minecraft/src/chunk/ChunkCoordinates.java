package net.minecraft.src.chunk;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class ChunkCoordinates
    implements Comparable
{

    public ChunkCoordinates()
    {
    }

    public ChunkCoordinates(int i, int j, int k)
    {
        posX = i;
        posY = j;
        posZ = k;
    }

    public ChunkCoordinates(ChunkCoordinates chunkcoordinates)
    {
        posX = chunkcoordinates.posX;
        posY = chunkcoordinates.posY;
        posZ = chunkcoordinates.posZ;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof ChunkCoordinates))
        {
            return false;
        } else
        {
            ChunkCoordinates chunkcoordinates = (ChunkCoordinates)obj;
            return posX == chunkcoordinates.posX && posY == chunkcoordinates.posY && posZ == chunkcoordinates.posZ;
        }
    }

    public int hashCode()
    {
        return posX + posZ << 8 + posY << 16;
    }

    public int func_22215_a(ChunkCoordinates chunkcoordinates)
    {
        if(posY == chunkcoordinates.posY)
        {
            if(posZ == chunkcoordinates.posZ)
            {
                return posX - chunkcoordinates.posX;
            } else
            {
                return posZ - chunkcoordinates.posZ;
            }
        } else
        {
            return posY - chunkcoordinates.posY;
        }
    }

    public int compareTo(Object obj)
    {
        return func_22215_a((ChunkCoordinates)obj);
    }

    public int posX;
    public int posY;
    public int posZ;
}
