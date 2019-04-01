package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.List;

public class BallComponent implements Component, Pool.Poolable {

    public final Vector2 initialPosition = new Vector2();

    public float speed = 1000000f;
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public List<Vector2>initialPositions = new ArrayList<Vector2>();

    public float chaseInterval = 0;

    private EntityColor entityColor = null;

    public BallColorContext getBallColorContext() {
        return ballColorContext;
    }

    private BallColorContext ballColorContext = new BallColorContext() {

        @Override
        public EntityColor getBallColor() {
            return entityColor;
        }

        @Override
        public void setBallColor(EntityColor color) {
            entityColor = color;
        }
    };

    @Override
    public void reset() {

        initialPositions = new ArrayList<Vector2>();
        chaseInterval = 0;

        speed = 1000000f;
    }
}
