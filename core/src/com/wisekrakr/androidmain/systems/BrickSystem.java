package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;


public class BrickSystem extends IteratingSystem implements SystemEntityContext{

    private AndroidGame game;

    private ComponentMapper<BrickComponent> brickComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;
    private ComponentMapper<CollisionComponent> collisionComponentMapper;

    public BrickSystem(AndroidGame game){
        super(Family.all(BrickComponent.class).get());

        this.game = game;

        brickComponentMapper = ComponentMapper.getFor(BrickComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BrickComponent brickComponent = brickComponentMapper.get(entity);
        Box2dBodyComponent bodyComponent = bodyComponentMapper.get(entity);
        CollisionComponent collisionComponent = collisionComponentMapper.get(entity);

        if (collisionComponent.hitBall && !brickComponent.outOfBounds) {
            game.getGameThread().getEntityFactory().getTotalBricks().remove(entity);
            destroy(entity, bodyComponent);
            collisionComponent.setHitBall(false);
        }else if (brickComponent.destroy){
            destroy(entity, bodyComponent);
        }

        outOfBounds(entity, bodyComponent);
        powerHandler(entity, bodyComponent);
    }

    private void scoreHandler(BrickComponent brickComponent){
        //todo better scores. Higher levels mean more points instead of always the same

        switch (brickComponent.getBrickColor()){
            case RED:
                ScoreKeeper.setPointsToGive(100);
                break;
            case BLUE:
                ScoreKeeper.setPointsToGive(50);
                break;
            case YELLOW:
                ScoreKeeper.setPointsToGive(25);
                break;
            case GREEN:
                ScoreKeeper.setPointsToGive(10);
                break;
            case PURPLE:
                ScoreKeeper.setPointsToGive(250);
                break;
            case ORANGE:
                ScoreKeeper.setPointsToGive(25);
                break;
            case CYAN:
                ScoreKeeper.setPointsToGive(500);
                break;
        }
        ScoreKeeper.setScore(ScoreKeeper.getPointsToGive() * ScoreKeeper.getMultiplier());
    }

    @Override
    public void destroy(Entity entity, Box2dBodyComponent bodyComponent){
        BrickComponent brickComponent = brickComponentMapper.get(entity);
        scoreHandler(brickComponent);
        bodyComponent.isDead = true;
        brickComponent.setDestroy(true);
        game.getGameThread().getEntityFactory().getTotalBricks().remove(entity);
    }

    @Override
    public void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent){
        BrickComponent brickComponent = brickComponentMapper.get(entity);

        if (bodyComponent.body.getPosition().x + brickComponent.width/2 > GameConstants.WORLD_WIDTH || bodyComponent.body.getPosition().x - brickComponent.width/2 < 0){
            destroy(entity, bodyComponent);
            brickComponent.setOutOfBounds(true);
        }else if (bodyComponent.body.getPosition().y + brickComponent.height/2 > GameConstants.WORLD_HEIGHT || bodyComponent.body.getPosition().y - brickComponent.height/2 < 0){
            destroy(entity, bodyComponent);
            brickComponent.setOutOfBounds(true);
        }
    }

    @Override
    public void powerHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        for (Entity ball: game.getGameThread().getEntityFactory().getTotalBalls()) {
            if (ball.getComponent(CollisionComponent.class).hitPower) {
                if (PowerHelper.getPower() == PowerHelper.Power.THEY_LIVE) {
                    if (bodyComponent.body.getType() == BodyDef.BodyType.StaticBody) {
                        bodyComponent.body.setType(BodyDef.BodyType.DynamicBody);
                        bodyComponent.body.setAwake(true);
                    }
                }
            }
        }
//        if (!game.getGameThread().getEntityFactory().getTotalBalls().isEmpty()) {
//            if (game.getGameThread().getEntityFactory().getTotalBalls().get(0).getComponent(CollisionComponent.class).hitPower) {
//
//            }
//        }
    }
}
