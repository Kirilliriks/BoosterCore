package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.entity.AxisAlignedBB;
import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.item.Item;
import net.minecraft.src.material.Material;

import java.util.Random;

public class BlockDoor extends Block
{

    protected BlockDoor(int i, Material material)
    {
        super(i, material);
        blockIndexInTexture = 97;
        if(material == Material.iron)
        {
            blockIndexInTexture++;
        }
        float f = 0.5F;
        float f1 = 1.0F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public int func_22009_a(int i, int j)
    {
        if(i == 0 || i == 1)
        {
            return blockIndexInTexture;
        }
        int k = func_271_d(j);
        if((k == 0 || k == 2) ^ (i <= 3))
        {
            return blockIndexInTexture;
        }
        int l = k / 2 + (i & 1 ^ k);
        l += (j & 4) / 4;
        int i1 = blockIndexInTexture - (j & 8) * 2;
        if((l & 1) != 0)
        {
            i1 = -i1;
        }
        return i1;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        func_26521_a(world, i, j, k);
        return super.getCollisionBoundingBoxFromPool(world, i, j, k);
    }

    public void func_26521_a(IBlockAccess worldgenliquids, int i, int j, int k)
    {
        func_273_b(func_271_d(worldgenliquids.getBlockMetadata(i, j, k)));
    }

    public void func_273_b(int i)
    {
        float f = 0.1875F;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        if(i == 0)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        }
        if(i == 1)
        {
            setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        if(i == 2)
        {
            setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        }
        if(i == 3)
        {
            setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        }
    }

    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        blockActivated(world, i, j, k, entityplayer);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(blockMaterial == Material.iron)
        {
            return true;
        }
        int l = world.getBlockMetadata(i, j, k);
        if((l & 8) != 0)
        {
            if(world.getBlockId(i, j - 1, k) == blockID)
            {
                blockActivated(world, i, j - 1, k, entityplayer);
            }
            return true;
        }
        if(world.getBlockId(i, j + 1, k) == blockID)
        {
            world.setBlockMetadataWithNotify(i, j + 1, k, (l ^ 4) + 8);
        }
        world.setBlockMetadataWithNotify(i, j, k, l ^ 4);
        world.markBlocksDirty(i, j - 1, k, i, j, k);
        if(Math.random() < 0.5D)
        {
            world.playSoundEffect((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.door_open", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
        } else
        {
            world.playSoundEffect((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.door_close", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
        }
        return true;
    }

    public void func_272_a(World world, int i, int j, int k, boolean flag)
    {
        int l = world.getBlockMetadata(i, j, k);
        if((l & 8) != 0)
        {
            if(world.getBlockId(i, j - 1, k) == blockID)
            {
                func_272_a(world, i, j - 1, k, flag);
            }
            return;
        }
        boolean flag1 = (world.getBlockMetadata(i, j, k) & 4) > 0;
        if(flag1 == flag)
        {
            return;
        }
        if(world.getBlockId(i, j + 1, k) == blockID)
        {
            world.setBlockMetadataWithNotify(i, j + 1, k, (l ^ 4) + 8);
        }
        world.setBlockMetadataWithNotify(i, j, k, l ^ 4);
        world.markBlocksDirty(i, j - 1, k, i, j, k);
        if(Math.random() < 0.5D)
        {
            world.playSoundEffect((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.door_open", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
        } else
        {
            world.playSoundEffect((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.door_close", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        int i1 = world.getBlockMetadata(i, j, k);
        if((i1 & 8) != 0)
        {
            if(world.getBlockId(i, j - 1, k) != blockID)
            {
                world.setBlockWithNotify(i, j, k, 0);
            }
            if(l > 0 && Block.blocksList[l].canProvidePower())
            {
                onNeighborBlockChange(world, i, j - 1, k, l);
            }
        } else
        {
            boolean flag = false;
            if(world.getBlockId(i, j + 1, k) != blockID)
            {
                world.setBlockWithNotify(i, j, k, 0);
                flag = true;
            }
            if(!world.isBlockOpaqueCube(i, j - 1, k))
            {
                world.setBlockWithNotify(i, j, k, 0);
                flag = true;
                if(world.getBlockId(i, j + 1, k) == blockID)
                {
                    world.setBlockWithNotify(i, j + 1, k, 0);
                }
            }
            if(flag)
            {
                if(!world.singleplayerWorld)
                {
                    dropBlockAsItem(world, i, j, k, i1);
                }
            } else
            if(l > 0 && Block.blocksList[l].canProvidePower())
            {
                boolean flag1 = world.isBlockIndirectlyGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i, j + 1, k);
                func_272_a(world, i, j, k, flag1);
            }
        }
    }

    public int idDropped(int i, Random random)
    {
        if((i & 8) != 0)
        {
            return 0;
        }
        if(blockMaterial == Material.iron)
        {
            return Item.doorSteel.shiftedIndex;
        } else
        {
            return Item.doorWood.shiftedIndex;
        }
    }

    public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1)
    {
        func_26521_a(world, i, j, k);
        return super.collisionRayTrace(world, i, j, k, vec3d, vec3d1);
    }

    public int func_271_d(int i)
    {
        if((i & 4) == 0)
        {
            return i - 1 & 3;
        } else
        {
            return i & 3;
        }
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        if(j >= 127)
        {
            return false;
        } else
        {
            return world.isBlockOpaqueCube(i, j - 1, k) && super.canPlaceBlockAt(world, i, j, k) && super.canPlaceBlockAt(world, i, j + 1, k);
        }
    }
}
