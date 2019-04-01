package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

public class CollisionComponent implements Component, Pool.Poolable {
    public Entity collisionEntity;

    public boolean hitSurface = false;
    public boolean hitObstacle = false;
    public boolean hitPower = false;
    public boolean hitPlayer = false;
    public boolean hitEnemy = false;
    public boolean hitPenis = false;

    public void setHitSurface(boolean hitSurface) {
        this.hitSurface = hitSurface;
    }
    public void setHitObstacle(boolean hitObstacle) {
        this.hitObstacle = hitObstacle;
    }
    public void setHitPower(boolean hitPower) {
        this.hitPower = hitPower;
    }
    public void setHitPlayer(boolean hitPlayer) {
        this.hitPlayer = hitPlayer;
    }
    public void setHitEnemy(boolean hitEnemy) {
        this.hitEnemy = hitEnemy;
    }
    public void setHitPenis(boolean hitPenis) {
        this.hitPenis = hitPenis;
    }

    @Override
    public void reset() {

        collisionEntity = null;
        hitPenis = false;
        hitPlayer = false;
        hitSurface = false;
        hitObstacle = false;
        hitEnemy = false;

    }
}
