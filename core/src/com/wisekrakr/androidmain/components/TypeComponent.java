package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TypeComponent implements Component, Pool.Poolable {

    public enum Type{
        PLAYER, BALL, BRICK, OBSTACLE, SCENERY, POWER, OTHER;
    }

    private Type type = null;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Tag {
        PLAYER_BALL, A_PRIORI_ENTITY, NONE
    }

    public Tag tag = null;

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public void reset() {
        type = null;
        tag = null;
    }
}
