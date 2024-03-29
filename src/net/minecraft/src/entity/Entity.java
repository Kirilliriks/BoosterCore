package net.minecraft.src.entity;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import com.booster.core.entity.BoosterEntity;
import net.minecraft.src.*;
import net.minecraft.src.block.Block;
import net.minecraft.src.block.BlockFluids;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.material.Material;
import net.minecraft.src.nbt.NBTTagCompound;
import net.minecraft.src.nbt.NBTTagDouble;
import net.minecraft.src.nbt.NBTTagFloat;
import net.minecraft.src.nbt.NBTTagList;
import net.minecraft.src.world.World;

import java.util.List;
import java.util.Random;

public abstract class Entity {

    // Booster
    protected BoosterEntity boosterEntity;
    //

    private static int nextEntityID = 0;
    public int entityId;
    public double renderDistanceWeight;
    public boolean preventEntitySpawning;
    public Entity riddenByEntity;
    public Entity ridingEntity;
    public World worldObj;
    public double prevPosX;
    public double prevPosY;
    public double prevPosZ;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public float rotationYaw;
    public float rotationPitch;
    public float prevRotationYaw;
    public float prevRotationPitch;
    public final AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    public boolean onGround;
    public boolean isCollidedHorizontally;
    public boolean isCollidedVertically;
    public boolean isCollided;
    public boolean beenAttacked;
    public boolean field_9077_F; //??
    public boolean isDead;
    public float yOffset;
    public float width;
    public float height;
    public float prevDistanceWalkedModified;
    public float distanceWalkedModified;
    protected float fallDistance;
    private int nextStepDistance;
    public double lastTickPosX;
    public double lastTickPosY;
    public double lastTickPosZ;
    public float ySize;
    public float stepHeight;
    public boolean noClip;
    public float entityCollisionReduction;
    public boolean field_9065_V; //??
    protected Random rand;
    public int ticksExisted;
    public int fireResistance;
    public int fire;
    protected int maxAir;
    protected boolean inWater;
    public int field_9083_ac;
    public int air;
    private boolean firstUpdate;
    protected boolean isImmuneToFire;
    protected DataWatcher dataWatcher;
    private double entityRiderPitchDelta;
    private double entityRiderYawDelta;
    public boolean addedToChunk;
    public int chunkCoordX;
    public int chunkCoordY;
    public int chunkCoordZ;

    public Entity(World world) {
        entityId = nextEntityID++;
        renderDistanceWeight = 1.0D;
        preventEntitySpawning = false;
        onGround = false;
        isCollided = false;
        beenAttacked = false;
        field_9077_F = true;
        isDead = false;
        yOffset = 0.0F;
        width = 0.6F;
        height = 1.8F;
        prevDistanceWalkedModified = 0.0F;
        distanceWalkedModified = 0.0F;
        fallDistance = 0.0F;
        nextStepDistance = 1;
        ySize = 0.0F;
        stepHeight = 0.0F;
        noClip = false;
        entityCollisionReduction = 0.0F;
        field_9065_V = false;
        rand = new Random();
        ticksExisted = 0;
        fireResistance = 1;
        fire = 0;
        maxAir = 300;
        inWater = false;
        field_9083_ac = 0;
        air = 300;
        firstUpdate = true;
        isImmuneToFire = false;
        dataWatcher = new DataWatcher();
        addedToChunk = false;
        worldObj = world;
        setPosition(0.0D, 0.0D, 0.0D);
        dataWatcher.addObject(0, (byte) 0);
        entityInit();

        // Booster
        boosterEntity = BoosterEntity.newBoosterEntity(this);
        //
    }

    // Booster
    public BoosterEntity getBoosterEntity() {
        if (boosterEntity == null) throw new RuntimeException("BoosterEntity not found");
        return boosterEntity;
    }
    //

    protected abstract void entityInit();

    public DataWatcher func_26572_T()
    {
        return dataWatcher;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof Entity)
        {
            return ((Entity)obj).entityId == entityId;
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return entityId;
    }

    public void setEntityDead()
    {
        isDead = true;
    }

    protected void setSize(float f, float f1)  {
        width = f;
        height = f1;
    }

    protected void setRotation(float f, float f1)  {
        rotationYaw = f;
        rotationPitch = f1;
    }

