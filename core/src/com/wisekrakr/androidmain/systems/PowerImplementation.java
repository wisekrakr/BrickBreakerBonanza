package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

import java.util.ArrayList;
import java.util.List;

/**
 * Initiate this system in EntitySystem since we did not extend this system with an IteratingSystem.
 *
 */

public class PowerImplementation implements SystemEntityContext {

    private List<Entity> powersList = new ArrayList<Entity>();

    public enum PowerStage {
        INIT, REST, UPDATE, EXIT
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
     * @param entity is the power currently in game
     */

    public void updatingPowerUpSystem(Entity entity){



        switch (powerStage) {
            case INIT:
                powerContext.init();
                break;
            case REST:
                powerContext.respite(entity);
                break;
            case UPDATE:
                powerContext.powerTime(entity);
                break;
            case EXIT:
                powerContext.exit(entity);
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

            Entity power = game.getGameThread().getEntityFactory().createPower(
                    GameHelper.notFilledPosition(game).x,
                    GameHelper.notFilledPosition(game).y,
                    GameHelper.randomDirection() * 10000000f,
                    GameHelper.randomDirection() * 10000000f
            );

            powersList.add(power);

            PowerHelper.setPowerUp(power, PowerHelper.randomPowerUp());

            System.out.println(PowerHelper.getPowerUpMap() + " " + power.getComponent(TransformComponent.class).position);//todo remove

            setPowerStage(PowerStage.REST);
        }

        @Override
        public void respite(Entity entity) {

            CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);
            Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
            GameObjectComponent gameObjectComponent = game.getGameThread().getComponentMapperSystem().getGameObjectComponentMapper().get(entity);

            if (collisionComponent.hitPlayer) {
                ScoreKeeper.setPointsToGive(1000);

                collisionComponent.setHitPlayer(false);
                setPowerStage(PowerStage.UPDATE);
            }

            outOfBounds(entity, bodyComponent, gameObjectComponent);
        }

        @Override
        public void powerTime(Entity entity) {

//            switch (PowerHelper.getPower()) {
//
//                case ENLARGE_PLAYER:
//                    System.out.println("Enlarge player power UP");
//                    for (EntitySystem system : game.getEngine().getSystems()) {
//                        if (system instanceof PlayerSystem) {
//                            ((PlayerSystem) system).powerHandler(game.getGameThread().getEntityFactory().getPlayer(),
//                                    bodyComponentMapper.get(game.getGameThread().getEntityFactory().getPlayer())
//                            );
//                        }
//                    }
//                    setPowerStage(PowerStage.EXIT);
//                    break;
//                case REDUCE_PLAYER:
//                    System.out.println("Shorten player power down");
//                    for (EntitySystem system : game.getEngine().getSystems()) {
//                        if (system instanceof PlayerSystem) {
//                            ((PlayerSystem) system).powerHandler(game.getGameThread().getEntityFactory().getPlayer(),
//                                    bodyComponentMapper.get(game.getGameThread().getEntityFactory().getPlayer())
//                            );
//                        }
//                    }
//                    setPowerStage(PowerStage.EXIT);
//                    break;
//                case BIGGER_BALL:
//                    System.out.println("Bigger Ball power UP ");
//                    for (EntitySystem system : game.getEngine().getSystems()) {
//                        if (system instanceof BallSystem) {
//                            for (Entity ent : game.getGameThread().getEntityFactory().getTotalBalls()) {
//                                ((BallSystem) system).powerHandler(ent,
//                                        bodyComponentMapper.get(ent)
//                                );
//                            }
//                        }
//                    }
//                    setPowerStage(PowerStage.EXIT);
//                    break;
//                case EXTRA_LIFE:
//                    System.out.println("Extra Life power up");
//                    for (EntitySystem system : game.getEngine().getSystems()) {
//                        if (system instanceof PlayerSystem) {
//                            ((PlayerSystem) system).powerHandler(game.getGameThread().getEntityFactory().getPlayer(),
//                                    bodyComponentMapper.get(game.getGameThread().getEntityFactory().getPlayer())
//                            );
//                        }
//                    }
//                    setPowerStage(PowerStage.EXIT);
//                    break;
//            }
        }

        @Override
        public void exit(Entity entity) {
            Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
            GameObjectComponent gameObjectComponent = game.getGameThread().getComponentMapperSystem().getGameObjectComponentMapper().get(entity);
            destroy(entity, bodyComponent, gameObjectComponent);
            timeCount = 0;
        }
    };

    @Deprecated
    public void powerHandler(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent) {

    }

    @Override
    public void destroy(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent) {
        bodyComponent.isDead = true;
        gameObjectComponent.setDestroy(true);
        gameObjectComponent.setOutOfBounds(false);
    }

    @Override
    public void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent){

        if (bodyComponent.body.getPosition().x + gameObjectComponent.width/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - gameObjectComponent.width/2 < 0 ||
                bodyComponent.body.getPosition().y + gameObjectComponent.height/2 > GameConstants.WORLD_HEIGHT ||
                bodyComponent.body.getPosition().y - gameObjectComponent.height/2 < 0) {

            System.out.println("Power is out of bounds");
            destroy(entity, bodyComponent, gameObjectComponent);
            gameObjectComponent.setOutOfBounds(true);
        }
    }
}
