package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

public interface SystemEntityContext {
    void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent);
    void powerHandler(Entity entity, Box2dBodyComponent bodyComponent);
    void destroy(Entity entity, Box2dBodyComponent bodyComponent);
}
