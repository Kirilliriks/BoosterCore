package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.block.Block;
import net.minecraft.src.block.BlockSand;
import net.minecraft.src.chunk.Chunk;
import net.minecraft.src.chunk.IChunkProvider;
import net.minecraft.src.entity.EntityPigZombie;
import net.minecraft.src.material.Material;
import net.minecraft.src.biome.MobSpawnerBase;
import net.minecraft.src.network.NetworkWriterThread;
import net.minecraft.src.packet.WorldGenPumpkin;

import java.util.Calendar;
import java.util.Random;

public class IProgressUpdate implements IChunkProvider {

    private Random field_26648_j;
    private NoiseGeneratorOctaves field_26647_k;
    private NoiseGeneratorOctaves field_26646_l;
    private NoiseGeneratorOctaves field_26645_m;
    private NoiseGeneratorOctaves field_26644_n;
    private NoiseGeneratorOctaves field_26643_o;
    public NoiseGeneratorOctaves field_26657_a;
    public NoiseGeneratorOctaves field_26656_b;
    public NoiseGeneratorOctaves field_26655_c;
    private World field_26642_p;
    private double field_26641_q[];
    private double[] field_26640_r;
    private double field_26639_s[];
    private double field_26638_t[];
    private MapGenBase field_26637_u;
    private MobSpawnerBase field_26636_v[];
    double field_26654_d[];
    double field_26653_e[];
    double field_26652_f[];
    double field_26651_g[];
    double field_26650_h[];
    int field_26649_i[][];
    private double field_26635_w[];

    public IProgressUpdate(World world, long l)
    {
        field_26640_r = new double[256];
        field_26639_s = new double[256];
        field_26638_t = new double[256];
        field_26637_u = new MapGenCaves();
        field_26649_i = new int[32][32];
        field_26642_p = world;
        field_26648_j = new Random(l);
        field_26647_k = new NoiseGeneratorOctaves(field_26648_j, 16);
        field_26646_l = new NoiseGeneratorOctaves(field_26648_j, 16);
        field_26645_m = new NoiseGeneratorOctaves(field_26648_j, 8);
        field_26644_n = new NoiseGeneratorOctaves(field_26648_j, 4);
        field_26643_o = new NoiseGeneratorOctaves(field_26648_j, 4);
        field_26657_a = new NoiseGeneratorOctaves(field_26648_j, 10);
        field_26656_b = new NoiseGeneratorOctaves(field_26648_j, 16);
        field_26655_c = new NoiseGeneratorOctaves(field_26648_j, 8);
    }

