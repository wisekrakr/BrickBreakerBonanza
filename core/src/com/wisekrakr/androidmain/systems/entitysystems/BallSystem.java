package com.wisekrakr.androidmain.systems.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.retainers.EntityKeeper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

public class BallSystem extends IteratingSystem implements SystemEntityContext {

    private BricksGame game;

    public BallSystem(BricksGame game){
        super(Family.all(BallComponent.class).get());

        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BallComponent ballComponent = game.getGameThread().getComponentMapperSystem().getBallComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

        ballComponent.setSpeed(1000000000f);

        if (collisionComponent.hitPlayer) {
            countBounces(collisionComponent);
        }else if (ballComponent.isDestroy()) {
            destroy(entity);
        }

        bodyHandler(entity);
        outOfBounds(entity);

    }

    @Override
    public void bodyHandler(Entity entity) {
        BallComponent ballComponent = game.getGameThread().getComponentMapperSystem().getBallComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        ballComponent.setPosition(bodyComponent.body.getPosition());
        ballComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        ballComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
        ballComponent.setDirection(bodyComponent.body.getAngle());
    }

    @Override
    public void destroy(Entity entity){
        BallComponent ballComponent = game.getGameThread().getComponentMapperSystem().getBallComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        ballComponent.setDestroy(false);
        EntityKeeper.setBallsInPlay(0);
        bodyComponent.isDead = true;

    }

    @Override
    public void outOfBounds(Entity entity){
        BallComponent ballComponent = game.getGameThread().getComponentMapperSystem().getBallComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (bodyComponent.body.getPosition().x + ballComponent.getRadius()/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - ballComponent.getRadius()/2 < 0 ||
                bodyComponent.body.getPosition().y + ballComponent.getRadius()/2 > GameConstants.WORLD_HEIGHT ||
                bodyComponent.body.getPosition().y - ballComponent.getRadius()/2 < 0){

            ballComponent.setDestroy(true);
            ScoreKeeper.setLives(ScoreKeeper.lives - 1);
        }
    }

    private void countBounces(CollisionComponent collisionComponent){
        collisionComponent.bounces++;
        if (collisionComponent.bounces > 5 && collisionComponent.bounces < 10) {
            ScoreKeeper.setMultiplier(2);
        } else if (collisionComponent.bounces >= 10 && collisionComponent.bounces < 20) {
            ScoreKeeper.setMultiplier(3);
        } else if (collisionComponent.bounces >= 20 && collisionComponent.bounces < 35) {
            ScoreKeeper.setMultiplier(4);
        } else if (collisionComponent.bounces >= 35 && collisionComponent.bounces < 50) {
            ScoreKeeper.setMultiplier(5);
        }
        collisionComponent.setHitPlayer(false);
    }

}
