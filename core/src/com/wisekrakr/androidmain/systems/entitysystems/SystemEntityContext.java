package com.wisekrakr.androidmain.systems.entitysystems;

import com.badlogic.ashley.core.Entity;

public interface SystemEntityContext {


    void outOfBounds(Entity entity);
    void bodyHandler(Entity entity);
    void destroy(Entity entity);
}
