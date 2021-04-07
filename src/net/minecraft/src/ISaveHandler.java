package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public class ISaveHandler
{

    public ISaveHandler(MinecraftServer minecraftserver)
    {
        field_26693_a = new ArrayList();
        field_26692_b = new MCHashTable2();
        field_26696_c = new ArrayList();
        field_26695_d = minecraftserver;
    }

    public void func_26685_a()
    {
        for(int i = 0; i < field_26696_c.size(); i++)
        {
            ((PlayerInstance)field_26696_c.get(i)).onUpdate();
        }

        field_26696_c.clear();
    }

    private PlayerInstance func_26689_a(int i, int j, boolean flag)
    {
        long l = (long)i + 0x7fffffffL | (long)j + 0x7fffffffL << 32;
        PlayerInstance playerinstance = (PlayerInstance)field_26692_b.getValueByKey(l);
        if(playerinstance == null && flag)
        {
            playerinstance = new PlayerInstance(this, i, j);
            field_26692_b.add(l, playerinstance);
        }
        return playerinstance;
    }

    public void func_26683_a(int i, int j, int k)
    {
        int l = i >> 4;
        int i1 = k >> 4;
        PlayerInstance playerinstance = func_26689_a(l, i1, false);
        if(playerinstance != null)
        {
            playerinstance.markBlockNeedsUpdate(i & 0xf, j, k & 0xf);
        }
    }

    public void func_26682_a(EntityPlayerMP entityplayermp)
    {
        int i = (int)entityplayermp.posX >> 4;
        int j = (int)entityplayermp.posZ >> 4;
        entityplayermp.field_9155_d = entityplayermp.posX;
        entityplayermp.field_9154_e = entityplayermp.posZ;
        int k = 0;
        byte byte0 = 10;
        int l = 0;
        int i1 = 0;
        func_26689_a(i, j, true).addPlayer(entityplayermp);
        for(int j1 = 1; j1 <= byte0 * 2; j1++)
        {
            for(int l1 = 0; l1 < 2; l1++)
            {
                int ai[] = field_26694_e[k++ % 4];
                for(int i2 = 0; i2 < j1; i2++)
                {
                    l += ai[0];
                    i1 += ai[1];
                    func_26689_a(i + l, j + i1, true).addPlayer(entityplayermp);
                }

            }

        }

        k %= 4;
        for(int k1 = 0; k1 < byte0 * 2; k1++)
        {
            l += field_26694_e[k][0];
            i1 += field_26694_e[k][1];
            func_26689_a(i + l, j + i1, true).addPlayer(entityplayermp);
        }

        field_26693_a.add(entityplayermp);
    }

    public void func_26681_b(EntityPlayerMP entityplayermp)
    {
        int i = (int)entityplayermp.field_9155_d >> 4;
        int j = (int)entityplayermp.field_9154_e >> 4;
        for(int k = i - 10; k <= i + 10; k++)
        {
            for(int l = j - 10; l <= j + 10; l++)
            {
                PlayerInstance playerinstance = func_26689_a(k, l, false);
                if(playerinstance != null)
                {
                    playerinstance.removePlayer(entityplayermp);
                }
            }

        }

        field_26693_a.remove(entityplayermp);
    }

    private boolean func_26691_a(int i, int j, int k, int l)
    {
        int i1 = i - k;
        int j1 = j - l;
        if(i1 < -10 || i1 > 10)
        {
            return false;
        }
        return j1 >= -10 && j1 <= 10;
    }

    public void func_26688_c(EntityPlayerMP entityplayermp)
    {
        int i = (int)entityplayermp.posX >> 4;
        int j = (int)entityplayermp.posZ >> 4;
        double d = entityplayermp.field_9155_d - entityplayermp.posX;
        double d1 = entityplayermp.field_9154_e - entityplayermp.posZ;
        double d2 = d * d + d1 * d1;
        if(d2 < 64D)
        {
            return;
        }
        int k = (int)entityplayermp.field_9155_d >> 4;
        int l = (int)entityplayermp.field_9154_e >> 4;
        int i1 = i - k;
        int j1 = j - l;
        if(i1 == 0 && j1 == 0)
        {
            return;
        }
        for(int k1 = i - 10; k1 <= i + 10; k1++)
        {
            for(int l1 = j - 10; l1 <= j + 10; l1++)
            {
                if(!func_26691_a(k1, l1, k, l))
                {
                    func_26689_a(k1, l1, true).addPlayer(entityplayermp);
                }
                if(func_26691_a(k1 - i1, l1 - j1, i, j))
                {
                    continue;
                }
                PlayerInstance playerinstance = func_26689_a(k1 - i1, l1 - j1, false);
                if(playerinstance != null)
                {
                    playerinstance.removePlayer(entityplayermp);
                }
            }

        }

        entityplayermp.field_9155_d = entityplayermp.posX;
        entityplayermp.field_9154_e = entityplayermp.posZ;
    }

    public int func_26687_b()
    {
        return 144;
    }

    static MinecraftServer func_26686_a(ISaveHandler isavehandler)
    {
        return isavehandler.field_26695_d;
    }

    static MCHashTable2 func_26684_b(ISaveHandler isavehandler)
    {
        return isavehandler.field_26692_b;
    }

    static List func_26690_c(ISaveHandler isavehandler)
    {
        return isavehandler.field_26696_c;
    }

    private List field_26693_a;
    private MCHashTable2 field_26692_b;
    private List field_26696_c;
    private MinecraftServer field_26695_d;
    private final int field_26694_e[][] = {
        {
            1, 0
        }, {
            0, 1
        }, {
            -1, 0
        }, {
            0, -1
        }
    };
}
