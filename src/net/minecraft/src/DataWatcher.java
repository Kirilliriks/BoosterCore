package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.chunk.ChunkCoordinates;
import net.minecraft.src.item.ItemStack;

import java.io.*;
import java.util.*;

public class DataWatcher
{

    public DataWatcher()
    {
    }

    public void addObject(int i, Object obj)
    {
        Integer integer = (Integer)dataTypes.get(obj.getClass());
        if(integer == null)
        {
            throw new IllegalArgumentException("Unknown data type: " + obj.getClass());
        }
        if(i > 31)
        {
            throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + 31 + ")");
        }
        if(watchedObjects.containsKey(i))
        {
            throw new IllegalArgumentException("Duplicate id value for " + i + "!");
        } else
        {
            WatchableObject watchableobject = new WatchableObject(integer, i, obj);
            watchedObjects.put(i, watchableobject);
            return;
        }
    }

    public byte getWatchableObjectByte(int i)
    {
        return (Byte) ((WatchableObject) watchedObjects.get(i)).getObject();
    }

    public int func_25075_b(int i)
    {
        return (Integer) ((WatchableObject) watchedObjects.get(i)).getObject();
    }

    public String func_25076_c(int i)
    {
        return (String)((WatchableObject)watchedObjects.get(i)).getObject();
    }

    public void updateObject(int i, Object obj)
    {
        WatchableObject watchableobject = (WatchableObject)watchedObjects.get(i);
        if(!obj.equals(watchableobject.getObject()))
        {
            watchableobject.setObject(obj);
            watchableobject.setWatching(true);
            objectChanged = true;
        }
    }

    public boolean hasObjectChanged()
    {
        return objectChanged;
    }

    public static void writeObjectsInListToStream(List list, DataOutputStream dataoutputstream) throws IOException
    {
        if(list != null)
        {
            WatchableObject watchableobject;
            for(Iterator iterator = list.iterator(); iterator.hasNext(); writeWatchableObject(dataoutputstream, watchableobject))
            {
                watchableobject = (WatchableObject)iterator.next();
            }

        }
        dataoutputstream.writeByte(127);
    }

    public ArrayList getChangedObjects()
    {
        ArrayList arraylist = null;
        if(objectChanged)
        {
            Iterator iterator = watchedObjects.values().iterator();
            do
            {
                if(!iterator.hasNext())
                {
                    break;
                }
                WatchableObject watchableobject = (WatchableObject)iterator.next();
                if(watchableobject.getWatching())
                {
                    watchableobject.setWatching(false);
                    if(arraylist == null)
                    {
                        arraylist = new ArrayList();
                    }
                    arraylist.add(watchableobject);
                }
            } while(true);
        }
        objectChanged = false;
        return arraylist;
    }

    public void writeWatchableObjects(DataOutputStream dataoutputstream) throws IOException
    {
        WatchableObject watchableobject;
        for(Iterator iterator = watchedObjects.values().iterator(); iterator.hasNext(); writeWatchableObject(dataoutputstream, watchableobject))
        {
            watchableobject = (WatchableObject)iterator.next();
        }

        dataoutputstream.writeByte(127);
    }

    private static void writeWatchableObject(DataOutputStream dataoutputstream, WatchableObject watchableobject) throws IOException
    {
        int i = (watchableobject.getObjectType() << 5 | watchableobject.getDataValueId() & 0x1f) & 0xff;
        dataoutputstream.writeByte(i);
        switch(watchableobject.getObjectType())
        {
        case 0: // '\0'
            dataoutputstream.writeByte(((Byte)watchableobject.getObject()).byteValue());
            break;

        case 1: // '\001'
            dataoutputstream.writeShort(((Short)watchableobject.getObject()).shortValue());
            break;

        case 2: // '\002'
            dataoutputstream.writeInt(((Integer)watchableobject.getObject()).intValue());
            break;

        case 3: // '\003'
            dataoutputstream.writeFloat(((Float)watchableobject.getObject()).floatValue());
            break;

        case 4: // '\004'
            dataoutputstream.writeUTF((String)watchableobject.getObject());
            break;

        case 5: // '\005'
            ItemStack itemstack = (ItemStack)watchableobject.getObject();
            dataoutputstream.writeShort(itemstack.getItem().shiftedIndex);
            dataoutputstream.writeByte(itemstack.stackSize);
            dataoutputstream.writeShort(itemstack.getItemDamage());
            // fall through

        case 6: // '\006'
            ChunkCoordinates chunkcoordinates = (ChunkCoordinates)watchableobject.getObject();
            dataoutputstream.writeInt(chunkcoordinates.posX);
            dataoutputstream.writeInt(chunkcoordinates.posY);
            dataoutputstream.writeInt(chunkcoordinates.posZ);
            break;
        }
    }

    public static List readWatchableObjects(DataInputStream datainputstream) throws IOException
    {
        ArrayList arraylist = null;
        for(byte byte0 = datainputstream.readByte(); byte0 != 127; byte0 = datainputstream.readByte())
        {
            if(arraylist == null)
            {
                arraylist = new ArrayList();
            }
            int i = (byte0 & 0xe0) >> 5;
            int j = byte0 & 0x1f;
            WatchableObject watchableobject = null;
            switch(i)
            {
            case 0: // '\0'
                watchableobject = new WatchableObject(i, j, Byte.valueOf(datainputstream.readByte()));
                break;

            case 1: // '\001'
                watchableobject = new WatchableObject(i, j, Short.valueOf(datainputstream.readShort()));
                break;

            case 2: // '\002'
                watchableobject = new WatchableObject(i, j, Integer.valueOf(datainputstream.readInt()));
                break;

            case 3: // '\003'
                watchableobject = new WatchableObject(i, j, Float.valueOf(datainputstream.readFloat()));
                break;

            case 4: // '\004'
                watchableobject = new WatchableObject(i, j, datainputstream.readUTF());
                break;

            case 5: // '\005'
                short word0 = datainputstream.readShort();
                byte byte1 = datainputstream.readByte();
                short word1 = datainputstream.readShort();
                watchableobject = new WatchableObject(i, j, new ItemStack(word0, byte1, word1));
                // fall through

            case 6: // '\006'
                int k = datainputstream.readInt();
                int l = datainputstream.readInt();
                int i1 = datainputstream.readInt();
                watchableobject = new WatchableObject(i, j, new ChunkCoordinates(k, l, i1));
                break;
            }
            arraylist.add(watchableobject);
        }

        return arraylist;
    }

    static Class _mthclass$(String s)
    {
        try
        {
            return Class.forName(s);
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private static final HashMap dataTypes;
    private final Map watchedObjects = new HashMap();
    private boolean objectChanged;

    static 
    {
        dataTypes = new HashMap();
        dataTypes.put(java.lang.Byte.class, Integer.valueOf(0));
        dataTypes.put(java.lang.Short.class, Integer.valueOf(1));
        dataTypes.put(java.lang.Integer.class, Integer.valueOf(2));
        dataTypes.put(java.lang.Float.class, Integer.valueOf(3));
        dataTypes.put(java.lang.String.class, Integer.valueOf(4));
        dataTypes.put(ItemStack.class, Integer.valueOf(5));
        dataTypes.put(ChunkCoordinates.class, Integer.valueOf(6));
    }
}
