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
        public void levelComponent(PooledEngine engine, Entity mainEntity) {
            LevelComponent levelComponent = engine.createComponent(LevelComponent.class);

            mainEntity.add(levelComponent);
        }


        @Override
        public void enemyComponent(PooledEngine engine, Entity mainEntity, float initialX, float initialY, float radius, EntityColor entityColor, Entity attachedEntity, float penisLength, float penisGirth) {
            EnemyComponent enemyComponent = engine.createComponent(EnemyComponent.class);

            enemyComponent.getEnemyColorContext().setEnemyColor(entityColor);

            enemyComponent.setRadius(radius);

            enemyComponent.setInitialPosition(new Vector2(initialX, initialY));
            enemyComponent.initialPositions.add(enemyComponent.getInitialPosition());

            enemyComponent.setAttachedEntity(attachedEntity);
            enemyComponent.setPenisLength(penisLength);
            enemyComponent.setPenisGirth(penisGirth);

            mainEntity.add(enemyComponent);
        }


        @Override
        public void obstacleComponent(PooledEngine engine, Entity mainEntity, float x, float y, float velocityX, float velocityY, float width, float height) {
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
        public void penisComponent(PooledEngine engine, Entity mainEntity, Entity attachedEntity, float velocityX, float velocityY, float width, float height, float direction) {
            PenisComponent penisComponent = engine.createComponent(PenisComponent.class);

            penisComponent.setWidth(width);
            penisComponent.setHeight(height);
            penisComponent.setDirection(direction);
            penisComponent.setAttachedEntity(attachedEntity);

            mainEntity.add(penisComponent);
        }

        @Override
        public void playerComponent(PooledEngine engine, Entity mainEntity, float x, float y, float radius, Entity attachedEntity, float penisLength, float penisGirth) {
            PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

            playerComponent.setPosition(new Vector2(x,y));
            playerComponent.setRadius(radius);
            playerComponent.setAttachedEntity(attachedEntity);
            playerComponent.setPenisLength(penisLength);
            playerComponent.setPenisGirth(penisGirth);

            mainEntity.add(playerComponent);
        }

        @Override
        public void wallComponent(PooledEngine engine, Entity mainEntity, float x, float y, float width, float height) {
            WallComponent wallComponent = engine.createComponent(WallComponent.class);

            wallComponent.setPosition(new Vector2(x, y));
            wallComponent.setWidth(width);
            wallComponent.setHeight(height);

            mainEntity.add(wallComponent);
        }
    };

}
