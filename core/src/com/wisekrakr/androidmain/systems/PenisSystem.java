package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;

public class PenisSystem extends IteratingSystem implements SystemEntityContext {

    private AndroidGame game;

    public PenisSystem(AndroidGame game) {
        super(Family.all(PenisComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PenisComponent penisComponent = game.getGameThread().getComponentMapperSystem().getPenisComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

        if (collisionComponent.hitEnemy){
            collisionComponent.setHitEnemy(false);
        }else if (collisionComponent.hitPlayer){
            collisionComponent.setHitPlayer(false);
        }

        outOfBounds(entity);
        bodyHandler(entity, bodyComponent);
    }

    @Override
    public void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        PenisComponent penisComponent = game.getGameThread().getComponentMapperSystem().getPenisComponentMapper().get(entity);

        penisComponent.setPosition(bodyComponent.body.getPosition());
        penisComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        penisComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
        penisComponent.setDirection(bodyComponent.body.getAngle());

    }

    @Override
    public void destroy(Entity entity) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        bodyComponent.isDead = true;
    }

    @Override
    public void outOfBounds(Entity entity) {
        PenisComponent penisComponent = game.getGameThread().getComponentMapperSystem().getPenisComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (penisComponent.getPosition().x + penisComponent.getLength()/2 > GameConstants.WORLD_WIDTH ||
                penisComponent.getPosition().x - penisComponent.getLength()/2 < 0){
            bodyComponent.body.setLinearVelocity(-penisComponent.getVelocityX(), penisComponent.getVelocityY());
        }else if (penisComponent.getPosition().y + penisComponent.getGirth()/2 > GameConstants.WORLD_HEIGHT ||
                penisComponent.getPosition().y - penisComponent.getGirth()/2 < 0){
            bodyComponent.body.setLinearVelocity(penisComponent.getVelocityX(), -penisComponent.getVelocityY());
        }
    }
}
