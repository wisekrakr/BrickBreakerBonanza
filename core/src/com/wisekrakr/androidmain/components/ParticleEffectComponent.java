package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class ParticleEffectComponent implements Component, Pool.Poolable {

    public ParticleEffectPool.PooledEffect effect;
    public ParticleEffectType effectType;
    public final Vector2 position = new Vector2();
    public float scaling;

    public enum ParticleEffectType{

        EFFECT_TYPE("images/particles/exhaust.party");

        private final String effectFilePath;

        ParticleEffectType(String effectFilePath) {
            this.effectFilePath = effectFilePath;
        }

        public String getEffectFilePath() {
            return effectFilePath;
        }
    }



    @Override
    public void reset() {
        if (effect!=null){
            effect.free();
            effect = null;
        }
        position.set(0,0);
        effectType = null;
        scaling = 0;
    }
}
