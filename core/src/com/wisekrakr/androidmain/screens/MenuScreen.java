package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.helpers.SpriteHelper;

public class MenuScreen extends ScreenAdapter {

    private Stage stage;
    private AndroidGame game;
    private TextureRegion textureRegion;

    public MenuScreen(AndroidGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT));
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

        table.add(newGame).expandX();
        table.row();
        table.add(preferences).expandX();
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
                game.changeScreen(AndroidGame.APPLICATION);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AndroidGame.PREFERENCES);
            }
        });

        Texture texture = new Texture("images/background/mainbg.jpg");
        textureRegion = new TextureRegion(texture);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        stage.getBatch().begin();
        stage.getBatch().draw(textureRegion, GameConstants.WORLD_WIDTH/2 - 300f/2 ,
                GameConstants.WORLD_HEIGHT/2 - 400f/2,
                300f, GameConstants.WORLD_HEIGHT);
        stage.getBatch().end();

        stage.draw();

    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
