package com.booster.core.entity;

import com.booster.api.entity.Player;
import com.booster.api.command.CommandSender;
import com.booster.core.util.Vector;
import net.minecraft.src.entity.Entity;
import net.minecraft.src.entity.EntityPlayer;
import net.minecraft.src.packet.Packet3Chat;

public class BoosterPlayer extends BoosterEntity implements Player, CommandSender {

    public BoosterPlayer(EntityPlayer entity) {
        super(entity);
    }

    @Override
    public EntityPlayer getEntityHandler() {
        return (EntityPlayer) entityHandler;
    }

    @Override
    public void sendMessage(String message) {
        getEntityHandler().playerNetServerHandler.sendPacket(new Packet3Chat(message));
    }

    @Override
    public void teleport(Vector vector) {
        getHandle().playerNetServerHandler.teleportTo(vector);
    }

    @Override
    public EntityPlayer getHandle() {
        return (EntityPlayer) super.getHandle();
    }
}
