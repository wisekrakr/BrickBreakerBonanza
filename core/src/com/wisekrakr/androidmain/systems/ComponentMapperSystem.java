package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.wisekrakr.androidmain.components.*;

public class ComponentMapperSystem {

    private ComponentMapper<EnemyComponent> enemyComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;
    private ComponentMapper<CollisionComponent> collisionComponentMapper;
    private ComponentMapper<PowerUpComponent> powerUpComponentMapper;
    private ComponentMapper<ObstacleComponent> obstacleComponentMapper;
    private ComponentMapper<ParticleEffectComponent> particleEffectComponentMapper;
    private ComponentMapper<PlayerComponent> playerComponentMapper;
    private ComponentMapper<TextureComponent> textureComponentMapper;
    private ComponentMapper<TransformComponent> transformComponentMapper;
    private ComponentMapper<TypeComponent> typeComponentMapper;
    private ComponentMapper<WallComponent> wallComponentMapper;
    private ComponentMapper<PenisComponent> penisComponentMapper;
    private ComponentMapper<TesticleComponent> testicleComponentMapper;

    public ComponentMapperSystem() {

        enemyComponentMapper = ComponentMapper.getFor(EnemyComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
        powerUpComponentMapper = ComponentMapper.getFor(PowerUpComponent.class);
        obstacleComponentMapper = ComponentMapper.getFor(ObstacleComponent.class);
        particleEffectComponentMapper = ComponentMapper.getFor(ParticleEffectComponent.class);
        playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
        textureComponentMapper = ComponentMapper.getFor(TextureComponent.class);
        transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);
        typeComponentMapper = ComponentMapper.getFor(TypeComponent.class);
        wallComponentMapper = ComponentMapper.getFor(WallComponent.class);
        penisComponentMapper = ComponentMapper.getFor(PenisComponent.class);
        testicleComponentMapper = ComponentMapper.getFor(TesticleComponent.class);
    }

    public ComponentMapper<EnemyComponent> getEnemyComponentMapper() {
        return enemyComponentMapper;
    }

    public ComponentMapper<Box2dBodyComponent> getBodyComponentMapper() {
        return bodyComponentMapper;
    }

    public ComponentMapper<CollisionComponent> getCollisionComponentMapper() {
        return collisionComponentMapper;
    }

    public ComponentMapper<PowerUpComponent> getPowerUpComponentMapper() {
        return powerUpComponentMapper;
    }

    public ComponentMapper<ObstacleComponent> getObstacleComponentMapper() {
        return obstacleComponentMapper;
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

    public ComponentMapper<WallComponent> getWallComponentMapper() {
        return wallComponentMapper;
    }

    public ComponentMapper<PenisComponent> getPenisComponentMapper() {
        return penisComponentMapper;
    }

    public ComponentMapper<TesticleComponent> getTesticleComponentMapper() {
        return testicleComponentMapper;
    }
}
