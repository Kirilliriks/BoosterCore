package com.booster.core.entity;

import com.booster.core.util.Vector;
import net.minecraft.src.entity.Entity;
import net.minecraft.src.entity.EntityPlayer;

public class BoosterEntity implements com.booster.api.entity.Entity {

    protected final Entity entityHandler;

    public BoosterEntity(net.minecraft.src.entity.Entity entityHandler) {
        this.entityHandler = entityHandler;
    }

    public net.minecraft.src.entity.Entity getEntityHandler() {
        return entityHandler;
    }

    @Override
    public Vector getPosition() {
        return new Vector(entityHandler.posX, entityHandler.posY, entityHandler.posZ);
    }

    @Override
    public void teleport(Vector vector){
        entityHandler.setLocationAndAngles(vector.getX(), vector.getY(), vector.getZ(), 0, 0);
    }

    public Entity getHandle() {
        return entityHandler;
    }

    public static BoosterEntity newBoosterEntity(net.minecraft.src.entity.Entity entity){
        if (entity instanceof EntityPlayer) return new BoosterPlayer((EntityPlayer) entity);
        return null;

        // TODO Добавить когда все сущности будут работать на API
        // throw new RuntimeException("Find unknown entity " + entity.getClass().getName());
    }
}
