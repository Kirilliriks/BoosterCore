package net.minecraft.src.item;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.material.Material;
import net.minecraft.src.world.World;
import net.minecraft.src.block.Block;
import net.minecraft.src.entity.EntityItem;
import net.minecraft.src.entity.EntityHuman;

public class ItemHoe extends Item
{

    public ItemHoe(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i);
        maxStackSize = 1;
        setMaxDamage(enumtoolmaterial.getMaxUses());
    }

    public boolean onItemUse(ItemStack itemstack, EntityHuman entityplayer, World world, int i, int j, int k, int l)
    {
        int i1 = world.getBlockId(i, j, k);
        Material material = world.getBlockMaterial(i, j + 1, k);
        if(!material.isSolid() && i1 == Block.grass.blockID || i1 == Block.dirt.blockID)
        {
            Block block = Block.tilledField;
            world.playSoundEffect((float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, block.stepSound.func_737_c(), (block.stepSound.func_738_a() + 1.0F) / 2.0F, block.stepSound.func_739_b() * 0.8F);
            if(world.singleplayerWorld)
            {
                return true;
            }
            world.setBlockWithNotify(i, j, k, block.blockID);
            itemstack.func_25125_a(1, entityplayer);
            if(world.rand.nextInt(8) == 0 && i1 == Block.grass.blockID)
            {
                int j1 = 1;
                for(int k1 = 0; k1 < j1; k1++)
                {
                    float f = 0.7F;
                    float f1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5F;
                    float f2 = 1.2F;
                    float f3 = world.rand.nextFloat() * f + (1.0F - f) * 0.5F;
                    EntityItem entityitem = new EntityItem(world, (float)i + f1, (float)j + f2, (float)k + f3, new ItemStack(Item.seeds));
                    entityitem.delayBeforeCanPickup = 10;
                    world.entityJoinedWorld(entityitem);
                }

            }
            return true;
        } else
        {
            return false;
        }
    }
}
