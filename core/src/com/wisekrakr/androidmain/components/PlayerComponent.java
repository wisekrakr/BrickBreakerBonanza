package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.systems.SystemEntityContext;

public class PlayerComponent implements Component, Pool.Poolable {

    private Vector2 position = new Vector2();
    private float velocityX = 0;
    private float velocityY = 0;
    private boolean destroy;
    private float radius = 0;
    private float direction = 0;
    private float penisLength = 0;
    private float penisGirth = 0;

    public boolean isMoving = false;

    public void setMoving(boolean moving) {
        isMoving = moving;
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

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getPenisLength() {
        return penisLength;
    }

    public void setPenisLength(float penisLength) {
        this.penisLength = penisLength;
    }

    public float getPenisGirth() {
        return penisGirth;
    }

    public void setPenisGirth(float penisGirth) {
        this.penisGirth = penisGirth;
    }

    private Entity attachedEntity;

    public Entity getAttachedEntity() {
        return attachedEntity;
    }

    public void setAttachedEntity(Entity attachedEntity) {
        this.attachedEntity = attachedEntity;
    }

    @Override
    public void reset() {
        position = new Vector2();
        velocityX = 0;
        velocityY = 0;
        destroy = false;

        radius = 0;
        direction = 0;

        penisLength = 0;
        penisGirth = 0;

        isMoving = false;

        attachedEntity = null;
    }
}
