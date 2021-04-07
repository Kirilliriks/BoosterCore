package net.minecraft.src.nbt;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.block.Block;
import net.minecraft.src.crafting.CraftingManager;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;

public class NBTTagString
{

    public NBTTagString()
    {
        field_26665_b = (new Object[][] {
            new Object[] {
                Block.planks, Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold
            }, new Object[] {
                Item.swordWood, Item.swordStone, Item.swordSteel, Item.swordDiamond, Item.swordGold
            }
        });
    }

    public void func_26664_a(CraftingManager craftingmanager)
    {
        for(int i = 0; i < field_26665_b[0].length; i++)
        {
            Object obj = field_26665_b[0][i];
            for(int j = 0; j < field_26665_b.length - 1; j++)
            {
                Item item = (Item)field_26665_b[j + 1][i];
                craftingmanager.addRecipe(new ItemStack(item), new Object[] {
                    stringValue[j], Character.valueOf('#'), Item.stick, Character.valueOf('X'), obj
                });
            }

        }

        craftingmanager.addRecipe(new ItemStack(Item.bow, 1), new Object[] {
            " #X", "# X", " #X", Character.valueOf('X'), Item.silk, Character.valueOf('#'), Item.stick
        });
        craftingmanager.addRecipe(new ItemStack(Item.arrow, 4), new Object[] {
            "X", "#", "Y", Character.valueOf('Y'), Item.feather, Character.valueOf('X'), Item.flint, Character.valueOf('#'), Item.stick
        });
    }

    private String stringValue[][] = {
        {
            "X", "X", "#"
        }
    };
    private Object field_26665_b[][];
}
