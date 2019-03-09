package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.EntityColor;
import com.wisekrakr.androidmain.components.TypeComponent;

public interface ComponentInitializer {

    void typeComponent(PooledEngine engine, Entity mainEntity, TypeComponent.Type type);
    void collisionComponent(PooledEngine engine, Entity mainEntity);
    void transformComponent(PooledEngine engine, Entity mainEntity, float x, float y, float rotation);
    void textureComponent(PooledEngine engine, Entity mainEntity);
    void levelComponent(PooledEngine engine, Entity mainEntity);
    void brickComponent(PooledEngine engine, Entity mainEntity, Box2dBodyComponent bodyComponent, float width, float height, EntityColor color);
    void obstacleComponent(PooledEngine engine, Entity mainEntity, float width, float height, float velocityX, float velocityY, float x, float y);
    void playerComponent(PooledEngine engine, Entity mainEntity, float width, float height);
    void ballComponent(PooledEngine engine, Entity mainEntity, Box2dBodyComponent bodyComponent, float radius);
    void powerUpComponent(PooledEngine engine, Entity mainEntity, Box2dBodyComponent bodyComponent, float velocityX, float velocityY, float width, float height);

//    void rowComponent(PooledEngine engine, Entity mainEntity, float x, float y, float width, float height, EntityColor color, List<Entity> bricksInRow);
}
