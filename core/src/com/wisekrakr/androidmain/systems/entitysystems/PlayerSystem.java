package com.wisekrakr.androidmain.systems.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.EntityKeeper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

import java.util.Iterator;

public class PlayerSystem extends IteratingSystem implements SystemEntityContext {

    private BricksGame game;

    @SuppressWarnings("unchecked")
    public PlayerSystem(BricksGame game){
        super(Family.all(PlayerComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

        if (!playerComponent.isHasBall()){
            for (Entity ent: game.getEngine().getEntities()){
                if (ent.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BALL){

                    ent.getComponent(Box2dBodyComponent.class).body.setTransform(
                            playerComponent.getPosition().x,
                            playerComponent.getPosition().y + GameConstants.BALL_RADIUS,
                            playerComponent.getShootDirection()
                    );
                }
            }
        }
        if (playerComponent.isDestroy()){
            destroy(entity);
        }

        bodyHandler(entity);
        outOfBounds(entity);
    }

    @Override
    public void bodyHandler(Entity entity) {
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        playerComponent.setPosition(bodyComponent.body.getPosition());
        playerComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        playerComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
    }

    @Override
    public void destroy(Entity entity) {
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        playerComponent.setDestroy(false);
        EntityKeeper.setPlayersInPlay(0);
        bodyComponent.isDead = true;

    }

    @Override
    public void outOfBounds(Entity entity){
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (bodyComponent.body.getPosition().x + playerComponent.getWidth()/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - playerComponent.getWidth()/2 < 0){
           bodyComponent.body.setLinearVelocity(-playerComponent.getVelocityX(), 0);
        }
    }
}
