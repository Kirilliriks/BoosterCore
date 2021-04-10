package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import net.minecraft.src.block.Block;
import net.minecraft.src.entity.*;
import net.minecraft.src.inventory.IInventory;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.nbt.NBTTagCompound;
import net.minecraft.src.nbt.NBTTagList;

import java.util.List;

public class TileEntitySign extends Entity
    implements IInventory
{

    public TileEntitySign(World world)
    {
        super(world);
        field_26621_h = new ItemStack[36];
        signText = 0;
        lineBeingEdited = 0;
        field_25053_c = 1;
        field_26620_i = false;
        preventEntitySpawning = true;
        setSize(0.98F, 0.7F);
        yOffset = height / 2.0F;
    }

    protected boolean func_25017_l()
    {
        return false;
    }

    protected void entityInit()
    {
    }

    public AxisAlignedBB func_89_d(Entity entity)
    {
        return entity.boundingBox;
    }

    public AxisAlignedBB getBoundingBox()
    {
        return null;
    }

    public boolean canBePushed()
    {
        return true;
    }

    public TileEntitySign(World world, double d, double d1, double d2, 
            int i)
    {
        this(world);
        setPosition(d, d1 + (double)yOffset, d2);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = d;
        prevPosY = d1;
        prevPosZ = d2;
        field_26625_d = i;
    }

    public double getMountedYOffset()
    {
        return (double)height * 0.0D - 0.30000001192092896D;
    }

    public boolean attackEntityFrom(Entity entity, int i)
    {
        if(worldObj.singleplayerWorld || isDead)
        {
            return true;
        }
        field_25053_c = -field_25053_c;
        lineBeingEdited = 10;
        func_26573_W();
        signText += i * 10;
        if(signText > 40)
        {
            dropItemWithOffset(Item.minecartEmpty.shiftedIndex, 1, 0.0F);
            if(field_26625_d == 1)
            {
                dropItemWithOffset(Block.crate.blockID, 1, 0.0F);
            } else
            if(field_26625_d == 2)
            {
                dropItemWithOffset(Block.stoneOvenIdle.blockID, 1, 0.0F);
            }
            setEntityDead();
        }
        return true;
    }

    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    public void setEntityDead()
    {
label0:
        for(int i = 0; i < getSizeInventory(); i++)
        {
            ItemStack itemstack = getStackInSlot(i);
            if(itemstack == null)
            {
                continue;
            }
            float f = rand.nextFloat() * 0.8F + 0.1F;
            float f1 = rand.nextFloat() * 0.8F + 0.1F;
            float f2 = rand.nextFloat() * 0.8F + 0.1F;
            do
            {
                if(itemstack.stackSize <= 0)
                {
                    continue label0;
                }
                int j = rand.nextInt(21) + 10;
                if(j > itemstack.stackSize)
                {
                    j = itemstack.stackSize;
                }
                itemstack.stackSize -= j;
                EntityItem entityitem = new EntityItem(worldObj, posX + (double)f, posY + (double)f1, posZ + (double)f2, new ItemStack(itemstack.itemID, j, itemstack.getItemDamage()));
                float f3 = 0.05F;
                entityitem.motionX = (float)rand.nextGaussian() * f3;
                entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float)rand.nextGaussian() * f3;
                worldObj.entityJoinedWorld(entityitem);
            } while(true);
        }

        super.setEntityDead();
    }

    public void onUpdate()
    {
        if(lineBeingEdited > 0)
        {
            lineBeingEdited--;
        }
        if(signText > 0)
        {
            signText--;
        }
        if(worldObj.singleplayerWorld && field_26618_k > 0)
        {
            if(field_26618_k > 0)
            {
                double d = posX + (field_26617_l - posX) / (double)field_26618_k;
                double d1 = posY + (field_26616_m - posY) / (double)field_26618_k;
                double d3 = posZ + (field_26615_n - posZ) / (double)field_26618_k;
                double d4;
                for(d4 = field_26614_o - (double)rotationYaw; d4 < -180D; d4 += 360D) { }
                for(; d4 >= 180D; d4 -= 360D) { }
                rotationYaw += d4 / (double)field_26618_k;
                rotationPitch += (field_26613_p - (double)rotationPitch) / (double)field_26618_k;
                field_26618_k--;
                setPosition(d, d1, d3);
                setRotation(rotationYaw, rotationPitch);
            } else
            {
                setPosition(posX, posY, posZ);
                setRotation(rotationYaw, rotationPitch);
            }
            return;
        }
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionY -= 0.039999999105930328D;
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);
        if(worldObj.getBlockId(i, j - 1, k) == Block.minecartTrack.blockID)
        {
            j--;
        }
        double d2 = 0.40000000000000002D;
        boolean flag = false;
        double d5 = 0.0078125D;
        if(worldObj.getBlockId(i, j, k) == Block.minecartTrack.blockID)
        {
            Vec3D vec3d = func_26612_g(posX, posY, posZ);
            int l = worldObj.getBlockMetadata(i, j, k);
            posY = j;
            if(l >= 2 && l <= 5)
            {
                posY = j + 1;
            }
            if(l == 2)
            {
                motionX -= d5;
            }
            if(l == 3)
            {
                motionX += d5;
            }
            if(l == 4)
            {
                motionZ += d5;
            }
            if(l == 5)
            {
                motionZ -= d5;
            }
            int ai[][] = field_26619_j[l];
            double d8 = ai[1][0] - ai[0][0];
            double d10 = ai[1][2] - ai[0][2];
            double d11 = Math.sqrt(d8 * d8 + d10 * d10);
            double d12 = motionX * d8 + motionZ * d10;
            if(d12 < 0.0D)
            {
                d8 = -d8;
                d10 = -d10;
            }
            double d13 = Math.sqrt(motionX * motionX + motionZ * motionZ);
            motionX = (d13 * d8) / d11;
            motionZ = (d13 * d10) / d11;
            double d16 = 0.0D;
            double d17 = (double)i + 0.5D + (double)ai[0][0] * 0.5D;
            double d18 = (double)k + 0.5D + (double)ai[0][2] * 0.5D;
            double d19 = (double)i + 0.5D + (double)ai[1][0] * 0.5D;
            double d20 = (double)k + 0.5D + (double)ai[1][2] * 0.5D;
            d8 = d19 - d17;
            d10 = d20 - d18;
            if(d8 == 0.0D)
            {
                posX = (double)i + 0.5D;
                d16 = posZ - (double)k;
            } else
            if(d10 == 0.0D)
            {
                posZ = (double)k + 0.5D;
                d16 = posX - (double)i;
            } else
            {
                double d21 = posX - d17;
                double d23 = posZ - d18;
                double d25 = (d21 * d8 + d23 * d10) * 2D;
                d16 = d25;
            }
            posX = d17 + d8 * d16;
            posZ = d18 + d10 * d16;
            setPosition(posX, posY + (double)yOffset, posZ);
            double d22 = motionX;
            double d24 = motionZ;
            if(riddenByEntity != null)
            {
                d22 *= 0.75D;
                d24 *= 0.75D;
            }
            if(d22 < -d2)
            {
                d22 = -d2;
            }
            if(d22 > d2)
            {
                d22 = d2;
            }
            if(d24 < -d2)
            {
                d24 = -d2;
            }
            if(d24 > d2)
            {
                d24 = d2;
            }
            moveEntity(d22, 0.0D, d24);
            if(ai[0][1] != 0 && MathHelper.floor_double(posX) - i == ai[0][0] && MathHelper.floor_double(posZ) - k == ai[0][2])
            {
                setPosition(posX, posY + (double)ai[0][1], posZ);
            } else
            if(ai[1][1] != 0 && MathHelper.floor_double(posX) - i == ai[1][0] && MathHelper.floor_double(posZ) - k == ai[1][2])
            {
                setPosition(posX, posY + (double)ai[1][1], posZ);
            }
            if(riddenByEntity != null)
            {
                motionX *= 0.99699997901916504D;
                motionY *= 0.0D;
                motionZ *= 0.99699997901916504D;
            } else
            {
                if(field_26625_d == 2)
                {
                    double d26 = MathHelper.sqrt_double(field_26623_f * field_26623_f + field_26622_g * field_26622_g);
                    if(d26 > 0.01D)
                    {
                        flag = true;
                        field_26623_f /= d26;
                        field_26622_g /= d26;
                        double d28 = 0.040000000000000001D;
                        motionX *= 0.80000001192092896D;
                        motionY *= 0.0D;
                        motionZ *= 0.80000001192092896D;
                        motionX += field_26623_f * d28;
                        motionZ += field_26622_g * d28;
                    } else
                    {
                        motionX *= 0.89999997615814209D;
                        motionY *= 0.0D;
                        motionZ *= 0.89999997615814209D;
                    }
                }
                motionX *= 0.95999997854232788D;
                motionY *= 0.0D;
                motionZ *= 0.95999997854232788D;
            }
            Vec3D vec3d1 = func_26612_g(posX, posY, posZ);
            if(vec3d1 != null && vec3d != null)
            {
                double d27 = (vec3d.yCoord - vec3d1.yCoord) * 0.050000000000000003D;
                double d14 = Math.sqrt(motionX * motionX + motionZ * motionZ);
                if(d14 > 0.0D)
                {
                    motionX = (motionX / d14) * (d14 + d27);
                    motionZ = (motionZ / d14) * (d14 + d27);
                }
                setPosition(posX, vec3d1.yCoord, posZ);
            }
            int j1 = MathHelper.floor_double(posX);
            int k1 = MathHelper.floor_double(posZ);
            if(j1 != i || k1 != k)
            {
                double d15 = Math.sqrt(motionX * motionX + motionZ * motionZ);
                motionX = d15 * (double)(j1 - i);
                motionZ = d15 * (double)(k1 - k);
            }
            if(field_26625_d == 2)
            {
                double d29 = MathHelper.sqrt_double(field_26623_f * field_26623_f + field_26622_g * field_26622_g);
                if(d29 > 0.01D && motionX * motionX + motionZ * motionZ > 0.001D)
                {
                    field_26623_f /= d29;
                    field_26622_g /= d29;
                    if(field_26623_f * motionX + field_26622_g * motionZ < 0.0D)
                    {
                        field_26623_f = 0.0D;
                        field_26622_g = 0.0D;
                    } else
                    {
                        field_26623_f = motionX;
                        field_26622_g = motionZ;
                    }
                }
            }
        } else
        {
            if(motionX < -d2)
            {
                motionX = -d2;
            }
            if(motionX > d2)
            {
                motionX = d2;
            }
            if(motionZ < -d2)
            {
                motionZ = -d2;
            }
            if(motionZ > d2)
            {
                motionZ = d2;
            }
            if(onGround)
            {
                motionX *= 0.5D;
                motionY *= 0.5D;
                motionZ *= 0.5D;
            }
            moveEntity(motionX, motionY, motionZ);
            if(!onGround)
            {
                motionX *= 0.94999998807907104D;
                motionY *= 0.94999998807907104D;
                motionZ *= 0.94999998807907104D;
            }
        }
        rotationPitch = 0.0F;
        double d6 = prevPosX - posX;
        double d7 = prevPosZ - posZ;
        if(d6 * d6 + d7 * d7 > 0.001D)
        {
            rotationYaw = (float)((Math.atan2(d7, d6) * 180D) / 3.1415926535897931D);
            if(field_26620_i)
            {
                rotationYaw += 180F;
            }
        }
        double d9;
        for(d9 = rotationYaw - prevRotationYaw; d9 >= 180D; d9 -= 360D) { }
        for(; d9 < -180D; d9 += 360D) { }
        if(d9 < -170D || d9 >= 170D)
        {
            rotationYaw += 180F;
            field_26620_i = !field_26620_i;
        }
        setRotation(rotationYaw, rotationPitch);
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
        if(list != null && list.size() > 0)
        {
            for(int i1 = 0; i1 < list.size(); i1++)
            {
                Entity entity = (Entity)list.get(i1);
                if(entity != riddenByEntity && entity.canBePushed() && (entity instanceof TileEntitySign))
                {
                    entity.applyEntityCollision(this);
                }
            }

        }
        if(riddenByEntity != null && riddenByEntity.isDead)
        {
            riddenByEntity = null;
        }
        if(flag && rand.nextInt(4) == 0)
        {
            field_26624_e--;
            if(field_26624_e < 0)
            {
                field_26623_f = field_26622_g = 0.0D;
            }
            worldObj.spawnParticle("largesmoke", posX, posY + 0.80000000000000004D, posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    public Vec3D func_26612_g(double d, double d1, double d2)
    {
        int i = MathHelper.floor_double(d);
        int j = MathHelper.floor_double(d1);
        int k = MathHelper.floor_double(d2);
        if(worldObj.getBlockId(i, j - 1, k) == Block.minecartTrack.blockID)
        {
            j--;
        }
        if(worldObj.getBlockId(i, j, k) == Block.minecartTrack.blockID)
        {
            int l = worldObj.getBlockMetadata(i, j, k);
            d1 = j;
            if(l >= 2 && l <= 5)
            {
                d1 = j + 1;
            }
            int ai[][] = field_26619_j[l];
            double d3 = 0.0D;
            double d4 = (double)i + 0.5D + (double)ai[0][0] * 0.5D;
            double d5 = (double)j + 0.5D + (double)ai[0][1] * 0.5D;
            double d6 = (double)k + 0.5D + (double)ai[0][2] * 0.5D;
            double d7 = (double)i + 0.5D + (double)ai[1][0] * 0.5D;
            double d8 = (double)j + 0.5D + (double)ai[1][1] * 0.5D;
            double d9 = (double)k + 0.5D + (double)ai[1][2] * 0.5D;
            double d10 = d7 - d4;
            double d11 = (d8 - d5) * 2D;
            double d12 = d9 - d6;
            if(d10 == 0.0D)
            {
                d = (double)i + 0.5D;
                d3 = d2 - (double)k;
            } else
            if(d12 == 0.0D)
            {
                d2 = (double)k + 0.5D;
                d3 = d - (double)i;
            } else
            {
                double d13 = d - d4;
                double d14 = d2 - d6;
                double d15 = (d13 * d10 + d14 * d12) * 2D;
                d3 = d15;
            }
            d = d4 + d10 * d3;
            d1 = d5 + d11 * d3;
            d2 = d6 + d12 * d3;
            if(d11 < 0.0D)
            {
                d1++;
            }
            if(d11 > 0.0D)
            {
                d1 += 0.5D;
            }
            return Vec3D.createVector(d, d1, d2);
        } else
        {
            return null;
        }
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setInteger("Type", field_26625_d);
        if(field_26625_d == 2)
        {
            nbttagcompound.setDouble("PushX", field_26623_f);
            nbttagcompound.setDouble("PushZ", field_26622_g);
            nbttagcompound.setShort("Fuel", (short)field_26624_e);
        } else
        if(field_26625_d == 1)
        {
            NBTTagList nbttaglist = new NBTTagList();
            for(int i = 0; i < field_26621_h.length; i++)
            {
                if(field_26621_h[i] != null)
                {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte)i);
                    field_26621_h[i].writeToNBT(nbttagcompound1);
                    nbttaglist.setTag(nbttagcompound1);
                }
            }

            nbttagcompound.setTag("Items", nbttaglist);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {

    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        field_26625_d = nbttagcompound.getInteger("Type");
        if(field_26625_d == 2)
        {
            field_26623_f = nbttagcompound.getDouble("PushX");
            field_26622_g = nbttagcompound.getDouble("PushZ");
            field_26624_e = nbttagcompound.getShort("Fuel");
        } else
        if(field_26625_d == 1)
        {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
            field_26621_h = new ItemStack[getSizeInventory()];
            for(int i = 0; i < nbttaglist.tagCount(); i++)
            {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if(j >= 0 && j < field_26621_h.length)
                {
                    field_26621_h[j] = new ItemStack(nbttagcompound1);
                }
            }

        }
    }

    public void applyEntityCollision(Entity entity)
    {
        if(worldObj.singleplayerWorld)
        {
            return;
        }
        if(entity == riddenByEntity)
        {
            return;
        }
        if((entity instanceof EntityLiving) && !(entity instanceof EntityPlayer) && field_26625_d == 0 && motionX * motionX + motionZ * motionZ > 0.01D && riddenByEntity == null && entity.ridingEntity == null)
        {
            entity.mountEntity(this);
        }
        double d = entity.posX - posX;
        double d1 = entity.posZ - posZ;
        double d2 = d * d + d1 * d1;
        if(d2 >= 9.9999997473787516E-005D)
        {
            d2 = MathHelper.sqrt_double(d2);
            d /= d2;
            d1 /= d2;
            double d3 = 1.0D / d2;
            if(d3 > 1.0D)
            {
                d3 = 1.0D;
            }
            d *= d3;
            d1 *= d3;
            d *= 0.10000000149011612D;
            d1 *= 0.10000000149011612D;
            d *= 1.0F - entityCollisionReduction;
            d1 *= 1.0F - entityCollisionReduction;
            d *= 0.5D;
            d1 *= 0.5D;
            if(entity instanceof TileEntitySign)
            {
                double d4 = entity.motionX + motionX;
                double d5 = entity.motionZ + motionZ;
                if(((TileEntitySign)entity).field_26625_d == 2 && field_26625_d != 2)
                {
                    motionX *= 0.20000000298023224D;
                    motionZ *= 0.20000000298023224D;
                    addVelocity(entity.motionX - d, 0.0D, entity.motionZ - d1);
                    entity.motionX *= 0.69999998807907104D;
                    entity.motionZ *= 0.69999998807907104D;
                } else
                if(((TileEntitySign)entity).field_26625_d != 2 && field_26625_d == 2)
                {
                    entity.motionX *= 0.20000000298023224D;
                    entity.motionZ *= 0.20000000298023224D;
                    entity.addVelocity(motionX + d, 0.0D, motionZ + d1);
                    motionX *= 0.69999998807907104D;
                    motionZ *= 0.69999998807907104D;
                } else
                {
                    d4 /= 2D;
                    d5 /= 2D;
                    motionX *= 0.20000000298023224D;
                    motionZ *= 0.20000000298023224D;
                    addVelocity(d4 - d, 0.0D, d5 - d1);
                    entity.motionX *= 0.20000000298023224D;
                    entity.motionZ *= 0.20000000298023224D;
                    entity.addVelocity(d4 + d, 0.0D, d5 + d1);
                }
            } else
            {
                addVelocity(-d, 0.0D, -d1);
                entity.addVelocity(d / 4D, 0.0D, d1 / 4D);
            }
        }
    }

    public int getSizeInventory()
    {
        return 27;
    }

    public ItemStack getStackInSlot(int i)
    {
        return field_26621_h[i];
    }

    public ItemStack decrStackSize(int i, int j)
    {
        if(field_26621_h[i] != null)
        {
            if(field_26621_h[i].stackSize <= j)
            {
                ItemStack itemstack = field_26621_h[i];
                field_26621_h[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = field_26621_h[i].splitStack(j);
            if(field_26621_h[i].stackSize == 0)
            {
                field_26621_h[i] = null;
            }
            return itemstack1;
        } else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        field_26621_h[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    public String getInvName()
    {
        return "Minecart";
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public void onInventoryChanged()
    {
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        if(field_26625_d == 0)
        {
            if(riddenByEntity != null && (riddenByEntity instanceof EntityPlayer) && riddenByEntity != entityplayer)
            {
                return true;
            }
            if(!worldObj.singleplayerWorld)
            {
                entityplayer.mountEntity(this);
            }
        } else
        if(field_26625_d == 1)
        {
            if(!worldObj.singleplayerWorld)
            {
                entityplayer.displayGUIChest(this);
            }
        } else
        if(field_26625_d == 2)
        {
            ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            if(itemstack != null && itemstack.itemID == Item.coal.shiftedIndex)
            {
                if(--itemstack.stackSize == 0)
                {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
                field_26624_e += 1200;
            }
            field_26623_f = posX - entityplayer.posX;
            field_26622_g = posZ - entityplayer.posZ;
        }
        return true;
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        if(isDead)
        {
            return false;
        }
        return entityplayer.getDistanceSqToEntity(this) <= 64D;
    }

    private ItemStack field_26621_h[];
    public int signText;
    public int lineBeingEdited;
    public int field_25053_c;
    private boolean field_26620_i;
    public int field_26625_d;
    public int field_26624_e;
    public double field_26623_f;
    public double field_26622_g;
    private static final int field_26619_j[][][] = {
        {
            {
                0, 0, -1
            }, {
                0, 0, 1
            }
        }, {
            {
                -1, 0, 0
            }, {
                1, 0, 0
            }
        }, {
            {
                -1, -1, 0
            }, {
                1, 0, 0
            }
        }, {
            {
                -1, 0, 0
            }, {
                1, -1, 0
            }
        }, {
            {
                0, 0, -1
            }, {
                0, -1, 1
            }
        }, {
            {
                0, -1, -1
            }, {
                0, 0, 1
            }
        }, {
            {
                0, 0, 1
            }, {
                1, 0, 0
            }
        }, {
            {
                0, 0, 1
            }, {
                -1, 0, 0
            }
        }, {
            {
                0, 0, -1
            }, {
                -1, 0, 0
            }
        }, {
            {
                0, 0, -1
            }, {
                1, 0, 0
            }
        }
    };
    private int field_26618_k;
    private double field_26617_l;
    private double field_26616_m;
    private double field_26615_n;
    private double field_26614_o;
    private double field_26613_p;

}
