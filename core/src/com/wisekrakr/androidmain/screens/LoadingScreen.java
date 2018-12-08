package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.systems.RenderingSystem;

public class LoadingScreen implements Screen {

    public final int IMAGE = 1;
    public final int FONT = 2;
    public final int SKINS = 3;
    public final int SOUND = 4;
    public final int MUSIC = 5;

    private final SpriteBatch spriteBatch;
    private final Stage stage;
    private AndroidGame game;
    private TextureAtlas.AtlasRegion title;
    private TextureAtlas.AtlasRegion dash;
    private int currentLoadingStage = 0;
    private float countDown = 2f;
    private Animation flameAnimation;
    private float stateTime;

    public LoadingScreen(AndroidGame game) {
        this.game = game;

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()));

        spriteBatch = new SpriteBatch();
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    }

    @Override
    public void show() {

        game.assetManager().queueAddLoadingImages();

        game.assetManager().assetManager.finishLoading();

        TextureAtlas atlas = game.assetManager().assetManager.get("images/loading/loading.atlas");
        title = atlas.findRegion("staying-alight-logo");
        dash = atlas.findRegion("loading-dash");

        flameAnimation = new Animation(0.07f, atlas.findRegions("flames"), Animation.PlayMode.LOOP);

    }

    private void drawLoadingBar(int stage, TextureRegion currentFrame){
        for(int i = 0; i < stage;i++){
            spriteBatch.draw(currentFrame, Gdx.graphics.getWidth()/2 - title.getRegionWidth()/2 + (i * 60), Gdx.graphics.getHeight()/2 - title.getRegionHeight()/2, 40, 40);
            spriteBatch.draw(dash, Gdx.graphics.getWidth()/2 - title.getRegionWidth()/2 + (i * 60), Gdx.graphics.getHeight()/2 - title.getRegionHeight()/2, 40, 40);
        }
    }

    @Override
    public void render(float delta) {
        stage.act();

        Gdx.gl.glClearColor(0,0,0,1); //  clear the screen
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        stateTime += delta;

        TextureRegion currentFrame = (TextureRegion) flameAnimation.getKeyFrame(stateTime, true);

        spriteBatch.begin();
        drawLoadingBar(currentLoadingStage , currentFrame);
        spriteBatch.draw(title, Gdx.graphics.getWidth()/2 - title.getRegionWidth()/2, Gdx.graphics.getHeight()/2 - title.getRegionHeight()/2 );
        spriteBatch.end();


        if (game.assetManager().assetManager.update()){
            currentLoadingStage += 1;
            switch (currentLoadingStage){
                case IMAGE:
                    System.out.println("Loading images...");
                    game.assetManager().queuePlayerImages();
                    game.assetManager().loadTextures();

                    break;
                case FONT:
                    System.out.println("Loading fonts...");
                    game.assetManager().loadFonts();
                    break;
                case SOUND:
                    System.out.println("Loading sounds...");
                    game.assetManager().loadSounds();
                    break;
                case MUSIC:
                    System.out.println("Loading music...");
                    game.assetManager().loadMusic();
                    break;
                case SKINS:
                    System.out.println("Loading skins...");
                    game.assetManager().loadSkins();
                    break;

            }

            if (currentLoadingStage > 6){
                countDown -= delta;

                currentLoadingStage = 6;

                if (countDown <= 0){
                    game.changeScreen(AndroidGame.MENU);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
    }
}