package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.GameObjectComponent;
import com.wisekrakr.androidmain.components.TypeComponent;

public class GameObjectSystem extends IteratingSystem implements SystemEntityContext {

    private AndroidGame game;

    public GameObjectSystem(AndroidGame game) {
        super(Family.all(GameObjectComponent.class).get());
        this.game = game;

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        GameObjectComponent gameObjectComponent = game.getGameThread().getComponentMapperSystem().getGameObjectComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (gameObjectComponent.destroy){
            destroy(entity, bodyComponent, gameObjectComponent);
        }else {
            bodyComponent.body.setLinearVelocity(gameObjectComponent.velocityX, gameObjectComponent.velocityY);
        }

        outOfBounds(entity,bodyComponent, gameObjectComponent);
        powerHandler(entity, bodyComponent, gameObjectComponent);
    }



    @Override
    public void destroy(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent) {
        bodyComponent.isDead = true;
        game.getGameThread().getEntityFactory().getGameObjects().remove(entity);
    }

    @Override
    public void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent) {

        if (bodyComponent.body.getPosition().x + gameObjectComponent.width/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - gameObjectComponent.width/2 < 0 ||
                bodyComponent.body.getPosition().y + gameObjectComponent.height/2 > GameConstants.WORLD_HEIGHT ||
                bodyComponent.body.getPosition().y - gameObjectComponent.height/2 < 0){
            gameObjectComponent.setOutOfBounds(true);

        }else if (bodyComponent.body.getPosition().x + gameObjectComponent.radius/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - gameObjectComponent.radius/2 < 0 ||
                bodyComponent.body.getPosition().y + gameObjectComponent.radius/2 > GameConstants.WORLD_HEIGHT ||
                bodyComponent.body.getPosition().y - gameObjectComponent.radius/2 < 0){
            gameObjectComponent.setOutOfBounds(true);
        }
    }

    @Override
    public void powerHandler(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent) {
        TypeComponent.Type type = game.getGameThread().getComponentMapperSystem().getTypeComponentMapper().get(entity).getType();

        if (type == TypeComponent.Type.PLAYER){
            gameObjectComponent.getSystemEntityContext().powerHandler(entity, bodyComponent, gameObjectComponent);
        }else if (type == TypeComponent.Type.BALL){
            gameObjectComponent.getSystemEntityContext().powerHandler(entity, bodyComponent, gameObjectComponent);
        }else if (type == TypeComponent.Type.POWER){
            gameObjectComponent.getSystemEntityContext().powerHandler(entity, bodyComponent, gameObjectComponent);
        }else if (type == TypeComponent.Type.OBSTACLE){
            gameObjectComponent.getSystemEntityContext().powerHandler(entity, bodyComponent, gameObjectComponent);
        }
    }
}
