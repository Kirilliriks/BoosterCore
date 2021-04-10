package com.booster.core.plugin;

import com.booster.api.Server;
import com.booster.api.plugin.Plugin;
import com.booster.core.BoosterServer;

public abstract class JavaPlugin implements Plugin {

    private Server boosterServer;

    public void initialize(BoosterServer boosterServer){
        this.boosterServer = boosterServer;
    }

    public Server getBoosterServer() {
        return boosterServer;
    }
}
