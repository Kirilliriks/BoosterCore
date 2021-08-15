package com.booster.core.entity;

import com.booster.api.entity.Entity;
import com.booster.core.util.Vector;
import net.minecraft.src.entity.EntityPlayerMP;

public class BoosterEntity implements Entity {

    protected final net.minecraft.src.entity.Entity entity;

    public BoosterEntity(net.minecraft.src.entity.Entity entity) {
        this.entity = entity;
    }

    public net.minecraft.src.entity.Entity getEntity() {
        return entity;
    }

    @Override
    public Vector getPosition() {
        return new Vector(entity.posX, entity.posY, entity.posZ);
    }

    @Override
    public void teleport(Vector vector){
        entity.setPosition(vector.getX(), vector.getY(), vector.getZ());
    }

    public static BoosterEntity newBoosterEntity(net.minecraft.src.entity.Entity entity){
        if (entity instanceof EntityPlayerMP) return new BoosterPlayer((EntityPlayerMP) entity);
        return null;

        // TODO Добавить когда все сущности будут работать на API
        // throw new RuntimeException("Find unknown entity " + entity.getClass().getName());
    }
}
