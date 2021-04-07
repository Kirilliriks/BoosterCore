package net.minecraft.src.network;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 


import net.minecraft.src.*;

public class NetHandler
{

    public NetHandler()
    {
    }

    public void handleMapChunk(Packet51MapChunk packet51mapchunk)
    {
    }

    public void registerPacket(Packet packet)
    {
    }

    public void handleErrorMessage(String s, Object aobj[])
    {
    }

    public void func_26503_a(ItemInWorldManager iteminworldmanager)
    {
        registerPacket(iteminworldmanager);
    }

    public void handleLogin(Packet1Login packet1login)
    {
        registerPacket(packet1login);
    }

    public void handleFlying(Packet10Flying packet10flying)
    {
        registerPacket(packet10flying);
    }

    public void func_26504_a(EntityChicken entitychicken)
    {
        registerPacket(entitychicken);
    }

    public void handleBlockDig(Packet14BlockDig packet14blockdig)
    {
        registerPacket(packet14blockdig);
    }

    public void handleBlockChange(Packet53BlockChange packet53blockchange)
    {
        registerPacket(packet53blockchange);
    }

    public void handlePreChunk(Packet50PreChunk packet50prechunk)
    {
        registerPacket(packet50prechunk);
    }

    public void handleNamedEntitySpawn(Packet20NamedEntitySpawn packet20namedentityspawn)
    {
        registerPacket(packet20namedentityspawn);
    }

    public void handleEntity(Packet30Entity packet30entity)
    {
        registerPacket(packet30entity);
    }

    public void handleEntityTeleport(Packet34EntityTeleport packet34entityteleport)
    {
        registerPacket(packet34entityteleport);
    }

    public void handlePlace(Packet15Place packet15place)
    {
        registerPacket(packet15place);
    }

    public void handleBlockItemSwitch(Packet16BlockItemSwitch packet16blockitemswitch)
    {
        registerPacket(packet16blockitemswitch);
    }

    public void handleDestroyEntity(Packet29DestroyEntity packet29destroyentity)
    {
        registerPacket(packet29destroyentity);
    }

    public void handlePickupSpawn(Packet21PickupSpawn packet21pickupspawn)
    {
        registerPacket(packet21pickupspawn);
    }

    public void handleCollect(Packet22Collect packet22collect)
    {
        registerPacket(packet22collect);
    }

    public void handleChat(Packet3Chat packet3chat)
    {
        registerPacket(packet3chat);
    }

    public void handleVehicleSpawn(Packet23VehicleSpawn packet23vehiclespawn)
    {
        registerPacket(packet23vehiclespawn);
    }

    public void handleArmAnimation(Packet18ArmAnimation packet18armanimation)
    {
        registerPacket(packet18armanimation);
    }

    public void func_21001_a(Packet19 packet19)
    {
        registerPacket(packet19);
    }

    public void handleHandshake(Packet2Handshake packet2handshake)
    {
        registerPacket(packet2handshake);
    }

    public void handleMobSpawn(Packet24MobSpawn packet24mobspawn)
    {
        registerPacket(packet24mobspawn);
    }

    public void handleUpdateTime(Packet4UpdateTime packet4updatetime)
    {
        registerPacket(packet4updatetime);
    }

    public void handleSpawnPosition(Packet6SpawnPosition packet6spawnposition)
    {
        registerPacket(packet6spawnposition);
    }

    public void func_6002_a(Packet28 packet28)
    {
        registerPacket(packet28);
    }

    public void func_21002_a(Packet40 packet40)
    {
        registerPacket(packet40);
    }

    public void func_6003_a(Packet39 packet39)
    {
        registerPacket(packet39);
    }

    public void func_6006_a(Packet7 packet7)
    {
        registerPacket(packet7);
    }

    public void func_9001_a(Packet38 packet38)
    {
        registerPacket(packet38);
    }

    public void handleHealth(Packet8 packet8)
    {
        registerPacket(packet8);
    }

    public void handleRespawnPacket(Packet9 packet9)
    {
        registerPacket(packet9);
    }

    public void func_12001_a(Packet60 packet60)
    {
        registerPacket(packet60);
    }

    public void func_20004_a(Packet100 packet100)
    {
        registerPacket(packet100);
    }

    public void handleCraftingGuiClosedPacked(Packet101 packet101)
    {
        registerPacket(packet101);
    }

    public void func_20007_a(Packet102 packet102)
    {
        registerPacket(packet102);
    }

    public void func_20003_a(Packet103 packet103)
    {
        registerPacket(packet103);
    }

    public void func_20001_a(Packet104 packet104)
    {
        registerPacket(packet104);
    }

    public void func_20005_a(Packet130 packet130)
    {
        registerPacket(packet130);
    }

    public void func_20002_a(Packet105 packet105)
    {
        registerPacket(packet105);
    }

    public void handlePlayerInventory(Packet5PlayerInventory packet5playerinventory)
    {
        registerPacket(packet5playerinventory);
    }

    public void func_20008_a(Packet106 packet106)
    {
        registerPacket(packet106);
    }

    public void func_26502_a(StatBasic statbasic)
    {
        registerPacket(statbasic);
    }

    public void func_26501_a(EntityEgg entityegg)
    {
        registerPacket(entityegg);
    }

    public void func_22002_a(Packet17Sleep packet17sleep)
    {
    }

    public void handleMovementTypePacket(Packet27 packet27)
    {
    }

    public void func_25001_a(Packet70 packet70)
    {
    }
}
