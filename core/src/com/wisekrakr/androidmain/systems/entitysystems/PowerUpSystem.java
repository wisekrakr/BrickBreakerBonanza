package com.wisekrakr.androidmain.systems.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.EntityColorHelper;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.EntityKeeper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

import java.util.ArrayList;
import java.util.List;

public class PowerUpSystem extends IteratingSystem implements SystemEntityContext {

    private BricksGame game;

    public PowerUpSystem(BricksGame game) {
        super(Family.all(PowerUpComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

        bodyComponent.body.setLinearVelocity(powerUpComponent.getVelocityX(), powerUpComponent.getVelocityY());

        if (collisionComponent.hitBall){
            powerHandler(entity);
            if (powerUpComponent.isDestroy()){
                destroy(entity);
            }
        }

        outOfBounds(entity);
        bodyHandler(entity);
    }

    @Override
    public void bodyHandler(Entity entity) {
        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        powerUpComponent.setPosition(bodyComponent.body.getPosition());
        powerUpComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        powerUpComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);

    }

    //todo fix ball power ups
    private void powerHandler(Entity entity) {
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);
        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

        switch (PowerHelper.getPower()) {

            case ENLARGE_PLAYER:
                System.out.println("Enlarge player power UP");
                for (Entity player: game.getEngine().getEntities()) {
                    if (player.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER) {
                        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(player);

                        float width = playerComponent.getWidth() + (playerComponent.getWidth()/5);

                        playerComponent.setWidth(width);
                        EntityKeeper.setPlayerWidth(width);

                        playerComponent.setDestroy(true);
                    }
                }
                break;
            case REDUCE_PLAYER:
                System.out.println("Shorten player power down");
                for (Entity player: game.getEngine().getEntities()) {
                    if (player.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER) {
                        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(player);

                        float width = playerComponent.getWidth() - (playerComponent.getWidth()/5);

                        playerComponent.setWidth(width);
                        EntityKeeper.setPlayerWidth(width);

                        playerComponent.setDestroy(true);
                    }
                }
                break;
            case BIGGER_BALL:
                System.out.println("Bigger Ball power UP ");
                for (Entity ball: game.getEngine().getEntities()) {
                    if (ball.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BALL) {
                        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(ball);
                        BallComponent ballComponent = game.getGameThread().getComponentMapperSystem().getBallComponentMapper().get(ball);

                        float initialRadius = ballComponent.getRadius();

                        for (Fixture fixture : bodyComponent.body.getFixtureList()) {
                            fixture.getShape().setRadius(initialRadius * 1.2f);
                            ballComponent.setRadius(initialRadius);
                            EntityKeeper.setBallRadius(initialRadius);
                        }

                        ballComponent.setDestroy(true);
                    }
                }

                break;

            case EXTRA_LIFE:
                System.out.println("Extra Life power up");
                ScoreKeeper.setLives(ScoreKeeper.lives + 1);
                break;
            case NUKE:
                System.out.println("Nuke power UP");
                List<Entity>firstList = new ArrayList<Entity>();
                for (Entity brick: game.getEngine().getEntities()){
                    if (brick.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BRICK){
                        firstList.add(brick);
                    }
                }

                List<Entity> sub = firstList.subList(0, firstList.size() / 7);
                ArrayList<Entity> toBeKilled = new ArrayList<Entity>(sub);

                for (Entity ent: toBeKilled){
                    ent.getComponent(BrickComponent.class).setDestroy(true);
                }

                break;
            case THEY_LIVE:
                for (Entity brick : game.getEngine().getEntities()) {
                    if (brick.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BRICK) {
                        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(brick);

                        if (bodyComponent.body.getType() == BodyDef.BodyType.StaticBody) {
                            bodyComponent.body.setType(BodyDef.BodyType.DynamicBody);
                            bodyComponent.body.setAwake(true);
                        }
                    }
                }
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
                break;
        }
        powerUpComponent.setDestroy(true);
        collisionComponent.setHitBall(false);

    }

    @Override
    public void destroy(Entity entity) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

        powerUpComponent.setDestroy(false);

        bodyComponent.isDead = true;

        EntityKeeper.setInitialPowerUps(EntityKeeper.getInitialPowerUps()-1);
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
