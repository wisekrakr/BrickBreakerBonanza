package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class BrickComponent implements Component, Pool.Poolable {

    public boolean destroy = false;
    public boolean outOfBounds = false;

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
    public void setOutOfBounds(boolean outOfBounds) {
        this.outOfBounds = outOfBounds;
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

    public Vector2 position = new Vector2();

    public float width = 0f;
    public float height = 0f;

    @Override
    public void reset() {
        destroy = false;
        outOfBounds = false;
        entityColor = null;
        position = new Vector2();
        width = 0f;
        height = 0f;
    }
}
