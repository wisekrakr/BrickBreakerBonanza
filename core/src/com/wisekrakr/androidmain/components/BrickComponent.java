package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.systems.SystemEntityContext;

public class BrickComponent implements Component, Pool.Poolable {

    public boolean destroy = false;
    public boolean outOfBounds = false;

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
    public void setOutOfBounds(boolean outOfBounds) {
        this.outOfBounds = outOfBounds;
    }

    public enum BrickColor {
        RED, BLUE, YELLOW, GREEN, PURPLE, ORANGE, CYAN
    }

    private static BrickColor[] brickColors = BrickColor.values();

    public static BrickColor randomBrickColor(){
        return brickColors[GameHelper.randomGenerator.nextInt(brickColors.length)];
    }

    private BrickColor brickColor;

    public BrickColor getBrickColor() {
        return brickColor;
    }

    public void setBrickColor(BrickColor brickColor) {
        this.brickColor = brickColor;
    }

    public Vector2 position = new Vector2();

    public float width = 0f;
    public float height = 0f;

    @Override
    public void reset() {
        brickColor = null;
        position = new Vector2();
        width = 0f;
        height = 0f;
    }
}
