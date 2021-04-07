package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.*;

public class Packet27 extends Packet
{

    public Packet27()
    {
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException
    {
        field_22035_a = datainputstream.readFloat();
        field_22034_b = datainputstream.readFloat();
        field_22037_e = datainputstream.readFloat();
        field_22036_f = datainputstream.readFloat();
        field_22039_c = datainputstream.readBoolean();
        field_22038_d = datainputstream.readBoolean();
    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeFloat(field_22035_a);
        dataoutputstream.writeFloat(field_22034_b);
        dataoutputstream.writeFloat(field_22037_e);
        dataoutputstream.writeFloat(field_22036_f);
        dataoutputstream.writeBoolean(field_22039_c);
        dataoutputstream.writeBoolean(field_22038_d);
    }

    public void processPacket(NetHandler nethandler)
    {
        nethandler.handleMovementTypePacket(this);
    }

    public int getPacketSize()
    {
        return 18;
    }

    public float func_22031_c()
    {
        return field_22035_a;
    }

    public float func_22029_d()
    {
        return field_22037_e;
    }

    public float func_22028_e()
    {
        return field_22034_b;
    }

    public float func_22033_f()
    {
        return field_22036_f;
    }

    public boolean func_22032_g()
    {
        return field_22039_c;
    }

    public boolean func_22030_h()
    {
        return field_22038_d;
    }

    private float field_22035_a;
    private float field_22034_b;
    private boolean field_22039_c;
    private boolean field_22038_d;
    private float field_22037_e;
    private float field_22036_f;
}
