package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.File;
import java.io.FileInputStream;

public class SaveFormatOld
    implements ISaveFormat
{

    public SaveFormatOld(File file)
    {
        if(!file.exists())
        {
            file.mkdirs();
        }
        field_22106_a = file;
    }

    public WorldInfo func_22103_b(String s)
    {
        File file = new File(field_22106_a, s);
        if(!file.exists())
        {
            return null;
        }
        File file1 = new File(file, "level.dat");
        if(file1.exists())
        {
            try
            {
                NBTTagCompound nbttagcompound = CompressedStreamTools.func_770_a(new FileInputStream(file1));
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
                return new WorldInfo(nbttagcompound1);
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        }
        return null;
    }

    protected static void func_22104_a(File afile[])
    {
        for(int i = 0; i < afile.length; i++)
        {
            if(afile[i].isDirectory())
            {
                func_22104_a(afile[i].listFiles());
            }
            afile[i].delete();
        }

    }

    public WorldGenTaiga2 func_26730_a(String s, boolean flag)
    {
        return new PlayerNBTManager(field_22106_a, s, flag);
    }

    public boolean func_22102_a(String s)
    {
        return false;
    }

    public boolean func_26729_a(String s, EntityPigZombie entitypigzombie)
    {
        return false;
    }

    protected final File field_22106_a;
}
