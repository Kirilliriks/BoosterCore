package net.minecraft.src.item;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.MathHelper;
import net.minecraft.src.mobspawner.MobSpawnerRainforest;
import net.minecraft.src.World;
import net.minecraft.src.block.Block;
import net.minecraft.src.entity.EntityPlayer;

public class ItemSign extends Item
{

    public ItemSign(int i)
    {
        super(i);
        maxStackSize = 1;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        if(l == 0)
        {
            return false;
        }
        if(!world.getBlockMaterial(i, j, k).isSolid())
        {
            return false;
        }
        if(l == 1)
        {
            j++;
        }
        if(l == 2)
        {
            k--;
        }
        if(l == 3)
        {
            k++;
        }
        if(l == 4)
        {
            i--;
        }
        if(l == 5)
        {
            i++;
        }
        if(!Block.signPost.canPlaceBlockAt(world, i, j, k))
        {
            return false;
        }
        if(l == 1)
        {
            world.setBlockAndMetadataWithNotify(i, j, k, Block.signPost.blockID, MathHelper.floor_double((double)(((entityplayer.rotationYaw + 180F) * 16F) / 360F) + 0.5D) & 0xf);
        } else
        {
            world.setBlockAndMetadataWithNotify(i, j, k, Block.signWall.blockID, l);
        }
        itemstack.stackSize--;
        MobSpawnerRainforest mobspawnerrainforest = (MobSpawnerRainforest)world.getBlockTileEntity(i, j, k);
        if(mobspawnerrainforest != null)
        {
            entityplayer.func_26606_a(mobspawnerrainforest);
        }
        return true;
    }
}
