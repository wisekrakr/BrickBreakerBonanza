package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {

    private Vector2 position = new Vector2();
    private float velocityX = 0;
    private float velocityY = 0;
    private boolean destroy;
    private float width = 0;
    private float height = 0;
    private boolean hasBall = false;

    private float shootDirection;

    public float getShootDirection() {
        return shootDirection;
    }

    public void setShootDirection(float shootDirection) {
        this.shootDirection = shootDirection;
    }

    public boolean isHasBall() {
        return hasBall;
    }

    public void setHasBall(boolean hasBall) {
        this.hasBall = hasBall;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
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

    @Override
    public void reset() {
        position = new Vector2();
        velocityX = 0;
        velocityY = 0;
        destroy = false;
        width = 0;
        height = 0;

        hasBall = false;
        shootDirection = 0;
    }
}
