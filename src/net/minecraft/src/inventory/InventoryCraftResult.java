package net.minecraft.src.inventory;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.MCHashTable;

public class InventoryCraftResult
{

    public InventoryCraftResult(int i, int j, Object obj, InventoryCraftResult inventorycraftresult)
    {
        field_26700_b = obj;
        field_26702_c = inventorycraftresult;
        stackResult = j;
        field_26701_d = i;
    }

    public final int func_26699_a()
    {
        return stackResult;
    }

    public final Object func_26698_b()
    {
        return field_26700_b;
    }

    public final boolean equals(Object obj)
    {
        if(!(obj instanceof InventoryCraftResult))
        {
            return false;
        }
        InventoryCraftResult inventorycraftresult = (InventoryCraftResult)obj;
        Integer integer = func_26699_a();
        Integer integer1 = inventorycraftresult.func_26699_a();
        if(integer == integer1 || integer != null && integer.equals(integer1))
        {
            Object obj1 = func_26698_b();
            Object obj2 = inventorycraftresult.func_26698_b();
            if(obj1 == obj2 || obj1 != null && obj1.equals(obj2))
            {
                return true;
            }
        }
        return false;
    }

    public final int hashCode()
    {
        return MCHashTable.getHash(stackResult);
    }

    public final String toString()
    {
        return (new StringBuilder()).append(func_26699_a()).append("=").append(func_26698_b()).toString();
    }

    public final int stackResult;
    public Object field_26700_b;
    public InventoryCraftResult field_26702_c;
    public final int field_26701_d;
}
