package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.EntityStyleHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;
import com.wisekrakr.androidmain.retainers.SelectedCharacter;

public class PlayerSystem extends IteratingSystem implements SystemEntityContext {

    private AndroidGame game;

    @SuppressWarnings("unchecked")
    public PlayerSystem(AndroidGame game){
        super(Family.all(PlayerComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        playerComponent.setSpeed(EntityStyleHelper.getStyle().getPenisSpeed());

        if (!playerComponent.isDestroy()) {
            penisHandler(entity);
            if (collisionComponent.hitPenis && playerComponent.isMoving) {
                collisionComponent.setHitPenis(false);
                playerComponent.setDestroy(true);

                ScoreKeeper.setLives(ScoreKeeper.lives - 1);
            }
        }else {
            playerComponent.setMoving(false);
            destroy(entity);
        }

        outOfBounds(entity);
        bodyHandler(entity, bodyComponent);
    }

    private void penisHandler(Entity entity){
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        for (Entity ent: playerComponent.getAttachedEntities()){
            ent.getComponent(Box2dBodyComponent.class).body.setLinearVelocity(
                    bodyComponent.body.getLinearVelocity().x,
                    bodyComponent.body.getLinearVelocity().y
            );
        }
    }

    @Override
    public void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

        playerComponent.setPosition(bodyComponent.body.getPosition());
        playerComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        playerComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
        playerComponent.setDirection(bodyComponent.body.getAngle());
    }

    @Override
    public void destroy(Entity entity) {
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        SelectedCharacter.setDestroyed(true); //In LevelModel this is set to false again.

        playerComponent.setDestroy(false);

        for (Entity ent: playerComponent.getAttachedEntities()){
            ent.getComponent(Box2dBodyComponent.class).isDead = true;
        }
        playerComponent.getAttachedEntities().clear();

        bodyComponent.isDead = true;
    }

    @Override
    public void outOfBounds(Entity entity){
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

        if (playerComponent.getPosition().x > GameConstants.WORLD_WIDTH ||
                playerComponent.getPosition().x  < 0 ||
                playerComponent.getPosition().y  > GameConstants.WORLD_HEIGHT ||
                playerComponent.getPosition().y  < 0){
            playerComponent.setDestroy(true);
        }
    }
}
