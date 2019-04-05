package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class BrickComponent implements Component, Pool.Poolable {
    private boolean destroy = false;
    private Vector2 position = new Vector2();
    private float width = 0f;
    private float height = 0f;

    public boolean isDestroy() {
        return destroy;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    private EntityColor entityColor = null;

    public BrickColorContext getBrickColorContext() {
        return brickColorContext;
    }

    private BrickColorContext brickColorContext = new BrickColorContext() {

        @Override
        public EntityColor getBrickColor() {
            return entityColor;
        }

        @Override
        public void setBrickColor(EntityColor color) {
            entityColor = color;
        }
    };



    @Override
    public void reset() {
        destroy = false;
        entityColor = null;
        position = new Vector2();
        width = 0f;
        height = 0f;
    }
}