    public void func_26633_a(int i, int j, byte abyte0[], MobSpawnerBase amobspawnerbase[], double ad[])
    {
        byte byte0 = 4;
        byte byte1 = 64;
        int k = byte0 + 1;
        byte byte2 = 17;
        int l = byte0 + 1;
        field_26641_q = func_26634_a(field_26641_q, i * byte0, 0, j * byte0, k, byte2, l);
        for(int i1 = 0; i1 < byte0; i1++)
        {
            for(int j1 = 0; j1 < byte0; j1++)
            {
                for(int k1 = 0; k1 < 16; k1++)
                {
                    double d = 0.125D;
                    double d1 = field_26641_q[((i1 + 0) * l + (j1 + 0)) * byte2 + (k1 + 0)];
                    double d2 = field_26641_q[((i1 + 0) * l + (j1 + 1)) * byte2 + (k1 + 0)];
                    double d3 = field_26641_q[((i1 + 1) * l + (j1 + 0)) * byte2 + (k1 + 0)];
                    double d4 = field_26641_q[((i1 + 1) * l + (j1 + 1)) * byte2 + (k1 + 0)];
                    double d5 = (field_26641_q[((i1 + 0) * l + (j1 + 0)) * byte2 + (k1 + 1)] - d1) * d;
                    double d6 = (field_26641_q[((i1 + 0) * l + (j1 + 1)) * byte2 + (k1 + 1)] - d2) * d;
                    double d7 = (field_26641_q[((i1 + 1) * l + (j1 + 0)) * byte2 + (k1 + 1)] - d3) * d;
                    double d8 = (field_26641_q[((i1 + 1) * l + (j1 + 1)) * byte2 + (k1 + 1)] - d4) * d;
                    for(int l1 = 0; l1 < 8; l1++)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for(int i2 = 0; i2 < 4; i2++)
                        {
                            int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                            char c = '\200';
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for(int k2 = 0; k2 < 4; k2++)
                            {
                                double d17 = ad[(i1 * 4 + i2) * 16 + (j1 * 4 + k2)];
                                int l2 = 0;
                                if(k1 * 8 + l1 < byte1)
                                {
                                    if(d17 < 0.5D && k1 * 8 + l1 >= byte1 - 1)
                                    {
                                        l2 = Block.ice.blockID;
                                    } else
                                    {
                                        l2 = Block.waterStill.blockID;
                                    }
                                }
                                if(d15 > 0.0D)
                                {
                                    l2 = Block.stone.blockID;
                                }
                                abyte0[j2] = (byte)l2;
                                j2 += c;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }

                }

            }

        }

    }

    public void func_26632_a(int i, int j, byte abyte0[], MobSpawnerBase amobspawnerbase[])
    {
        byte byte0 = 64;
        double d = 0.03125D;
        field_26640_r = field_26644_n.generateNoiseOctaves(field_26640_r, i * 16, j * 16, 0.0D, 16, 16, 1, d, d, 1.0D);
        field_26639_s = field_26644_n.generateNoiseOctaves(field_26639_s, i * 16, 109.0134D, j * 16, 16, 1, 16, d, 1.0D, d);
        field_26638_t = field_26643_o.generateNoiseOctaves(field_26638_t, i * 16, j * 16, 0.0D, 16, 16, 1, d * 2D, d * 2D, d * 2D);
        for(int k = 0; k < 16; k++)
        {
            for(int l = 0; l < 16; l++)
            {
                MobSpawnerBase mobspawnerbase = amobspawnerbase[k + l * 16];
                boolean flag = field_26640_r[k + l * 16] + field_26648_j.nextDouble() * 0.20000000000000001D > 0.0D;
                boolean flag1 = field_26639_s[k + l * 16] + field_26648_j.nextDouble() * 0.20000000000000001D > 3D;
                int i1 = (int)(field_26638_t[k + l * 16] / 3D + 3D + field_26648_j.nextDouble() * 0.25D);
                int j1 = -1;
                byte byte1 = mobspawnerbase.topBlock;
                byte byte2 = mobspawnerbase.fillerBlock;
                for(int k1 = 127; k1 >= 0; k1--)
                {
                    int l1 = (l * 16 + k) * 128 + k1;
                    if(k1 <= 0 + field_26648_j.nextInt(5))
                    {
                        abyte0[l1] = (byte)Block.bedrock.blockID;
                        continue;
                    }
                    byte byte3 = abyte0[l1];
                    if(byte3 == 0)
                    {
                        j1 = -1;
                        continue;
                    }
                    if(byte3 != Block.stone.blockID)
                    {
                        continue;
                    }
                    if(j1 == -1)
                    {
                        if(i1 <= 0)
                        {
                            byte1 = 0;
                            byte2 = (byte)Block.stone.blockID;
                        } else
                        if(k1 >= byte0 - 4 && k1 <= byte0 + 1)
                        {
                            byte1 = mobspawnerbase.topBlock;
                            byte2 = mobspawnerbase.fillerBlock;
                            if(flag1)
                            {
                                byte1 = 0;
                            }
                            if(flag1)
                            {
                                byte2 = (byte)Block.gravel.blockID;
                            }
                            if(flag)
                            {
                                byte1 = (byte)Block.sand.blockID;
                            }
                            if(flag)
                            {
                                byte2 = (byte)Block.sand.blockID;
                            }
                        }
                        if(k1 < byte0 && byte1 == 0)
                        {
                            byte1 = (byte)Block.waterStill.blockID;
                        }
                        j1 = i1;
                        if(k1 >= byte0 - 1)
                        {
                            abyte0[l1] = byte1;
                        } else
                        {
                            abyte0[l1] = byte2;
                        }
                        continue;
                    }
                    if(j1 <= 0)
                    {
                        continue;
                    }
                    j1--;
                    abyte0[l1] = byte2;
                    if(j1 == 0 && byte2 == Block.sand.blockID)
                    {
                        j1 = field_26648_j.nextInt(4);
                        byte2 = (byte)Block.sandStone.blockID;
                    }
                }

            }

        }

    }

    public Chunk loadChunk(int i, int j)
    {
        return provideChunk(i, j);
    }

    public Chunk provideChunk(int i, int j)
    {
        field_26648_j.setSeed((long)i * 0x4f9939f508L + (long)j * 0x1ef1565bd5L);
        byte abyte0[] = new byte[32768];
        Chunk chunk = new Chunk(field_26642_p, abyte0, i, j);
        field_26636_v = field_26642_p.func_26662_a().loadBlockGeneratorData(field_26636_v, i * 16, j * 16, 16, 16);
        double ad[] = field_26642_p.func_26662_a().x;
        func_26633_a(i, j, abyte0, field_26636_v, ad);
        func_26632_a(i, j, abyte0, field_26636_v);
        field_26637_u.func_667_a(this, field_26642_p, i, j, abyte0);
        chunk.func_353_b();
        return chunk;
    }

    private double[] func_26634_a(double ad[], int i, int j, int k, int l, int i1, int j1)
    {
        if(ad == null)
        {
            ad = new double[l * i1 * j1];
        }
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        double ad1[] = field_26642_p.func_26662_a().x;
        double ad2[] = field_26642_p.func_26662_a().y;
        field_26651_g = field_26657_a.func_4103_a(field_26651_g, i, k, l, j1, 1.121D, 1.121D, 0.5D);
        field_26650_h = field_26656_b.func_4103_a(field_26650_h, i, k, l, j1, 200D, 200D, 0.5D);
        field_26654_d = field_26645_m.generateNoiseOctaves(field_26654_d, i, j, k, l, i1, j1, d / 80D, d1 / 160D, d / 80D);
        field_26653_e = field_26647_k.generateNoiseOctaves(field_26653_e, i, j, k, l, i1, j1, d, d1, d);
        field_26652_f = field_26646_l.generateNoiseOctaves(field_26652_f, i, j, k, l, i1, j1, d, d1, d);
        int k1 = 0;
        int l1 = 0;
        int i2 = 16 / l;
        for(int j2 = 0; j2 < l; j2++)
        {
            int k2 = j2 * i2 + i2 / 2;
            for(int l2 = 0; l2 < j1; l2++)
            {
                int i3 = l2 * i2 + i2 / 2;
                double d2 = ad1[k2 * 16 + i3];
                double d3 = ad2[k2 * 16 + i3] * d2;
                double d4 = 1.0D - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1.0D - d4;
                double d5 = (field_26651_g[l1] + 256D) / 512D;
                d5 *= d4;
                if(d5 > 1.0D)
                {
                    d5 = 1.0D;
                }
                double d6 = field_26650_h[l1] / 8000D;
                if(d6 < 0.0D)
                {
                    d6 = -d6 * 0.29999999999999999D;
                }
                d6 = d6 * 3D - 2D;
                if(d6 < 0.0D)
                {
                    d6 /= 2D;
                    if(d6 < -1D)
                    {
                        d6 = -1D;
                    }
                    d6 /= 1.3999999999999999D;
                    d6 /= 2D;
                    d5 = 0.0D;
                } else
                {
                    if(d6 > 1.0D)
                    {
                        d6 = 1.0D;
                    }
                    d6 /= 8D;
                }
                if(d5 < 0.0D)
                {
                    d5 = 0.0D;
                }
                d5 += 0.5D;
                d6 = (d6 * (double)i1) / 16D;
                double d7 = (double)i1 / 2D + d6 * 4D;
                l1++;
                for(int j3 = 0; j3 < i1; j3++)
                {
                    double d8 = 0.0D;
                    double d9 = (((double)j3 - d7) * 12D) / d5;
                    if(d9 < 0.0D)
                    {
                        d9 *= 4D;
                    }
                    double d10 = field_26653_e[k1] / 512D;
                    double d11 = field_26652_f[k1] / 512D;
                    double d12 = (field_26654_d[k1] / 10D + 1.0D) / 2D;
                    if(d12 < 0.0D)
                    {
                        d8 = d10;
                    } else
                    if(d12 > 1.0D)
                    {
                        d8 = d11;
                    } else
                    {
                        d8 = d10 + (d11 - d10) * d12;
                    }
                    d8 -= d9;
                    if(j3 > i1 - 4)
                    {
                        double d13 = (float)(j3 - (i1 - 4)) / 3F;
                        d8 = d8 * (1.0D - d13) + -10D * d13;
                    }
                    ad[k1] = d8;
                    k1++;
                }

            }

        }

        return ad;
    }

    public boolean chunkExists(int i, int j)
    {
        return true;
    }

    public void populate(IChunkProvider ichunkprovider, int i, int j)
    {
        BlockSand.fallInstantly = true;
        int k = i * 16;
        int l = j * 16;
        MobSpawnerBase mobspawnerbase = field_26642_p.func_26662_a().func_4067_a(k + 16, l + 16);
        field_26648_j.setSeed(field_26642_p.func_22079_j());
        long l1 = (field_26648_j.nextLong() / 2L) * 2L + 1L;
        long l2 = (field_26648_j.nextLong() / 2L) * 2L + 1L;
        field_26648_j.setSeed((long)i * l1 + (long)j * l2 ^ field_26642_p.func_22079_j());
        double d = 0.25D;
        if(field_26648_j.nextInt(4) == 0)
        {
            int i1 = k + field_26648_j.nextInt(16) + 8;
            int l4 = field_26648_j.nextInt(128);
            int i8 = l + field_26648_j.nextInt(16) + 8;
            (new WorldGenLakes(Block.waterStill.blockID)).generate(field_26642_p, field_26648_j, i1, l4, i8);
        }
        if(field_26648_j.nextInt(8) == 0)
        {
            int j1 = k + field_26648_j.nextInt(16) + 8;
            int i5 = field_26648_j.nextInt(field_26648_j.nextInt(120) + 8);
            int j8 = l + field_26648_j.nextInt(16) + 8;
            if(i5 < 64 || field_26648_j.nextInt(10) == 0)
            {
                (new WorldGenLakes(Block.lavaStill.blockID)).generate(field_26642_p, field_26648_j, j1, i5, j8);
            }
        }
        for(int k1 = 0; k1 < 8; k1++)
        {
            int j5 = k + field_26648_j.nextInt(16) + 8;
            int k8 = field_26648_j.nextInt(128);
            int i13 = l + field_26648_j.nextInt(16) + 8;
            (new WorldGenDungeons()).generate(field_26642_p, field_26648_j, j5, k8, i13);
        }

        for(int i2 = 0; i2 < 10; i2++)
        {
            int k5 = k + field_26648_j.nextInt(16);
            int l8 = field_26648_j.nextInt(128);
            int j13 = l + field_26648_j.nextInt(16);
            (new WorldGenClay(32)).generate(field_26642_p, field_26648_j, k5, l8, j13);
        }

        for(int j2 = 0; j2 < 20; j2++)
        {
            int l5 = k + field_26648_j.nextInt(16);
            int i9 = field_26648_j.nextInt(128);
            int k13 = l + field_26648_j.nextInt(16);
            (new WorldGenMinable(Block.dirt.blockID, 32)).generate(field_26642_p, field_26648_j, l5, i9, k13);
        }

        for(int k2 = 0; k2 < 10; k2++)
        {
            int i6 = k + field_26648_j.nextInt(16);
            int j9 = field_26648_j.nextInt(128);
            int l13 = l + field_26648_j.nextInt(16);
            (new WorldGenMinable(Block.gravel.blockID, 32)).generate(field_26642_p, field_26648_j, i6, j9, l13);
        }

        for(int i3 = 0; i3 < 20; i3++)
        {
            int j6 = k + field_26648_j.nextInt(16);
            int k9 = field_26648_j.nextInt(128);
            int i14 = l + field_26648_j.nextInt(16);
            (new WorldGenMinable(Block.oreCoal.blockID, 16)).generate(field_26642_p, field_26648_j, j6, k9, i14);
        }

        for(int j3 = 0; j3 < 20; j3++)
        {
            int k6 = k + field_26648_j.nextInt(16);
            int l9 = field_26648_j.nextInt(64);
            int j14 = l + field_26648_j.nextInt(16);
            (new WorldGenMinable(Block.oreIron.blockID, 8)).generate(field_26642_p, field_26648_j, k6, l9, j14);
        }

        for(int k3 = 0; k3 < 2; k3++)
        {
            int l6 = k + field_26648_j.nextInt(16);
            int i10 = field_26648_j.nextInt(32);
            int k14 = l + field_26648_j.nextInt(16);
            (new WorldGenMinable(Block.oreGold.blockID, 8)).generate(field_26642_p, field_26648_j, l6, i10, k14);
        }

        for(int l3 = 0; l3 < 8; l3++)
        {
            int i7 = k + field_26648_j.nextInt(16);
            int j10 = field_26648_j.nextInt(16);
            int l14 = l + field_26648_j.nextInt(16);
            (new WorldGenMinable(Block.oreRedstone.blockID, 7)).generate(field_26642_p, field_26648_j, i7, j10, l14);
        }

        for(int i4 = 0; i4 < 1; i4++)
        {
            int j7 = k + field_26648_j.nextInt(16);
            int k10 = field_26648_j.nextInt(16);
            int i15 = l + field_26648_j.nextInt(16);
            (new WorldGenMinable(Block.oreDiamond.blockID, 7)).generate(field_26642_p, field_26648_j, j7, k10, i15);
        }

        for(int j4 = 0; j4 < 1; j4++)
        {
            int k7 = k + field_26648_j.nextInt(16);
            int l10 = field_26648_j.nextInt(16) + field_26648_j.nextInt(16);
            int j15 = l + field_26648_j.nextInt(16);
            (new WorldGenMinable(Block.oreLapis.blockID, 6)).generate(field_26642_p, field_26648_j, k7, l10, j15);
        }

        d = 0.5D;
        int k4 = (int)((field_26655_c.func_647_a((double)k * d, (double)l * d) / 8D + field_26648_j.nextDouble() * 4D + 4D) / 3D);
        int l7 = 0;
        if(field_26648_j.nextInt(10) == 0)
        {
            l7++;
        }
        if(mobspawnerbase == MobSpawnerBase.forest)
        {
            l7 += k4 + 5;
        }
        if(mobspawnerbase == MobSpawnerBase.rainforest)
        {
            l7 += k4 + 5;
        }
        if(mobspawnerbase == MobSpawnerBase.seasonalForest)
        {
            l7 += k4 + 2;
        }
        if(mobspawnerbase == MobSpawnerBase.taiga)
        {
            l7 += k4 + 5;
        }
        if(mobspawnerbase == MobSpawnerBase.desert)
        {
            l7 -= 20;
        }
        if(mobspawnerbase == MobSpawnerBase.tundra)
        {
            l7 -= 20;
        }
        if(mobspawnerbase == MobSpawnerBase.plains)
        {
            l7 -= 20;
        }
        for(int i11 = 0; i11 < l7; i11++)
        {
            int k15 = k + field_26648_j.nextInt(16) + 8;
            int j18 = l + field_26648_j.nextInt(16) + 8;
            WorldGenerator worldgenerator = mobspawnerbase.getRandomWorldGenForTrees(field_26648_j);
            worldgenerator.func_420_a(1.0D, 1.0D, 1.0D);
            worldgenerator.generate(field_26642_p, field_26648_j, k15, field_26642_p.getHeightValue(k15, j18), j18);
        }

        for(int j11 = 0; j11 < 2; j11++)
        {
            int l15 = k + field_26648_j.nextInt(16) + 8;
            int k18 = field_26648_j.nextInt(128);
            int j21 = l + field_26648_j.nextInt(16) + 8;
            (new WorldGenFlowers(Block.plantYellow.blockID)).generate(field_26642_p, field_26648_j, l15, k18, j21);
        }

        if(field_26648_j.nextInt(2) == 0)
        {
            int k11 = k + field_26648_j.nextInt(16) + 8;
            int i16 = field_26648_j.nextInt(128);
            int l18 = l + field_26648_j.nextInt(16) + 8;
            (new WorldGenFlowers(Block.plantRed.blockID)).generate(field_26642_p, field_26648_j, k11, i16, l18);
        }
        if(field_26648_j.nextInt(4) == 0)
        {
            int l11 = k + field_26648_j.nextInt(16) + 8;
            int j16 = field_26648_j.nextInt(128);
            int i19 = l + field_26648_j.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(field_26642_p, field_26648_j, l11, j16, i19);
        }
        if(field_26648_j.nextInt(8) == 0)
        {
            int i12 = k + field_26648_j.nextInt(16) + 8;
            int k16 = field_26648_j.nextInt(128);
            int j19 = l + field_26648_j.nextInt(16) + 8;
            (new WorldGenFlowers(Block.mushroomRed.blockID)).generate(field_26642_p, field_26648_j, i12, k16, j19);
        }
        for(int j12 = 0; j12 < 10; j12++)
        {
            int l16 = k + field_26648_j.nextInt(16) + 8;
            int k19 = field_26648_j.nextInt(128);
            int k21 = l + field_26648_j.nextInt(16) + 8;
            (new WorldGenReed()).generate(field_26642_p, field_26648_j, l16, k19, k21);
        }

        if(field_26648_j.nextInt(32) == 0)
        {
            int k12 = k + field_26648_j.nextInt(16) + 8;
            int i17 = field_26648_j.nextInt(128);
            int l19 = l + field_26648_j.nextInt(16) + 8;
            (new WorldGenPumpkin()).generate(field_26642_p, field_26648_j, k12, i17, l19);
        }
        int l12 = 0;
        if(mobspawnerbase == MobSpawnerBase.desert)
        {
            l12 += 10;
        }
        for(int j17 = 0; j17 < l12; j17++)
        {
            int i20 = k + field_26648_j.nextInt(16) + 8;
            int l21 = field_26648_j.nextInt(128);
            int i23 = l + field_26648_j.nextInt(16) + 8;
            (new WorldGenCactus()).generate(field_26642_p, field_26648_j, i20, l21, i23);
        }

        for(int k17 = 0; k17 < 50; k17++)
        {
            int j20 = k + field_26648_j.nextInt(16) + 8;
            int i22 = field_26648_j.nextInt(field_26648_j.nextInt(120) + 8);
            int j23 = l + field_26648_j.nextInt(16) + 8;
            (new NetworkWriterThread(Block.waterMoving.blockID)).generate(field_26642_p, field_26648_j, j20, i22, j23);
        }

        for(int l17 = 0; l17 < 20; l17++)
        {
            int k20 = k + field_26648_j.nextInt(16) + 8;
            int j22 = field_26648_j.nextInt(field_26648_j.nextInt(field_26648_j.nextInt(112) + 8) + 8);
            int k23 = l + field_26648_j.nextInt(16) + 8;
            (new NetworkWriterThread(Block.lavaMoving.blockID)).generate(field_26642_p, field_26648_j, k20, j22, k23);
        }

        field_26635_w = field_26642_p.func_26662_a().getTemperatures(field_26635_w, k + 8, l + 8, 16, 16);
        for(int i18 = k + 8; i18 < k + 8 + 16; i18++)
        {
            for(int l20 = l + 8; l20 < l + 8 + 16; l20++)
            {
                int k22 = i18 - (k + 8);
                int l23 = l20 - (l + 8);
                int j24 = field_26642_p.findTopSolidBlock(i18, l20);
                double d1 = field_26635_w[k22 * 16 + l23] - ((double)(j24 - 64) / 64D) * 0.29999999999999999D;
                if(d1 < 0.5D && j24 > 0 && j24 < 128 && field_26642_p.isAirBlock(i18, j24, l20) && field_26642_p.getBlockMaterial(i18, j24 - 1, l20).getIsSolid() && field_26642_p.getBlockMaterial(i18, j24 - 1, l20) != Material.ice)
                {
                    field_26642_p.setBlockWithNotify(i18, j24, l20, Block.snow.blockID);
                }
            }

        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        if(calendar.get(2) == 3 && calendar.get(5) == 1)
        {
            int i21 = k + field_26648_j.nextInt(16) + 8;
            int l22 = field_26648_j.nextInt(128);
            int i24 = l + field_26648_j.nextInt(16) + 8;
            if(field_26642_p.getBlockId(i21, l22, i24) == 0 && field_26642_p.isBlockOpaqueCube(i21, l22 - 1, i24))
            {
                System.out.println("added a chest!!");
                field_26642_p.setBlockWithNotify(i21, l22, i24, Block.lockedChest.blockID);
            }
        }
        BlockSand.fallInstantly = false;
    }

    public boolean func_26631_a(boolean flag, EntityPigZombie entitypigzombie)
    {
        return true;
    }

    public boolean func_361_a()
    {
        return false;
    }

    public boolean func_364_b()
    {
        return true;
    }
}
