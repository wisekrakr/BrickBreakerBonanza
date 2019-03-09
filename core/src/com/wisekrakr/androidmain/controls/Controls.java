package com.wisekrakr.androidmain.controls;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.wisekrakr.androidmain.screens.PlayScreen;

public class Controls implements InputProcessor {
    public boolean left, right, up, down;
    public boolean nextLevel;
    public boolean speedUp;
    public boolean isLeftMouseDown;
    public boolean isRightMouseDown;
    public boolean isDragged;
    public Vector2 mousePosition;

    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) // switch code base on the variable keycode
        {
            case Input.Keys.A:
                left = true;
                keyProcessed = true;
                break;
            case Input.Keys.D:
                right = true;
                keyProcessed = true;
                break;
            case Input.Keys.W:
                up = true;
                keyProcessed = true;
                break;
            case Input.Keys.S:
                down = true;
                keyProcessed = true;
                break;
            case Input.Keys.ALT_LEFT:
                nextLevel = true;
                keyProcessed = true;
                break;
            case Input.Keys.SPACE:
                speedUp = true;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) // switch code base on the variable keycode
        {
            case Input.Keys.A:
                left = false;
                keyProcessed = true;
                break;
            case Input.Keys.D:
                right = false;
                keyProcessed = true;
                break;
            case Input.Keys.W:
                up = false;
                keyProcessed = true;
                break;
            case Input.Keys.S:
                down = false;
                keyProcessed = true;
                break;
            case Input.Keys.ALT_LEFT:
                nextLevel = false;
                keyProcessed = true;
                break;
            case Input.Keys.SPACE:
                speedUp = false;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (button == Input.Buttons.LEFT){
            isLeftMouseDown = true;
        }else if (button == Input.Buttons.RIGHT){
            isRightMouseDown = true;
        }

        mousePosition = new Vector2(screenX, screenY);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragged = false;

        if (button == Input.Buttons.LEFT ) {
            isLeftMouseDown = false;
        }else if (button == Input.Buttons.RIGHT){
            isRightMouseDown = false;
        }

        mousePosition = new Vector2(screenX, screenY);

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        isDragged = true;
        mousePosition = new Vector2(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        mousePosition = new Vector2(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }




}
