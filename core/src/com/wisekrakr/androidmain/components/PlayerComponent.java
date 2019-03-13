package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.systems.SystemEntityContext;

public class PlayerComponent implements Component, Pool.Poolable {

    public boolean isMoving = false;

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    @Override
    public void reset() {
       isMoving = false;
    }
}
