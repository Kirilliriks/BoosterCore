package net.minecraft.src.biome;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.tileentity.TileEntity;
import net.minecraft.src.nbt.NBTTagCompound;
import net.minecraft.src.packet.Packet;
import net.minecraft.src.packet.Packet130;

public class MobSpawnerRainforest extends TileEntity
{

    public MobSpawnerRainforest()
    {
        field_26609_b = -1;
        field_26611_c = true;
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setString("Text1", field_26610_a[0]);
        nbttagcompound.setString("Text2", field_26610_a[1]);
        nbttagcompound.setString("Text3", field_26610_a[2]);
        nbttagcompound.setString("Text4", field_26610_a[3]);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        field_26611_c = false;
        super.readFromNBT(nbttagcompound);
        for(int i = 0; i < 4; i++)
        {
            field_26610_a[i] = nbttagcompound.getString((new StringBuilder()).append("Text").append(i + 1).toString());
            if(field_26610_a[i].length() > 15)
            {
                field_26610_a[i] = field_26610_a[i].substring(0, 15);
            }
        }

    }

    public Packet getDescriptionPacket()
    {
        String as[] = new String[4];
        for(int i = 0; i < 4; i++)
        {
            as[i] = field_26610_a[i];
        }

        return new Packet130(xCoord, yCoord, zCoord, as);
    }

    public boolean func_26608_a()
    {
        return field_26611_c;
    }

    public String field_26610_a[] = {
        "", "", "", ""
    };
    public int field_26609_b;
    private boolean field_26611_c;
}
