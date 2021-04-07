package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.Material;
import net.minecraft.src.World;

import java.util.Random;

public class BlockStationary extends BlockFluids
{

    protected BlockStationary(int i, Material material)
    {
        super(i, material);
        func_26519_a(false);
        if(material == Material.lava)
        {
            func_26519_a(true);
        }
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        super.onNeighborBlockChange(world, i, j, k, l);
        if(world.getBlockId(i, j, k) == blockID)
        {
            func_22025_i(world, i, j, k);
        }
    }

    private void func_22025_i(World world, int i, int j, int k)
    {
        int l = world.getBlockMetadata(i, j, k);
        world.editingBlocks = true;
        world.setBlockAndMetadata(i, j, k, blockID - 1, l);
        world.markBlocksDirty(i, j, k, i, j, k);
        world.scheduleUpdateTick(i, j, k, blockID - 1, tickRate());
        world.editingBlocks = false;
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(blockMaterial == Material.lava)
        {
            int l = random.nextInt(3);
            for(int i1 = 0; i1 < l; i1++)
            {
                i += random.nextInt(3) - 1;
                j++;
                k += random.nextInt(3) - 1;
                int j1 = world.getBlockId(i, j, k);
                if(j1 == 0)
                {
                    if(func_4033_j(world, i - 1, j, k) || func_4033_j(world, i + 1, j, k) || func_4033_j(world, i, j, k - 1) || func_4033_j(world, i, j, k + 1) || func_4033_j(world, i, j - 1, k) || func_4033_j(world, i, j + 1, k))
                    {
                        world.setBlockWithNotify(i, j, k, Block.fire.blockID);
                        return;
                    }
                    continue;
                }
                if(Block.blocksList[j1].blockMaterial.getIsSolid())
                {
                    return;
                }
            }

        }
    }

    private boolean func_4033_j(World world, int i, int j, int k)
    {
        return world.getBlockMaterial(i, j, k).getBurning();
    }
}
