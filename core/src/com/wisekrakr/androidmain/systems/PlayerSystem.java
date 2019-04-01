package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

public class PlayerSystem extends IteratingSystem implements SystemEntityContext {

    private AndroidGame game;

    @SuppressWarnings("unchecked")
    public PlayerSystem(AndroidGame game){
        super(Family.all(PlayerComponent.class, GameObjectComponent.class).get());
        this.game = game;

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);
        GameObjectComponent gameObjectComponent = game.getGameThread().getComponentMapperSystem().getGameObjectComponentMapper().get(entity);

        if (collisionComponent.hitBall){
            destroy(entity,bodyComponent, gameObjectComponent);
            gameObjectComponent.setDestroy(true);
            for (Entity ball: game.getEngine().getEntities()){
                if (ball.getComponent(TypeComponent.class).getType()== TypeComponent.Type.BALL) {
                    ball.getComponent(GameObjectComponent.class).setDestroy(true);
                }
            }
            if (gameObjectComponent.destroy && ScoreKeeper.lives > 0){
                spawnNewPlayer();
                playerComponent.setMoving(false);
                collisionComponent.setHitBall(false);
            }
        }

        outOfBounds(entity, bodyComponent, gameObjectComponent);
        powerHandler(entity, bodyComponent, gameObjectComponent);
    }

    @Override
    public void destroy(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent) {
        ScoreKeeper.setLives(ScoreKeeper.lives - 1);
        bodyComponent.isDead = true;

        gameObjectComponent.setDestroy(false);
    }

    @Override
    public void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent){

        if (bodyComponent.body.getPosition().x + gameObjectComponent.radius > GameConstants.WORLD_WIDTH ||
                bodyComponent.body.getPosition().x - gameObjectComponent.radius < 0){
            bodyComponent.body.setLinearVelocity(-bodyComponent.body.getLinearVelocity().x, 0);
        }
    }

    @Override
    public void powerHandler(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent) {

        Vector2 savedPosition = bodyComponent.body.getPosition();
        float initialRadius = gameObjectComponent.radius;

        for (Entity power: game.getGameThread().getEntityFactory().getGameObjects()){
            if (power.getComponent(TypeComponent.class).getType() == TypeComponent.Type.POWER) {
                if (power.getComponent(CollisionComponent.class).hitBall) {
                    switch (PowerHelper.getPower()) {
                        case REDUCE_PLAYER:
                            destroy(entity, bodyComponent, gameObjectComponent);
                            game.getGameThread().getEntityFactory().createPlayer(
                                    savedPosition.x,
                                    savedPosition.y
                            );
                            gameObjectComponent.radius = initialRadius;
                            break;
                        case ENLARGE_PLAYER:
                            destroy(entity, bodyComponent, gameObjectComponent);
                            game.getGameThread().getEntityFactory().createPlayer(
                                    savedPosition.x,
                                    savedPosition.y
                            );
                            gameObjectComponent.radius = initialRadius;
                            break;
                        case EXTRA_LIFE:
                            ScoreKeeper.setLives(ScoreKeeper.lives + 1);
                            break;
                    }
                }
            }
        }

    }

    private void spawnNewPlayer(){
        game.getGameThread().getEntityFactory().createPlayer(
                GameConstants.WORLD_WIDTH/2, GameConstants.WORLD_HEIGHT/2
        );
    }
}
