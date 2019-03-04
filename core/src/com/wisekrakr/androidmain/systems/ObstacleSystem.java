package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.ObstacleComponent;
import com.wisekrakr.androidmain.components.TransformComponent;

public class ObstacleSystem extends IteratingSystem implements SystemEntityContext {

    private AndroidGame game;
    private ComponentMapper<ObstacleComponent>obstacleComponentMapper;
    private ComponentMapper<Box2dBodyComponent>bodyComponentMapper;

    public ObstacleSystem(AndroidGame game) {
        super(Family.all(ObstacleComponent.class).get());
        this.game = game;

        obstacleComponentMapper = ComponentMapper.getFor(ObstacleComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ObstacleComponent obstacleComponent = obstacleComponentMapper.get(entity);
        Box2dBodyComponent bodyComponent = bodyComponentMapper.get(entity);

        if (bodyComponent.body.getPosition().x + obstacleComponent.width/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - obstacleComponent.width/2 < 0){
            obstacleComponent.velocityX = -obstacleComponent.velocityX;
        }else if (bodyComponent.body.getPosition().y + obstacleComponent.height/2 > GameConstants.WORLD_HEIGHT ||
                bodyComponent.body.getPosition().y - obstacleComponent.height/2 < 0){
            obstacleComponent.velocityY = -obstacleComponent.velocityY;
        }

        if (obstacleComponent.destroy) {
           destroy(entity, bodyComponent);
        }else {
            bodyComponent.body.setLinearVelocity(obstacleComponent.velocityX, obstacleComponent.velocityY);
        }
    }

    @Override
    public void destroy(Entity entity, Box2dBodyComponent bodyComponent) {
        bodyComponent.isDead = true;
        game.getGameThread().getEntityFactory().getTotalObstacles().remove(entity);
    }

    @Override
    public void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent) {

    }

    @Override
    public void powerHandler(Entity entity, Box2dBodyComponent bodyComponent) {

    }
}
