package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.entity.EntityHuman;
import net.minecraft.src.material.Material;
import net.minecraft.src.world.World;

public class BlockWorkbench extends Block
{

    protected BlockWorkbench(int i)
    {
        super(i, Material.wood);
        blockIndexInTexture = 59;
    }

    public int getBlockTextureFromSide(int i)
    {
        if(i == 1)
        {
            return blockIndexInTexture - 16;
        }
        if(i == 0)
        {
            return Block.planks.getBlockTextureFromSide(0);
        }
        if(i == 2 || i == 4)
        {
            return blockIndexInTexture + 1;
        } else
        {
            return blockIndexInTexture;
        }
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityHuman entityplayer)
    {
        if(world.singleplayerWorld)
        {
            return true;
        } else
        {
            entityplayer.displayWorkbenchGUI(i, j, k);
            return true;
        }
    }
}