    public void setPosition(double x, double y, double z)  {
        posX = x;
        posY = y;
        posZ = z;
        float f = width / 2.0F;
        float f1 = height;
        boundingBox.setBounds(x - (double)f, (y - (double)yOffset) + (double)ySize, z - (double)f, x + (double)f, (y - (double)yOffset) + (double)ySize + (double)f1, z + (double)f);
    }

    public void onUpdate()  {
        spawnExplosionParticle();
    }

    public void spawnExplosionParticle()  {
        if(ridingEntity != null && ridingEntity.isDead)  {
            ridingEntity = null;
        }

        ticksExisted++;
        prevDistanceWalkedModified = distanceWalkedModified;
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        prevRotationPitch = rotationPitch;
        prevRotationYaw = rotationYaw;

        if(handleWaterMovement())  {
            if(!inWater && !firstUpdate)  {
                float f = MathHelper.sqrt_double(motionX * motionX * 0.20000000298023224D + motionY * motionY + motionZ * motionZ * 0.20000000298023224D) * 0.2F;
                if(f > 1.0F)  {
                    f = 1.0F;
                }
                worldObj.playSoundAtEntity(this, "random.splash", f, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
                float f1 = MathHelper.floor_double(boundingBox.minY);
                for(int i = 0; (float)i < 1.0F + width * 20F; i++)  {
                    float f2 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    float f4 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    worldObj.spawnParticle("bubble", posX + (double)f2, f1 + 1.0F, posZ + (double)f4, motionX, motionY - (double)(rand.nextFloat() * 0.2F), motionZ);
                }

                for(int j = 0; (float)j < 1.0F + width * 20F; j++)
                {
                    float f3 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    float f5 = (rand.nextFloat() * 2.0F - 1.0F) * width;
                    worldObj.spawnParticle("splash", posX + (double)f3, f1 + 1.0F, posZ + (double)f5, motionX, motionY, motionZ);
                }

            }
            fallDistance = 0.0F;
            inWater = true;
            fire = 0;
        } else
        {
            inWater = false;
        }
        if(worldObj.singleplayerWorld)
        {
            fire = 0;
        } else
        if(fire > 0)
        {
            if(isImmuneToFire)
            {
                fire -= 4;
                if(fire < 0)
                {
                    fire = 0;
                }
            } else
            {
                if(fire % 20 == 0)
                {
                    attackEntityFrom(null, 1);
                }
                fire--;
            }
        }
        if(func_26567_V())
        {
            func_26574_U();
        }
        if(posY < -64D)
        {
            func_26570_R();
        }
        if(!worldObj.singleplayerWorld)
        {
            func_21041_a(0, fire > 0);
            func_21041_a(2, ridingEntity != null);
        }
        firstUpdate = false;
    }

    protected void func_26574_U()
    {
        if(!isImmuneToFire)
        {
            attackEntityFrom(null, 4);
            fire = 600;
        }
    }

    protected void func_26570_R()
    {
        setEntityDead();
    }

    public boolean isOffsetPositionInLiquid(double d, double d1, double d2)
    {
        AxisAlignedBB axisalignedbb = boundingBox.getOffsetBoundingBox(d, d1, d2);
        List list = worldObj.getCollidingBoundingBoxes(this, axisalignedbb);
        if(list.size() > 0)
        {
            return false;
        }
        return !worldObj.getIsAnyLiquid(axisalignedbb);
    }

    public void moveEntity(double x, double y, double z) {
        if(noClip)
        {
            boundingBox.offset(x, y, z);
            posX = (boundingBox.minX + boundingBox.maxX) / 2D;
            posY = (boundingBox.minY + (double)yOffset) - (double)ySize;
            posZ = (boundingBox.minZ + boundingBox.maxZ) / 2D;
            return;
        }
        double d3 = posX;
        double d4 = posZ;
        double d5 = x;
        double d6 = y;
        double d7 = z;
        AxisAlignedBB axisalignedbb = boundingBox.copy();
        boolean flag = onGround && func_26575_Z();
        if(flag)
        {
            double d8 = 0.050000000000000003D;
            for(; x != 0.0D && worldObj.getCollidingBoundingBoxes(this, boundingBox.getOffsetBoundingBox(x, -1D, 0.0D)).size() == 0; d5 = x)
            {
                if(x < d8 && x >= -d8)
                {
                    x = 0.0D;
                    continue;
                }
                if(x > 0.0D)
                {
                    x -= d8;
                } else
                {
                    x += d8;
                }
            }

            for(; z != 0.0D && worldObj.getCollidingBoundingBoxes(this, boundingBox.getOffsetBoundingBox(0.0D, -1D, z)).size() == 0; d7 = z)
            {
                if(z < d8 && z >= -d8)
                {
                    z = 0.0D;
                    continue;
                }
                if(z > 0.0D)
                {
                    z -= d8;
                } else
                {
                    z += d8;
                }
            }

        }
        List list = worldObj.getCollidingBoundingBoxes(this, boundingBox.addCoord(x, y, z));
        for(int i = 0; i < list.size(); i++)
        {
            y = ((AxisAlignedBB)list.get(i)).calculateYOffset(boundingBox, y);
        }

        boundingBox.offset(0.0D, y, 0.0D);
        if(!field_9077_F && d6 != y)
        {
            x = y = z = 0.0D;
        }
        boolean flag1 = onGround || d6 != y && d6 < 0.0D;
        for(int j = 0; j < list.size(); j++)
        {
            x = ((AxisAlignedBB)list.get(j)).calculateXOffset(boundingBox, x);
        }

        boundingBox.offset(x, 0.0D, 0.0D);
        if(!field_9077_F && d5 != x)
        {
            x = y = z = 0.0D;
        }
        for(int k = 0; k < list.size(); k++)
        {
            z = ((AxisAlignedBB)list.get(k)).calculateZOffset(boundingBox, z);
        }

        boundingBox.offset(0.0D, 0.0D, z);
        if(!field_9077_F && d7 != z)
        {
            x = y = z = 0.0D;
        }
        if(stepHeight > 0.0F && flag1 && ySize < 0.05F && (d5 != x || d7 != z))
        {
            double d9 = x;
            double d11 = y;
            double d13 = z;
            x = d5;
            y = stepHeight;
            z = d7;
            AxisAlignedBB axisalignedbb1 = boundingBox.copy();
            boundingBox.setBB(axisalignedbb);
            List list1 = worldObj.getCollidingBoundingBoxes(this, boundingBox.addCoord(x, y, z));
            for(int j2 = 0; j2 < list1.size(); j2++)
            {
                y = ((AxisAlignedBB)list1.get(j2)).calculateYOffset(boundingBox, y);
            }

            boundingBox.offset(0.0D, y, 0.0D);
            if(!field_9077_F && d6 != y)
            {
                x = y = z = 0.0D;
            }
            for(int k2 = 0; k2 < list1.size(); k2++)
            {
                x = ((AxisAlignedBB)list1.get(k2)).calculateXOffset(boundingBox, x);
            }

            boundingBox.offset(x, 0.0D, 0.0D);
            if(!field_9077_F && d5 != x)
            {
                x = y = z = 0.0D;
            }
            for(int l2 = 0; l2 < list1.size(); l2++)
            {
                z = ((AxisAlignedBB)list1.get(l2)).calculateZOffset(boundingBox, z);
            }

            boundingBox.offset(0.0D, 0.0D, z);
            if(!field_9077_F && d7 != z)
            {
                x = y = z = 0.0D;
            }
            if(d9 * d9 + d13 * d13 >= x * x + z * z)
            {
                x = d9;
                y = d11;
                z = d13;
                boundingBox.setBB(axisalignedbb1);
            } else
            {
                ySize += 0.5D;
            }
        }
        posX = (boundingBox.minX + boundingBox.maxX) / 2D;
        posY = (boundingBox.minY + (double)yOffset) - (double)ySize;
        posZ = (boundingBox.minZ + boundingBox.maxZ) / 2D;
        isCollidedHorizontally = d5 != x || d7 != z;
        isCollidedVertically = d6 != y;
        onGround = d6 != y && d6 < 0.0D;
        isCollided = isCollidedHorizontally || isCollidedVertically;
        updateFallState(y, onGround);
        if(d5 != x)
        {
            motionX = 0.0D;
        }
        if(d6 != y)
        {
            motionY = 0.0D;
        }
        if(d7 != z)
        {
            motionZ = 0.0D;
        }
        double d10 = posX - d3;
        double d12 = posZ - d4;
        if(func_25017_l() && !flag)
        {
            distanceWalkedModified += (double)MathHelper.sqrt_double(d10 * d10 + d12 * d12) * 0.59999999999999998D;
            int l = MathHelper.floor_double(posX);
            int j1 = MathHelper.floor_double(posY - 0.20000000298023224D - (double)yOffset);
            int l1 = MathHelper.floor_double(posZ);
            int i3 = worldObj.getBlockId(l, j1, l1);
            if(distanceWalkedModified > (float)nextStepDistance && i3 > 0)
            {
                nextStepDistance++;
                StepSound stepsound = Block.blocksList[i3].stepSound;
                if(worldObj.getBlockId(l, j1 + 1, l1) == Block.snow.blockID)
                {
                    stepsound = Block.snow.stepSound;
                    worldObj.playSoundAtEntity(this, stepsound.func_737_c(), stepsound.func_738_a() * 0.15F, stepsound.func_739_b());
                } else
                if(!Block.blocksList[i3].blockMaterial.getIsLiquid())
                {
                    worldObj.playSoundAtEntity(this, stepsound.func_737_c(), stepsound.func_738_a() * 0.15F, stepsound.func_739_b());
                }
                Block.blocksList[i3].onEntityWalking(worldObj, l, j1, l1, this);
            }
        }
        int i1 = MathHelper.floor_double(boundingBox.minX);
        int k1 = MathHelper.floor_double(boundingBox.minY);
        int i2 = MathHelper.floor_double(boundingBox.minZ);
        int j3 = MathHelper.floor_double(boundingBox.maxX);
        int k3 = MathHelper.floor_double(boundingBox.maxY);
        int l3 = MathHelper.floor_double(boundingBox.maxZ);
        if(worldObj.checkChunksExist(i1, k1, i2, j3, k3, l3))
        {
            for(int i4 = i1; i4 <= j3; i4++)
            {
                for(int j4 = k1; j4 <= k3; j4++)
                {
                    for(int k4 = i2; k4 <= l3; k4++)
                    {
                        int l4 = worldObj.getBlockId(i4, j4, k4);
                        if(l4 > 0)
                        {
                            Block.blocksList[l4].onEntityCollidedWithBlock(worldObj, i4, j4, k4, this);
                        }
                    }

                }

            }

        }
        ySize *= 0.4F;
        boolean flag2 = handleWaterMovement();
        if(worldObj.isBoundingBoxBurning(boundingBox))
        {
            dealFireDamage(1);
            if(!flag2)
            {
                fire++;
                if(fire == 0)
                {
                    fire = 300;
                }
            }
        } else
        if(fire <= 0)
        {
            fire = -fireResistance;
        }
        if(flag2 && fire > 0)
        {
            worldObj.playSoundAtEntity(this, "random.fizz", 0.7F, 1.6F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
            fire = -fireResistance;
        }
    }

    protected boolean func_25017_l()
    {
        return true;
    }

    protected void updateFallState(double d, boolean flag)
    {
        if(flag)
        {
            if(fallDistance > 0.0F)
            {
                fall(fallDistance);
                fallDistance = 0.0F;
            }
        } else if(d < 0.0D) {
            fallDistance -= d;
        }
    }

    public AxisAlignedBB getBoundingBox()
    {
        return null;
    }

    protected void dealFireDamage(int i)
    {
        if(!isImmuneToFire)
        {
            attackEntityFrom(null, i);
        }
    }

    protected void fall(float f)
    {
    }

    public boolean handleWaterMovement()
    {
        return worldObj.handleMaterialAcceleration(boundingBox.expand(0.0D, -0.40000000596046448D, 0.0D), Material.water, this);
    }

    public boolean isInsideOfMaterial(Material material)
    {
        double d = posY + (double)getEyeHeight();
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_float(MathHelper.floor_double(d));
        int k = MathHelper.floor_double(posZ);
        int l = worldObj.getBlockId(i, j, k);
        if(l != 0 && Block.blocksList[l].blockMaterial == material)
        {
            float f = BlockFluids.setFluidHeight(worldObj.getBlockMetadata(i, j, k)) - 0.1111111F;
            float f1 = (float)(j + 1) - f;
            return d < (double)f1;
        } else
        {
            return false;
        }
    }

    public float getEyeHeight()
    {
        return 0.0F;
    }

    public boolean func_26567_V()
    {
        return worldObj.isMaterialInBB(boundingBox.expand(-0.10000000149011612D, -0.40000000596046448D, -0.10000000149011612D), Material.lava);
    }

    public void moveFlying(float f, float f1, float f2)
    {
        float f3 = MathHelper.sqrt_float(f * f + f1 * f1);
        if(f3 < 0.01F)
        {
            return;
        }
        if(f3 < 1.0F)
        {
            f3 = 1.0F;
        }
        f3 = f2 / f3;
        f *= f3;
        f1 *= f3;
        float f4 = MathHelper.sin((rotationYaw * 3.141593F) / 180F);
        float f5 = MathHelper.cos((rotationYaw * 3.141593F) / 180F);
        motionX += f * f5 - f1 * f4;
        motionZ += f1 * f5 + f * f4;
    }

    public float getEntityBrightness(float f) {
        int i = MathHelper.floor_double(posX);
        double d = (boundingBox.maxY - boundingBox.minY) * 0.66000000000000003D;
        int j = MathHelper.floor_double((posY - (double)yOffset) + d);
        int k = MathHelper.floor_double(posZ);
        if(worldObj.checkChunksExist(MathHelper.floor_double(boundingBox.minX), MathHelper.floor_double(boundingBox.minY), MathHelper.floor_double(boundingBox.minZ), MathHelper.floor_double(boundingBox.maxX), MathHelper.floor_double(boundingBox.maxY), MathHelper.floor_double(boundingBox.maxZ))) {
            return worldObj.getLightBrightness(i, j, k);
        } else {
            return 0.0F;
        }
    }

    public void setPositionAndRotation(double d, double d1, double d2, float f, 
            float f1) {
        prevPosX = posX = d;
        prevPosY = posY = d1;
        prevPosZ = posZ = d2;
        prevRotationYaw = rotationYaw = f;
        prevRotationPitch = rotationPitch = f1;
        ySize = 0.0F;

        double d3 = prevRotationYaw - f;
        if(d3 < -180D)  {
            prevRotationYaw += 360F;
        }
        if(d3 >= 180D)  {
            prevRotationYaw -= 360F;
        }

        setPosition(posX, posY, posZ);
        setRotation(f, f1);
    }

    public void setLocationAndAngles(double d, double d1, double d2, float f, 
            float f1)  {
        lastTickPosX = prevPosX = posX = d;
        lastTickPosY = prevPosY = posY = d1 + (double)yOffset;
        lastTickPosZ = prevPosZ = posZ = d2;
        rotationYaw = f;
        rotationPitch = f1;
        setPosition(posX, posY, posZ);
    }

    public float getDistanceToEntity(Entity entity)
    {
        float f = (float)(posX - entity.posX);
        float f1 = (float)(posY - entity.posY);
        float f2 = (float)(posZ - entity.posZ);
        return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
    }

    public double getDistanceSq(double d, double d1, double d2)
    {
        double d3 = posX - d;
        double d4 = posY - d1;
        double d5 = posZ - d2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double getDistance(double d, double d1, double d2)
    {
        double d3 = posX - d;
        double d4 = posY - d1;
        double d5 = posZ - d2;
        return (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
    }

    public double getDistanceSqToEntity(Entity entity)
    {
        double d = posX - entity.posX;
        double d1 = posY - entity.posY;
        double d2 = posZ - entity.posZ;
        return d * d + d1 * d1 + d2 * d2;
    }

    public void onCollideWithPlayer(EntityHuman entityplayer)
    {
    }

    public void applyEntityCollision(Entity entity)
    {
        if(entity.riddenByEntity == this || entity.ridingEntity == this)
        {
            return;
        }
        double d = entity.posX - posX;
        double d1 = entity.posZ - posZ;
        double d2 = MathHelper.abs_max(d, d1);
        if(d2 >= 0.0099999997764825821D)
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
            d *= 0.05000000074505806D;
            d1 *= 0.05000000074505806D;
            d *= 1.0F - entityCollisionReduction;
            d1 *= 1.0F - entityCollisionReduction;
            addVelocity(-d, 0.0D, -d1);
            entity.addVelocity(d, 0.0D, d1);
        }
    }

    public void addVelocity(double d, double d1, double d2)
    {
        motionX += d;
        motionY += d1;
        motionZ += d2;
    }

    protected void func_26573_W()
    {
        beenAttacked = true;
    }

    public boolean attackEntityFrom(Entity entity, int i)
    {
        func_26573_W();
        return false;
    }

    public boolean canBeCollidedWith()
    {
        return false;
    }

    public boolean canBePushed()
    {
        return false;
    }

    public void addToPlayerScore(Entity entity, int i)
    {
    }

    public boolean addEntityID(NBTTagCompound nbttagcompound)
    {
        String s = func_26568_X();
        if(isDead || s == null)
        {
            return false;
        } else
        {
            nbttagcompound.setString("id", s);
            writeToNBT(nbttagcompound);
            return true;
        }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setTag("Pos", newDoubleNBTList(new double[] {
            posX, posY, posZ
        }));
        nbttagcompound.setTag("Motion", newDoubleNBTList(new double[] {
            motionX, motionY, motionZ
        }));
        nbttagcompound.setTag("Rotation", newFloatNBTList(new float[] {
            rotationYaw, rotationPitch
        }));
        nbttagcompound.setFloat("FallDistance", fallDistance);
        nbttagcompound.setShort("Fire", (short)fire);
        nbttagcompound.setShort("Air", (short)air);
        nbttagcompound.setBoolean("OnGround", onGround);
        writeEntityToNBT(nbttagcompound);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        NBTTagList nbttaglist = nbttagcompound.getTagList("Pos");
        NBTTagList nbttaglist1 = nbttagcompound.getTagList("Motion");
        NBTTagList nbttaglist2 = nbttagcompound.getTagList("Rotation");
        setPosition(0.0D, 0.0D, 0.0D);
        motionX = ((NBTTagDouble)nbttaglist1.tagAt(0)).doubleValue;
        motionY = ((NBTTagDouble)nbttaglist1.tagAt(1)).doubleValue;
        motionZ = ((NBTTagDouble)nbttaglist1.tagAt(2)).doubleValue;
        if(Math.abs(motionX) > 10D)
        {
            motionX = 0.0D;
        }
        if(Math.abs(motionY) > 10D)
        {
            motionY = 0.0D;
        }
        if(Math.abs(motionZ) > 10D)
        {
            motionZ = 0.0D;
        }
        prevPosX = lastTickPosX = posX = ((NBTTagDouble)nbttaglist.tagAt(0)).doubleValue;
        prevPosY = lastTickPosY = posY = ((NBTTagDouble)nbttaglist.tagAt(1)).doubleValue;
        prevPosZ = lastTickPosZ = posZ = ((NBTTagDouble)nbttaglist.tagAt(2)).doubleValue;
        prevRotationYaw = rotationYaw = ((NBTTagFloat)nbttaglist2.tagAt(0)).floatValue % 6.283185F;
        prevRotationPitch = rotationPitch = ((NBTTagFloat)nbttaglist2.tagAt(1)).floatValue % 6.283185F;
        fallDistance = nbttagcompound.getFloat("FallDistance");
        fire = nbttagcompound.getShort("Fire");
        air = nbttagcompound.getShort("Air");
        onGround = nbttagcompound.getBoolean("OnGround");
        setPosition(posX, posY, posZ);
        readEntityFromNBT(nbttagcompound);
    }

    protected final String func_26568_X()
    {
        return EntityList.getEntityString(this);
    }

    protected abstract void readEntityFromNBT(NBTTagCompound nbttagcompound);

    protected abstract void writeEntityToNBT(NBTTagCompound nbttagcompound);

    protected NBTTagList newDoubleNBTList(double ad[])
    {
        NBTTagList nbttaglist = new NBTTagList();
        double ad1[] = ad;
        int i = ad1.length;
        for(int j = 0; j < i; j++)
        {
            double d = ad1[j];
            nbttaglist.setTag(new NBTTagDouble(d));
        }

        return nbttaglist;
    }

    protected NBTTagList newFloatNBTList(float af[])
    {
        NBTTagList nbttaglist = new NBTTagList();
        float af1[] = af;
        int i = af1.length;
        for(int j = 0; j < i; j++)
        {
            float f = af1[j];
            nbttaglist.setTag(new NBTTagFloat(f));
        }

        return nbttaglist;
    }

    public EntityItem dropItem(int i, int j)
    {
        return dropItemWithOffset(i, j, 0.0F);
    }

    public EntityItem dropItemWithOffset(int i, int j, float f)
    {
        return entityDropItem(new ItemStack(i, j, 0), f);
    }

    public EntityItem entityDropItem(ItemStack itemstack, float f)
    {
        EntityItem entityitem = new EntityItem(worldObj, posX, posY + (double)f, posZ, itemstack);
        entityitem.delayBeforeCanPickup = 10;
        worldObj.entityJoinedWorld(entityitem);
        return entityitem;
    }

    public boolean func_25021_O()
    {
        return !isDead;
    }

    public boolean isEntityInsideOpaqueBlock()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY + (double)getEyeHeight());
        int k = MathHelper.floor_double(posZ);
        return worldObj.isBlockOpaqueCube(i, j, k);
    }

    public boolean interact(EntityHuman entityplayer)
    {
        return false;
    }

    public AxisAlignedBB func_89_d(Entity entity)
    {
        return null;
    }

    public void updateRidden()
    {
        if(ridingEntity.isDead)
        {
            ridingEntity = null;
            return;
        }
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        onUpdate();
        ridingEntity.updateRiderPosition();
        entityRiderYawDelta += ridingEntity.rotationYaw - ridingEntity.prevRotationYaw;
        entityRiderPitchDelta += ridingEntity.rotationPitch - ridingEntity.prevRotationPitch;
        for(; entityRiderYawDelta >= 180D; entityRiderYawDelta -= 360D) { }
        for(; entityRiderYawDelta < -180D; entityRiderYawDelta += 360D) { }
        for(; entityRiderPitchDelta >= 180D; entityRiderPitchDelta -= 360D) { }
        for(; entityRiderPitchDelta < -180D; entityRiderPitchDelta += 360D) { }
        double d = entityRiderYawDelta * 0.5D;
        double d1 = entityRiderPitchDelta * 0.5D;
        float f = 10F;
        if(d > (double)f)
        {
            d = f;
        }
        if(d < (double)(-f))
        {
            d = -f;
        }
        if(d1 > (double)f)
        {
            d1 = f;
        }
        if(d1 < (double)(-f))
        {
            d1 = -f;
        }
        entityRiderYawDelta -= d;
        entityRiderPitchDelta -= d1;
        rotationYaw += d;
        rotationPitch += d1;
    }

    public void updateRiderPosition()
    {
        riddenByEntity.setPosition(posX, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ);
    }

    public double getYOffset()
    {
        return (double)yOffset;
    }

    public double getMountedYOffset()
    {
        return (double)height * 0.75D;
    }

    public void mountEntity(Entity entity)
    {
        entityRiderPitchDelta = 0.0D;
        entityRiderYawDelta = 0.0D;
        if(entity == null)
        {
            if(ridingEntity != null)
            {
                setLocationAndAngles(ridingEntity.posX, ridingEntity.boundingBox.minY + (double)ridingEntity.height, ridingEntity.posZ, rotationYaw, rotationPitch);
                ridingEntity.riddenByEntity = null;
            }
            ridingEntity = null;
            return;
        }
        if(ridingEntity == entity)
        {
            ridingEntity.riddenByEntity = null;
            ridingEntity = null;
            setLocationAndAngles(entity.posX, entity.boundingBox.minY + (double)entity.height, entity.posZ, rotationYaw, rotationPitch);
            return;
        }
        if(ridingEntity != null)
        {
            ridingEntity.riddenByEntity = null;
        }
        if(entity.riddenByEntity != null)
        {
            entity.riddenByEntity.ridingEntity = null;
        }
        ridingEntity = entity;
        entity.riddenByEntity = this;
    }

    public Vec3D func_26571_S()
    {
        return null;
    }

    public void func_26569_Y()
    {
    }

    public ItemStack[] getInventory()
    {
        return null;
    }

    public boolean func_26575_Z()
    {
        return func_21042_c(1);
    }

    public void func_21043_b(boolean flag)
    {
        func_21041_a(1, flag);
    }

    protected boolean func_21042_c(int i)
    {
        return (dataWatcher.getWatchableObjectByte(0) & 1 << i) != 0;
    }

    protected void func_21041_a(int i, boolean flag)
    {
        byte byte0 = dataWatcher.getWatchableObjectByte(0);
        if(flag)
        {
            dataWatcher.updateObject(0, (byte) (byte0 | 1 << i));
        } else
        {
            dataWatcher.updateObject(0, (byte) (byte0 & ~(1 << i)));
        }
    }
}
