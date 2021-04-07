package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.Random;

public class BlockChest extends BlockContainer
{

    protected BlockChest(int i)
    {
        super(i, Material.wood);
        random = new Random();
        blockIndexInTexture = 26;
    }

    public int getBlockTextureFromSide(int i)
    {
        if(i == 1)
        {
            return blockIndexInTexture - 1;
        }
        if(i == 0)
        {
            return blockIndexInTexture - 1;
        }
        if(i == 3)
        {
            return blockIndexInTexture + 1;
        } else
        {
            return blockIndexInTexture;
        }
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        int l = 0;
        if(world.getBlockId(i - 1, j, k) == blockID)
        {
            l++;
        }
        if(world.getBlockId(i + 1, j, k) == blockID)
        {
            l++;
        }
        if(world.getBlockId(i, j, k - 1) == blockID)
        {
            l++;
        }
        if(world.getBlockId(i, j, k + 1) == blockID)
        {
            l++;
        }
        if(l > 1)
        {
            return false;
        }
        if(isThereANeighborChest(world, i - 1, j, k))
        {
            return false;
        }
        if(isThereANeighborChest(world, i + 1, j, k))
        {
            return false;
        }
        if(isThereANeighborChest(world, i, j, k - 1))
        {
            return false;
        }
        return !isThereANeighborChest(world, i, j, k + 1);
    }

    private boolean isThereANeighborChest(World world, int i, int j, int k)
    {
        if(world.getBlockId(i, j, k) != blockID)
        {
            return false;
        }
        if(world.getBlockId(i - 1, j, k) == blockID)
        {
            return true;
        }
        if(world.getBlockId(i + 1, j, k) == blockID)
        {
            return true;
        }
        if(world.getBlockId(i, j, k - 1) == blockID)
        {
            return true;
        }
        return world.getBlockId(i, j, k + 1) == blockID;
    }

    public void onBlockRemoval(World world, int i, int j, int k)
    {
        TileEntityChest tileentitychest = (TileEntityChest)world.getBlockTileEntity(i, j, k);
label0:
        for(int l = 0; l < tileentitychest.getSizeInventory(); l++)
        {
            ItemStack itemstack = tileentitychest.getStackInSlot(l);
            if(itemstack == null)
            {
                continue;
            }
            float f = random.nextFloat() * 0.8F + 0.1F;
            float f1 = random.nextFloat() * 0.8F + 0.1F;
            float f2 = random.nextFloat() * 0.8F + 0.1F;
            do
            {
                if(itemstack.stackSize <= 0)
                {
                    continue label0;
                }
                int i1 = random.nextInt(21) + 10;
                if(i1 > itemstack.stackSize)
                {
                    i1 = itemstack.stackSize;
                }
                itemstack.stackSize -= i1;
                EntityItem entityitem = new EntityItem(world, (float)i + f, (float)j + f1, (float)k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
                float f3 = 0.05F;
                entityitem.motionX = (float)random.nextGaussian() * f3;
                entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float)random.nextGaussian() * f3;
                world.entityJoinedWorld(entityitem);
            } while(true);
        }

        super.onBlockRemoval(world, i, j, k);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        Object obj = (TileEntityChest)world.getBlockTileEntity(i, j, k);
        if(world.isBlockOpaqueCube(i, j + 1, k))
        {
            return true;
        }
        if(world.getBlockId(i - 1, j, k) == blockID && world.isBlockOpaqueCube(i - 1, j + 1, k))
        {
            return true;
        }
        if(world.getBlockId(i + 1, j, k) == blockID && world.isBlockOpaqueCube(i + 1, j + 1, k))
        {
            return true;
        }
        if(world.getBlockId(i, j, k - 1) == blockID && world.isBlockOpaqueCube(i, j + 1, k - 1))
        {
            return true;
        }
        if(world.getBlockId(i, j, k + 1) == blockID && world.isBlockOpaqueCube(i, j + 1, k + 1))
        {
            return true;
        }
        if(world.getBlockId(i - 1, j, k) == blockID)
        {
            obj = new InventoryLargeChest("Large chest", (TileEntityChest)world.getBlockTileEntity(i - 1, j, k), ((IInventory) (obj)));
        }
        if(world.getBlockId(i + 1, j, k) == blockID)
        {
            obj = new InventoryLargeChest("Large chest", ((IInventory) (obj)), (TileEntityChest)world.getBlockTileEntity(i + 1, j, k));
        }
        if(world.getBlockId(i, j, k - 1) == blockID)
        {
            obj = new InventoryLargeChest("Large chest", (TileEntityChest)world.getBlockTileEntity(i, j, k - 1), ((IInventory) (obj)));
        }
        if(world.getBlockId(i, j, k + 1) == blockID)
        {
            obj = new InventoryLargeChest("Large chest", ((IInventory) (obj)), (TileEntityChest)world.getBlockTileEntity(i, j, k + 1));
        }
        if(world.singleplayerWorld)
        {
            return true;
        } else
        {
            entityplayer.displayGUIChest(((IInventory) (obj)));
            return true;
        }
    }

    protected TileEntity getBlockEntity()
    {
        return new TileEntityChest();
    }

    private Random random;
}
