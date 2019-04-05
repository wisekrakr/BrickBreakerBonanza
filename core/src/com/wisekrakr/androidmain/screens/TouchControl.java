package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.GameConstants;


public class TouchControl implements Disposable {
    private Stage stage;
    private BricksGame game;
    private Touchpad touchPad;

    public TouchControl(BricksGame game) {
        this.game = game;

        stage = new Stage(new FitViewport(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        setUpTouchPad();

        stage.addActor(touchPad);
    }

    private void setUpTouchPad(){
        Skin skin = game.assetManager().assetManager.get(String.valueOf(Gdx.files.internal("font/flat-earth-ui.json")));

        touchPad = new Touchpad(5, skin);
        touchPad.setBounds(10,10,GameConstants.WORLD_WIDTH/2.5f,GameConstants.WORLD_HEIGHT/5);

    }

    public void renderTouchControls() {
        stage.act();
        stage.draw();

        if (touchPad.isTouched()){
            touchPad.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("bliep");
                }
            });
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
