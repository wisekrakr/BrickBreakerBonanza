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

        //todo change power part ===> no longer hitPower in powerimplement  but hitball for power

        if (thisType.getType().equals(TypeComponent.Type.BALL)){
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()) {
                        case BRICK:
                            collisionComponent.setHitEntity(true);
                            collisionComponentMapper.get(collidedEntity).setHitBall(true);
                            break;
                        case POWER:
                            collisionComponent.setHitPower(true);
                            collisionComponentMapper.get(collidedEntity).setHitBall(true);
                            System.out.println(StringHelper.ANSI_RED_BACKGROUND + "Ball hit Power" + StringHelper.ANSI_RESET); //todo remove
                            break;
                        case SCENERY:
                            collisionComponent.setHitSurface(true);
                            break;
                        case OBSTACLE:
                            collisionComponent.setHitObstacle(true);
                            break;
                        case PLAYER:
                            collisionComponent.setHitPlayer(true);
                            collisionComponentMapper.get(collidedEntity).setHitBall(true);
                            System.out.println(StringHelper.ANSI_CYAN_BACKGROUND + "Ball hit Player" + StringHelper.ANSI_RESET);//todo remove
                            break;
                        default:
                            //System.out.println("ball: No matching type found " );
                    }
                    collisionComponent.collisionEntity = null;
                    collisionComponent.setHitEntity(false);
                }
            }
        }else if (thisType.getType().equals(TypeComponent.Type.POWER)) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    if (typeComponent.getType() == TypeComponent.Type.BALL) {
                        collisionComponent.setHitBall(true);
                        collidedEntity.getComponent(CollisionComponent.class).setHitPower(true);
                        System.out.println(StringHelper.ANSI_GREEN_BACKGROUND + "Power hit Ball" + StringHelper.ANSI_RESET); //todo remove
                    }
                    collisionComponent.collisionEntity = null;//
                }
            }
        }else if (thisType.getType().equals(TypeComponent.Type.BRICK)) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    if (typeComponent.getType() == TypeComponent.Type.BALL) {
                        collisionComponent.setHitBall(true);
                        collisionComponentMapper.get(collidedEntity).setHitEntity(true);
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }else if (thisType.getType().equals(TypeComponent.Type.PLAYER)) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    if (typeComponent.getType() == TypeComponent.Type.BALL) {
                        collisionComponentMapper.get(collidedEntity).setHitPlayer(true);
                        System.out.println("player hit ball");//todo remove
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }
    }
}
