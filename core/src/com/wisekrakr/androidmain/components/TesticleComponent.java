package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;


public class TesticleComponent implements Component, Pool.Poolable{

    private Vector2 position = new Vector2();
    private float velocityX = 0;
    private float velocityY = 0;
    private boolean destroy;
    private float radius = 0;

    private Entity attachedEntity;

    public Entity getAttachedEntity() {
        return attachedEntity;
    }

    public void setAttachedEntity(Entity attachedEntity) {
        this.attachedEntity = attachedEntity;
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

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }



    @Override
    public void reset() {
        position = new Vector2();
        velocityX = 0;
        velocityY = 0;
        destroy = false;

        radius = 0;

        attachedEntity = null;
    }
}
