package net.minecraft.src.item;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.chunk.ChunkPosition;
import net.minecraft.src.entity.EntityLiving;

public class ItemSaddle extends Item
{

    public ItemSaddle(int i)
    {
        super(i);
        maxStackSize = 1;
    }

    public void saddleEntity(ItemStack itemstack, EntityLiving entityliving)
    {
        if(entityliving instanceof ChunkPosition)
        {
            ChunkPosition chunkposition = (ChunkPosition)entityliving;
            if(!chunkposition.func_26603_v())
            {
                chunkposition.func_26602_a(true);
                itemstack.stackSize--;
            }
        }
    }

    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1)
    {
        saddleEntity(itemstack, entityliving);
        return true;
    }
}
