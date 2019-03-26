package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.helpers.LabelFormatter;
import com.wisekrakr.androidmain.helpers.SpriteHelper;
import javafx.scene.text.TextBoundsType;

public class MenuScreen extends ScreenAdapter {

    private Stage stage;
    private AndroidGame game;
    private TextureRegion textureRegion;
    private TextureRegion textureRegionSean;
    private Label formatLabel;

    public MenuScreen(AndroidGame game) {
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
                game.changeScreen(AndroidGame.APPLICATION);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AndroidGame.PREFERENCES);
            }
        });

        reset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getGameThread().startNewLevelGeneration();
            }
        });

        Texture texture = new Texture("images/background/mainbg.jpg");
        textureRegion = new TextureRegion(texture);
        Texture texSean = new Texture("images/others/seanA.png");
        textureRegionSean = new TextureRegion(texSean);

        Sound sound = game.assetManager().assetManager.get("sounds/titleA.wav", Sound.class);
        sound.play(game.getGamePreferences().getSoundVolume());

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
        stage.getBatch().draw(textureRegionSean, 0,0);
        stage.getBatch().end();
        stage.draw();


    }

//    private void labelFormattingTest(final float delta){
//
//        formatLabel = new Label("A Game made with Love, by The Wisekrakr", skin);
//        formatLabel.setBounds(10, 10, Gdx.graphics.getWidth() - 10f, 30);
//
//
//        formatLabel.addAction(new TemporalAction(10) {
//            LabelFormatter formatter = new LabelFormatter("A Game made with Love, by The Wisekrakr");
//            @Override
//            protected void update(float percent) {
//                formatLabel.setText(formatter.getText(delta));
//            }
//        });
//
//    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
