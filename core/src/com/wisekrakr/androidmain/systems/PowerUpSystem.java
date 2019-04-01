package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;
import com.wisekrakr.androidmain.retainers.SelectedCharacter;

public class PowerUpSystem extends IteratingSystem implements SystemEntityContext {

    private AndroidGame game;

    public PowerUpSystem(AndroidGame game) {
        super(Family.all(PowerUpComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

        bodyComponent.body.setLinearVelocity(powerUpComponent.getVelocityX(), powerUpComponent.getVelocityY());

        if (collisionComponent.hitPlayer){
            powerHandler(entity);
            if (powerUpComponent.isDestroy()){
                destroy(entity);
            }
        }





        outOfBounds(entity);
        bodyHandler(entity, bodyComponent);
    }

    @Override
    public void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

        powerUpComponent.setPosition(bodyComponent.body.getPosition());
        powerUpComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        powerUpComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);

    }

    private void powerHandler(Entity entity) {
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);
        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

        switch (PowerHelper.getPower()){
            case ENLARGE_PLAYER:
                for (Entity player: game.getEngine().getEntities()) {
                    if (player.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER) {
                        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(player);

                        float length = playerComponent.getPenisLength();
                        float girth = playerComponent.getPenisGirth();

                        playerComponent.setPenisLength(length + (length/5));
                        playerComponent.setPenisGirth(girth + (girth/5));

                        SelectedCharacter.setPenisLengthGirth(playerComponent.getPenisLength(), playerComponent.getPenisGirth());

                        playerComponent.setDestroy(true);
                    }
                }
                break;
            case REDUCE_PLAYER:
                for (Entity player: game.getEngine().getEntities()) {
                    if (player.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER) {

                        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(player);

                        float length = playerComponent.getPenisLength();
                        float girth = playerComponent.getPenisGirth();

                        playerComponent.setPenisLength(length - (length/5));
                        playerComponent.setPenisGirth(girth - (girth/5));

                        SelectedCharacter.setPenisLengthGirth(playerComponent.getPenisLength(), playerComponent.getPenisGirth());

                        playerComponent.setDestroy(true);
                    }
                }
                break;
            case SPEED_BOOST:
                for (Entity enemy: game.getEngine().getEntities()) {
                    if (enemy.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER) {
                        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(enemy);

                        playerComponent.setSpeed(playerComponent.getSpeed() * 2);
                    }
                }
                break;
            case SLOW_MO:
                for (Entity enemy: game.getEngine().getEntities()) {
                    if (enemy.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER) {
                        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(enemy);

                        playerComponent.setSpeed(playerComponent.getSpeed() / 2);
                    }
                }
                break;
            case EXTRA_LIFE:
                ScoreKeeper.setLives(ScoreKeeper.lives + 1);
                break;
        }
        powerUpComponent.setDestroy(true);
        collisionComponent.setHitPlayer(false);

    }

    @Override
    public void destroy(Entity entity) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

        powerUpComponent.setDestroy(false);

        bodyComponent.isDead = true;

        ScoreKeeper.setInitialPowerUps(0);
    }

    @Override
    public void outOfBounds(Entity entity) {
        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (powerUpComponent.getPosition().x + powerUpComponent.getWidth() > GameConstants.WORLD_WIDTH ||
                powerUpComponent.getPosition().x - powerUpComponent.getWidth() < 0){
            bodyComponent.body.setLinearVelocity(-powerUpComponent.getVelocityX(), powerUpComponent.getVelocityY());
        }else if (powerUpComponent.getPosition().y + powerUpComponent.getHeight() > GameConstants.WORLD_HEIGHT ||
                powerUpComponent.getPosition().y - powerUpComponent.getHeight() < 0){
            bodyComponent.body.setLinearVelocity(powerUpComponent.getVelocityX(), -powerUpComponent.getVelocityY());
        }
    }
}
