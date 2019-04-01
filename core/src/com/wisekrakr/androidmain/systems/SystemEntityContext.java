package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

public interface SystemEntityContext {

    void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent); // informational -> get body info and set to entity

    void destroy(Entity entity); // destroy body and entity
    void outOfBounds(Entity entity);
}
