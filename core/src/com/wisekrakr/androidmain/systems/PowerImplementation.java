package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Initiate this system in EntitySystem since we did not extend this system with an IteratingSystem.
 *
 */

public class PowerImplementation implements SystemEntityContext {

    private ConcurrentLinkedQueue<Entity> powersList = new ConcurrentLinkedQueue<Entity>();

    public enum PowerStage {
        INIT, REST, UPDATE, EXIT
    }

    public PowerStage getPowerStage() {
        return powerStage;
    }

    private void setPowerStage(PowerStage powerStage) {
        this.powerStage = powerStage;
    }

    private PowerStage powerStage = PowerStage.INIT;

    private float timeCount = 0f;

    private AndroidGame game;
    private int spawnInterval;

    public PowerImplementation(AndroidGame game, int spawnInterval){
        this.game = game;
        this.spawnInterval = spawnInterval;

    }

    /**
     * Initiate this method in the processEntity method in LevelModel
     *
     */

    public void updatingPowerUpSystem(){

        switch (powerStage) {
            case INIT:
                powerContext.init();
                break;
            case REST:
                powerContext.respite();
                break;
            case UPDATE:
                powerContext.powerTime();
                break;
            case EXIT:
                powerContext.exit();
                break;
            default:
                System.out.println("no power stage");
        }
    }

    public PowerContext getPowerContext() {
        return powerContext;
    }

    private PowerContext powerContext = new PowerContext() {

        @Override
        public void init(){

            if (timeCount == 0){
                timeCount = game.getGameThread().getTimeKeeper().gameClock;
            }
            if (game.getGameThread().getTimeKeeper().gameClock - timeCount > spawnInterval){
                if (powersList.size() == 0) {
                    spawnPower();
                }
            }

        }

        @Override
        public void spawnPower() {

//            Entity power = game.getGameThread().getEntityFactory().createPower(
//                    GameHelper.notFilledPosition(game).x,
//                    GameHelper.notFilledPosition(game).y,
//                    GameHelper.randomDirection() * 10000000f,
//                    GameHelper.randomDirection() * 10000000f,
//                    PowerHelper.randomPowerUp()
//            );

//            powersList.add(power);
//
//            System.out.println(PowerHelper.getPowerUpMap() + " " + power.getComponent(TransformComponent.class).position);//todo remove

            setPowerStage(PowerStage.REST);

        }

        @Override
        public void respite() {

            for (Entity entity: powersList) {

                CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);
                Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

                bodyHandler(entity, bodyComponent);

                if (collisionComponent.hitPlayer) {
                    ScoreKeeper.setPointsToGive(1000);

                    collisionComponent.setHitPlayer(false);
                    setPowerStage(PowerStage.UPDATE);
                }

                outOfBounds(entity);
            }

        }

        @Override
        public void powerTime() {

            for (Entity entity: game.getEngine().getEntities()) {

                TypeComponent.Type type = game.getGameThread().getComponentMapperSystem().getTypeComponentMapper().get(entity).getType();

                switch (PowerHelper.getPower()) {
                    case ENLARGE_PLAYER:
                        System.out.println("Enlarge player power UP");
                        for (EntitySystem system : game.getEngine().getSystems()) {
                            if (system instanceof PlayerSystem) {
                                if (type == TypeComponent.Type.PLAYER) {
                                    ((PlayerSystem) system).powerHandler(entity);
                                }
                            }
                        }
                        setPowerStage(PowerStage.EXIT);
                        break;
                    case REDUCE_PLAYER:
                        System.out.println("Shorten player power down");
                        for (EntitySystem system : game.getEngine().getSystems()) {
                            if (system instanceof PlayerSystem) {
                                if (type == TypeComponent.Type.PLAYER) {
                                    ((PlayerSystem) system).powerHandler(entity);
                                }
                            }
                        }
                        setPowerStage(PowerStage.EXIT);
                        break;
                    case ENLARGE_ENEMY:
                        System.out.println("Bigger Ball power UP ");
                        for (EntitySystem system : game.getEngine().getSystems()) {
                            if (system instanceof EnemySystem) {
                                if (type == TypeComponent.Type.ENEMY) {
                                    ((EnemySystem) system).powerHandler(entity);
                                }
                            }
                        }
                        setPowerStage(PowerStage.EXIT);
                        break;
                    case EXTRA_LIFE:
                        System.out.println("Extra Life power up");
                        for (EntitySystem system : game.getEngine().getSystems()) {
                            if (system instanceof PlayerSystem) {
                                if (type == TypeComponent.Type.PLAYER) {
                                    ((PlayerSystem) system).powerHandler(entity);
                                }
                            }
                        }
                        setPowerStage(PowerStage.EXIT);
                        break;
                }
            }
        }

        @Override
        public void exit() {
            for (Entity entity: powersList) {
                powersList.remove(entity);
                destroy(entity);
            }
            timeCount = 0;

            setPowerStage(PowerStage.INIT);
        }
    };

    @Override
    public void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

        powerUpComponent.setPosition(bodyComponent.body.getPosition());
        powerUpComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        powerUpComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
    }

    @Deprecated
    public void powerHandler(Entity entity) {

    }

    @Override
    public void destroy(Entity entity) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

        bodyComponent.isDead = true;
        powerUpComponent.setDestroy(true);
    }

    @Override
    public void outOfBounds(Entity entity){

        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

        if (powerUpComponent.getPosition().x + powerUpComponent.getWidth()/2 > GameConstants.WORLD_WIDTH ||
                powerUpComponent.getPosition().x - powerUpComponent.getWidth()/2 < 0 ||
                powerUpComponent.getPosition().y + powerUpComponent.getHeight()/2 > GameConstants.WORLD_HEIGHT ||
                powerUpComponent.getPosition().y - powerUpComponent.getHeight()/2 < 0) {
            destroy(entity);
        }
    }

}
