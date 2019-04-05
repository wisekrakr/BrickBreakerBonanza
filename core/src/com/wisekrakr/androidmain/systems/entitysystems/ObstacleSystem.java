package com.wisekrakr.androidmain.systems.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.ObstacleComponent;

public class ObstacleSystem extends IteratingSystem implements SystemEntityContext {

    private BricksGame game;


    public ObstacleSystem(BricksGame game) {
        super(Family.all(ObstacleComponent.class).get());
        this.game = game;


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        outOfBounds(entity);

        if (obstacleComponent.isDestroy()) {
           destroy(entity);
        }else {
            bodyComponent.body.setLinearVelocity(bodyComponent.body.getLinearVelocity().x, bodyComponent.body.getLinearVelocity().y);
        }

        outOfBounds(entity);
    }

    @Override
    public void bodyHandler(Entity entity) {
        ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        obstacleComponent.setPosition(bodyComponent.body.getPosition());
        obstacleComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        obstacleComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
    }

    @Override
    public void destroy(Entity entity) {
        ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        bodyComponent.isDead = true;

        obstacleComponent.setDestroy(false);
    }

    @Override
    public void outOfBounds(Entity entity) {
        ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (bodyComponent.body.getPosition().x + obstacleComponent.getWidth()/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - obstacleComponent.getHeight()/2 < 0){
            obstacleComponent.setVelocityX(-obstacleComponent.getVelocityX());
        }else if (bodyComponent.body.getPosition().y + obstacleComponent.getHeight()/2 > GameConstants.WORLD_HEIGHT ||
                bodyComponent.body.getPosition().y - obstacleComponent.getHeight()/2 < 0){
            obstacleComponent.setVelocityY(-obstacleComponent.getVelocityY());
        }
    }

}
