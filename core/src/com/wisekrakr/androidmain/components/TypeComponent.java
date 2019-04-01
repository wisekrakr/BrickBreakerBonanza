package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TypeComponent implements Component, Pool.Poolable {

    public enum Type{
        PLAYER, ENEMY, OBSTACLE, SCENERY, POWER, PENIS, TESTICLE //todo (diff level looks) jungle obstacle, ice obstacle
    }

    private Type type = null;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }



    @Override
    public void reset() {
        type = null;

    }
}
