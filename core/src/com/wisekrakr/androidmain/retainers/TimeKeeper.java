package com.wisekrakr.androidmain.retainers;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TimeKeeper {

    public float time = 0;

    public void setTime(float time) {
        this.time = time;
    }

    public float gameClock = 0;

    public void reset(){
        time = 0f;
    }

}
