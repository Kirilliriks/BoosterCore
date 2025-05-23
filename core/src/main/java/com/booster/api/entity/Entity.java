package com.booster.api.entity;

import com.booster.core.util.Vector;

public interface Entity {

    Vector getPosition();

    void teleport(Vector vector);
}
