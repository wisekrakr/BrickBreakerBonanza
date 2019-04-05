package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.BricksGame;

public class MenuScreen extends ScreenAdapter {

    private Stage stage;
    private BricksGame game;
    private TextureRegion textureRegion;
    private TextureRegion textureRegionTitle;

    public MenuScreen(BricksGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.bottom().center();

        Skin skin = game.assetManager().assetManager.get(String.valueOf(Gdx.files.internal("font/flat-earth-ui.json")));

        TextButton newGame = new TextButton("start", skin);
        TextButton preferences = new TextButton("preferences", skin);
        TextButton exit = new TextButton("exit", skin);
        TextButton reset = new TextButton("reset levels", skin);

        table.add(newGame).expandX();
        table.row();
        table.add(preferences).expandX();
        table.row();
        table.add(reset).expandX();
        table.row();
        table.add(exit).expandX();

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();

            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(BricksGame.APPLICATION);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(BricksGame.PREFERENCES);
            }
        });

        reset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getGameThread().startNewLevelGeneration();
            }
        });

        textureRegion = new TextureRegion(new Texture("images/background/mainbg.jpg"));
        textureRegionTitle = new TextureRegion(new Texture("images/others/controls.png"));

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        stage.getBatch().begin();
        stage.getBatch().draw(textureRegion, Gdx.graphics.getWidth()/2f - Gdx.graphics.getWidth()/2f,
                Gdx.graphics.getHeight()/2f - Gdx.graphics.getHeight()/2f,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        stage.getBatch().draw(textureRegionTitle, 0, Gdx.graphics.getHeight()/2f - Gdx.graphics.getHeight()/2f,
//                Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        stage.getBatch().end();
        stage.draw();

    }


    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
