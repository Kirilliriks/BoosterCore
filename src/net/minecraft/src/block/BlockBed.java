package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;

import java.util.Random;

public class BlockBed extends Block
{

    public BlockBed(int i)
    {
        super(i, 134, Material.cloth);
        setBounds();
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        int l = world.getBlockMetadata(i, j, k);
        if(!func_22020_d(l))
        {
            int i1 = func_22019_c(l);
            i += field_22023_a[i1][0];
            k += field_22023_a[i1][1];
            if(world.getBlockId(i, j, k) != blockID)
            {
                return true;
            }
            l = world.getBlockMetadata(i, j, k);
        }
        if(func_22018_f(l))
        {
            entityplayer.func_22061_a("tile.bed.occupied");
            return true;
        }
        EnumStatus enumstatus = entityplayer.goToSleep(i, j, k);
        if(enumstatus == EnumStatus.OK)
        {
            func_22022_a(world, i, j, k, true);
            return true;
        }
        if(enumstatus == EnumStatus.NOT_POSSIBLE_NOW)
        {
            entityplayer.func_22061_a("tile.bed.noSleep");
        }
        return true;
    }

    public int func_22009_a(int i, int j)
    {
        if(i == 0)
        {
            return Block.planks.blockIndexInTexture;
        }
        int k = func_22019_c(j);
        int l = ModelBed.field_22155_c[k][i];
        if(func_22020_d(j))
        {
            if(l == 2)
            {
                return blockIndexInTexture + 2 + 16;
            }
            if(l == 5 || l == 4)
            {
                return blockIndexInTexture + 1 + 16;
            } else
            {
                return blockIndexInTexture + 1;
            }
        }
        if(l == 3)
        {
            return (blockIndexInTexture - 1) + 16;
        }
        if(l == 5 || l == 4)
        {
            return blockIndexInTexture + 16;
        } else
        {
            return blockIndexInTexture;
        }
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public void func_26521_a(WorldGenLiquids worldgenliquids, int i, int j, int k)
    {
        setBounds();
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        int i1 = world.getBlockMetadata(i, j, k);
        int j1 = func_22019_c(i1);
        if(func_22020_d(i1))
        {
            if(world.getBlockId(i - field_22023_a[j1][0], j, k - field_22023_a[j1][1]) != blockID)
            {
                world.setBlockWithNotify(i, j, k, 0);
            }
        } else
        if(world.getBlockId(i + field_22023_a[j1][0], j, k + field_22023_a[j1][1]) != blockID)
        {
            world.setBlockWithNotify(i, j, k, 0);
            if(!world.singleplayerWorld)
            {
                dropBlockAsItem(world, i, j, k, i1);
            }
        }
    }

    public int idDropped(int i, Random random)
    {
        if(func_22020_d(i))
        {
            return 0;
        } else
        {
            return Item.bed.shiftedIndex;
        }
    }

    private void setBounds()
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    }

    public static int func_22019_c(int i)
    {
        return i & 3;
    }

    public static boolean func_22020_d(int i)
    {
        return (i & 8) != 0;
    }

    public static boolean func_22018_f(int i)
    {
        return (i & 4) != 0;
    }

    public static void func_22022_a(World world, int i, int j, int k, boolean flag)
    {
        int l = world.getBlockMetadata(i, j, k);
        if(flag)
        {
            l |= 4;
        } else
        {
            l &= -5;
        }
        world.setBlockMetadataWithNotify(i, j, k, l);
    }

    public static ChunkCoordinates func_22021_g(World world, int i, int j, int k, int l)
    {
        int i1 = world.getBlockMetadata(i, j, k);
        int j1 = func_22019_c(i1);
        for(int k1 = 0; k1 <= 1; k1++)
        {
            int l1 = i - field_22023_a[j1][0] * k1 - 1;
            int i2 = k - field_22023_a[j1][1] * k1 - 1;
            int j2 = l1 + 2;
            int k2 = i2 + 2;
            for(int l2 = l1; l2 <= j2; l2++)
            {
                for(int i3 = i2; i3 <= k2; i3++)
                {
                    if(!world.isBlockOpaqueCube(l2, j - 1, i3) || !world.isAirBlock(l2, j, i3) || !world.isAirBlock(l2, j + 1, i3))
                    {
                        continue;
                    }
                    if(l > 0)
                    {
                        l--;
                    } else
                    {
                        return new ChunkCoordinates(l2, j, i3);
                    }
                }

            }

        }

        return null;
    }

    public static final int field_22023_a[][] = {
        {
            0, 1
        }, {
            -1, 0
        }, {
            0, -1
        }, {
            1, 0
        }
    };

}
