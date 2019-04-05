package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.wisekrakr.androidmain.components.EntityColor;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.helpers.PowerHelper;

public interface ComponentInitializer {

    void typeComponent(PooledEngine engine, Entity mainEntity, TypeComponent.Type type);
    void collisionComponent(PooledEngine engine, Entity mainEntity);
    void transformComponent(PooledEngine engine, Entity mainEntity, float x, float y, float rotation);
    void textureComponent(PooledEngine engine, Entity mainEntity);
    void obstacleComponent(PooledEngine engine, Entity mainEntity, float x, float y, float width, float height, float velocityX, float velocityY);
    void playerComponent(PooledEngine engine, Entity mainEntity, float x, float y, float width, float height);
    void ballComponent(PooledEngine engine, Entity mainEntity, float x, float y, float radius, float direction);
    void powerUpComponent(PooledEngine engine, Entity mainEntity, float x, float y, float velocityX, float velocityY, float width, float height, PowerHelper.Power power);
    void brickComponent(PooledEngine engine, Entity mainEntity, float x, float y, float width, float height, EntityColor color);

}
