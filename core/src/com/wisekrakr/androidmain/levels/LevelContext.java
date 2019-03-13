package com.wisekrakr.androidmain.levels;

public interface LevelContext {

    void init();

    void startLevel(int numberOfLevel);

    void updateLevel(int numberOfLevel, float delta);

    void completeLevel(int numberOfLevel);

    void gameOver(int numberOfLevel);
}
