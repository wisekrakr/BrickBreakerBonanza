package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class StateComponent implements Component, Pool.Poolable {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_MOVING = 1;
    public static final int STATE_HIT = 2;

    private int state = 0;
    public float time = 0.0f;
    public boolean isLooping = false;

    public void set(int newState){
        state = newState;
        time = 0.0f;
    }

    public int get(){
        return state;
    }

    @Override
    public void reset() {
        state = 0;
        time = 0.0f;
        isLooping = false;
    }
}
