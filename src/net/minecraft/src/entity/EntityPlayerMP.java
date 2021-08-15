package net.minecraft.src.entity;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.util.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import net.minecraft.src.block.BlockFire;
import net.minecraft.src.chunk.ChunkCoordinate;
import net.minecraft.src.chunk.ChunkCoordinates;
import net.minecraft.src.crafting.*;
import net.minecraft.src.inventory.IInventory;
import net.minecraft.src.item.ItemFood;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.network.NetServerHandler;
import net.minecraft.src.packet.*;
import net.minecraft.src.world.World;

public class EntityPlayerMP extends EntityPlayer implements ICrafting {

    public NetServerHandler playerNetServerHandler;
    public MinecraftServer mcServer;
    public BlockFire itemInWorldManager;
    public double field_9155_d;
    public double field_9154_e;
    public List loadedChunks;
    public Set field_420_ah;
    private int lastHealth;
    private int ticksOfInvuln;
    private ItemStack playerInventory[] = {
            null, null, null, null, null
    };
    private int currentWindowId;
    public boolean isChangingQuantityOnly;

    public EntityPlayerMP(MinecraftServer minecraftserver, World world, String s, BlockFire blockfire) {
        super(world);
        loadedChunks = new LinkedList();
        field_420_ah = new HashSet();
        lastHealth = 0xfa0a1f01;
        ticksOfInvuln = 60;
        currentWindowId = 0;
        ChunkCoordinates chunkcoordinates = world.getSpawnPoint();
        int i = chunkcoordinates.posX;
        int j = chunkcoordinates.posZ;
        int k = chunkcoordinates.posY;
        if(!world.worldProvider.field_26678_e)
        {
            i += rand.nextInt(20) - 10;
            k = world.findTopSolidBlock(i, j);
            j += rand.nextInt(20) - 10;
        }
        setLocationAndAngles((double)i + 0.5D, k, (double)j + 0.5D, 0.0F, 0.0F);
        mcServer = minecraftserver;
        stepHeight = 0.0F;
        blockfire.chanceToEncourageFire = this;
        username = s;
        itemInWorldManager = blockfire;
        yOffset = 0.0F;
    }

    public void func_20057_k()
    {
        currentCraftingInventory.onCraftGuiOpened(this);
    }

    public ItemStack[] getInventory()
    {
        return playerInventory;
    }

    protected void resetHeight()
    {
        yOffset = 0.0F;
    }

    public float getEyeHeight()
    {
        return 1.62F;
    }

    public void onUpdate()
    {
        itemInWorldManager.func_26553_a();
        ticksOfInvuln--;
        currentCraftingInventory.updateCraftingMatrix();
        for(int i = 0; i < 5; i++)
        {
            ItemStack itemstack = getEquipmentInSlot(i);
            if(itemstack != playerInventory[i])
            {
                mcServer.entityTracker.sendPacketToTrackedPlayers(this, new Packet5PlayerInventory(entityId, i, itemstack));
                playerInventory[i] = itemstack;
            }
        }

    }

    public ItemStack getEquipmentInSlot(int i)
    {
        if(i == 0)
        {
            return inventory.getCurrentItem();
        } else
        {
            return inventory.armorInventory[i - 1];
        }
    }

    public void onDeath(Entity entity)
    {
        inventory.dropAllItems();
    }

    public boolean attackEntityFrom(Entity entity, int i)
    {
        if(ticksOfInvuln > 0)
        {
            return false;
        }
        if(!mcServer.pvpOn)
        {
            if(entity instanceof EntityPlayer)
            {
                return false;
            }
            if(entity instanceof EntityArrow)
            {
                EntityArrow entityarrow = (EntityArrow)entity;
                if(entityarrow.owner instanceof EntityPlayer)
                {
                    return false;
                }
            }
        }
        return super.attackEntityFrom(entity, i);
    }

    public void heal(int i)
    {
        super.heal(i);
    }

    public void onUpdateEntity(boolean flag)
    {
        super.onUpdate();
        if(flag && !loadedChunks.isEmpty())
        {
            ChunkCoordinate slotcrafting = (ChunkCoordinate)loadedChunks.get(0);
            if(slotcrafting != null)
            {
                boolean flag1 = false;
                if(playerNetServerHandler.getNumChunkDataPackets() < 2)
                {
                    flag1 = true;
                }
                if(flag1)
                {
                    loadedChunks.remove(slotcrafting);
                    playerNetServerHandler.sendPacket(new Packet51MapChunk(slotcrafting.field_26507_a * 16, 0, slotcrafting.field_26506_b * 16, 16, 128, 16, mcServer.worldManager));
                    List list = mcServer.worldManager.getTileEntityList(slotcrafting.field_26507_a * 16, 0, slotcrafting.field_26506_b * 16, slotcrafting.field_26507_a * 16 + 16, 128, slotcrafting.field_26506_b * 16 + 16);
                    for(int i = 0; i < list.size(); i++)
                    {
                        getTileEntityInfo((TileEntity)list.get(i));
                    }

                }
            }
        }
        if(health != lastHealth)
        {
            playerNetServerHandler.sendPacket(new Packet8(health));
            lastHealth = health;
        }
    }

