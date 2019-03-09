package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Pool;

import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.systems.SystemEntityContext;

import java.awt.Color;
import java.util.ArrayList;

public class BallComponent implements Component, Pool.Poolable {

    public Vector2 position = new Vector2();
    public float radius = 0;
    public int initialBalls = 1;
    public void setInitialBalls(int initialBalls) {
        this.initialBalls = initialBalls;
    }

    public boolean destroy = false;
    public boolean outOfBounds = false;

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
    public void setOutOfBounds(boolean outOfBounds) {
        this.outOfBounds = outOfBounds;
    }

    @Override
    public void reset() {
        position = new Vector2();
        radius = 0;
        initialBalls = 1;
        destroy = false;
        outOfBounds = false;
    }
}
