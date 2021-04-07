package com.kirillirik.core.entity;

import com.kirillirik.core.BoosterServer;
import net.minecraft.src.entity.Entity;

public class BoosterEntity {

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
}
