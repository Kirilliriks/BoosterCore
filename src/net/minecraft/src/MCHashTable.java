package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.inventory.InventoryCraftResult;

public class MCHashTable
{

    public MCHashTable()
    {
        threshold = 12;
        slots = new InventoryCraftResult[16];
    }

    private static int computeHash(int i)
    {
        i ^= i >>> 20 ^ i >>> 12;
        return i ^ i >>> 7 ^ i >>> 4;
    }

    private static int getSlotIndex(int i, int j)
    {
        return i & j - 1;
    }

    public Object lookup(int i)
    {
        int j = computeHash(i);
        for(InventoryCraftResult inventorycraftresult = slots[getSlotIndex(j, slots.length)]; inventorycraftresult != null; inventorycraftresult = inventorycraftresult.field_26702_c)
        {
            if(inventorycraftresult.stackResult == i)
            {
                return inventorycraftresult.field_26700_b;
            }
        }

        return null;
    }

    public boolean containsItem(int i)
    {
        return func_26735_c(i) != null;
    }

    final InventoryCraftResult func_26735_c(int i)
    {
        int j = computeHash(i);
        for(InventoryCraftResult inventorycraftresult = slots[getSlotIndex(j, slots.length)]; inventorycraftresult != null; inventorycraftresult = inventorycraftresult.field_26702_c)
        {
            if(inventorycraftresult.stackResult == i)
            {
                return inventorycraftresult;
            }
        }

        return null;
    }

    public void addKey(int i, Object obj)
    {
        int j = computeHash(i);
        int k = getSlotIndex(j, slots.length);
        for(InventoryCraftResult inventorycraftresult = slots[k]; inventorycraftresult != null; inventorycraftresult = inventorycraftresult.field_26702_c)
        {
            if(inventorycraftresult.stackResult == i)
            {
                inventorycraftresult.field_26700_b = obj;
            }
        }

        versionStamp++;
        insert(j, i, obj, k);
    }

    private void grow(int i)
    {
        InventoryCraftResult ainventorycraftresult[] = slots;
        int j = ainventorycraftresult.length;
        if(j == 0x40000000)
        {
            threshold = 0x7fffffff;
            return;
        } else
        {
            InventoryCraftResult ainventorycraftresult1[] = new InventoryCraftResult[i];
            func_26734_a(ainventorycraftresult1);
            slots = ainventorycraftresult1;
            threshold = (int)((float)i * growFactor);
            return;
        }
    }

    private void func_26734_a(InventoryCraftResult ainventorycraftresult[])
    {
        InventoryCraftResult ainventorycraftresult1[] = slots;
        int i = ainventorycraftresult.length;
        for(int j = 0; j < ainventorycraftresult1.length; j++)
        {
            InventoryCraftResult inventorycraftresult = ainventorycraftresult1[j];
            if(inventorycraftresult == null)
            {
                continue;
            }
            ainventorycraftresult1[j] = null;
            do
            {
                InventoryCraftResult inventorycraftresult1 = inventorycraftresult.field_26702_c;
                int k = getSlotIndex(inventorycraftresult.field_26701_d, i);
                inventorycraftresult.field_26702_c = ainventorycraftresult[k];
                ainventorycraftresult[k] = inventorycraftresult;
                inventorycraftresult = inventorycraftresult1;
            } while(inventorycraftresult != null);
        }

    }

    public Object removeObject(int i)
    {
        InventoryCraftResult inventorycraftresult = func_26733_e(i);
        return inventorycraftresult != null ? inventorycraftresult.field_26700_b : null;
    }

    final InventoryCraftResult func_26733_e(int i)
    {
        int j = computeHash(i);
        int k = getSlotIndex(j, slots.length);
        InventoryCraftResult inventorycraftresult = slots[k];
        InventoryCraftResult inventorycraftresult1;
        InventoryCraftResult inventorycraftresult2;
        for(inventorycraftresult1 = inventorycraftresult; inventorycraftresult1 != null; inventorycraftresult1 = inventorycraftresult2)
        {
            inventorycraftresult2 = inventorycraftresult1.field_26702_c;
            if(inventorycraftresult1.stackResult == i)
            {
                versionStamp++;
                count--;
                if(inventorycraftresult == inventorycraftresult1)
                {
                    slots[k] = inventorycraftresult2;
                } else
                {
                    inventorycraftresult.field_26702_c = inventorycraftresult2;
                }
                return inventorycraftresult1;
            }
            inventorycraftresult = inventorycraftresult1;
        }

        return inventorycraftresult1;
    }

    public void clearMap()
    {
        versionStamp++;
        InventoryCraftResult ainventorycraftresult[] = slots;
        for(int i = 0; i < ainventorycraftresult.length; i++)
        {
            ainventorycraftresult[i] = null;
        }

        count = 0;
    }

    private void insert(int i, int j, Object obj, int k)
    {
        InventoryCraftResult inventorycraftresult = slots[k];
        slots[k] = new InventoryCraftResult(i, j, obj, inventorycraftresult);
        if(count++ >= threshold)
        {
            grow(2 * slots.length);
        }
    }

    public static int getHash(int i)
    {
        return computeHash(i);
    }

    private transient InventoryCraftResult slots[];
    private transient int count;
    private int threshold;
    private final float growFactor = 0.75F;
    private volatile transient int versionStamp;
}
