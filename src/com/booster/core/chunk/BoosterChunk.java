package com.booster.core.chunk;

import com.booster.api.chunk.Chunk;

public class BoosterChunk implements Chunk {

    private final net.minecraft.src.chunk.Chunk chunk;

    public BoosterChunk(net.minecraft.src.chunk.Chunk chunk) {
        this.chunk = chunk;
    }
}
