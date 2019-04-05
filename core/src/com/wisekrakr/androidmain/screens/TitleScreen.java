package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.GameConstants;

public class TitleScreen extends ScreenAdapter {

    private final SpriteBatch spriteBatch;
    private final Stage stage;
    private BricksGame game;
    private float countDown;
    private TextureRegion title;


    public TitleScreen(BricksGame game) {
        this.game = game;

        stage = new Stage(new FitViewport(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT));

        spriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {

        title = new TextureRegion(new Texture("images/others/title.jpg"));
    }


    @Override
    public void render(float delta) {
        stage.act();

        Gdx.gl.glClearColor(0,0,0,1); //  clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        spriteBatch.begin();
        spriteBatch.draw(title, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();

        countDown += delta;
        if (countDown > 3){
            countDown = 0;

            game.changeScreen(BricksGame.MENU);

        }

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
    }
}
