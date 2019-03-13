package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.wisekrakr.androidmain.components.*;

public class ComponentMapperSystem {

    private ComponentMapper<BallComponent> ballComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;
    private ComponentMapper<CollisionComponent> collisionComponentMapper;
    private ComponentMapper<GameObjectComponent> gameObjectComponentMapper;
    private ComponentMapper<LevelComponent> levelComponentMapper;
    private ComponentMapper<ParticleEffectComponent> particleEffectComponentMapper;
    private ComponentMapper<PlayerComponent> playerComponentMapper;
    private ComponentMapper<TextureComponent> textureComponentMapper;
    private ComponentMapper<TransformComponent> transformComponentMapper;
    private ComponentMapper<TypeComponent> typeComponentMapper;

    public ComponentMapperSystem() {

        ballComponentMapper = ComponentMapper.getFor(BallComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
        gameObjectComponentMapper = ComponentMapper.getFor(GameObjectComponent.class);
        levelComponentMapper = ComponentMapper.getFor(LevelComponent.class);
        particleEffectComponentMapper = ComponentMapper.getFor(ParticleEffectComponent.class);
        playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
        textureComponentMapper = ComponentMapper.getFor(TextureComponent.class);
        transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);
        typeComponentMapper = ComponentMapper.getFor(TypeComponent.class);
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

    public ComponentMapper<GameObjectComponent> getGameObjectComponentMapper() {
        return gameObjectComponentMapper;
    }

    public ComponentMapper<LevelComponent> getLevelComponentMapper() {
        return levelComponentMapper;
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
}
