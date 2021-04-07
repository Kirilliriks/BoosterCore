package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.entity.EntityPainting;
import net.minecraft.src.network.NetHandler;
import net.minecraft.src.packet.Packet;

import java.io.*;

public class StatBasic extends Packet
{

    public StatBasic()
    {
    }

    public StatBasic(EntityPainting entitypainting)
    {
        field_26548_a = entitypainting.entityId;
        field_26547_b = entitypainting.xPosition;
        field_26549_c = entitypainting.yPosition;
        field_25063_d = entitypainting.zPosition;
        field_25062_e = entitypainting.direction;
        field_25061_f = entitypainting.art.title;
    }

    public void readPacketData(DataInputStream datainputstream)
    {
        try {
            field_25061_f = datainputstream.readUTF();
            field_26547_b = datainputstream.readInt();
            field_26549_c = datainputstream.readInt();
            field_25063_d = datainputstream.readInt();
            field_25062_e = datainputstream.readInt();
            field_26548_a = datainputstream.readInt();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void writePacketData(DataOutputStream dataoutputstream)
    {
        try {
            dataoutputstream.writeInt(field_26548_a);
            dataoutputstream.writeUTF(field_25061_f);
            dataoutputstream.writeInt(field_26547_b);
            dataoutputstream.writeInt(field_26549_c);
            dataoutputstream.writeInt(field_25063_d);
            dataoutputstream.writeInt(field_25062_e);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.func_26502_a(this);
    }

    public int getPacketSize()
    {
        return 24;
    }

    public int field_26548_a;
    public int field_26547_b;
    public int field_26549_c;
    public int field_25063_d;
    public int field_25062_e;
    public String field_25061_f;
}
