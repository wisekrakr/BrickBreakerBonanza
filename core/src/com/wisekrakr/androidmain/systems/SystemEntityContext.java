package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.GameObjectComponent;

public interface SystemEntityContext {
    void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent);
    void powerHandler(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent);
    void destroy(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent);
}
