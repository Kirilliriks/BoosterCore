package net.minecraft.src.chunk;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.entity.AxisAlignedBB;
import net.minecraft.src.entity.Entity;
import net.minecraft.src.material.Material;
import net.minecraft.src.World;
import net.minecraft.src.block.Block;

import java.util.Random;

public class ChunkBlockMap_ButBlock extends Block
{

    public ChunkBlockMap_ButBlock(int i)
    {
        super(i, Material.ground);
        blockIndexInTexture = 87;
        func_26519_a(true);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        setLightOpacity(255);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return AxisAlignedBB.getBoundingBoxFromPool(i + 0, j + 0, k + 0, i + 1, j + 1, k + 1);
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public int func_22009_a(int i, int j)
    {
        if(i == 1 && j > 0)
        {
            return blockIndexInTexture - 1;
        }
        if(i == 1)
        {
            return blockIndexInTexture;
        } else
        {
            return 2;
        }
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(random.nextInt(5) == 0)
        {
            if(func_26536_h(world, i, j, k))
            {
                world.setBlockMetadataWithNotify(i, j, k, 7);
            } else
            {
                int l = world.getBlockMetadata(i, j, k);
                if(l > 0)
                {
                    world.setBlockMetadataWithNotify(i, j, k, l - 1);
                } else
                if(!func_26537_g(world, i, j, k))
                {
                    world.setBlockWithNotify(i, j, k, Block.dirt.blockID);
                }
            }
        }
    }

    public void onEntityWalking(World world, int i, int j, int k, Entity entity)
    {
        if(world.rand.nextInt(4) == 0)
        {
            world.setBlockWithNotify(i, j, k, Block.dirt.blockID);
        }
    }

    private boolean func_26537_g(World world, int i, int j, int k)
    {
        int l = 0;
        for(int i1 = i - l; i1 <= i + l; i1++)
        {
            for(int j1 = k - l; j1 <= k + l; j1++)
            {
                if(world.getBlockId(i1, j + 1, j1) == Block.crops.blockID)
                {
                    return true;
                }
            }

        }

        return false;
    }

    private boolean func_26536_h(World world, int i, int j, int k)
    {
        for(int l = i - 4; l <= i + 4; l++)
        {
            for(int i1 = j; i1 <= j + 1; i1++)
            {
                for(int j1 = k - 4; j1 <= k + 4; j1++)
                {
                    if(world.getBlockMaterial(l, i1, j1) == Material.water)
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        super.onNeighborBlockChange(world, i, j, k, l);
        Material material = world.getBlockMaterial(i, j + 1, k);
        if(material.isSolid())
        {
            world.setBlockWithNotify(i, j, k, Block.dirt.blockID);
        }
    }

    public int idDropped(int i, Random random)
    {
        return Block.dirt.idDropped(0, random);
    }
}
