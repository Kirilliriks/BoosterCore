package net.minecraft.src.chunk;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.Empty2;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChunkFolderPattern
    implements FileFilter
{

    private ChunkFolderPattern()
    {
    }

    public boolean accept(File file)
    {
        if(file.isDirectory())
        {
            Matcher matcher = field_22214_a.matcher(file.getName());
            return matcher.matches();
        } else
        {
            return false;
        }
    }

    public ChunkFolderPattern(Empty2 empty2)
    {
        this();
    }

    public static final Pattern field_22214_a = Pattern.compile("[0-9a-z]|([0-9a-z][0-9a-z])");

}