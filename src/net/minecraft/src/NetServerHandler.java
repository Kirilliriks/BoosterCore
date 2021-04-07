package net.minecraft.src;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

import java.io.PrintStream;
import java.util.*;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public class NetServerHandler extends NetHandler
    implements ICommandListener
{

    public NetServerHandler(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayerMP entityplayermp)
    {
        connectionClosed = false;
        hasMoved = true;
        field_10_k = new HashMap();
        mcServer = minecraftserver;
        netManager = networkmanager;
        networkmanager.setNetHandler(this);
        playerEntity = entityplayermp;
        entityplayermp.playerNetServerHandler = this;
    }

    public void handlePackets()
    {
        field_22003_h = false;
        netManager.processReadPackets();
        if(field_15_f - field_22004_g > 20)
        {
            sendPacket(new Packet0KeepAlive());
        }
    }

    public void kickPlayer(String s)
    {
        sendPacket(new ItemInWorldManager(s));
        netManager.serverShutdown();
        mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append("\247e").append(playerEntity.username).append(" left the game.").toString()));
        mcServer.configManager.playerLoggedOut(playerEntity);
        connectionClosed = true;
    }

    public void handleMovementTypePacket(Packet27 packet27)
    {
        playerEntity.setMovementType(packet27.func_22031_c(), packet27.func_22028_e(), packet27.func_22032_g(), packet27.func_22030_h(), packet27.func_22029_d(), packet27.func_22033_f());
    }

    public void handleFlying(Packet10Flying packet10flying)
    {
        field_22003_h = true;
        if(!hasMoved)
        {
            double d = packet10flying.yPosition - lastPosY;
            if(packet10flying.xPosition == lastPosX && d * d < 0.01D && packet10flying.zPosition == lastPosZ)
            {
                hasMoved = true;
            }
        }
        if(hasMoved)
        {
            if(playerEntity.ridingEntity != null)
            {
                float f = playerEntity.rotationYaw;
                float f1 = playerEntity.rotationPitch;
                playerEntity.ridingEntity.updateRiderPosition();
                double d2 = playerEntity.posX;
                double d4 = playerEntity.posY;
                double d6 = playerEntity.posZ;
                double d8 = 0.0D;
                double d9 = 0.0D;
                if(packet10flying.rotating)
                {
                    f = packet10flying.yaw;
                    f1 = packet10flying.pitch;
                }
                if(packet10flying.moving && packet10flying.yPosition == -999D && packet10flying.stance == -999D)
                {
                    d8 = packet10flying.xPosition;
                    d9 = packet10flying.zPosition;
                }
                playerEntity.onGround = packet10flying.onGround;
                playerEntity.onUpdateEntity(true);
                playerEntity.moveEntity(d8, 0.0D, d9);
                playerEntity.setPositionAndRotation(d2, d4, d6, f, f1);
                playerEntity.motionX = d8;
                playerEntity.motionZ = d9;
                if(playerEntity.ridingEntity != null)
                {
                    mcServer.worldMngr.func_12017_b(playerEntity.ridingEntity, true);
                }
                if(playerEntity.ridingEntity != null)
                {
                    playerEntity.ridingEntity.updateRiderPosition();
                }
                mcServer.configManager.func_613_b(playerEntity);
                lastPosX = playerEntity.posX;
                lastPosY = playerEntity.posY;
                lastPosZ = playerEntity.posZ;
                mcServer.worldMngr.updateEntity(playerEntity);
                return;
            }
            double d1 = playerEntity.posY;
            lastPosX = playerEntity.posX;
            lastPosY = playerEntity.posY;
            lastPosZ = playerEntity.posZ;
            double d3 = playerEntity.posX;
            double d5 = playerEntity.posY;
            double d7 = playerEntity.posZ;
            float f2 = playerEntity.rotationYaw;
            float f3 = playerEntity.rotationPitch;
            if(packet10flying.moving && packet10flying.yPosition == -999D && packet10flying.stance == -999D)
            {
                packet10flying.moving = false;
            }
            if(packet10flying.moving)
            {
                d3 = packet10flying.xPosition;
                d5 = packet10flying.yPosition;
                d7 = packet10flying.zPosition;
                double d10 = packet10flying.stance - packet10flying.yPosition;
                if(d10 > 1.6499999999999999D || d10 < 0.10000000000000001D)
                {
                    kickPlayer("Illegal stance");
                    logger.warning((new StringBuilder()).append(playerEntity.username).append(" had an illegal stance: ").append(d10).toString());
                }
            }
            if(packet10flying.rotating)
            {
                f2 = packet10flying.yaw;
                f3 = packet10flying.pitch;
            }
            playerEntity.onUpdateEntity(true);
            playerEntity.ySize = 0.0F;
            playerEntity.setPositionAndRotation(lastPosX, lastPosY, lastPosZ, f2, f3);
            double d11 = d3 - playerEntity.posX;
            double d12 = d5 - playerEntity.posY;
            double d13 = d7 - playerEntity.posZ;
            float f4 = 0.0625F;
            boolean flag = mcServer.worldMngr.getCollidingBoundingBoxes(playerEntity, playerEntity.boundingBox.copy().func_694_e(f4, f4, f4)).size() == 0;
            playerEntity.moveEntity(d11, d12, d13);
            d11 = d3 - playerEntity.posX;
            d12 = d5 - playerEntity.posY;
            if(d12 > -0.5D || d12 < 0.5D)
            {
                d12 = 0.0D;
            }
            d13 = d7 - playerEntity.posZ;
            double d14 = d11 * d11 + d12 * d12 + d13 * d13;
            boolean flag1 = false;
            if(d14 > 0.0625D && !playerEntity.isPlayerSleeping())
            {
                flag1 = true;
                logger.warning((new StringBuilder()).append(playerEntity.username).append(" moved wrongly!").toString());
                System.out.println((new StringBuilder()).append("Got position ").append(d3).append(", ").append(d5).append(", ").append(d7).toString());
                System.out.println((new StringBuilder()).append("Expected ").append(playerEntity.posX).append(", ").append(playerEntity.posY).append(", ").append(playerEntity.posZ).toString());
            }
            playerEntity.setPositionAndRotation(d3, d5, d7, f2, f3);
            boolean flag2 = mcServer.worldMngr.getCollidingBoundingBoxes(playerEntity, playerEntity.boundingBox.copy().func_694_e(f4, f4, f4)).size() == 0;
            if(flag && (flag1 || !flag2) && !playerEntity.isPlayerSleeping())
            {
                teleportTo(lastPosX, lastPosY, lastPosZ, f2, f3);
                return;
            }
            playerEntity.onGround = packet10flying.onGround;
            mcServer.configManager.func_613_b(playerEntity);
            playerEntity.handleFalling(playerEntity.posY - d1, packet10flying.onGround);
        }
    }

    public void teleportTo(double d, double d1, double d2, float f, 
            float f1)
    {
        hasMoved = false;
        lastPosX = d;
        lastPosY = d1;
        lastPosZ = d2;
        playerEntity.setPositionAndRotation(d, d1, d2, f, f1);
        playerEntity.playerNetServerHandler.sendPacket(new Packet13PlayerLookMove(d, d1 + 1.6200000047683716D, d1, d2, f, f1, false));
    }

    public void handleBlockDig(Packet14BlockDig packet14blockdig)
    {
        if(packet14blockdig.status == 4)
        {
            playerEntity.dropCurrentItem();
            return;
        }
        boolean flag = mcServer.worldMngr.field_819_z = mcServer.configManager.isOp(playerEntity.username);
        boolean flag1 = false;
        if(packet14blockdig.status == 0)
        {
            flag1 = true;
        }
        if(packet14blockdig.status == 2)
        {
            flag1 = true;
        }
        int i = packet14blockdig.xPosition;
        int j = packet14blockdig.yPosition;
        int k = packet14blockdig.zPosition;
        if(flag1)
        {
            double d = playerEntity.posX - ((double)i + 0.5D);
            double d1 = playerEntity.posY - ((double)j + 0.5D);
            double d3 = playerEntity.posZ - ((double)k + 0.5D);
            double d5 = d * d + d1 * d1 + d3 * d3;
            if(d5 > 36D)
            {
                return;
            }
        }
        ChunkCoordinates chunkcoordinates = mcServer.worldMngr.getSpawnPoint();
        int l = (int)MathHelper.abs(i - chunkcoordinates.posX);
        int i1 = (int)MathHelper.abs(k - chunkcoordinates.posZ);
        if(l > i1)
        {
            i1 = l;
        }
        if(packet14blockdig.status == 0)
        {
            if(i1 > 16 || flag)
            {
                playerEntity.itemInWorldManager.setBurnRate(i, j, k);
            }
        } else
        if(packet14blockdig.status == 2)
        {
            playerEntity.itemInWorldManager.func_26552_b(i, j, k);
        } else
        if(packet14blockdig.status == 3)
        {
            double d2 = playerEntity.posX - ((double)i + 0.5D);
            double d4 = playerEntity.posY - ((double)j + 0.5D);
            double d6 = playerEntity.posZ - ((double)k + 0.5D);
            double d7 = d2 * d2 + d4 * d4 + d6 * d6;
            if(d7 < 256D)
            {
                playerEntity.playerNetServerHandler.sendPacket(new Packet53BlockChange(i, j, k, mcServer.worldMngr));
            }
        }
        mcServer.worldMngr.field_819_z = false;
    }

    public void handlePlace(Packet15Place packet15place)
    {
        ItemStack itemstack = playerEntity.inventory.getCurrentItem();
        boolean flag = mcServer.worldMngr.field_819_z = mcServer.configManager.isOp(playerEntity.username);
        if(packet15place.direction == 255)
        {
            if(itemstack == null)
            {
                return;
            }
            playerEntity.itemInWorldManager.func_26554_a(playerEntity, mcServer.worldMngr, itemstack);
        } else
        {
            int i = packet15place.xPosition;
            int j = packet15place.yPosition;
            int k = packet15place.zPosition;
            int l = packet15place.direction;
            ChunkCoordinates chunkcoordinates = mcServer.worldMngr.getSpawnPoint();
            int i1 = (int)MathHelper.abs(i - chunkcoordinates.posX);
            int j1 = (int)MathHelper.abs(k - chunkcoordinates.posZ);
            if(i1 > j1)
            {
                j1 = i1;
            }
            if(j1 > 16 || flag)
            {
                playerEntity.itemInWorldManager.func_26555_a(playerEntity, mcServer.worldMngr, itemstack, i, j, k, l);
            }
            playerEntity.playerNetServerHandler.sendPacket(new Packet53BlockChange(i, j, k, mcServer.worldMngr));
            if(l == 0)
            {
                j--;
            }
            if(l == 1)
            {
                j++;
            }
            if(l == 2)
            {
                k--;
            }
            if(l == 3)
            {
                k++;
            }
            if(l == 4)
            {
                i--;
            }
            if(l == 5)
            {
                i++;
            }
            playerEntity.playerNetServerHandler.sendPacket(new Packet53BlockChange(i, j, k, mcServer.worldMngr));
        }
        if(itemstack != null && itemstack.stackSize == 0)
        {
            playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] = null;
        }
        playerEntity.isChangingQuantityOnly = true;
        playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] = ItemStack.func_20117_a(playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem]);
        Slot slot = playerEntity.currentCraftingInventory.func_20127_a(playerEntity.inventory, playerEntity.inventory.currentItem);
        playerEntity.currentCraftingInventory.updateCraftingMatrix();
        playerEntity.isChangingQuantityOnly = false;
        if(!ItemStack.areItemStacksEqual(playerEntity.inventory.getCurrentItem(), packet15place.itemStack))
        {
            sendPacket(new Packet103(playerEntity.currentCraftingInventory.windowId, slot.id, playerEntity.inventory.getCurrentItem()));
        }
        mcServer.worldMngr.field_819_z = false;
    }

    public void handleErrorMessage(String s, Object aobj[])
    {
        logger.info((new StringBuilder()).append(playerEntity.username).append(" lost connection: ").append(s).toString());
        mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append("\247e").append(playerEntity.username).append(" left the game.").toString()));
        mcServer.configManager.playerLoggedOut(playerEntity);
        connectionClosed = true;
    }

    public void registerPacket(Packet packet)
    {
        logger.warning((new StringBuilder()).append(getClass()).append(" wasn't prepared to deal with a ").append(packet.getClass()).toString());
        kickPlayer("Protocol error, unexpected packet");
    }

    public void sendPacket(Packet packet)
    {
        netManager.addToSendQueue(packet);
        field_22004_g = field_15_f;
    }

    public void handleBlockItemSwitch(Packet16BlockItemSwitch packet16blockitemswitch)
    {
        if(packet16blockitemswitch.id < 0 || packet16blockitemswitch.id > InventoryPlayer.func_25054_e())
        {
            logger.warning((new StringBuilder()).append(playerEntity.username).append(" tried to set an invalid carried item").toString());
            return;
        } else
        {
            playerEntity.inventory.currentItem = packet16blockitemswitch.id;
            return;
        }
    }

    public void handleChat(Packet3Chat packet3chat)
    {
        String s = packet3chat.message;
        if(s.length() > 100)
        {
            kickPlayer("Chat message too long");
            return;
        }
        s = s.trim();
        for(int i = 0; i < s.length(); i++)
        {
            if(FontAllowedCharacters.allowedCharacters.indexOf(s.charAt(i)) < 0)
            {
                kickPlayer("Illegal characters in chat");
                return;
            }
        }

        if(s.startsWith("/"))
        {
            handleSlashCommand(s);
        } else
        {
            s = (new StringBuilder()).append("<").append(playerEntity.username).append("> ").append(s).toString();
            logger.info(s);
            mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat(s));
        }
    }

    private void handleSlashCommand(String s)
    {
        if(s.toLowerCase().startsWith("/me "))
        {
            s = (new StringBuilder()).append("* ").append(playerEntity.username).append(" ").append(s.substring(s.indexOf(" ")).trim()).toString();
            logger.info(s);
            mcServer.configManager.sendPacketToAllPlayers(new Packet3Chat(s));
        } else
        if(s.toLowerCase().startsWith("/kill"))
        {
            playerEntity.attackEntityFrom(null, 1000);
        } else
        if(s.toLowerCase().startsWith("/tell "))
        {
            String as[] = s.split(" ");
            if(as.length >= 3)
            {
                s = s.substring(s.indexOf(" ")).trim();
                s = s.substring(s.indexOf(" ")).trim();
                s = (new StringBuilder()).append("\2477").append(playerEntity.username).append(" whispers ").append(s).toString();
                logger.info((new StringBuilder()).append(s).append(" to ").append(as[1]).toString());
                if(!mcServer.configManager.sendPacketToPlayer(as[1], new Packet3Chat(s)))
                {
                    sendPacket(new Packet3Chat("\247cThere's no player by that name online."));
                }
            }
        } else
        if(mcServer.configManager.isOp(playerEntity.username))
        {
            String s1 = s.substring(1);
            logger.info((new StringBuilder()).append(playerEntity.username).append(" issued server command: ").append(s1).toString());
            mcServer.addCommand(s1, this);
        } else
        {
            String s2 = s.substring(1);
            logger.info((new StringBuilder()).append(playerEntity.username).append(" tried command: ").append(s2).toString());
        }
    }

    public void handleArmAnimation(Packet18ArmAnimation packet18armanimation)
    {
        if(packet18armanimation.animate == 1)
        {
            playerEntity.swingItem();
        }
    }

    public void func_21001_a(Packet19 packet19)
    {
        if(packet19.state == 1)
        {
            playerEntity.func_21043_b(true);
        } else
        if(packet19.state == 2)
        {
            playerEntity.func_21043_b(false);
        } else
        if(packet19.state == 3)
        {
            playerEntity.wakeUpPlayer(false, true, true);
            hasMoved = false;
        }
    }

    public void func_26503_a(ItemInWorldManager iteminworldmanager)
    {
        netManager.networkShutdown("disconnect.quitting", new Object[0]);
    }

    public int getNumChunkDataPackets()
    {
        return netManager.getNumChunkDataPackets();
    }

    public void log(String s)
    {
        sendPacket(new Packet3Chat((new StringBuilder()).append("\2477").append(s).toString()));
    }

    public String getUsername()
    {
        return playerEntity.username;
    }

    public void func_6006_a(Packet7 packet7)
    {
        Entity entity = mcServer.worldMngr.func_6158_a(packet7.targetEntity);
        if(entity != null && playerEntity.canEntityBeSeen(entity) && playerEntity.getDistanceToEntity(entity) < 4F)
        {
            if(packet7.isLeftClick == 0)
            {
                playerEntity.useCurrentItemOnEntity(entity);
            } else
            if(packet7.isLeftClick == 1)
            {
                playerEntity.attackTargetEntityWithCurrentItem(entity);
            }
        }
    }

    public void handleRespawnPacket(Packet9 packet9)
    {
        if(playerEntity.health > 0)
        {
            return;
        } else
        {
            playerEntity = mcServer.configManager.recreatePlayerEntity(playerEntity);
            return;
        }
    }

    public void handleCraftingGuiClosedPacked(Packet101 packet101)
    {
        playerEntity.closeCraftingGui();
    }

    public void func_20007_a(Packet102 packet102)
    {
        if(playerEntity.currentCraftingInventory.windowId == packet102.window_Id && playerEntity.currentCraftingInventory.getCanCraft(playerEntity))
        {
            ItemStack itemstack = playerEntity.currentCraftingInventory.placeItem(packet102.inventorySlot, packet102.mouseClick, playerEntity);
            if(ItemStack.areItemStacksEqual(packet102.itemStack, itemstack))
            {
                playerEntity.playerNetServerHandler.sendPacket(new Packet106(packet102.window_Id, packet102.action, true));
                playerEntity.isChangingQuantityOnly = true;
                playerEntity.currentCraftingInventory.updateCraftingMatrix();
                playerEntity.updateHeldItem();
                playerEntity.isChangingQuantityOnly = false;
            } else
            {
                field_10_k.put(Integer.valueOf(playerEntity.currentCraftingInventory.windowId), Short.valueOf(packet102.action));
                playerEntity.playerNetServerHandler.sendPacket(new Packet106(packet102.window_Id, packet102.action, false));
                playerEntity.currentCraftingInventory.setCanCraft(playerEntity, false);
                ArrayList arraylist = new ArrayList();
                for(int i = 0; i < playerEntity.currentCraftingInventory.inventorySlots.size(); i++)
                {
                    arraylist.add(((Slot)playerEntity.currentCraftingInventory.inventorySlots.get(i)).getStack());
                }

                playerEntity.updateCraftingInventory(playerEntity.currentCraftingInventory, arraylist);
            }
        }
    }

    public void func_20008_a(Packet106 packet106)
    {
        Short short1 = (Short)field_10_k.get(Integer.valueOf(playerEntity.currentCraftingInventory.windowId));
        if(short1 != null && packet106.shortWindowId == short1.shortValue() && playerEntity.currentCraftingInventory.windowId == packet106.windowId && !playerEntity.currentCraftingInventory.getCanCraft(playerEntity))
        {
            playerEntity.currentCraftingInventory.setCanCraft(playerEntity, true);
        }
    }

    public void func_20005_a(Packet130 packet130)
    {
        if(mcServer.worldMngr.blockExists(packet130.xPosition, packet130.yPosition, packet130.zPosition))
        {
            TileEntity tileentity = mcServer.worldMngr.getBlockTileEntity(packet130.xPosition, packet130.yPosition, packet130.zPosition);
            if(tileentity instanceof MobSpawnerRainforest)
            {
                MobSpawnerRainforest mobspawnerrainforest = (MobSpawnerRainforest)tileentity;
                if(!mobspawnerrainforest.func_26608_a())
                {
                    mcServer.func_25002_c((new StringBuilder()).append("Player ").append(playerEntity.username).append(" just tried to change non-editable sign").toString());
                    return;
                }
            }
            for(int i = 0; i < 4; i++)
            {
                boolean flag = true;
                if(packet130.signLines[i].length() > 15)
                {
                    flag = false;
                } else
                {
                    for(int l = 0; l < packet130.signLines[i].length(); l++)
                    {
                        if(FontAllowedCharacters.allowedCharacters.indexOf(packet130.signLines[i].charAt(l)) < 0)
                        {
                            flag = false;
                        }
                    }

                }
                if(!flag)
                {
                    packet130.signLines[i] = "!?";
                }
            }

            if(tileentity instanceof MobSpawnerRainforest)
            {
                int j = packet130.xPosition;
                int k = packet130.yPosition;
                int i1 = packet130.zPosition;
                MobSpawnerRainforest mobspawnerrainforest1 = (MobSpawnerRainforest)tileentity;
                for(int j1 = 0; j1 < 4; j1++)
                {
                    mobspawnerrainforest1.field_26610_a[j1] = packet130.signLines[j1];
                }

                mobspawnerrainforest1.onInventoryChanged();
                mcServer.worldMngr.markBlockNeedsUpdate(j, k, i1);
            }
        }
    }

    public static Logger logger = Logger.getLogger("Minecraft");
    public NetworkManager netManager;
    public boolean connectionClosed;
    private MinecraftServer mcServer;
    private EntityPlayerMP playerEntity;
    private int field_15_f;
    private int field_22004_g;
    private boolean field_22003_h;
    private double lastPosX;
    private double lastPosY;
    private double lastPosZ;
    private boolean hasMoved;
    private Map field_10_k;

}