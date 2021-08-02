package com.booster.core.entity;

import com.booster.api.entity.Entity;
import com.booster.core.util.Vector;
import net.minecraft.src.entity.EntityPlayerMP;

public class BoosterEntity <T extends net.minecraft.src.entity.Entity> implements Entity {

    private final T entity;

    public BoosterEntity(T entity) {
        this.entity = entity;
    }

    public T getEntity() {
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

    public static BoosterEntity<? extends net.minecraft.src.entity.Entity> newBoosterEntity(net.minecraft.src.entity.Entity entity){
        if (entity instanceof EntityPlayerMP) return new BoosterPlayer((EntityPlayerMP) entity);
        return null;

        // TODO Добавить когда все сущности будут работать на API
        // throw new RuntimeException("Find unknown entity " + entity.getClass().getName());
    }
}
