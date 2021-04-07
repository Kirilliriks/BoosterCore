package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.IOException;
import java.util.Properties;

public class StringTranslate
{

    private StringTranslate()
    {
        field_25081_b = new Properties();
        try
        {
            field_25081_b.load((StringTranslate.class).getResourceAsStream("/lang/en_US.lang"));
            field_25081_b.load((StringTranslate.class).getResourceAsStream("/lang/stats_US.lang"));
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public static StringTranslate func_25079_a()
    {
        return field_25082_a;
    }

    public String func_25080_a(String s)
    {
        return field_25081_b.getProperty(s, s);
    }

    public String func_25078_a(String s, Object aobj[])
    {
        String s1 = field_25081_b.getProperty(s, s);
        return String.format(s1, aobj);
    }

    private static StringTranslate field_25082_a = new StringTranslate();
    private Properties field_25081_b;

}
