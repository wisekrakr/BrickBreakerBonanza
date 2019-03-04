package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.systems.SystemEntityContext;

import java.util.ArrayList;

public class PowerUpComponent implements Component, Pool.Poolable{

    public boolean destroy = false;
    public boolean outOfBounds = false;

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
    public void setOutOfBounds(boolean outOfBounds) {
        this.outOfBounds = outOfBounds;
    }

    public Vector2 position = new Vector2();
    public float radius = 0f;
    public float velocityX = 0;
    public float velocityY = 0;

    @Override
    public void reset() {
        position = new Vector2();
        radius = 0;
        velocityX = 0;
        velocityY = 0;
    }
}
