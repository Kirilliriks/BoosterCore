package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


public class MCHashTable2
{

    public MCHashTable2()
    {
        capacity = 12;
        hashArray = new HashEntry2[16];
    }

    private static int getHashedKey(long l)
    {
        return hash((int)(l ^ l >>> 32));
    }

    private static int hash(int i)
    {
        i ^= i >>> 20 ^ i >>> 12;
        return i ^ i >>> 7 ^ i >>> 4;
    }

    private static int getHashIndex(int i, int j)
    {
        return i & j - 1;
    }

    public Object getValueByKey(long l)
    {
        int i = getHashedKey(l);
        for(HashEntry2 hashentry2 = hashArray[getHashIndex(i, hashArray.length)]; hashentry2 != null; hashentry2 = hashentry2.nextEntry)
        {
            if(hashentry2.key == l)
            {
                return hashentry2.value;
            }
        }

        return null;
    }

    public void add(long l, Object obj)
    {
        int i = getHashedKey(l);
        int j = getHashIndex(i, hashArray.length);
        for(HashEntry2 hashentry2 = hashArray[j]; hashentry2 != null; hashentry2 = hashentry2.nextEntry)
        {
            if(hashentry2.key == l)
            {
                hashentry2.value = obj;
            }
        }

        field_950_e++;
        createKey(i, l, obj, j);
    }

    private void resizeTable(int i)
    {
        HashEntry2 ahashentry2[] = hashArray;
        int j = ahashentry2.length;
        if(j == 0x40000000)
        {
            capacity = 0x7fffffff;
            return;
        } else
        {
            HashEntry2 ahashentry2_1[] = new HashEntry2[i];
            copyHashTableTo(ahashentry2_1);
            hashArray = ahashentry2_1;
            capacity = (int)((float)i * percentUsable);
            return;
        }
    }

    private void copyHashTableTo(HashEntry2 ahashentry2[])
    {
        HashEntry2 ahashentry2_1[] = hashArray;
        int i = ahashentry2.length;
        for(int j = 0; j < ahashentry2_1.length; j++)
        {
            HashEntry2 hashentry2 = ahashentry2_1[j];
            if(hashentry2 == null)
            {
                continue;
            }
            ahashentry2_1[j] = null;
            do
            {
                HashEntry2 hashentry2_1 = hashentry2.nextEntry;
                int k = getHashIndex(hashentry2.field_1026_d, i);
                hashentry2.nextEntry = ahashentry2[k];
                ahashentry2[k] = hashentry2;
                hashentry2 = hashentry2_1;
            } while(hashentry2 != null);
        }

    }

    public Object remove(long l)
    {
        HashEntry2 hashentry2 = removeKey(l);
        return hashentry2 != null ? hashentry2.value : null;
    }

    final HashEntry2 removeKey(long l)
    {
        int i = getHashedKey(l);
        int j = getHashIndex(i, hashArray.length);
        HashEntry2 hashentry2 = hashArray[j];
        HashEntry2 hashentry2_1;
        HashEntry2 hashentry2_2;
        for(hashentry2_1 = hashentry2; hashentry2_1 != null; hashentry2_1 = hashentry2_2)
        {
            hashentry2_2 = hashentry2_1.nextEntry;
            if(hashentry2_1.key == l)
            {
                field_950_e++;
                numHashElements--;
                if(hashentry2 == hashentry2_1)
                {
                    hashArray[j] = hashentry2_2;
                } else
                {
                    hashentry2.nextEntry = hashentry2_2;
                }
                return hashentry2_1;
            }
            hashentry2 = hashentry2_1;
        }

        return hashentry2_1;
    }

    private void createKey(int i, long l, Object obj, int j)
    {
        HashEntry2 hashentry2 = hashArray[j];
        hashArray[j] = new HashEntry2(i, l, obj, hashentry2);
        if(numHashElements++ >= capacity)
        {
            resizeTable(2 * hashArray.length);
        }
    }

    static int getHashCode(long l)
    {
        return getHashedKey(l);
    }

    private transient HashEntry2 hashArray[];
    private transient int numHashElements;
    private int capacity;
    private final float percentUsable = 0.75F;
    private volatile transient int field_950_e;
}
