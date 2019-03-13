package com.wisekrakr.androidmain.helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.wisekrakr.androidmain.ComponentInitializer;
import com.wisekrakr.androidmain.components.*;

public class ComponentHelper {

    public static ComponentInitializer getComponentInitializer() {
        return componentInitializer;
    }

    private static ComponentInitializer componentInitializer = new ComponentInitializer() {


        @Override
        public void typeComponent(PooledEngine engine, Entity mainEntity, TypeComponent.Type type) {
            TypeComponent typeComponent = engine.createComponent(TypeComponent.class);

            typeComponent.setType(type);

            mainEntity.add(typeComponent);
        }

        @Override
        public void collisionComponent(PooledEngine engine, Entity mainEntity) {
            CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);

            mainEntity.add(collisionComponent);
        }

        @Override
        public void transformComponent(PooledEngine engine, Entity mainEntity, float x, float y, float rotation) {
            TransformComponent transformComponent = engine.createComponent(TransformComponent.class);

            transformComponent.position.set(x, y, 0);
            transformComponent.rotation = rotation;

            mainEntity.add(transformComponent);
        }

        @Override
        public void textureComponent(PooledEngine engine, Entity mainEntity) {
            TextureComponent textureComponent = engine.createComponent(TextureComponent.class);

            mainEntity.add(textureComponent);
        }

        @Override
        public void levelComponent(PooledEngine engine, Entity mainEntity) {
            LevelComponent levelComponent = engine.createComponent(LevelComponent.class);

            mainEntity.add(levelComponent);
        }

        @Override
        public void gameObjectComponent(PooledEngine engine, Entity mainEntity, Box2dBodyComponent bodyComponent, float x, float y, float velocityX, float velocityY, float direction, float width, float height, float radius) {
            GameObjectComponent gameObjectComponent = engine.createComponent(GameObjectComponent.class);

            gameObjectComponent.position = bodyComponent.body.getPosition();
            gameObjectComponent.velocityX = bodyComponent.body.getLinearVelocity().x;
            gameObjectComponent.velocityY = bodyComponent.body.getLinearVelocity().y;
            gameObjectComponent.width = width;
            gameObjectComponent.height = height;
            gameObjectComponent.radius = radius;
            gameObjectComponent.direction = bodyComponent.body.getAngle();

            mainEntity.add(gameObjectComponent);

        }

        @Override
        public void ballComponent(PooledEngine engine, Entity mainEntity, float initialX, float initialY, EntityColor entityColor) {
            BallComponent ballComponent = engine.createComponent(BallComponent.class);

            ballComponent.getBallColorContext().setBallColor(entityColor);
            ballComponent.initialPosition.set(initialX,initialY);
            ballComponent.initialPositions.add(ballComponent.initialPosition);

            mainEntity.add(ballComponent);
        }

//
//        @Override
//        public void obstacleComponent(PooledEngine engine, Entity mainEntity, float width, float height, float velocityX, float velocityY, float x, float y) {
//            ObstacleComponent obstacleComponent = engine.createComponent(ObstacleComponent.class);
//
//            obstacleComponent.width = width;
//            obstacleComponent.height = height;
//            obstacleComponent.velocityX = velocityX;
//            obstacleComponent.velocityY = velocityY;
//            obstacleComponent.position.set(x,y);
//
//            mainEntity.add(obstacleComponent);
//        }

//        @Override
//        public void powerUpComponent(PooledEngine engine, Entity mainEntity, Box2dBodyComponent bodyComponent, float velocityX, float velocityY, float width, float height) {
//            PowerUpComponent powerUpComponent = engine.createComponent(PowerUpComponent.class);
//
//            powerUpComponent.width = width;
//            powerUpComponent.height = height;
//            powerUpComponent.velocityX = velocityX;
//            powerUpComponent.velocityY = velocityY;
//            powerUpComponent.position = bodyComponent.body.getPosition();
//
//            mainEntity.add(powerUpComponent);
//        }

        @Override
        public void playerComponent(PooledEngine engine, Entity mainEntity) {
            PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

            mainEntity.add(playerComponent);
        }
    };

}
