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

public class EnemySystem extends IteratingSystem implements SystemEntityContext{

    private AndroidGame game;

    private List<Vector2>savedPositions = new ArrayList<Vector2>();

    public EnemySystem(AndroidGame game){
        super(Family.all(EnemyComponent.class).get());

        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

        savedPositions = enemyComponent.initialPositions;

        enemyComponent.setSpeed(GameConstants.ENEMY_SPEED);//todo change with screen resolution (scaling)

        for (Entity ent: game.getEngine().getEntities()){
            if (ent.getComponent(TypeComponent.class).getType()== TypeComponent.Type.PLAYER){

                float angle = GameHelper.angleBetween(enemyComponent.getPosition(), ent.getComponent(PlayerComponent.class).getPosition());
                enemyComponent.setDirection(angle);
                bodyComponent.body.setTransform(bodyComponent.body.getPosition(), angle);

                if (enemyComponent.chaseInterval == 0){
                    enemyComponent.chaseInterval = game.getGameThread().getTimeKeeper().gameClock;
                }

                if (ent.getComponent(PlayerComponent.class).isMoving) {
                    if (game.getGameThread().getTimeKeeper().gameClock - enemyComponent.chaseInterval > game.getGameThread().getTimeKeeper().getTimeToChase()) {
                        enemyComponent.chaseInterval = game.getGameThread().getTimeKeeper().gameClock;

                        speedImpulse(enemyComponent, collisionComponent);

                        bodyComponent.body.setLinearVelocity(enemyComponent.getPosition().x * enemyComponent.getSpeed() * MathUtils.cos(enemyComponent.getDirection()),
                                enemyComponent.getPosition().y * enemyComponent.getSpeed() * MathUtils.sin(enemyComponent.getDirection())
                        );
                    }
                }else {
                    bodyComponent.body.setLinearVelocity(0,0);
                }
            }
        }

        float length = enemyComponent.getPenisLength();
        float girth = enemyComponent.getPenisGirth();

        if (!enemyComponent.isDestroy()){
            penisHandler(entity);
            if (collisionComponent.hitPenis){
                spawnEnemyAtPoint(length, girth);
                destroy(entity);
                collisionComponent.setHitPenis(false);
            }
        }else {
            spawnEnemyAtPoint(length, girth);
            destroy(entity);
        }


        outOfBounds(entity);
        bodyHandler(entity, bodyComponent);

    }

    private void penisHandler(Entity entity){
        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        for (Entity ent: game.getEngine().getEntities()) {

            if (ent == enemyComponent.getAttachedEntity()) {
                ent.getComponent(Box2dBodyComponent.class).body.setLinearVelocity(
                        bodyComponent.body.getLinearVelocity().x,
                        bodyComponent.body.getLinearVelocity().y
                );
            }
        }
    }

    @Override
    public void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

        enemyComponent.setPosition(bodyComponent.body.getPosition());
        enemyComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        enemyComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
        enemyComponent.setDirection(bodyComponent.body.getAngle());
    }

    @Override
    public void destroy(Entity entity){
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

        enemyComponent.getAttachedEntity().getComponent(Box2dBodyComponent.class).isDead = true;
        bodyComponent.isDead = true;
        enemyComponent.setDestroy(false);

    }

    @Override
    public void outOfBounds(Entity entity){
        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

        if (enemyComponent.getPosition().x - enemyComponent.getRadius()/2 > GameConstants.WORLD_WIDTH
                ||  enemyComponent.getPosition().x + enemyComponent.getRadius()/2 < 0 ||
                enemyComponent.getPosition().y - enemyComponent.getRadius()/2 > GameConstants.WORLD_HEIGHT ||
                enemyComponent.getPosition().y + enemyComponent.getRadius()/2 < 0){
            System.out.println("ball is out of bounds"); //todo remove
            enemyComponent.setDestroy(true);
        }
    }


    @Override
    public void powerHandler(Entity entity) {
        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);
        float initialRadius = enemyComponent.getRadius();

        if (collisionComponent.hitPower) {
            if (PowerHelper.getPower() == PowerHelper.Power.ENLARGE_ENEMY) {
                for (Fixture fixture : bodyComponent.body.getFixtureList()) {
                    fixture.getShape().setRadius(initialRadius * 1.2f);
                    enemyComponent.setRadius(initialRadius);
                }
            }
        }
    }

    private void speedImpulse(EnemyComponent enemyComponent, CollisionComponent collisionComponent){
        if (collisionComponent.hitSurface) {
            enemyComponent.setBounces(enemyComponent.getBounces() + 1);
            for (int i = enemyComponent.getBounces(); i>0; i--) {
                if (i % 5 == 0) {
                    enemyComponent.setSpeed(enemyComponent.getSpeed() + GameConstants.ENEMY_SPEED/5);
                }
            }
            collisionComponent.setHitSurface(false);
        }
    }

    private void spawnEnemyAtPoint(float length, float girth){

        for (int i = savedPositions.size(); i>0; i--) {
            game.getGameThread().getEntityFactory().createEnemy(
                    savedPositions.get(i-1).x,
                    savedPositions.get(i-1).y,
                    EntityColorHelper.randomEntityColor(),
                    length,
                    girth
            );
            //todo if out of bounds set a new position
        }
    }
}
