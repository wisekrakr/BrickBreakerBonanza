package com.wisekrakr.androidmain.helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
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
        public void ballComponent(PooledEngine engine, Entity mainEntity, float x, float y, float radius, float direction) {
            BallComponent ballComponent = engine.createComponent(BallComponent.class);

            ballComponent.setPosition(new Vector2(x,y));
            ballComponent.setDirection(direction);
            ballComponent.setRadius(radius);

            mainEntity.add(ballComponent);
        }


        @Override
        public void obstacleComponent(PooledEngine engine, Entity mainEntity, float x, float y, float width, float height, float velocityX, float velocityY) {
            ObstacleComponent obstacleComponent = engine.createComponent(ObstacleComponent.class);

            obstacleComponent.setWidth(width);
            obstacleComponent.setHeight(height);
            obstacleComponent.setVelocityX(velocityX);
            obstacleComponent.setVelocityY(velocityY);
            obstacleComponent.setPosition(new Vector2(x,y));

            mainEntity.add(obstacleComponent);
        }

        @Override
        public void powerUpComponent(PooledEngine engine, Entity mainEntity, float x, float y, float velocityX, float velocityY, float width, float height, PowerHelper.Power power) {
            PowerUpComponent powerUpComponent = engine.createComponent(PowerUpComponent.class);

            powerUpComponent.setWidth(width);
            powerUpComponent.setHeight(height);
            powerUpComponent.setVelocityX(velocityX);
            powerUpComponent.setVelocityY(velocityY);
            powerUpComponent.setPosition(new Vector2(x,y));
            PowerHelper.setPowerUp(mainEntity, power);

            mainEntity.add(powerUpComponent);
        }

        @Override
        public void brickComponent(PooledEngine engine, Entity mainEntity, float x, float y, float width, float height, EntityColor color) {
            BrickComponent brickComponent = engine.createComponent(BrickComponent.class);

            brickComponent.setWidth(width);
            brickComponent.setHeight(height);
            brickComponent.setPosition(new Vector2(x,y));
            brickComponent.getBrickColorContext().setBrickColor(color);

            mainEntity.add(brickComponent);
        }

        @Override
        public void playerComponent(PooledEngine engine, Entity mainEntity, float x, float y, float width, float height) {
            PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

            playerComponent.setPosition(new Vector2(x, y));
            playerComponent.setWidth(width);
            playerComponent.setHeight(height);

            mainEntity.add(playerComponent);
        }
    };

}
