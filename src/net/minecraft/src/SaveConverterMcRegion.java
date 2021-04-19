package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.chunk.ChunkFilePattern;
import net.minecraft.src.chunk.ChunkFolderPattern;
import net.minecraft.src.entity.IProgressUpdate;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class SaveConverterMcRegion extends SaveFormatOld
{

    public SaveConverterMcRegion(File file)
    {
        super(file);
    }

    public IDataManager func_26730_a(String s, boolean flag)
    {
        return new SaveOldDir(field_22106_a, s, flag);
    }

    public boolean func_22102_a(String s)
    {
        WorldInfo worldinfo = func_22103_b(s);
        return worldinfo != null && worldinfo.getVersion() == 0;
    }

    public boolean func_26729_a(String s, IProgressUpdate entitypigzombie)
    {
        entitypigzombie.setLoadingProgress(0);
        ArrayList arraylist = new ArrayList();
        ArrayList arraylist1 = new ArrayList();
        ArrayList arraylist2 = new ArrayList();
        ArrayList arraylist3 = new ArrayList();
        File file = new File(field_22106_a, s);
        File file1 = new File(file, "DIM-1");
        System.out.println("Scanning folders...");
        func_22108_a(file, arraylist, arraylist1);
        if(file1.exists())
        {
            func_22108_a(file1, arraylist2, arraylist3);
        }
        int i = arraylist.size() + arraylist2.size() + arraylist1.size() + arraylist3.size();
        System.out.println((new StringBuilder()).append("Total conversion count is ").append(i).toString());
        func_26731_a(file, arraylist, 0, i, entitypigzombie);
        func_26731_a(file1, arraylist2, arraylist.size(), i, entitypigzombie);
        WorldInfo worldinfo = func_22103_b(s);
        worldinfo.setVersion(19132);
        IDataManager worldgentaiga2 = func_26730_a(s, false);
        worldgentaiga2.func_22094_a(worldinfo);
        func_26732_a(arraylist1, arraylist.size() + arraylist2.size(), i, entitypigzombie);
        if(file1.exists())
        {
            func_26732_a(arraylist3, arraylist.size() + arraylist2.size() + arraylist1.size(), i, entitypigzombie);
        }
        return true;
    }

    private void func_22108_a(File file, ArrayList arraylist, ArrayList arraylist1)
    {
        ChunkFolderPattern chunkfolderpattern = new ChunkFolderPattern(null);
        ChunkFilePattern chunkfilepattern = new ChunkFilePattern(null);
        File afile[] = file.listFiles(chunkfolderpattern);
        File afile1[] = afile;
        int i = afile1.length;
        for(int j = 0; j < i; j++)
        {
            File file1 = afile1[j];
            arraylist1.add(file1);
            File afile2[] = file1.listFiles(chunkfolderpattern);
            File afile3[] = afile2;
            int k = afile3.length;
            for(int l = 0; l < k; l++)
            {
                File file2 = afile3[l];
                File afile4[] = file2.listFiles(chunkfilepattern);
                File afile5[] = afile4;
                int i1 = afile5.length;
                for(int j1 = 0; j1 < i1; j1++)
                {
                    File file3 = afile5[j1];
                    arraylist.add(new FileMatcher(file3));
                }

            }

        }

    }

    private void func_26731_a(File file, ArrayList arraylist, int i, int j, IProgressUpdate entitypigzombie)
    {
        Collections.sort(arraylist);
        byte abyte0[] = new byte[4096];
        int i1;
        for(Iterator iterator = arraylist.iterator(); iterator.hasNext(); entitypigzombie.setLoadingProgress(i1))
        {
            FileMatcher filematcher = (FileMatcher)iterator.next();
            int k = filematcher.func_22205_b();
            int l = filematcher.func_22204_c();
            RegionFile regionfile = RegionFileCache.func_22123_a(file, k, l);
            if(!regionfile.isChunkSaved(k & 0x1f, l & 0x1f))
            {
                try
                {
                    DataInputStream datainputstream = new DataInputStream(new GZIPInputStream(new FileInputStream(filematcher.func_22207_a())));
                    DataOutputStream dataoutputstream = regionfile.getChunkDataOutputStream(k & 0x1f, l & 0x1f);
                    for(int j1 = 0; (j1 = datainputstream.read(abyte0)) != -1;)
                    {
                        dataoutputstream.write(abyte0, 0, j1);
                    }

                    dataoutputstream.close();
                    datainputstream.close();
                }
                catch(IOException ioexception)
                {
                    ioexception.printStackTrace();
                }
            }
            i++;
            i1 = (int)Math.round((100D * (double)i) / (double)j);
        }

        RegionFileCache.func_22122_a();
    }

    private void func_26732_a(ArrayList arraylist, int i, int j, IProgressUpdate entitypigzombie)
    {
        int k;
        for(Iterator iterator = arraylist.iterator(); iterator.hasNext(); entitypigzombie.setLoadingProgress(k))
        {
            File file = (File)iterator.next();
            File afile[] = file.listFiles();
            func_22104_a(afile);
            file.delete();
            i++;
            k = (int)Math.round((100D * (double)i) / (double)j);
        }

    }
}
