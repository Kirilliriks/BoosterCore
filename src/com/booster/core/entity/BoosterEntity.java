package com.booster.core.entity;

import com.booster.api.entity.Entity;
import com.booster.core.command.CommandSender;
import net.minecraft.src.entity.EntityPlayerMP;
import net.minecraft.src.packet.Packet3Chat;

public class BoosterEntity implements Entity, CommandSender {

    private final net.minecraft.src.entity.Entity entity;

    public BoosterEntity(net.minecraft.src.entity.Entity entity){
        this.entity = entity;
    }

    public net.minecraft.src.entity.Entity getEntity() {
        return entity;
    }

    @Override
    public void teleport(double x, double y, double z){
        entity.setPosition(x, y, z);
    }

    @Override
    public void sendMessage(String message) {
        ((EntityPlayerMP)entity).playerNetServerHandler.sendPacket(new Packet3Chat(message));
    }
}
