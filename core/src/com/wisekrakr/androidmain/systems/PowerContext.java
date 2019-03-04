package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;

public interface PowerContext {

    void init();
    void spawnPower();
    void respite(Entity power);
    void powerTime();
    void exit();

}
