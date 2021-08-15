package net.minecraft.src.entity;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.*;
import net.minecraft.src.block.BlockStep;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.nbt.NBTTagCompound;
import net.minecraft.src.world.World;

import java.util.*;

public class EntityWolf extends EntityAnimals
{

    public EntityWolf(World world)
    {
        super(world);
        field_25039_a = false;
        texture = "/mob/wolf.png";
        setSize(0.8F, 0.8F);
        moveSpeed = 1.1F;
        health = 8;
    }

    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(16, Byte.valueOf((byte)0));
        dataWatcher.addObject(17, "");
        dataWatcher.addObject(18, new Integer(health));
    }

    protected boolean func_25017_l()
    {
        return false;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Angry", func_25028_x());
        nbttagcompound.setBoolean("Sitting", func_25036_w());
        if(func_25034_v() == null)
        {
            nbttagcompound.setString("Owner", "");
        } else
        {
            nbttagcompound.setString("Owner", func_25034_v());
        }
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        func_25037_c(nbttagcompound.getBoolean("Angry"));
        func_25031_b(nbttagcompound.getBoolean("Sitting"));
        String s = nbttagcompound.getString("Owner");
        if(s.length() > 0)
        {
            func_25029_a(s);
            func_25032_d(true);
        }
    }

    protected boolean func_25020_s()
    {
        return !func_25030_y();
    }

    protected String getLivingSound()
    {
        if(func_25028_x())
        {
            return "mob.wolf.growl";
        }
        if(rand.nextInt(3) == 0)
        {
            if(func_25030_y() && health < 10)
            {
                return "mob.wolf.whine";
            } else
            {
                return "mob.wolf.panting";
            }
        } else
        {
            return "mob.wolf.bark";
        }
    }

    protected String getHurtSound()
    {
        return "mob.wolf.hurt";
    }

    protected String getDeathSound()
    {
        return "mob.wolf.death";
    }

    protected float getSoundVolume()
    {
        return 0.4F;
    }

    protected int getDropItemId()
    {
        return -1;
    }

    protected void updatePlayerActionState()
    {
        super.updatePlayerActionState();
        if(!hasAttacked && !func_25023_z() && func_25030_y())
        {
            EntityPlayer entityplayer = worldObj.getPlayerEntityByName(func_25034_v());
            if(entityplayer != null)
            {
                float f = entityplayer.getDistanceToEntity(this);
                if(f > 5F)
                {
                    func_25035_b(entityplayer, f);
                }
            } else
            if(!handleWaterMovement())
            {
                func_25031_b(true);
            }
        } else
        if(playerToAttack == null && !func_25023_z() && !func_25030_y() && worldObj.rand.nextInt(100) == 0)
        {
            List list = worldObj.getEntitiesWithinAABB(EntitySheep.class, AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
            if(!list.isEmpty())
            {
                func_25025_c((Entity)list.get(worldObj.rand.nextInt(list.size())));
            }
        }
        if(handleWaterMovement())
        {
            func_25031_b(false);
        }
        if(!worldObj.singleplayerWorld)
        {
            dataWatcher.updateObject(18, Integer.valueOf(health));
        }
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        field_25039_a = false;
        if(func_26581_O() && !func_25023_z() && !func_25028_x())
        {
            Entity entity = func_26580_P();
            if(entity instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entity;
                ItemStack itemstack = entityplayer.inventory.getCurrentItem();
                if(itemstack != null)
                {
                    if(!func_25030_y() && itemstack.itemID == Item.bone.shiftedIndex)
                    {
                        field_25039_a = true;
                    } else
                    if(func_25030_y() && (Item.itemsList[itemstack.itemID] instanceof BlockStep))
                    {
                        field_25039_a = ((BlockStep)Item.itemsList[itemstack.itemID]).func_26517_k();
                    }
                }
            }
        }
        if(!field_9112_aN && field_25043_f && !field_25042_g && !func_25023_z())
        {
            field_25042_g = true;
            field_25041_h = 0.0F;
            field_25040_i = 0.0F;
            worldObj.func_9206_a(this, (byte)8);
        }
    }

    public void onUpdate()
    {
        super.onUpdate();
        field_25044_c = field_25038_b;
        if(field_25039_a)
        {
            field_25038_b = field_25038_b + (1.0F - field_25038_b) * 0.4F;
        } else
        {
            field_25038_b = field_25038_b + (0.0F - field_25038_b) * 0.4F;
        }
        if(field_25039_a)
        {
            numTicksToChaseTarget = 10;
        }
        if(handleWaterMovement())
        {
            field_25043_f = true;
            field_25042_g = false;
            field_25041_h = 0.0F;
            field_25040_i = 0.0F;
        } else
        if((field_25043_f || field_25042_g) && field_25042_g)
        {
            if(field_25041_h == 0.0F)
            {
                worldObj.playSoundAtEntity(this, "mob.wolf.shake", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }
            field_25040_i = field_25041_h;
            field_25041_h += 0.05F;
            if(field_25040_i >= 2.0F)
            {
                field_25043_f = false;
                field_25042_g = false;
                field_25040_i = 0.0F;
                field_25041_h = 0.0F;
            }
            if(field_25041_h > 0.4F)
            {
                float f = (float)boundingBox.minY;
                int i = (int)(MathHelper.sin((field_25041_h - 0.4F) * 3.141593F) * 7F);
                for(int j = 0; j < i; j++)
                {
                    float f1 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
                    float f2 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
                    worldObj.spawnParticle("splash", posX + (double)f1, f + 0.8F, posZ + (double)f2, motionX, motionY, motionZ);
                }

            }
        }
    }

    public float getEyeHeight()
    {
        return height * 0.8F;
    }

    protected int func_25018_n_()
    {
        if(func_25036_w())
        {
            return 20;
        } else
        {
            return super.func_25018_n_();
        }
    }

    private void func_25035_b(Entity entity, float f)
    {
        PathEntity pathentity = worldObj.getPathToEntity(this, entity, 16F);
        if(pathentity == null && f > 12F)
        {
            int i = MathHelper.floor_double(entity.posX) - 2;
            int j = MathHelper.floor_double(entity.posZ) - 2;
            int k = MathHelper.floor_double(entity.boundingBox.minY);
            for(int l = 0; l <= 4; l++)
            {
                for(int i1 = 0; i1 <= 4; i1++)
                {
                    if((l < 1 || i1 < 1 || l > 3 || i1 > 3) && worldObj.isBlockOpaqueCube(i + l, k - 1, j + i1) && !worldObj.isBlockOpaqueCube(i + l, k, j + i1) && !worldObj.isBlockOpaqueCube(i + l, k + 1, j + i1))
                    {
                        setLocationAndAngles((float)(i + l) + 0.5F, k, (float)(j + i1) + 0.5F, rotationYaw, rotationPitch);
                        return;
                    }
                }

            }

        } else
        {
            func_25022_a(pathentity);
        }
    }

    protected boolean func_25026_u()
    {
        return func_25036_w() || field_25042_g;
    }

    public boolean attackEntityFrom(Entity entity, int i)
    {
        func_25031_b(false);
        if(entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
        {
            i = (i + 1) / 2;
        }
        if(super.attackEntityFrom(entity, i))
        {
            if(!func_25030_y() && !func_25028_x())
            {
                if(entity instanceof EntityPlayer)
                {
                    func_25037_c(true);
                    playerToAttack = entity;
                }
                if((entity instanceof EntityArrow) && ((EntityArrow)entity).owner != null)
                {
                    entity = ((EntityArrow)entity).owner;
                }
                if(entity instanceof EntityLiving)
                {
                    List list = worldObj.getEntitiesWithinAABB(EntityWolf.class, AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
                    Iterator iterator = list.iterator();
                    do
                    {
                        if(!iterator.hasNext())
                        {
                            break;
                        }
                        Entity entity1 = (Entity)iterator.next();
                        EntityWolf entitywolf = (EntityWolf)entity1;
                        if(!entitywolf.func_25030_y() && entitywolf.playerToAttack == null)
                        {
                            entitywolf.playerToAttack = entity;
                            if(entity instanceof EntityPlayer)
                            {
                                entitywolf.func_25037_c(true);
                            }
                        }
                    } while(true);
                }
            } else
            if(entity != this && entity != null)
            {
                if(func_25030_y() && (entity instanceof EntityPlayer) && ((EntityPlayer)entity).username.equals(func_25034_v()))
                {
                    return true;
                }
                playerToAttack = entity;
            }
            return true;
        } else
        {
            return false;
        }
    }

    protected Entity findPlayerToAttack()
    {
        if(func_25028_x())
        {
            return worldObj.getClosestPlayerToEntity(this, 16D);
        } else
        {
            return null;
        }
    }

    protected void attackEntity(Entity entity, float f)
    {
        if(f > 2.0F && f < 6F && rand.nextInt(10) == 0)
        {
            if(onGround)
            {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
                motionX = (d / (double)f1) * 0.5D * 0.80000001192092896D + motionX * 0.20000000298023224D;
                motionZ = (d1 / (double)f1) * 0.5D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
                motionY = 0.40000000596046448D;
            }
        } else
        if((double)f < 1.5D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            attackTime = 20;
            byte byte0 = 2;
            if(func_25030_y())
            {
                byte0 = 4;
            }
            entity.attackEntityFrom(this, byte0);
        }
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if(!func_25030_y())
        {
            if(itemstack != null && itemstack.itemID == Item.bone.shiftedIndex && !func_25028_x())
            {
                itemstack.stackSize--;
                if(itemstack.stackSize <= 0)
                {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
                if(!worldObj.singleplayerWorld)
                {
                    if(rand.nextInt(3) == 0)
                    {
                        func_25032_d(true);
                        func_25022_a(null);
                        func_25031_b(true);
                        health = 20;
                        func_25029_a(entityplayer.username);
                        func_25033_a(true);
                        worldObj.func_9206_a(this, (byte)7);
                    } else
                    {
                        func_25033_a(false);
                        worldObj.func_9206_a(this, (byte)6);
                    }
                }
                return true;
            }
        } else
        {
            if(itemstack != null && (Item.itemsList[itemstack.itemID] instanceof BlockStep))
            {
                BlockStep blockstep = (BlockStep)Item.itemsList[itemstack.itemID];
                if(blockstep.func_26517_k() && dataWatcher.func_25075_b(18) < 20)
                {
                    itemstack.stackSize--;
                    if(itemstack.stackSize <= 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }
                    heal(((BlockStep)Item.porkRaw).func_26516_j());
                    return true;
                }
            }
            if(entityplayer.username.equals(func_25034_v()))
            {
                if(!worldObj.singleplayerWorld)
                {
                    func_25031_b(!func_25036_w());
                    isJumping = false;
                    func_25022_a(null);
                }
                return true;
            }
        }
        return false;
    }

    void func_25033_a(boolean flag)
    {
        String s = "heart";
        if(!flag)
        {
            s = "smoke";
        }
        for(int i = 0; i < 7; i++)
        {
            double d = rand.nextGaussian() * 0.02D;
            double d1 = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            worldObj.spawnParticle(s, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
        }

    }

    public int getMaxSpawnedInChunk()
    {
        return 8;
    }

    public String func_25034_v()
    {
        return dataWatcher.func_25076_c(17);
    }

    public void func_25029_a(String s)
    {
        dataWatcher.updateObject(17, s);
    }

    public boolean func_25036_w()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void func_25031_b(boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);
        if(flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 1)));
        } else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -2)));
        }
    }

    public boolean func_25028_x()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    public void func_25037_c(boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);
        if(flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 2)));
        } else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -3)));
        }
    }

    public boolean func_25030_y()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 4) != 0;
    }

    public void func_25032_d(boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(16);
        if(flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 | 4)));
        } else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & -5)));
        }
    }

    private boolean field_25039_a;
    private float field_25038_b;
    private float field_25044_c;
    private boolean field_25043_f;
    private boolean field_25042_g;
    private float field_25041_h;
    private float field_25040_i;
}
