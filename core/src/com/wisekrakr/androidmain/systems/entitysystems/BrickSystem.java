package com.wisekrakr.androidmain.systems.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.retainers.EntityKeeper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;


public class BrickSystem extends IteratingSystem implements SystemEntityContext {

    private BricksGame game;
    private float awakeTime = 0f;

    public BrickSystem(BricksGame game){
        super(Family.all(BrickComponent.class).get());

        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BrickComponent brickComponent = game.getGameThread().getComponentMapperSystem().getBrickComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

        if (collisionComponent.hitBall && !brickComponent.isDestroy()) {
            destroy(entity);
            scoreHandler(brickComponent);
            collisionComponent.setHitBall(false);
        }else if (brickComponent.isDestroy()){
            destroy(entity);
        }else {
            if (bodyComponent.body.isAwake()){
                if (awakeTime == 0){
                    awakeTime = game.getGameThread().getTimeKeeper().gameClock;
                }
                if (game.getGameThread().getTimeKeeper().gameClock - awakeTime > 10){
                    bodyComponent.body.setAwake(false);
                }
            }
        }

        outOfBounds(entity);

    }

    @Override
    public void bodyHandler(Entity entity) {
        BrickComponent brickComponent = game.getGameThread().getComponentMapperSystem().getBrickComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        brickComponent.setPosition(bodyComponent.body.getPosition());

    }

    private void scoreHandler(BrickComponent brickComponent){

        switch (brickComponent.getBrickColorContext().getBrickColor()){
            case RED:
                ScoreKeeper.setPointsToGive(100);
                break;
            case BLUE:
                ScoreKeeper.setPointsToGive(50);
                break;
            case GOLD:
                ScoreKeeper.setPointsToGive(500);
                break;
            case GREEN:
                ScoreKeeper.setPointsToGive(10);
                break;
            case PURPLE:
                ScoreKeeper.setPointsToGive(250);
                break;
            case ORANGE:
                ScoreKeeper.setPointsToGive(100);
                break;
            case WHITE:
                ScoreKeeper.setPointsToGive(25);
                break;
        }
        ScoreKeeper.setScore(ScoreKeeper.getPointsToGive() * ScoreKeeper.getMultiplier());

        if (ScoreKeeper.getScore() > game.getGamePreferences().getHighScore()){
            game.getGamePreferences().setHighScore(ScoreKeeper.getScore());
        }
    }

    @Override
    public void destroy(Entity entity){
        BrickComponent brickComponent = game.getGameThread().getComponentMapperSystem().getBrickComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        bodyComponent.isDead = true;

        brickComponent.setDestroy(false);
        EntityKeeper.setInitialBricks(EntityKeeper.getInitialBricks()-1);
    }

    @Override
    public void outOfBounds(Entity entity){
        BrickComponent brickComponent = game.getGameThread().getComponentMapperSystem().getBrickComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (bodyComponent.body.getPosition().x + brickComponent.getWidth()/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - brickComponent.getWidth()/2 < 0 ||
                bodyComponent.body.getPosition().y + brickComponent.getHeight()/2 > GameConstants.WORLD_HEIGHT ||
                bodyComponent.body.getPosition().y - brickComponent.getHeight()/2 < 0){
            brickComponent.setDestroy(true);
        }
    }

}