    private void getTileEntityInfo(TileEntity tileentity)
    {
        if(tileentity != null)
        {
            Packet packet = tileentity.getDescriptionPacket();
            if(packet != null)
            {
                playerNetServerHandler.sendPacket(packet);
            }
        }
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    public void onItemPickup(Entity entity, int i)
    {
        if(!entity.isDead)
        {
            if(entity instanceof EntityItem)
            {
                mcServer.entityTracker.sendPacketToTrackedPlayers(entity, new Packet22Collect(entity.entityId, entityId));
            }
            if(entity instanceof EntityArrow)
            {
                mcServer.entityTracker.sendPacketToTrackedPlayers(entity, new Packet22Collect(entity.entityId, entityId));
            }
        }
        super.onItemPickup(entity, i);
        currentCraftingInventory.updateCraftingMatrix();
    }

    public void swingItem()
    {
        if(!isSwinging)
        {
            swingProgressInt = -1;
            isSwinging = true;
            mcServer.entityTracker.sendPacketToTrackedPlayers(this, new Packet18ArmAnimation(this, 1));
        }
    }

    public void func_22068_s()
    {
    }

    public EnumStatus goToSleep(int i, int j, int k)
    {
        EnumStatus enumstatus = super.goToSleep(i, j, k);
        if(enumstatus == EnumStatus.OK)
        {
            mcServer.entityTracker.sendPacketToTrackedPlayers(this, new Packet17Sleep(this, 0, i, j, k));
        }
        return enumstatus;
    }

    public void wakeUpPlayer(boolean flag, boolean flag1, boolean flag2)
    {
        if(isPlayerSleeping())
        {
            mcServer.entityTracker.sendPacketToTrackedPlayersAndTrackedEntity(this, new Packet18ArmAnimation(this, 3));
        }
        super.wakeUpPlayer(flag, flag1, flag2);
        playerNetServerHandler.teleportTo(posX, posY, posZ, rotationYaw, rotationPitch);
    }

    public void mountEntity(Entity entity)
    {
        super.mountEntity(entity);
        playerNetServerHandler.sendPacket(new Packet39(this, ridingEntity));
        playerNetServerHandler.teleportTo(posX, posY, posZ, rotationYaw, rotationPitch);
    }

    protected void updateFallState(double d, boolean flag)
    {
    }

    public void handleFalling(double d, boolean flag)
    {
        super.updateFallState(d, flag);
    }

    private void func_26607_aa()
    {
        currentWindowId = currentWindowId % 100 + 1;
    }

    public void displayWorkbenchGUI(int i, int j, int k)
    {
        func_26607_aa();
        playerNetServerHandler.sendPacket(new Packet100(currentWindowId, 1, "Crafting", 9));
        currentCraftingInventory = new CraftingInventoryWorkbenchCB(inventory, worldObj, i, j, k);
        currentCraftingInventory.windowId = currentWindowId;
        currentCraftingInventory.onCraftGuiOpened(this);
    }

    public void displayGUIChest(IInventory iinventory)
    {
        func_26607_aa();
        playerNetServerHandler.sendPacket(new Packet100(currentWindowId, 0, iinventory.getInvName(), iinventory.getSizeInventory()));
        currentCraftingInventory = new CraftingInventoryChestCB(inventory, iinventory);
        currentCraftingInventory.windowId = currentWindowId;
        currentCraftingInventory.onCraftGuiOpened(this);
    }

    public void displayGUIFurnace(TileEntityFurnace tileentityfurnace)
    {
        func_26607_aa();
        playerNetServerHandler.sendPacket(new Packet100(currentWindowId, 2, tileentityfurnace.getInvName(), tileentityfurnace.getSizeInventory()));
        currentCraftingInventory = new CraftingInventoryFurnaceCB(inventory, tileentityfurnace);
        currentCraftingInventory.windowId = currentWindowId;
        currentCraftingInventory.onCraftGuiOpened(this);
    }

    public void displayGUIDispenser(TileEntityDispenser tileentitydispenser)
    {
        func_26607_aa();
        playerNetServerHandler.sendPacket(new Packet100(currentWindowId, 3, tileentitydispenser.getInvName(), tileentitydispenser.getSizeInventory()));
        currentCraftingInventory = new CraftingInventoryDispenserCB(inventory, tileentitydispenser);
        currentCraftingInventory.windowId = currentWindowId;
        currentCraftingInventory.onCraftGuiOpened(this);
    }

    public void updateCraftingInventorySlot(CraftingInventoryCB craftinginventorycb, int i, ItemStack itemstack)
    {
        if(craftinginventorycb.getSlot(i) instanceof ItemFood)
        {
            return;
        }
        if(isChangingQuantityOnly)
        {
            return;
        } else
        {
            playerNetServerHandler.sendPacket(new Packet103(craftinginventorycb.windowId, i, itemstack));
            return;
        }
    }

    public void updateCraftingInventory(CraftingInventoryCB craftinginventorycb, List list)
    {
        playerNetServerHandler.sendPacket(new Packet104(craftinginventorycb.windowId, list));
        playerNetServerHandler.sendPacket(new Packet103(-1, -1, inventory.getItemStack()));
    }

    public void updateCraftingInventoryInfo(CraftingInventoryCB craftinginventorycb, int i, int j)
    {
        playerNetServerHandler.sendPacket(new Packet105(craftinginventorycb.windowId, i, j));
    }

    public void onItemStackChanged(ItemStack itemstack)
    {
    }

    public void usePersonalCraftingInventory()
    {
        playerNetServerHandler.sendPacket(new Packet101(currentCraftingInventory.windowId));
        closeCraftingGui();
    }

    public void updateHeldItem()
    {
        if(isChangingQuantityOnly)
        {
            return;
        } else {
            playerNetServerHandler.sendPacket(new Packet103(-1, -1, inventory.getItemStack()));
            return;
        }
    }

    public void closeCraftingGui()
    {
        currentCraftingInventory.onCraftGuiClosed(this);
        currentCraftingInventory = personalCraftingInventory;
    }

    public void setMovementType(float f, float f1, boolean flag, boolean flag1, float f2, float f3)
    {
        moveStrafing = f;
        moveForward = f1;
        isJumping = flag;
        func_21043_b(flag1);
        rotationPitch = f2;
        rotationYaw = f3;
    }
}
