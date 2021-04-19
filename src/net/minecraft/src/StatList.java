package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.achievement.Achievement;
import net.minecraft.src.achievement.AchievementList;
import net.minecraft.src.achievement.AchievementMap;
import net.minecraft.src.block.Block;
import net.minecraft.src.crafting.CraftingManager;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;

import java.util.*;

public class StatList
{

    public StatList()
    {
        System.out.println((new StringBuilder()).append("Stats: ").append(field_25123_a.size()).toString());
    }

    public static void func_26736_a(Statistic slotfurnace)
    {
        if(field_25104_C.containsKey(Integer.valueOf(slotfurnace.field_26629_d)))
        {
            throw new RuntimeException((new StringBuilder()).append("Duplicate stat id: ").append(((Statistic)field_25104_C.get(Integer.valueOf(slotfurnace.field_26629_d))).field_26628_e).append(" and ").append(slotfurnace.field_26628_e).append(" at id ").append(slotfurnace.field_26629_d).toString());
        }
        slotfurnace.field_26627_f = AchievementMap.func_25132_a(slotfurnace.field_26629_d);
        field_25123_a.add(slotfurnace);
        if(slotfurnace.func_25060_a())
        {
            AchievementList.field_25129_a.add((Achievement)slotfurnace);
        } else
        if(slotfurnace instanceof StatCrafting)
        {
            StatCrafting statcrafting = (StatCrafting)slotfurnace;
            if(statcrafting.func_25064_b() < Block.blocksList.length)
            {
                field_25121_c.add(statcrafting);
            } else
            {
                field_25120_d.add(statcrafting);
            }
        } else
        {
            field_25122_b.add(slotfurnace);
        }
    }

    public static void func_25088_a()
    {
        field_25107_A = func_26740_a(field_25107_A, "stat.useItem", 0x1020000, 0, Block.blocksList.length);
        field_25105_B = func_26738_b(field_25105_B, "stat.breakItem", 0x1030000, 0, Block.blocksList.length);
        field_25101_D = true;
        func_25091_c();
    }

    public static void func_25086_b()
    {
        field_25107_A = func_26740_a(field_25107_A, "stat.useItem", 0x1020000, Block.blocksList.length, 32000);
        field_25105_B = func_26738_b(field_25105_B, "stat.breakItem", 0x1030000, Block.blocksList.length, 32000);
        field_25099_E = true;
        func_25091_c();
    }

    public static void func_25091_c()
    {
        if(!field_25101_D || !field_25099_E)
        {
            return;
        }
        HashSet hashset = new HashSet();
        IRecipe irecipe;
        for(Iterator iterator = CraftingManager.getInstance().func_25126_b().iterator(); iterator.hasNext(); hashset.add(Integer.valueOf(irecipe.func_25077_b().itemID)))
        {
            irecipe = (IRecipe)iterator.next();
        }

        ItemStack itemstack;
        for(Iterator iterator1 = FurnaceRecipes.smelting().func_25127_b().values().iterator(); iterator1.hasNext(); hashset.add(Integer.valueOf(itemstack.itemID)))
        {
            itemstack = (ItemStack)iterator1.next();
        }

        field_25093_z = new Statistic[32000];
        Iterator iterator2 = hashset.iterator();
        do
        {
            if(!iterator2.hasNext())
            {
                break;
            }
            Integer integer = (Integer)iterator2.next();
            if(Item.itemsList[integer.intValue()] != null)
            {
                String s = StatCollector.func_25135_a("stat.craftItem", new Object[] {
                    Item.itemsList[integer.intValue()].func_25006_i()
                });
                field_25093_z[integer.intValue()] = (new StatCrafting(0x1010000 + integer.intValue(), s, integer.intValue())).func_26626_c();
            }
        } while(true);
        func_26741_a(field_25093_z);
    }

    private static Statistic[] func_26739_a(String s, int i)
    {
        Statistic aslotfurnace[] = new Statistic[256];
        for(int j = 0; j < 256; j++)
        {
            if(Block.blocksList[j] != null)
            {
                String s1 = StatCollector.func_25135_a(s, new Object[] {
                    Block.blocksList[j].func_25012_e()
                });
                aslotfurnace[j] = (new StatCrafting(i + j, s1, j)).func_26626_c();
            }
        }

        func_26741_a(aslotfurnace);
        return aslotfurnace;
    }

    private static Statistic[] func_26740_a(Statistic aslotfurnace[], String s, int i, int j, int k)
    {
        if(aslotfurnace == null)
        {
            aslotfurnace = new Statistic[32000];
        }
        for(int l = j; l < k; l++)
        {
            if(Item.itemsList[l] != null)
            {
                String s1 = StatCollector.func_25135_a(s, new Object[] {
                    Item.itemsList[l].func_25006_i()
                });
                aslotfurnace[l] = (new StatCrafting(i + l, s1, l)).func_26626_c();
            }
        }

        func_26741_a(aslotfurnace);
        return aslotfurnace;
    }

