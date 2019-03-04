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

import java.util.Iterator;

public class PlayerSystem extends IteratingSystem implements SystemEntityContext {

    private AndroidGame game;

    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;
    private ComponentMapper<PlayerComponent>playerComponentMapper;

    @SuppressWarnings("unchecked")
    public PlayerSystem(AndroidGame game){
        super(Family.all(PlayerComponent.class).get());
        this.game = game;

        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = playerComponentMapper.get(entity);
        Box2dBodyComponent bodyComponent = bodyComponentMapper.get(entity);

        Iterator<Entity>iterator = game.getGameThread().getEntityFactory().getTotalBalls().iterator();
        if (iterator.hasNext()){
            Entity ent = iterator.next();
            if (!playerComponent.hasBall) {
                bodyComponentMapper.get(ent).body.setTransform(new Vector2(
                        bodyComponent.body.getPosition().x,
                        bodyComponent.body.getPosition().y + ent.getComponent(BallComponent.class).radius
                ), 0);
            }
        }else {
            if (game.getGameThread().getEntityFactory().getTotalBalls().size() <= 0) {
                spawnBall(entity);
                System.out.println("Player gets new ball");
            }
        }

        outOfBounds(entity, bodyComponent);
        powerHandler(entity, bodyComponent);
    }

    @Override
    public void destroy(Entity entity, Box2dBodyComponent bodyComponent) {
        PlayerComponent playerComponent = playerComponentMapper.get(entity);
        bodyComponent.isDead = true;
        playerComponent.setDestroy(true);
    }

    @Override
    public void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent){
        PlayerComponent playerComponent = playerComponentMapper.get(entity);

        if (bodyComponent.body.getPosition().x + (playerComponent.width - playerComponent.width/2) > GameConstants.WORLD_WIDTH || bodyComponent.body.getPosition().x - (playerComponent.width - playerComponent.width/2) < 0){
            bodyComponent.body.setLinearVelocity(-bodyComponent.body.getLinearVelocity().x, 0);
        }else if (bodyComponent.body.getPosition().y + playerComponent.height > GameConstants.WORLD_HEIGHT || bodyComponent.body.getPosition().y - playerComponent.height < 0){
            bodyComponent.body.setLinearVelocity(0, -bodyComponent.body.getLinearVelocity().x);
        }
    }

    @Override
    public void powerHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        PlayerComponent playerComponent = playerComponentMapper.get(entity);

        Vector2 savedPosition = bodyComponent.body.getPosition();
        float initialWidth = playerComponent.width;

        for (Entity ball: game.getGameThread().getEntityFactory().getTotalBalls()){
            if (ball.getComponent(CollisionComponent.class).hitPower){
                switch (PowerHelper.getPower()){
                    case SHORTEN_PLAYER:
                        destroy(entity, bodyComponent);
                        game.getGameThread().getEntityFactory().createPlayer(
                                savedPosition.x,
                                savedPosition.y,
                                initialWidth /1.5f,
                                GameConstants.PLAYER_HEIGHT
                        );
//                        playerComponent.width = initialWidth;
                        break;
                    case ENLARGE_PLAYER:
                        destroy(entity, bodyComponent);
                        game.getGameThread().getEntityFactory().createPlayer(
                                savedPosition.x,
                                savedPosition.y,
                                initialWidth *1.5f,
                                GameConstants.PLAYER_HEIGHT
                        );
//                        playerComponent.width = initialWidth;
                        break;
                    case EXTRA_LIFE:
                        destroy(entity, bodyComponent);
                        playerComponent.setLives(playerComponent.lives + 1);
                        break;
                }
            }
        }

    }

    private void spawnBall(Entity player){
        Box2dBodyComponent bodyComponent = bodyComponentMapper.get(player);

        game.getGameThread().getEntityFactory().createBall(
                bodyComponent.body.getPosition().x,
                bodyComponent.body.getPosition().y + GameConstants.BALL_RADIUS
        );
        player.getComponent(PlayerComponent.class).setHasBall(false);
    }
}
