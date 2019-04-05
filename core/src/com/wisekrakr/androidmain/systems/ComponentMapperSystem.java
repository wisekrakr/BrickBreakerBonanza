package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.wisekrakr.androidmain.components.*;

public class ComponentMapperSystem {

    private ComponentMapper<BallComponent> ballComponentMapper;
    private ComponentMapper<BrickComponent> brickComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;
    private ComponentMapper<CollisionComponent> collisionComponentMapper;
    private ComponentMapper<PowerUpComponent> powerUpComponentMapper;
    private ComponentMapper<ParticleEffectComponent> particleEffectComponentMapper;
    private ComponentMapper<PlayerComponent> playerComponentMapper;
    private ComponentMapper<TextureComponent> textureComponentMapper;
    private ComponentMapper<TransformComponent> transformComponentMapper;
    private ComponentMapper<TypeComponent> typeComponentMapper;
    private ComponentMapper<ObstacleComponent> obstacleComponentMapper;


    public ComponentMapperSystem() {

        ballComponentMapper = ComponentMapper.getFor(BallComponent.class);
        brickComponentMapper = ComponentMapper.getFor(BrickComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
        powerUpComponentMapper = ComponentMapper.getFor(PowerUpComponent.class);
        particleEffectComponentMapper = ComponentMapper.getFor(ParticleEffectComponent.class);
        playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
        textureComponentMapper = ComponentMapper.getFor(TextureComponent.class);
        transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);
        typeComponentMapper = ComponentMapper.getFor(TypeComponent.class);
        obstacleComponentMapper = ComponentMapper.getFor(ObstacleComponent.class);
    }

    public ComponentMapper<BallComponent> getBallComponentMapper() {
        return ballComponentMapper;
    }

    public ComponentMapper<Box2dBodyComponent> getBodyComponentMapper() {
        return bodyComponentMapper;
    }

    public ComponentMapper<CollisionComponent> getCollisionComponentMapper() {
        return collisionComponentMapper;
    }

    public ComponentMapper<BrickComponent> getBrickComponentMapper() {
        return brickComponentMapper;
    }

    public ComponentMapper<PowerUpComponent> getPowerUpComponentMapper() {
        return powerUpComponentMapper;
    }

    public ComponentMapper<ParticleEffectComponent> getParticleEffectComponentMapper() {
        return particleEffectComponentMapper;
    }

    public ComponentMapper<PlayerComponent> getPlayerComponentMapper() {
        return playerComponentMapper;
    }

    public ComponentMapper<TextureComponent> getTextureComponentMapper() {
        return textureComponentMapper;
    }

    public ComponentMapper<TransformComponent> getTransformComponentMapper() {
        return transformComponentMapper;
    }

    public ComponentMapper<TypeComponent> getTypeComponentMapper() {
        return typeComponentMapper;
    }

    public ComponentMapper<ObstacleComponent> getObstacleComponentMapper() {
        return obstacleComponentMapper;
    }
}
