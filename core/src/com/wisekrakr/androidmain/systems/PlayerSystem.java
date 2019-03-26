package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

public class PlayerSystem extends IteratingSystem implements SystemEntityContext {

    private AndroidGame game;

    @SuppressWarnings("unchecked")
    public PlayerSystem(AndroidGame game){
        super(Family.all(PlayerComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        float length = playerComponent.getPenisLength();
        float girth = playerComponent.getPenisGirth();

        if (!playerComponent.isDestroy()) {
            penisHandler(entity);
            if (collisionComponent.hitPenis) {

                playerComponent.setDestroy(true);
                collisionComponent.setHitPenis(false);

                ScoreKeeper.setLives(ScoreKeeper.lives - 1);
            }
        }else {
            for (Entity ball: game.getEngine().getEntities()){
                if (ball.getComponent(TypeComponent.class).getType()== TypeComponent.Type.ENEMY) {
                    ball.getComponent(EnemyComponent.class).setDestroy(true);
                }
            }
            destroy(entity);
            spawnNewPlayer(entity);
            playerComponent.setMoving(false);
            collisionComponent.setHitEnemy(false);
        }

        outOfBounds(entity);
        bodyHandler(entity, bodyComponent);
    }

    private void penisHandler(Entity entity){
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        for (Entity ent: game.getEngine().getEntities()) {

            if (ent == playerComponent.getAttachedEntity()) {
                ent.getComponent(Box2dBodyComponent.class).body.setLinearVelocity(
                        bodyComponent.body.getLinearVelocity().x,
                        bodyComponent.body.getLinearVelocity().y
                );

                //todo do something here
                if (ent.getComponent(CollisionComponent.class).hitPenis){

                }
            }
        }
    }

    @Override
    public void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

        playerComponent.setPosition(bodyComponent.body.getPosition());
        playerComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        playerComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
        playerComponent.setDirection(bodyComponent.body.getAngle());
    }

    @Override
    public void destroy(Entity entity) {
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        playerComponent.getAttachedEntity().getComponent(Box2dBodyComponent.class).isDead = true;
        bodyComponent.isDead = true;

        playerComponent.setDestroy(false);
    }

    @Override
    public void outOfBounds(Entity entity){
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

        if (playerComponent.getPosition().x  > GameConstants.WORLD_WIDTH ||
                playerComponent.getPosition().x  < 0 ||
                playerComponent.getPosition().y > GameConstants.WORLD_HEIGHT ||
                playerComponent.getPosition().y < 0){
            playerComponent.setDestroy(true);
        }
    }


    @Override
    public void powerHandler(Entity entity) {
//        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);
//        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);
//
//        Vector2 savedPosition = playerComponent.getPosition();
//        float initialRadius = playerComponent.getRadius();
//
//        if (collisionComponent.hitPower) {
//            switch (PowerHelper.getPower()) {
//                case REDUCE_PLAYER:
//                    destroy(entity);
//                    game.getGameThread().getEntityFactory().createPlayer(
//                            savedPosition.x,
//                            savedPosition.y,
//                            initialRadius / 2,
//                            , );
//
//                    System.out.println("powerHandler reduce");//todo remove
//                    break;
//                case ENLARGE_PLAYER:
//                    destroy(entity);
//                    game.getGameThread().getEntityFactory().createPlayer(
//                            savedPosition.x,
//                            savedPosition.y,
//                            initialRadius * 2,
//                            , );
//
//                    System.out.println("powerHandler enlarge");//todo remove
//                    break;
//                case EXTRA_LIFE:
//                    ScoreKeeper.setLives(ScoreKeeper.lives + 1);
//
//                    System.out.println("powerHandler extra life");//todo remove
//                    break;
//            }
//            collisionComponent.setHitPower(false);
//        }

    }

    private void spawnNewPlayer(Entity entity){
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);


        game.getGameThread().getEntityFactory().createPlayer(
                GameConstants.WORLD_WIDTH/2, GameConstants.WORLD_HEIGHT/2,
                GameConstants.PLAYER_RADIUS,
                playerComponent.getPenisLength(),
                playerComponent.getPenisGirth()
        );
    }
}
