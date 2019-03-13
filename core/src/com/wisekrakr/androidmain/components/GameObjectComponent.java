package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.systems.SystemEntityContext;

public class GameObjectComponent implements Component, Pool.Poolable {

    public Vector2 position = new Vector2();
    public float velocityX = 0;
    public float velocityY = 0;

    public boolean destroy;
    public boolean outOfBounds;

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
    public void setOutOfBounds(boolean outOfBounds) {
        this.outOfBounds = outOfBounds;
    }

    public float width = 0;
    public float height = 0;
    public float radius = 0;

    public float direction = 0;

    public SystemEntityContext getSystemEntityContext() {
        return systemEntityContext;
    }

    private SystemEntityContext systemEntityContext = new SystemEntityContext() {
        @Override
        public void outOfBounds(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent) {

        }

        @Override
        public void powerHandler(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent) {

        }

        @Override
        public void destroy(Entity entity, Box2dBodyComponent bodyComponent, GameObjectComponent gameObjectComponent) {

        }
    };

    @Override
    public void reset() {
        position = new Vector2();
        velocityX = 0;
        velocityY = 0;
        destroy = false;
        outOfBounds = false;
        width = 0;
        height = 0;
        radius = 0;
        direction = 0;
    }
}
