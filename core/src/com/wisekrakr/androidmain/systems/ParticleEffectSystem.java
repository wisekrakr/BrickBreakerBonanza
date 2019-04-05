package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.components.ParticleEffectComponent;

import java.util.EnumMap;

public class ParticleEffectSystem extends IteratingSystem {
    private final EnumMap<ParticleEffectComponent.ParticleEffectType, ParticleEffectPool>effectPoolEnumMap;
    private BricksGame game;

    public ParticleEffectSystem(BricksGame game) {
        super(Family.all(ParticleEffectComponent.class).get());
        this.game = game;

        effectPoolEnumMap = new EnumMap<ParticleEffectComponent.ParticleEffectType, ParticleEffectPool>(ParticleEffectComponent.ParticleEffectType.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ParticleEffectComponent particleEffectComponent = ComponentMapper.getFor(ParticleEffectComponent.class).get(entity);

        if (particleEffectComponent.effect != null){
            particleEffectComponent.effect.update(deltaTime);
            if (particleEffectComponent.effect.isComplete()){
                entity.remove(ParticleEffectComponent.class);
            }
        }else if (particleEffectComponent.effectType != null){
            ParticleEffectPool effectPool = effectPoolEnumMap.get(particleEffectComponent.effectType);
            if (effectPool==null){
                ParticleEffect particleEffect = game.assetManager().assetManager.get(particleEffectComponent.effectType.getEffectFilePath(), ParticleEffect.class);
                particleEffect.setEmittersCleanUpBlendFunction(false);
                effectPoolEnumMap.put(particleEffectComponent.effectType, new ParticleEffectPool(particleEffect, 1, 128));
            }

            particleEffectComponent.effect = effectPool.obtain();
            particleEffectComponent.effect.setPosition(particleEffectComponent.position.x, particleEffectComponent.position.y);
            particleEffectComponent.effect.scaleEffect(particleEffectComponent.scaling);
        }
    }
}
