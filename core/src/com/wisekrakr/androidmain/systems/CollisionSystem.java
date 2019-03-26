package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.StringHelper;


public class CollisionSystem extends IteratingSystem {

    private AndroidGame game;

    @SuppressWarnings("unchecked")
    public CollisionSystem(AndroidGame game) {
        super(Family.all(CollisionComponent.class).get());
        this.game = game;

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

        Entity collidedEntity = collisionComponent.collisionEntity;

        TypeComponent thisType = ComponentMapper.getFor(TypeComponent.class).get(entity);

        if (thisType.getType().equals(TypeComponent.Type.ENEMY)){
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()) {
                        case SCENERY:
                            collisionComponent.setHitSurface(true);
                            break;
                        case ENEMY:
                            collisionComponent.setHitEnemy(true);
                            break;
                        case OBSTACLE:
                            collisionComponent.setHitObstacle(true);
                            break;
                        case PLAYER:
                            collisionComponent.setHitPlayer(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitEnemy(true);
                            break;
                        case POWER:
                            collisionComponent.setHitPower(true);
                            break;
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }else if (thisType.getType().equals(TypeComponent.Type.POWER)) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()) {
                        case SCENERY:
                            collisionComponent.setHitSurface(true);
                            break;
                        case ENEMY:
                            collisionComponent.setHitEnemy(true);
                            break;
                        case OBSTACLE:
                            collisionComponent.setHitObstacle(true);
                            break;
                        case PLAYER:
                            collisionComponent.setHitPlayer(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitPower(true);
                            break;
                        case POWER:
                            collisionComponent.setHitPower(true);
                            break;
                        case PENIS:
                            collisionComponent.setHitPenis(true);
                            break;
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }else if (thisType.getType().equals(TypeComponent.Type.PLAYER)) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()) {
                        case SCENERY:
                            collisionComponent.setHitSurface(true);
                            break;
                        case ENEMY:
                            collisionComponent.setHitEnemy(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitPlayer(true);
                            break;
                        case OBSTACLE:
                            collisionComponent.setHitObstacle(true);
                            break;
                        case PLAYER:
                            collisionComponent.setHitPlayer(true);
                            break;
                        case POWER:
                            collisionComponent.setHitPower(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitPlayer(true);
                            break;
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }else if (thisType.getType().equals(TypeComponent.Type.SCENERY)) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()) {
                        case ENEMY:
                            collisionComponent.setHitEnemy(true);
                            break;
                        case OBSTACLE:
                            collisionComponent.setHitObstacle(true);
                            break;
                        case PLAYER:
                            collisionComponent.setHitPlayer(true);
                            break;
                        case POWER:
                            collisionComponent.setHitPower(true);
                            break;
                        case PENIS:
                            collisionComponent.setHitPenis(true);
                            break;
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }else if (thisType.getType().equals(TypeComponent.Type.PENIS)) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()){
                        case PLAYER:
                            collisionComponent.setHitPlayer(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitPenis(true);
                            System.out.println("Penis hit player");
                            break;
                        case ENEMY:
                            collisionComponent.setHitEnemy(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitPenis(true);
                            System.out.println("Penis hit ball");
                            break;
                        case PENIS:
                            collisionComponent.setHitPenis(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitPenis(true);
                            System.out.println("Penis hit Penis");
                            break;
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }
    }
}
