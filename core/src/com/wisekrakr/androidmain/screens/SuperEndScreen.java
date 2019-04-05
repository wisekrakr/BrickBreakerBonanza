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
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

public class SuperEndScreen extends ScreenAdapter {

    private Stage stage;
    private BricksGame game;
    private Label highScoreLabel;

    public SuperEndScreen(BricksGame game) {
        this.game = game;

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        BitmapFont font = game.assetManager().assetManager.get("font/gamerFont.fnt");
        font.getData().setScale(GameConstants.FONT_SCALE);

        Skin skin = game.assetManager().assetManager.get(String.valueOf(Gdx.files.internal("font/flat-earth-ui.json")));

        Label currentScoreTextLabel = new Label("YOUR SCORE:", new Label.LabelStyle(font, Color.LIME));
        Label highScoreTextLabel = new Label("HIGH SCORE:", new Label.LabelStyle(font, Color.LIME));
        Label currentScoreLabel = new Label(Integer.toString(ScoreKeeper.getScore()), new Label.LabelStyle(font, Color.LIME));
        highScoreLabel = new Label(Integer.toString(game.getGamePreferences().getHighScore()), new Label.LabelStyle(font, Color.LIME));
        Label gameOverLabel = new Label("YOU DID IT!", new Label.LabelStyle(font, Color.GREEN));

        TextButton mainMenu = new TextButton("main menu", skin);
        TextButton exit = new TextButton("exit", skin);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(mainMenu).uniformX();
        table.row();
        table.add(exit).uniformX();
        table.row();
        table.add(currentScoreTextLabel).uniformX().padTop(50);
        table.row();
        table.add(currentScoreLabel).uniformX().padTop(10);
        table.row();
        table.add(highScoreTextLabel).uniformX().padTop(50);
        table.row();
        table.add(highScoreLabel).uniformX().padTop(10);

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(BricksGame.MENU);
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

        highScoreLabel.setText(game.getGamePreferences().getHighScore());
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
