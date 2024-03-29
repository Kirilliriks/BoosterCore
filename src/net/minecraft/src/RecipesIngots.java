package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.block.Block;
import net.minecraft.src.crafting.CraftingManager;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;

public class RecipesIngots
{

    public RecipesIngots()
    {
        recipeItems = (new Object[][] {
            new Object[] {
                Block.blockGold, new ItemStack(Item.ingotGold, 9)
            }, new Object[] {
                Block.blockSteel, new ItemStack(Item.ingotIron, 9)
            }, new Object[] {
                Block.blockDiamond, new ItemStack(Item.diamond, 9)
            }, new Object[] {
                Block.blockLapis, new ItemStack(Item.dyePowder, 9, 4)
            }
        });
    }

    public void addRecipes(CraftingManager craftingmanager)
    {
        for(int i = 0; i < recipeItems.length; i++)
        {
            Block block = (Block)recipeItems[i][0];
            ItemStack itemstack = (ItemStack)recipeItems[i][1];
            craftingmanager.addRecipe(new ItemStack(block), new Object[] {
                "###", "###", "###", Character.valueOf('#'), itemstack
            });
            craftingmanager.addRecipe(itemstack, new Object[] {
                "#", Character.valueOf('#'), block
            });
        }

    }

    private Object recipeItems[][];
}
