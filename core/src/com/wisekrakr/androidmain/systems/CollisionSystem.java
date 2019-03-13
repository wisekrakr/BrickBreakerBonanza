package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.StringHelper;


public class CollisionSystem extends IteratingSystem {
    private ComponentMapper<CollisionComponent> collisionComponentMapper;
    private ComponentMapper<TransformComponent> transformComponentMapper;

    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        super(Family.all(CollisionComponent.class).get());
        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
        transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        CollisionComponent collisionComponent = collisionComponentMapper.get(entity);

        Entity collidedEntity = collisionComponent.collisionEntity;

        TypeComponent thisType = ComponentMapper.getFor(TypeComponent.class).get(entity);

        if (thisType.getType().equals(TypeComponent.Type.BALL)){
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()) {
                        case POWER:
                            collisionComponent.setHitPower(true);
                            collisionComponentMapper.get(collidedEntity).setHitBall(true);
                            break;
                        case SCENERY:
                            collisionComponent.setHitSurface(true);
                            break;
                        case OBSTACLE:
                            collisionComponent.setHitObstacle(true);
                            break;
                        case PLAYER:
                            collisionComponent.setHitPlayer(true);
                            System.out.println(StringHelper.ANSI_RED_BACKGROUND +"Ball hit Player" + StringHelper.ANSI_RESET);
                            break;
                        default:
                            //System.out.println("ball: No matching type found " );
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }else if (thisType.getType().equals(TypeComponent.Type.POWER)) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    if (typeComponent.getType() == TypeComponent.Type.PLAYER) {
                        collisionComponent.setHitPlayer(true);
                        collidedEntity.getComponent(CollisionComponent.class).setHitPower(true);
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }else if (thisType.getType().equals(TypeComponent.Type.PLAYER)) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    if (typeComponent.getType() == TypeComponent.Type.BALL) {
                        collisionComponent.setHitBall(true);
                        collisionComponentMapper.get(collidedEntity).setHitPlayer(true);
                        System.out.println(StringHelper.ANSI_GREEN_BACKGROUND +"Player hit Ball" + StringHelper.ANSI_RESET);
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }
    }
}
