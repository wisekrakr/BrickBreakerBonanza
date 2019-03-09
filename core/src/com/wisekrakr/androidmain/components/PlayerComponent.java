package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.systems.SystemEntityContext;

public class PlayerComponent implements Component, Pool.Poolable {

    public boolean destroy = false;

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public float width = 0f;
    public float height = 0f;

    public boolean hasBall = false;

    public void setHasBall(boolean hasBall) {
        this.hasBall = hasBall;
    }

    @Override
    public void reset() {
        width = 0f;
        height = 0f;
        hasBall = false;
        destroy = false;
    }
}
