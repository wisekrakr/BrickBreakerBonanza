package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

import java.util.Iterator;

public class BallSystem extends IteratingSystem implements SystemEntityContext{

    private AndroidGame game;

    private ComponentMapper<BallComponent> ballComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;
    private ComponentMapper<CollisionComponent> collisionComponentMapper;

    public BallSystem(AndroidGame game){
        super(Family.all(BallComponent.class).get());

        this.game = game;

        ballComponentMapper = ComponentMapper.getFor(BallComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BallComponent ballComponent = ballComponentMapper.get(entity);
        Box2dBodyComponent bodyComponent = bodyComponentMapper.get(entity);
        CollisionComponent collisionComponent = collisionComponentMapper.get(entity);

        if (collisionComponent.hitPlayer) {
            countBounces(collisionComponent);
        }else if (ballComponent.destroy) {
            collisionComponent.reset();
            ScoreKeeper.setLives(ScoreKeeper.lives - 1);
            destroy(entity, bodyComponent);
        }

        outOfBounds(entity, bodyComponent);
        powerHandler(entity, bodyComponent);
    }

    @Override
    public void destroy(Entity entity, Box2dBodyComponent bodyComponent){
        BallComponent ballComponent = ballComponentMapper.get(entity);
        bodyComponent.isDead = true;
        game.getGameThread().getEntityFactory().getTotalBalls().remove(entity);
        ballComponent.setOutOfBounds(false);
    }

    @Override
    public void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent){
        BallComponent ballComponent = ballComponentMapper.get(entity);

        if (bodyComponent.body.getPosition().x + ballComponent.radius/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - ballComponent.radius/2 < 0 ||
                bodyComponent.body.getPosition().y + ballComponent.radius/2 > GameConstants.WORLD_HEIGHT ||
                bodyComponent.body.getPosition().y - ballComponent.radius/2 < 0){

            ballComponent.setDestroy(true);
            ballComponent.setOutOfBounds(true);
        }
    }

    @Override
    public void powerHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        BallComponent ballComponent = ballComponentMapper.get(entity);
        float initialRadius = ballComponent.radius;

        for (Entity power: game.getGameThread().getEntityFactory().getTotalPowers()) {
            if (power.getComponent(CollisionComponent.class).hitBall) {
                if (PowerHelper.getPower() == PowerHelper.Power.BIGGER_BALL) {
                    for (Fixture fixture : bodyComponent.body.getFixtureList()) {
                        fixture.getShape().setRadius(initialRadius * 1.2f);
                        ballComponent.radius = initialRadius;
                    }
                }
            }
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
