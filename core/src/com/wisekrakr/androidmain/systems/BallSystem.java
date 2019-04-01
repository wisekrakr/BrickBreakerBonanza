package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.EntityColorHelper;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;

import java.util.ArrayList;
import java.util.List;

public class BallSystem extends IteratingSystem implements SystemEntityContext{

    private AndroidGame game;

    private List<Vector2>savedPositions = new ArrayList<Vector2>();

    public BallSystem(AndroidGame game){
        super(Family.all(BallComponent.class, GameObjectComponent.class).get());

        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BallComponent ballComponent = game.getGameThread().getComponentMapperSystem().getBallComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);
        GameObjectComponent gameObjectComponent = game.getGameThread().getComponentMapperSystem().getGameObjectComponentMapper().get(entity);

        savedPositions = ballComponent.initialPositions;

        for (Entity player: game.getEngine().getEntities()){
            if (player.getComponent(TypeComponent.class).getType()== TypeComponent.Type.PLAYER){

                gameObjectComponent.direction = GameHelper.angleBetween(gameObjectComponent.position, player.getComponent(GameObjectComponent.class).position);

                if (ballComponent.chaseInterval == 0){
                    ballComponent.chaseInterval = game.getGameThread().getTimeKeeper().gameClock;
                }

                if (player.getComponent(PlayerComponent.class).isMoving) {
                    if (game.getGameThread().getTimeKeeper().gameClock - ballComponent.chaseInterval > game.getGameThread().getTimeKeeper().getTimeToChase()) {
                        ballComponent.chaseInterval = game.getGameThread().getTimeKeeper().gameClock;

//                    speedImpulse(collisionComponent);


                        bodyComponent.body.setLinearVelocity(gameObjectComponent.position.x * ballComponent.speed * MathUtils.cos(gameObjectComponent.direction),
                                gameObjectComponent.position.y * ballComponent.speed * MathUtils.sin(gameObjectComponent.direction));
                    }
                }else {
                    bodyComponent.body.setLinearVelocity(0,0);
                }
            }
        }

        if (gameObjectComponent.destroy) {
            spawnBall(); //todo in GOSystem give every entity the three methods via gameobjectComponent (just like EntityColor) and run them in GOSystem (init, update, end)
            destroy(entity, bodyComponent, gameObjectComponent);

            collisionComponent.setHitPlayer(false);
        }

        outOfBounds(entity, bodyComponent, gameObjectComponent);
        powerHandler(entity, bodyComponent, gameObjectComponent);
    }

    @Override
    public void destroy(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent){
        bodyComponent.isDead = true;
        game.getGameThread().getEntityFactory().getGameObjects().remove(entity);
        gameObjectComponent.setOutOfBounds(false);
        gameObjectComponent.setDestroy(false);
    }

    @Override
    public void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent){

        if (bodyComponent.body.getPosition().x + gameObjectComponent.radius/2 > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - gameObjectComponent.radius/2 < 0 ||
                bodyComponent.body.getPosition().y + gameObjectComponent.radius/2 > GameConstants.WORLD_HEIGHT ||
                bodyComponent.body.getPosition().y - gameObjectComponent.radius/2 < 0){

            gameObjectComponent.setDestroy(true);
            gameObjectComponent.setOutOfBounds(true);
        }
    }

    @Override
    public void powerHandler(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent) {

        float initialRadius = gameObjectComponent.radius;

        for (Entity power: game.getGameThread().getEntityFactory().getGameObjects()) {
            if (power.getComponent(TypeComponent.class).getType() == TypeComponent.Type.POWER) {
                if (power.getComponent(CollisionComponent.class).hitBall) {
                    if (PowerHelper.getPower() == PowerHelper.Power.BIGGER_BALL) {
                        for (Fixture fixture : bodyComponent.body.getFixtureList()) {
                            fixture.getShape().setRadius(initialRadius * 1.2f);
                            gameObjectComponent.radius = initialRadius;
                        }
                    }
                }
            }
        }
    }
//
//    private void speedImpulse(CollisionComponent collisionComponent){
//        collisionComponent.bounces++;
//        if (collisionComponent.bounces > 5 && collisionComponent.bounces < 10) {
//            speed = speed + 50000f;
//        } else if (collisionComponent.bounces >= 10 && collisionComponent.bounces < 20) {
//            speed = speed + 50000f;
//        } else if (collisionComponent.bounces >= 20 && collisionComponent.bounces < 35) {
//            speed = speed + 50000f;
//        } else if (collisionComponent.bounces >= 35 && collisionComponent.bounces < 50) {
//            speed = speed + 50000f;
//        }
//        collisionComponent.setHitPlayer(false);
//    }

    private void spawnBall(){

        for (int i = savedPositions.size(); i>0; i--) {
            game.getGameThread().getEntityFactory().createBall(
                    savedPositions.get(i-1).x,
                    savedPositions.get(i-1).y,
                    EntityColorHelper.randomEntityColor()
            );
        }
    }

}
