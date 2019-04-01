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
            scoreHandler(brickComponent);
            collisionComponent.setHitBall(false);
        }else if (brickComponent.destroy){
            destroy(entity, bodyComponent);
        }

        outOfBounds(entity, bodyComponent);
        powerHandler(entity, bodyComponent);
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
    public void destroy(Entity entity, Box2dBodyComponent bodyComponent){
        BrickComponent brickComponent = brickComponentMapper.get(entity);

        bodyComponent.isDead = true;
        game.getGameThread().getEntityFactory().getTotalBricks().remove(entity);
        brickComponent.setOutOfBounds(false);

    }

    @Override
    public void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent){
        BrickComponent brickComponent = brickComponentMapper.get(entity);

        if (bodyComponent.body.getPosition().x + brickComponent.width/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - brickComponent.width/2 < 0 ||
                bodyComponent.body.getPosition().y + brickComponent.height/2 > GameConstants.WORLD_HEIGHT ||
                bodyComponent.body.getPosition().y - brickComponent.height/2 < 0){
            brickComponent.setDestroy(true);
            brickComponent.setOutOfBounds(true);
        }
    }

    @Override
    public void powerHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        for (Entity power: game.getGameThread().getEntityFactory().getTotalPowers()) {
            if (power.getComponent(CollisionComponent.class).hitBall) {
                if (PowerHelper.getPower() == PowerHelper.Power.THEY_LIVE) {
                    if (bodyComponent.body.getType() == BodyDef.BodyType.StaticBody) {
                        bodyComponent.body.setType(BodyDef.BodyType.DynamicBody);
                        bodyComponent.body.setAwake(true);
                    }
                }
            }
        }

    }
}
