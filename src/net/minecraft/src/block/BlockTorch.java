package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.world.World;
import net.minecraft.src.chunk.ChunkPosition;

import java.util.ArrayList;
import java.util.List;

class BlockTorch
{

    public BlockTorch(BlockMinecartTrack blockminecarttrack, World world, int i, int j, int k)
    {
        super();
        field_26723_a = blockminecarttrack;
        field_26724_g = new ArrayList();
        field_26722_b = world;
        field_26728_c = i;
        field_26727_d = j;
        field_26726_e = k;
        field_26725_f = world.getBlockMetadata(i, j, k);
        func_26716_a();
    }

    private void func_26716_a()
    {
        field_26724_g.clear();
        if(field_26725_f == 0)
        {
            field_26724_g.add(new ChunkPosition(field_26728_c, field_26727_d, field_26726_e - 1));
            field_26724_g.add(new ChunkPosition(field_26728_c, field_26727_d, field_26726_e + 1));
        } else
        if(field_26725_f == 1)
        {
            field_26724_g.add(new ChunkPosition(field_26728_c - 1, field_26727_d, field_26726_e));
            field_26724_g.add(new ChunkPosition(field_26728_c + 1, field_26727_d, field_26726_e));
        } else
        if(field_26725_f == 2)
        {
            field_26724_g.add(new ChunkPosition(field_26728_c - 1, field_26727_d, field_26726_e));
            field_26724_g.add(new ChunkPosition(field_26728_c + 1, field_26727_d + 1, field_26726_e));
        } else
        if(field_26725_f == 3)
        {
            field_26724_g.add(new ChunkPosition(field_26728_c - 1, field_26727_d + 1, field_26726_e));
            field_26724_g.add(new ChunkPosition(field_26728_c + 1, field_26727_d, field_26726_e));
        } else
        if(field_26725_f == 4)
        {
            field_26724_g.add(new ChunkPosition(field_26728_c, field_26727_d + 1, field_26726_e - 1));
            field_26724_g.add(new ChunkPosition(field_26728_c, field_26727_d, field_26726_e + 1));
        } else
        if(field_26725_f == 5)
        {
            field_26724_g.add(new ChunkPosition(field_26728_c, field_26727_d, field_26726_e - 1));
            field_26724_g.add(new ChunkPosition(field_26728_c, field_26727_d + 1, field_26726_e + 1));
        } else
        if(field_26725_f == 6)
        {
            field_26724_g.add(new ChunkPosition(field_26728_c + 1, field_26727_d, field_26726_e));
            field_26724_g.add(new ChunkPosition(field_26728_c, field_26727_d, field_26726_e + 1));
        } else
        if(field_26725_f == 7)
        {
            field_26724_g.add(new ChunkPosition(field_26728_c - 1, field_26727_d, field_26726_e));
            field_26724_g.add(new ChunkPosition(field_26728_c, field_26727_d, field_26726_e + 1));
        } else
        if(field_26725_f == 8)
        {
            field_26724_g.add(new ChunkPosition(field_26728_c - 1, field_26727_d, field_26726_e));
            field_26724_g.add(new ChunkPosition(field_26728_c, field_26727_d, field_26726_e - 1));
        } else
        if(field_26725_f == 9)
        {
            field_26724_g.add(new ChunkPosition(field_26728_c + 1, field_26727_d, field_26726_e));
            field_26724_g.add(new ChunkPosition(field_26728_c, field_26727_d, field_26726_e - 1));
        }
    }

    private void func_26711_b()
    {
        for(int i = 0; i < field_26724_g.size(); i++)
        {
            BlockTorch blocktorch = func_26714_a((ChunkPosition)field_26724_g.get(i));
            if(blocktorch == null || !blocktorch.func_26715_b(this))
            {
                field_26724_g.remove(i--);
            } else
            {
                field_26724_g.set(i, new ChunkPosition(blocktorch.field_26728_c, blocktorch.field_26727_d, blocktorch.field_26726_e));
            }
        }

    }

    private boolean func_26710_a(int i, int j, int k)
    {
        if(field_26722_b.getBlockId(i, j, k) == field_26723_a.blockID)
        {
            return true;
        }
        if(field_26722_b.getBlockId(i, j + 1, k) == field_26723_a.blockID)
        {
            return true;
        }
        return field_26722_b.getBlockId(i, j - 1, k) == field_26723_a.blockID;
    }

