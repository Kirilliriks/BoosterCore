package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.entity.AxisAlignedBB;
import net.minecraft.src.entity.EntityHuman;
import net.minecraft.src.material.Material;
import net.minecraft.src.world.World;

import java.util.Random;

public class BlockButton extends Block
{

    protected BlockButton(int i, int j)
    {
        super(i, j, Material.circuits);
        func_26519_a(true);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public int tickRate()
    {
        return 20;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        if(world.isBlockOpaqueCube(i - 1, j, k))
        {
            return true;
        }
        if(world.isBlockOpaqueCube(i + 1, j, k))
        {
            return true;
        }
        if(world.isBlockOpaqueCube(i, j, k - 1))
        {
            return true;
        }
        return world.isBlockOpaqueCube(i, j, k + 1);
    }

    public void onBlockPlaced(World world, int i, int j, int k, int l)
    {
        int i1 = world.getBlockMetadata(i, j, k);
        int j1 = i1 & 8;
        i1 &= 7;
        if(l == 2 && world.isBlockOpaqueCube(i, j, k + 1))
        {
            i1 = 4;
        } else
        if(l == 3 && world.isBlockOpaqueCube(i, j, k - 1))
        {
            i1 = 3;
        } else
        if(l == 4 && world.isBlockOpaqueCube(i + 1, j, k))
        {
            i1 = 2;
        } else
        if(l == 5 && world.isBlockOpaqueCube(i - 1, j, k))
        {
            i1 = 1;
        } else
        {
            i1 = getOrientation(world, i, j, k);
        }
        world.setBlockMetadataWithNotify(i, j, k, i1 + j1);
    }

    private int getOrientation(World world, int i, int j, int k)
    {
        if(world.isBlockOpaqueCube(i - 1, j, k))
        {
            return 1;
        }
        if(world.isBlockOpaqueCube(i + 1, j, k))
        {
            return 2;
        }
        if(world.isBlockOpaqueCube(i, j, k - 1))
        {
            return 3;
        }
        return !world.isBlockOpaqueCube(i, j, k + 1) ? 1 : 4;
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(func_322_g(world, i, j, k))
        {
            int i1 = world.getBlockMetadata(i, j, k) & 7;
            boolean flag = false;
            if(!world.isBlockOpaqueCube(i - 1, j, k) && i1 == 1)
            {
                flag = true;
            }
            if(!world.isBlockOpaqueCube(i + 1, j, k) && i1 == 2)
            {
                flag = true;
            }
            if(!world.isBlockOpaqueCube(i, j, k - 1) && i1 == 3)
            {
                flag = true;
            }
            if(!world.isBlockOpaqueCube(i, j, k + 1) && i1 == 4)
            {
                flag = true;
            }
            if(flag)
            {
                dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
                world.setBlockWithNotify(i, j, k, 0);
            }
        }
    }

    private boolean func_322_g(World world, int i, int j, int k)
    {
        if(!canPlaceBlockAt(world, i, j, k))
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k));
            world.setBlockWithNotify(i, j, k, 0);
            return false;
        } else
        {
            return true;
        }
    }

    public void func_26521_a(IBlockAccess worldgenliquids, int i, int j, int k)
    {
        int l = worldgenliquids.getBlockMetadata(i, j, k);
        int i1 = l & 7;
        boolean flag = (l & 8) > 0;
        float f = 0.375F;
        float f1 = 0.625F;
        float f2 = 0.1875F;
        float f3 = 0.125F;
        if(flag)
        {
            f3 = 0.0625F;
        }
        if(i1 == 1)
        {
            setBlockBounds(0.0F, f, 0.5F - f2, f3, f1, 0.5F + f2);
        } else
        if(i1 == 2)
        {
            setBlockBounds(1.0F - f3, f, 0.5F - f2, 1.0F, f1, 0.5F + f2);
        } else
        if(i1 == 3)
        {
            setBlockBounds(0.5F - f2, f, 0.0F, 0.5F + f2, f1, f3);
        } else
        if(i1 == 4)
        {
            setBlockBounds(0.5F - f2, f, 1.0F - f3, 0.5F + f2, f1, 1.0F);
        }
    }

    public void onBlockClicked(World world, int i, int j, int k, EntityHuman entityplayer)
    {
        blockActivated(world, i, j, k, entityplayer);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityHuman entityplayer)
    {
        int l = world.getBlockMetadata(i, j, k);
        int i1 = l & 7;
        int j1 = 8 - (l & 8);
        if(j1 == 0)
        {
            return true;
        }
        world.setBlockMetadataWithNotify(i, j, k, i1 + j1);
        world.markBlocksDirty(i, j, k, i, j, k);
        world.playSoundEffect((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.click", 0.3F, 0.6F);
        world.notifyBlocksOfNeighborChange(i, j, k, blockID);
        if(i1 == 1)
        {
            world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
        } else
        if(i1 == 2)
        {
            world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
        } else
        if(i1 == 3)
        {
            world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
        } else
        if(i1 == 4)
        {
            world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
        } else
        {
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        }
        world.scheduleUpdateTick(i, j, k, blockID, tickRate());
        return true;
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        int l = world.getBlockMetadata(i, j, k);
        if((l & 8) > 0)
        {
            world.notifyBlocksOfNeighborChange(i, j, k, blockID);
            int i1 = l & 7;
            if(i1 == 1)
            {
                world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
            } else
            if(i1 == 2)
            {
                world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
            } else
            if(i1 == 3)
            {
                world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
            } else
            if(i1 == 4)
            {
                world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
            } else
            {
                world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
            }
        }
        super.onBlockRemoval(world, i, j, k);
    }

    public boolean func_26520_b(IBlockAccess worldgenliquids, int i, int j, int k, int l)
    {
        return (worldgenliquids.getBlockMetadata(i, j, k) & 8) > 0;
    }

    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l)
    {
        int i1 = world.getBlockMetadata(i, j, k);
        if((i1 & 8) == 0)
        {
            return false;
        }
        int j1 = i1 & 7;
        if(j1 == 5 && l == 1)
        {
            return true;
        }
        if(j1 == 4 && l == 2)
        {
            return true;
        }
        if(j1 == 3 && l == 3)
        {
            return true;
        }
        if(j1 == 2 && l == 4)
        {
            return true;
        }
        return j1 == 1 && l == 5;
    }

    public boolean canProvidePower()
    {
        return true;
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(world.singleplayerWorld)
        {
            return;
        }
        int l = world.getBlockMetadata(i, j, k);
        if((l & 8) == 0)
        {
            return;
        }
        world.setBlockMetadataWithNotify(i, j, k, l & 7);
        world.notifyBlocksOfNeighborChange(i, j, k, blockID);
        int i1 = l & 7;
        if(i1 == 1)
        {
            world.notifyBlocksOfNeighborChange(i - 1, j, k, blockID);
        } else
        if(i1 == 2)
        {
            world.notifyBlocksOfNeighborChange(i + 1, j, k, blockID);
        } else
        if(i1 == 3)
        {
            world.notifyBlocksOfNeighborChange(i, j, k - 1, blockID);
        } else
        if(i1 == 4)
        {
            world.notifyBlocksOfNeighborChange(i, j, k + 1, blockID);
        } else
        {
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        }
        world.playSoundEffect((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.click", 0.3F, 0.5F);
        world.markBlocksDirty(i, j, k, i, j, k);
    }
}
