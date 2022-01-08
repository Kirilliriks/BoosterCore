package com.booster.core.world;

import com.booster.api.world.World;
import net.minecraft.src.world.WorldServer;

public final class BoosterWorld implements World {

    private final WorldServer worldServer;

    public BoosterWorld(WorldServer worldServer) {
        this.worldServer = worldServer;
    }
}
