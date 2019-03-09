package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.systems.RenderingSystem;

public class PreferencesScreen extends ScreenAdapter {

    private AndroidGame game;
    private Stage stage;

    public PreferencesScreen(AndroidGame game) {
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        Skin skin = game.assetManager().assetManager.get(String.valueOf(Gdx.files.internal("font/flat-earth-ui.json")));

        Label titleLabel = new Label("Preferences", skin);
        Label volumeMusicLabel = new Label("Music Volume", skin);
        Label volumeSoundLabel = new Label("Sound Volume", skin);
        Label musicOnOffLabel = new Label("Music on/off", skin);
        Label soundOnOffLabel = new Label("Sound on/off", skin);

        //volume
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin);
        volumeMusicSlider.setValue(game.getGamePreferences().getMusicVolume());
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getGamePreferences().setMusicVolume( volumeMusicSlider.getValue() );
                return false;
            }
        });

        final Slider soundMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin);
        soundMusicSlider.setValue(game.getGamePreferences().getSoundVolume());
        soundMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getGamePreferences().setSoundVolume( soundMusicSlider.getValue() );
                return false;
            }
        });

        //music
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( game.getGamePreferences().isMusicEnabled() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                game.getGamePreferences().setMusicEnabled( enabled );
                return false;
            }
        });

        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked( game.getGamePreferences().isSoundEffectsEnabled() );
        soundEffectsCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getGamePreferences().setSoundEffectsEnabled( enabled );
                return false;
            }
        });

        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(AndroidGame.MENU);
            }
        });

        final TextButton resetHighScores = new TextButton("Reset Highscore", skin);
        resetHighScores.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getGamePreferences().setHighScore(0);
            }
        });

        table.add(titleLabel);
        table.row();
        table.add(volumeMusicLabel);
        table.add(volumeMusicSlider);
        table.row();
        table.add(musicOnOffLabel);
        table.add(musicCheckbox);
        table.row();
        table.add(volumeSoundLabel);
        table.add(soundMusicSlider);
        table.row();
        table.add(soundOnOffLabel);
        table.add(soundEffectsCheckbox);
        table.row();
        table.add(resetHighScores);
        table.row();
        table.add(backButton);

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
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }



    @Override
    public void dispose() {
        stage.dispose();
    }
}
