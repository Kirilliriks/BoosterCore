package net.minecraft.src.block;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemTool;

public class BlockFlower extends ItemTool
{

    public BlockFlower(int i, EnumToolMaterial enumtoolmaterial)
    {
        super(i, 1, enumtoolmaterial, field_26515_bi);
    }

    public boolean canHarvestBlock(Block block)
    {
        if(block == Block.snow)
        {
            return true;
        }
        return block == Block.blockSnow;
    }

    private static Block field_26515_bi[];

    static 
    {
        field_26515_bi = (new Block[] {
            Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay
        });
    }
}
