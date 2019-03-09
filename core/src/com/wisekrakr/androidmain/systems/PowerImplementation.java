package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.EntityColorHelper;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

import java.util.*;

/**
 * Initiate this system in EntitySystem since we did not extend this system with an IteratingSystem.
 *
 */

public class PowerImplementation implements SystemEntityContext {

    public enum PowerStage {
        INIT, REST, UPDATE, EXIT
    }

    private void setPowerStage(PowerStage powerStage) {
        this.powerStage = powerStage;
    }

    private PowerStage powerStage = PowerStage.INIT;

    private float timeCount = 0f;

    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;
    private ComponentMapper<PowerUpComponent> powerUpComponentMapper;
    private ComponentMapper<CollisionComponent>collisionComponentMapper;

    private AndroidGame game;
    private int spawnInterval;

    public PowerImplementation(AndroidGame game, int spawnInterval){
        this.game = game;
        this.spawnInterval = spawnInterval;

        powerUpComponentMapper = ComponentMapper.getFor(PowerUpComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
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
                if (game.getGameThread().getEntityFactory().getTotalPowers().size() == 0) {
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
                    GameHelper.randomDirection() * 10000000f,
                    GameConstants.POWER_WIDTH,
                    GameConstants.POWER_HEIGHT
            );

            PowerHelper.setPowerUp(power, PowerHelper.randomPowerUp());

            System.out.println(PowerHelper.getPowerUpMap() + " " + power.getComponent(PowerUpComponent.class).position);//todo remove

            setPowerStage(PowerStage.REST);
        }

        @Override
        public void respite(Entity entity) {

            CollisionComponent collisionComponent = collisionComponentMapper.get(entity);
            Box2dBodyComponent bodyComponent = bodyComponentMapper.get(entity);

            if (collisionComponent.hitBall) {
                ScoreKeeper.setPointsToGive(1000);

                collisionComponent.setHitBall(false);
                setPowerStage(PowerStage.UPDATE);
            }

            outOfBounds(entity, bodyComponent);
        }

        @Override
        public void powerTime(Entity entity) {

            int randomBrick = GameHelper.randomGenerator.nextInt(game.getGameThread().getEntityFactory().getTotalBricks().size());

            switch (PowerHelper.getPower()) {
                case NUKE:
                    System.out.println("Nuke power UP");
                    List<Entity> sub = game.getGameThread().getEntityFactory().getTotalBricks().subList(0,
                            game.getGameThread().getEntityFactory().getTotalBricks().size() / 10);
                    ArrayList<Entity> toBeKilled = new ArrayList<Entity>(sub);

                    for (Entity ent: toBeKilled){
                        ent.getComponent(BrickComponent.class).setDestroy(true);
                    }

                    setPowerStage(PowerStage.EXIT);
                    break;
                case THEY_LIVE:
                    for (EntitySystem system : game.getEngine().getSystems()) {
                        if (system instanceof BrickSystem) {
                            Entity ent = game.getGameThread().getEntityFactory().getTotalBricks().get(randomBrick);
                            if (ent != null){
                                System.out.println("THEY CAN MOVE?! power down " + ent.getComponent(TypeComponent.class).getType());
                                ((BrickSystem) system).powerHandler(ent,
                                        bodyComponentMapper.get(ent)
                                );
                            }
                        }
                    }
                    setPowerStage(PowerStage.EXIT);
                    break;
                case MORE_BRICKS:
                    System.out.println("More Bricks Power Down");
                    for (int i = 0; i < 10; i++) {
                        game.getGameThread().getEntityFactory().createBrick(
                                GameHelper.notFilledPosition(game).x,
                                GameHelper.notFilledPosition(game).y,
                                EntityColorHelper.randomEntityColor()
                        );
                    }
                    setPowerStage(PowerStage.EXIT);
                    break;
                case ENLARGE_PLAYER:
                    System.out.println("Enlarge player power UP");
                    for (EntitySystem system : game.getEngine().getSystems()) {
                        if (system instanceof PlayerSystem) {
                            ((PlayerSystem) system).powerHandler(game.getGameThread().getEntityFactory().getPlayer(),
                                    bodyComponentMapper.get(game.getGameThread().getEntityFactory().getPlayer())
                            );
                        }
                    }
                    setPowerStage(PowerStage.EXIT);
                    break;
                case SHORTEN_PLAYER:
                    System.out.println("Shorten player power down");
                    for (EntitySystem system : game.getEngine().getSystems()) {
                        if (system instanceof PlayerSystem) {
                            ((PlayerSystem) system).powerHandler(game.getGameThread().getEntityFactory().getPlayer(),
                                    bodyComponentMapper.get(game.getGameThread().getEntityFactory().getPlayer())
                            );
                        }
                    }
                    setPowerStage(PowerStage.EXIT);
                    break;
                case BIGGER_BALL:
                    System.out.println("Bigger Ball power UP ");
                    for (EntitySystem system : game.getEngine().getSystems()) {
                        if (system instanceof BallSystem) {
                            for (Entity ent : game.getGameThread().getEntityFactory().getTotalBalls()) {
                                ((BallSystem) system).powerHandler(ent,
                                        bodyComponentMapper.get(ent)
                                );
                            }
                        }
                    }
                    setPowerStage(PowerStage.EXIT);
                    break;
                case EXTRA_LIFE:
                    System.out.println("Extra Life power up");
                    for (EntitySystem system : game.getEngine().getSystems()) {
                        if (system instanceof PlayerSystem) {
                            ((PlayerSystem) system).powerHandler(game.getGameThread().getEntityFactory().getPlayer(),
                                    bodyComponentMapper.get(game.getGameThread().getEntityFactory().getPlayer())
                            );
                        }
                    }
                    setPowerStage(PowerStage.EXIT);
                    break;
            }
        }

        @Override
        public void exit(Entity entity) {
            CollisionComponent collisionComponent = collisionComponentMapper.get(entity);

            collisionComponent.setHitBall(false);
            destroy(entity, bodyComponentMapper.get(entity));
            timeCount = 0;
        }
    };

    @Deprecated
    public void powerHandler(Entity entity, Box2dBodyComponent bodyComponent) {

    }

    @Override
    public void destroy(Entity entity, Box2dBodyComponent bodyComponent) {
        game.getGameThread().getEntityFactory().getTotalPowers().remove(entity);
        bodyComponent.isDead = true;
        powerUpComponentMapper.get(entity).setDestroy(true);
        powerUpComponentMapper.get(entity).setOutOfBounds(false);
    }

    @Override
    public void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent){

        PowerUpComponent powerUpComponent = powerUpComponentMapper.get(entity);

        if (bodyComponent.body.getPosition().x + powerUpComponent.width/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - powerUpComponent.width/2 < 0 ||
                bodyComponent.body.getPosition().y + powerUpComponent.height/2 > GameConstants.WORLD_HEIGHT ||
                bodyComponent.body.getPosition().y - powerUpComponent.height/2 < 0) {

            System.out.println("Power is out of bounds");
            destroy(entity, bodyComponent);
            powerUpComponent.setOutOfBounds(true);
        }
    }
}
