package net.minecraft.src.item;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.entity.AxisAlignedBB;
import net.minecraft.src.material.Material;
import net.minecraft.src.World;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.block.Block;

import java.util.Random;

public class ItemPainting extends Block
{

    public ItemPainting(int i, int j)
    {
        super(i, j, Material.fire);
        field_26530_a = new int[256];
        field_26529_b = new int[256];
        func_26523_a(Block.planks.blockID, 5, 20);
        func_26523_a(Block.wood.blockID, 5, 5);
        func_26523_a(Block.leaves.blockID, 30, 60);
        func_26523_a(Block.bookShelf.blockID, 30, 20);
        func_26523_a(Block.tnt.blockID, 15, 100);
        func_26523_a(Block.cloth.blockID, 30, 60);
        func_26519_a(true);
    }

    private void func_26523_a(int i, int j, int k)
    {
        field_26530_a[i] = j;
        field_26529_b[i] = k;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }

    public int tickRate()
    {
        return 10;
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        boolean flag = world.getBlockId(i, j - 1, k) == Block.bloodStone.blockID;
        int l = world.getBlockMetadata(i, j, k);
        if(l < 15)
        {
            world.setBlockMetadataWithNotify(i, j, k, l + 1);
            world.scheduleUpdateTick(i, j, k, blockID, tickRate());
        }
        if(!flag && !func_26525_g(world, i, j, k))
        {
            if(!world.isBlockOpaqueCube(i, j - 1, k) || l > 3)
            {
                world.setBlockWithNotify(i, j, k, 0);
            }
            return;
        }
        if(!flag && !func_26524_b(world, i, j - 1, k) && l == 15 && random.nextInt(4) == 0)
        {
            world.setBlockWithNotify(i, j, k, 0);
            return;
        }
        if(l % 2 == 0 && l > 2)
        {
            func_26526_a(world, i + 1, j, k, 300, random);
            func_26526_a(world, i - 1, j, k, 300, random);
            func_26526_a(world, i, j - 1, k, 250, random);
            func_26526_a(world, i, j + 1, k, 250, random);
            func_26526_a(world, i, j, k - 1, 300, random);
            func_26526_a(world, i, j, k + 1, 300, random);
            for(int i1 = i - 1; i1 <= i + 1; i1++)
            {
                for(int j1 = k - 1; j1 <= k + 1; j1++)
                {
                    for(int k1 = j - 1; k1 <= j + 4; k1++)
                    {
                        if(i1 == i && k1 == j && j1 == k)
                        {
                            continue;
                        }
                        int l1 = 100;
                        if(k1 > j + 1)
                        {
                            l1 += (k1 - (j + 1)) * 100;
                        }
                        int i2 = func_26527_h(world, i1, k1, j1);
                        if(i2 > 0 && random.nextInt(l1) <= i2)
                        {
                            world.setBlockWithNotify(i1, k1, j1, blockID);
                        }
                    }

                }

            }

        }
        if(l == 15)
        {
            func_26526_a(world, i + 1, j, k, 1, random);
            func_26526_a(world, i - 1, j, k, 1, random);
            func_26526_a(world, i, j - 1, k, 1, random);
            func_26526_a(world, i, j + 1, k, 1, random);
            func_26526_a(world, i, j, k - 1, 1, random);
            func_26526_a(world, i, j, k + 1, 1, random);
        }
    }

    private void func_26526_a(World world, int i, int j, int k, int l, Random random)
    {
        int i1 = field_26529_b[world.getBlockId(i, j, k)];
        if(random.nextInt(l) < i1)
        {
            boolean flag = world.getBlockId(i, j, k) == Block.tnt.blockID;
            if(random.nextInt(2) == 0)
            {
                world.setBlockWithNotify(i, j, k, blockID);
            } else
            {
                world.setBlockWithNotify(i, j, k, 0);
            }
            if(flag)
            {
                Block.tnt.onBlockDestroyedByPlayer(world, i, j, k, 0);
            }
        }
    }

    private boolean func_26525_g(World world, int i, int j, int k)
    {
        if(func_26524_b(world, i + 1, j, k))
        {
            return true;
        }
        if(func_26524_b(world, i - 1, j, k))
        {
            return true;
        }
        if(func_26524_b(world, i, j - 1, k))
        {
            return true;
        }
        if(func_26524_b(world, i, j + 1, k))
        {
            return true;
        }
        if(func_26524_b(world, i, j, k - 1))
        {
            return true;
        }
        return func_26524_b(world, i, j, k + 1);
    }

    private int func_26527_h(World world, int i, int j, int k)
    {
        int l = 0;
        if(!world.isAirBlock(i, j, k))
        {
            return 0;
        } else
        {
            l = func_26528_f(world, i + 1, j, k, l);
            l = func_26528_f(world, i - 1, j, k, l);
            l = func_26528_f(world, i, j - 1, k, l);
            l = func_26528_f(world, i, j + 1, k, l);
            l = func_26528_f(world, i, j, k - 1, l);
            l = func_26528_f(world, i, j, k + 1, l);
            return l;
        }
    }

    public boolean isCollidable()
    {
        return false;
    }

    public boolean func_26524_b(IBlockAccess worldgenliquids, int i, int j, int k)
    {
        return field_26530_a[worldgenliquids.getBlockId(i, j, k)] > 0;
    }

    public int func_26528_f(World world, int i, int j, int k, int l)
    {
        int i1 = field_26530_a[world.getBlockId(i, j, k)];
        if(i1 > l)
        {
            return i1;
        } else
        {
            return l;
        }
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        return world.isBlockOpaqueCube(i, j - 1, k) || func_26525_g(world, i, j, k);
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(!world.isBlockOpaqueCube(i, j - 1, k) && !func_26525_g(world, i, j, k))
        {
            world.setBlockWithNotify(i, j, k, 0);
            return;
        } else
        {
            return;
        }
    }

    public void onBlockAdded(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j - 1, k) == Block.obsidian.blockID && Block.portal.tryToCreatePortal(world, i, j, k))
        {
            return;
        }
        if(!world.isBlockOpaqueCube(i, j - 1, k) && !func_26525_g(world, i, j, k))
        {
            world.setBlockWithNotify(i, j, k, 0);
            return;
        } else
        {
            world.scheduleUpdateTick(i, j, k, blockID, tickRate());
            return;
        }
    }

    private int field_26530_a[];
    private int field_26529_b[];
}
