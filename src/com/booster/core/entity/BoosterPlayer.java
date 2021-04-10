package com.booster.core.entity;

import com.booster.api.entity.Player;
import com.booster.core.command.CommandSender;
import net.minecraft.src.entity.EntityPlayerMP;
import net.minecraft.src.packet.Packet3Chat;

public class BoosterPlayer extends BoosterEntity<EntityPlayerMP> implements Player, CommandSender {

    public BoosterPlayer(EntityPlayerMP entity) {
        super(entity);
    }

    @Override
    public EntityPlayerMP getEntity() {
        return super.getEntity();
    }

    @Override
    public void sendMessage(String message) {
        getEntity().playerNetServerHandler.sendPacket(new Packet3Chat(message));
    }
}
