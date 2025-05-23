package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FontAllowedCharacters
{

    public FontAllowedCharacters()
    {
    }

    private static String getAllowedCharacters()
    {
        String s = "";
        try
        {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((FontAllowedCharacters.class).getResourceAsStream("/net/minecraft/font.txt"), StandardCharsets.UTF_8));
            String s1 = "";
            do
            {
                String s2;
                if((s2 = bufferedreader.readLine()) == null)
                {
                    break;
                }
                if(!s2.startsWith("#"))
                {
                    s = (new StringBuilder()).append(s).append(s2).toString();
                }
            } while(true);
            bufferedreader.close();
        } catch(Exception exception) { }
        return s;
    }

    public static final String allowedCharacters = getAllowedCharacters();
    public static final char field_22175_b[] = {
        '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', 
        '<', '>', '|', '"', ':'
    };

}