    private BlockTorch func_26714_a(ChunkPosition worldproviderhell)
    {
        if(field_26722_b.getBlockId(worldproviderhell.field_26708_a, worldproviderhell.field_26707_b, worldproviderhell.field_26709_c) == field_26723_a.blockID)
        {
            return new BlockTorch(field_26723_a, field_26722_b, worldproviderhell.field_26708_a, worldproviderhell.field_26707_b, worldproviderhell.field_26709_c);
        }
        if(field_26722_b.getBlockId(worldproviderhell.field_26708_a, worldproviderhell.field_26707_b + 1, worldproviderhell.field_26709_c) == field_26723_a.blockID)
        {
            return new BlockTorch(field_26723_a, field_26722_b, worldproviderhell.field_26708_a, worldproviderhell.field_26707_b + 1, worldproviderhell.field_26709_c);
        }
        if(field_26722_b.getBlockId(worldproviderhell.field_26708_a, worldproviderhell.field_26707_b - 1, worldproviderhell.field_26709_c) == field_26723_a.blockID)
        {
            return new BlockTorch(field_26723_a, field_26722_b, worldproviderhell.field_26708_a, worldproviderhell.field_26707_b - 1, worldproviderhell.field_26709_c);
        } else
        {
            return null;
        }
    }

    private boolean func_26715_b(BlockTorch blocktorch)
    {
        for(int i = 0; i < field_26724_g.size(); i++)
        {
            ChunkPosition worldproviderhell = (ChunkPosition)field_26724_g.get(i);
            if(worldproviderhell.field_26708_a == blocktorch.field_26728_c && worldproviderhell.field_26709_c == blocktorch.field_26726_e)
            {
                return true;
            }
        }

        return false;
    }

    private boolean func_26719_b(int i, int j, int k)
    {
        for(int l = 0; l < field_26724_g.size(); l++)
        {
            ChunkPosition worldproviderhell = (ChunkPosition)field_26724_g.get(l);
            if(worldproviderhell.field_26708_a == i && worldproviderhell.field_26709_c == k)
            {
                return true;
            }
        }

        return false;
    }

    private int func_26717_c()
    {
        int i = 0;
        if(func_26710_a(field_26728_c, field_26727_d, field_26726_e - 1))
        {
            i++;
        }
        if(func_26710_a(field_26728_c, field_26727_d, field_26726_e + 1))
        {
            i++;
        }
        if(func_26710_a(field_26728_c - 1, field_26727_d, field_26726_e))
        {
            i++;
        }
        if(func_26710_a(field_26728_c + 1, field_26727_d, field_26726_e))
        {
            i++;
        }
        return i;
    }

    private boolean func_26720_c(BlockTorch blocktorch)
    {
        if(func_26715_b(blocktorch))
        {
            return true;
        }
        if(field_26724_g.size() == 2)
        {
            return false;
        }
        if(field_26724_g.size() == 0)
        {
            return true;
        }
        ChunkPosition worldproviderhell = (ChunkPosition)field_26724_g.get(0);
        return blocktorch.field_26727_d != field_26727_d || worldproviderhell.field_26707_b != field_26727_d ? true : true;
    }

    private void func_26721_d(BlockTorch blocktorch)
    {
        field_26724_g.add(new ChunkPosition(blocktorch.field_26728_c, blocktorch.field_26727_d, blocktorch.field_26726_e));
        boolean flag = func_26719_b(field_26728_c, field_26727_d, field_26726_e - 1);
        boolean flag1 = func_26719_b(field_26728_c, field_26727_d, field_26726_e + 1);
        boolean flag2 = func_26719_b(field_26728_c - 1, field_26727_d, field_26726_e);
        boolean flag3 = func_26719_b(field_26728_c + 1, field_26727_d, field_26726_e);
        byte byte0 = -1;
        if(flag || flag1)
        {
            byte0 = 0;
        }
        if(flag2 || flag3)
        {
            byte0 = 1;
        }
        if(flag1 && flag3 && !flag && !flag2)
        {
            byte0 = 6;
        }
        if(flag1 && flag2 && !flag && !flag3)
        {
            byte0 = 7;
        }
        if(flag && flag2 && !flag1 && !flag3)
        {
            byte0 = 8;
        }
        if(flag && flag3 && !flag1 && !flag2)
        {
            byte0 = 9;
        }
        if(byte0 == 0)
        {
            if(field_26722_b.getBlockId(field_26728_c, field_26727_d + 1, field_26726_e - 1) == field_26723_a.blockID)
            {
                byte0 = 4;
            }
            if(field_26722_b.getBlockId(field_26728_c, field_26727_d + 1, field_26726_e + 1) == field_26723_a.blockID)
            {
                byte0 = 5;
            }
        }
        if(byte0 == 1)
        {
            if(field_26722_b.getBlockId(field_26728_c + 1, field_26727_d + 1, field_26726_e) == field_26723_a.blockID)
            {
                byte0 = 2;
            }
            if(field_26722_b.getBlockId(field_26728_c - 1, field_26727_d + 1, field_26726_e) == field_26723_a.blockID)
            {
                byte0 = 3;
            }
        }
        if(byte0 < 0)
        {
            byte0 = 0;
        }
        field_26722_b.setBlockMetadataWithNotify(field_26728_c, field_26727_d, field_26726_e, byte0);
    }

