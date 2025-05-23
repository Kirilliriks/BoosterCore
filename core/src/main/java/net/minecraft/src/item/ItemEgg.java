package net.minecraft.src.item;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.packet.Packet25_ButEntity;
import net.minecraft.src.world.World;
import net.minecraft.src.entity.EntityHuman;

public class ItemEgg extends Item
{

    public ItemEgg(int i)
    {
        super(i);
        maxStackSize = 16;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityHuman entityplayer)
    {
        itemstack.stackSize--;
        world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if(!world.singleplayerWorld)
        {
            world.entityJoinedWorld(new Packet25_ButEntity(world, entityplayer));
        }
        return itemstack;
    }
}
