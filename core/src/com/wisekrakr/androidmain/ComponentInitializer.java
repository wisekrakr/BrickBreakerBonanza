package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.wisekrakr.androidmain.components.EntityStyle;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.helpers.PowerHelper;

import java.util.List;

public interface ComponentInitializer {

    void typeComponent(PooledEngine engine, Entity mainEntity, TypeComponent.Type type);
    void collisionComponent(PooledEngine engine, Entity mainEntity);
    void transformComponent(PooledEngine engine, Entity mainEntity, float x, float y, float rotation);
    void textureComponent(PooledEngine engine, Entity mainEntity);
    void obstacleComponent(PooledEngine engine, Entity mainEntity, float x, float y, float velocityX, float velocityY, float width, float height);
    void playerComponent(PooledEngine engine, Entity mainEntity, float x, float y, float width, float height, List<Entity> attachedEntities, float penisLength, float penisGirth, EntityStyle entityStyle);
    void enemyComponent(PooledEngine engine, Entity mainEntity, float initialX, float initialY, float width, float height, EntityStyle entityStyle, List<Entity> attachedEntities, float penisLength, float penisGirth);
    void powerUpComponent(PooledEngine engine, Entity mainEntity, float x, float y, float velocityX, float velocityY, float width, float height, PowerHelper.Power power);
    void penisComponent(PooledEngine engine, Entity mainEntity, Entity attachedEntity, float velocityX, float velocityY, float length, float girth, float direction);
    void testicleComponent(PooledEngine engine, Entity mainEntity, Entity attachedEntity, float velocityX, float velocityY, float radius);
    void wallComponent(PooledEngine engine, Entity mainEntity, float x, float y, float width, float height);
}
