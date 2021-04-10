package com.booster.core.entity;

import com.booster.core.BoosterServer;
import com.booster.core.command.CommandSender;
import net.minecraft.src.entity.Entity;
import net.minecraft.src.entity.EntityPlayerMP;
import net.minecraft.src.packet.Packet3Chat;

public class BoosterEntity implements CommandSender {

    private final Entity entity;

    public BoosterEntity(Entity entity){
        this.entity = entity;
        BoosterServer.logger.info("Register new BoosterEntity with id " + entity.entityId);
    }

    public Entity getEntity() {
        return entity;
    }

    public void teleport(double x, double y, double z){
        entity.setPosition(x, y, z);
    }

    @Override
    public void sendMessage(String message) {
        ((EntityPlayerMP)entity).playerNetServerHandler.sendPacket(new Packet3Chat(message));
    }
}
