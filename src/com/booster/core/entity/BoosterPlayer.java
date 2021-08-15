package com.booster.core.entity;

import com.booster.api.entity.Player;
import com.booster.api.command.CommandSender;
import net.minecraft.src.entity.EntityPlayerMP;
import net.minecraft.src.packet.Packet3Chat;

public class BoosterPlayer extends BoosterEntity implements Player, CommandSender {

    public BoosterPlayer(EntityPlayerMP entity) {
        super(entity);
    }

    @Override
    public EntityPlayerMP getEntity() {
        return (EntityPlayerMP) entity;
    }

    @Override
    public void sendMessage(String message) {
        getEntity().playerNetServerHandler.sendPacket(new Packet3Chat(message));
    }
}