    private boolean func_26713_c(int i, int j, int k)
    {
        BlockTorch blocktorch = func_26714_a(new ChunkPosition(i, j, k));
        if(blocktorch == null)
        {
            return false;
        } else
        {
            blocktorch.func_26711_b();
            return blocktorch.func_26720_c(this);
        }
    }

    public void func_26718_a(boolean flag)
    {
        boolean flag1 = func_26713_c(field_26728_c, field_26727_d, field_26726_e - 1);
        boolean flag2 = func_26713_c(field_26728_c, field_26727_d, field_26726_e + 1);
        boolean flag3 = func_26713_c(field_26728_c - 1, field_26727_d, field_26726_e);
        boolean flag4 = func_26713_c(field_26728_c + 1, field_26727_d, field_26726_e);
        int i = -1;
        if((flag1 || flag2) && !flag3 && !flag4)
        {
            i = 0;
        }
        if((flag3 || flag4) && !flag1 && !flag2)
        {
            i = 1;
        }
        if(flag2 && flag4 && !flag1 && !flag3)
        {
            i = 6;
        }
        if(flag2 && flag3 && !flag1 && !flag4)
        {
            i = 7;
        }
        if(flag1 && flag3 && !flag2 && !flag4)
        {
            i = 8;
        }
        if(flag1 && flag4 && !flag2 && !flag3)
        {
            i = 9;
        }
        if(i == -1)
        {
            if(flag1 || flag2)
            {
                i = 0;
            }
            if(flag3 || flag4)
            {
                i = 1;
            }
            if(flag)
            {
                if(flag2 && flag4)
                {
                    i = 6;
                }
                if(flag3 && flag2)
                {
                    i = 7;
                }
                if(flag4 && flag1)
                {
                    i = 9;
                }
                if(flag1 && flag3)
                {
                    i = 8;
                }
            } else
            {
                if(flag1 && flag3)
                {
                    i = 8;
                }
                if(flag4 && flag1)
                {
                    i = 9;
                }
                if(flag3 && flag2)
                {
                    i = 7;
                }
                if(flag2 && flag4)
                {
                    i = 6;
                }
            }
        }
        if(i == 0)
        {
            if(field_26722_b.getBlockId(field_26728_c, field_26727_d + 1, field_26726_e - 1) == field_26723_a.blockID)
            {
                i = 4;
            }
            if(field_26722_b.getBlockId(field_26728_c, field_26727_d + 1, field_26726_e + 1) == field_26723_a.blockID)
            {
                i = 5;
            }
        }
        if(i == 1)
        {
            if(field_26722_b.getBlockId(field_26728_c + 1, field_26727_d + 1, field_26726_e) == field_26723_a.blockID)
            {
                i = 2;
            }
            if(field_26722_b.getBlockId(field_26728_c - 1, field_26727_d + 1, field_26726_e) == field_26723_a.blockID)
            {
                i = 3;
            }
        }
        if(i < 0)
        {
            i = 0;
        }
        field_26725_f = i;
        func_26716_a();
        field_26722_b.setBlockMetadataWithNotify(field_26728_c, field_26727_d, field_26726_e, i);
        for(int j = 0; j < field_26724_g.size(); j++)
        {
            BlockTorch blocktorch = func_26714_a((ChunkPosition)field_26724_g.get(j));
            if(blocktorch == null)
            {
                continue;
            }
            blocktorch.func_26711_b();
            if(blocktorch.func_26720_c(this))
            {
                blocktorch.func_26721_d(this);
            }
        }

    }

    static int func_26712_a(BlockTorch blocktorch)
    {
        return blocktorch.func_26717_c();
    }

    private World field_26722_b;
    private int field_26728_c;
    private int field_26727_d;
    private int field_26726_e;
    private int field_26725_f;
    private List field_26724_g;
    final BlockMinecartTrack field_26723_a; /* synthetic field */
}
