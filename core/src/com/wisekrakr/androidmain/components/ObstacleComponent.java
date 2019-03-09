package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.systems.SystemEntityContext;


public class ObstacleComponent implements Component, Pool.Poolable{

    public boolean destroy = false;

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public Vector2 position = new Vector2();
    public float velocityX = 0f;
    public float velocityY = 0f;

    public float width = 0f;
    public float height = 0f;

    @Override
    public void reset() {
        destroy = false;

        position = new Vector2();
        velocityX = 0;
        velocityY = 0;
        width = 0f;
        height = 0f;
    }
}
