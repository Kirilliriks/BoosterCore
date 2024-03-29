package net.minecraft.src.item;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.world.World;
import net.minecraft.src.entity.EntityFish;
import net.minecraft.src.entity.EntityHuman;

public class ItemFishingRod extends Item
{

    public ItemFishingRod(int i)
    {
        super(i);
        setMaxDamage(64);
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityHuman entityplayer)
    {
        if(entityplayer.fishEntity != null)
        {
            int i = entityplayer.fishEntity.func_6143_c();
            itemstack.func_25125_a(i, entityplayer);
            entityplayer.swingItem();
        } else
        {
            world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if(!world.singleplayerWorld)
            {
                world.entityJoinedWorld(new EntityFish(world, entityplayer));
            }
            entityplayer.swingItem();
        }
        return itemstack;
    }
}
