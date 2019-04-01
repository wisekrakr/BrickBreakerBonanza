package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.GamePreferences;

public class LevelCompleteScreen extends ScreenAdapter {

    private AndroidGame game;
    private Stage stage;

    public LevelCompleteScreen(AndroidGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        BitmapFont font = game.assetManager().assetManager.get("font/gamerFont.fnt");
        font.getData().setScale(GameConstants.FONT_SCALE);

        Skin skin = game.assetManager().assetManager.get(String.valueOf(Gdx.files.internal("font/flat-earth-ui.json")));

        Label highScoreTextLabel = new Label("HIGH SCORE" , new Label.LabelStyle(font, Color.LIME));
        Label highScoreLabel = new Label(game.getGamePreferences().getHighScore() + " on " +
                game.getGameThread().getTimeKeeper().getDate(), new Label.LabelStyle(font, Color.LIME));
        Label playAgainLabel = new Label("play another one?", new Label.LabelStyle(font, Color.LIME));

        TextButton nextLevel = new TextButton("next level", skin);
        TextButton mainMenu = new TextButton("main menu", skin);

        table.add(playAgainLabel).expandX().padTop(10f);
        table.row();
        table.add(nextLevel).uniformX();
        table.row();
        table.add(mainMenu).uniformX();
        table.row();
        table.add(highScoreTextLabel).uniformX().padTop(50);
        table.row();
        table.add(highScoreLabel).uniformX().padTop(10);

        nextLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AndroidGame.APPLICATION);
            }
        });

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AndroidGame.MENU); //app screen shows level 2
            }
        });

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
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
