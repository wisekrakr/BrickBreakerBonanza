package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.ObstacleComponent;

public class ObstacleSystem extends IteratingSystem implements SystemEntityContext {

    private AndroidGame game;

    public ObstacleSystem(AndroidGame game) {
        super(Family.all(ObstacleComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);


        if (obstacleComponent.isDestroy()) {
            destroy(entity);
        }else {
            bodyComponent.body.setLinearVelocity(obstacleComponent.getVelocityX(), obstacleComponent.getVelocityY());
        }

        outOfBounds(entity);
        bodyHandler(entity, bodyComponent);
    }

    @Override
    public void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);

        obstacleComponent.setPosition(bodyComponent.body.getPosition());
        obstacleComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        obstacleComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
        obstacleComponent.setDirection(bodyComponent.body.getAngle());
    }

    @Override
    public void destroy(Entity entity) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        bodyComponent.isDead = true;
    }

    @Override
    public void outOfBounds(Entity entity) {
        ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (obstacleComponent.getPosition().x + obstacleComponent.getWidth()/2 > GameConstants.WORLD_WIDTH ||
                obstacleComponent.getPosition().x - obstacleComponent.getWidth()/2 < 0){
            bodyComponent.body.setLinearVelocity(-obstacleComponent.getVelocityX(), obstacleComponent.getVelocityY());
        }else if (obstacleComponent.getPosition().y + obstacleComponent.getHeight()/2 > GameConstants.WORLD_HEIGHT ||
                obstacleComponent.getPosition().y - obstacleComponent.getHeight()/2 < 0){
            bodyComponent.body.setLinearVelocity(obstacleComponent.getVelocityX(), -obstacleComponent.getVelocityY());
        }
    }
}