    private static Statistic[] func_26738_b(Statistic aslotfurnace[], String s, int i, int j, int k)
    {
        if(aslotfurnace == null)
        {
            aslotfurnace = new Statistic[32000];
        }
        for(int l = j; l < k; l++)
        {
            if(Item.itemsList[l] != null && Item.itemsList[l].func_25005_e())
            {
                String s1 = StatCollector.func_25135_a(s, new Object[] {
                    Item.itemsList[l].func_25006_i()
                });
                aslotfurnace[l] = (new StatCrafting(i + l, s1, l)).func_26626_c();
            }
        }

        func_26741_a(aslotfurnace);
        return aslotfurnace;
    }

    private static void func_26741_a(Statistic aslotfurnace[])
    {
        func_26737_a(aslotfurnace, Block.waterStill.blockID, Block.waterMoving.blockID);
        func_26737_a(aslotfurnace, Block.lavaStill.blockID, Block.lavaStill.blockID);
        func_26737_a(aslotfurnace, Block.pumpkinLantern.blockID, Block.pumpkin.blockID);
        func_26737_a(aslotfurnace, Block.stoneOvenActive.blockID, Block.stoneOvenIdle.blockID);
        func_26737_a(aslotfurnace, Block.oreRedstoneGlowing.blockID, Block.oreRedstone.blockID);
        func_26737_a(aslotfurnace, Block.redstoneRepeaterActive.blockID, Block.redstoneRepeaterIdle.blockID);
        func_26737_a(aslotfurnace, Block.torchRedstoneActive.blockID, Block.torchRedstoneIdle.blockID);
        func_26737_a(aslotfurnace, Block.mushroomRed.blockID, Block.mushroomBrown.blockID);
        func_26737_a(aslotfurnace, Block.stairSingle.blockID, Block.stairDouble.blockID);
    }

    private static void func_26737_a(Statistic aslotfurnace[], int i, int j)
    {
        aslotfurnace[i] = aslotfurnace[j];
        field_25123_a.remove(aslotfurnace[i]);
        field_25122_b.remove(aslotfurnace[i]);
    }

    private static Map field_25104_C = new HashMap();
    public static List field_25123_a = new ArrayList();
    public static List field_25122_b = new ArrayList();
    public static List field_25121_c = new ArrayList();
    public static List field_25120_d = new ArrayList();
    public static Statistic field_25119_e = (new Statistic(1000, StatCollector.func_25136_a("stat.startGame"))).func_26626_c();
    public static Statistic field_25118_f = (new Statistic(1001, StatCollector.func_25136_a("stat.createWorld"))).func_26626_c();
    public static Statistic field_25117_g = (new Statistic(1002, StatCollector.func_25136_a("stat.loadWorld"))).func_26626_c();
    public static Statistic field_25116_h = (new Statistic(1003, StatCollector.func_25136_a("stat.joinMultiplayer"))).func_26626_c();
    public static Statistic field_25115_i = (new Statistic(1004, StatCollector.func_25136_a("stat.leaveGame"))).func_26626_c();
    public static Statistic field_25114_j = (new TimeStatistic(1100, StatCollector.func_25136_a("stat.playOneMinute"))).func_26626_c();
    public static Statistic field_25113_k = (new StatDistance(2000, StatCollector.func_25136_a("stat.walkOneCm"))).func_26626_c();
    public static Statistic field_25112_l = (new StatDistance(2001, StatCollector.func_25136_a("stat.swimOneCm"))).func_26626_c();
    public static Statistic field_25111_m = (new StatDistance(2002, StatCollector.func_25136_a("stat.fallOneCm"))).func_26626_c();
    public static Statistic field_25110_n = (new StatDistance(2003, StatCollector.func_25136_a("stat.climbOneCm"))).func_26626_c();
    public static Statistic field_25109_o = (new StatDistance(2004, StatCollector.func_25136_a("stat.flyOneCm"))).func_26626_c();
    public static Statistic field_25108_p = (new StatDistance(2005, StatCollector.func_25136_a("stat.diveOneCm"))).func_26626_c();
    public static Statistic field_25106_q = (new Statistic(2010, StatCollector.func_25136_a("stat.jump"))).func_26626_c();
    public static Statistic field_25103_r = (new Statistic(2011, StatCollector.func_25136_a("stat.drop"))).func_26626_c();
    public static Statistic field_25102_s = (new Statistic(2020, StatCollector.func_25136_a("stat.damageDealt"))).func_26626_c();
    public static Statistic field_25100_t = (new Statistic(2021, StatCollector.func_25136_a("stat.damageTaken"))).func_26626_c();
    public static Statistic field_25098_u = (new Statistic(2022, StatCollector.func_25136_a("stat.deaths"))).func_26626_c();
    public static Statistic field_25097_v = (new Statistic(2023, StatCollector.func_25136_a("stat.mobKills"))).func_26626_c();
    public static Statistic field_25096_w = (new Statistic(2024, StatCollector.func_25136_a("stat.playerKills"))).func_26626_c();
    public static Statistic field_25095_x = (new Statistic(2025, StatCollector.func_25136_a("stat.fishCaught"))).func_26626_c();
    public static Statistic field_25094_y[] = func_26739_a("stat.mineBlock", 0x1000000);
    public static Statistic field_25093_z[] = null;
    public static Statistic field_25107_A[] = null;
    public static Statistic field_25105_B[] = null;
    private static boolean field_25101_D = false;
    private static boolean field_25099_E = false;

}
