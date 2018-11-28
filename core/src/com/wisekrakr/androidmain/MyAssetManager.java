package com.wisekrakr.androidmain;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;


public class MyAssetManager implements Disposable {

    public AssetManager assetManager = new AssetManager();
    /*
     * All the Sounds loaded in the the AssetManager
     */
    public void loadSounds() {

        assetManager.finishLoading();
    }
    /*
     * All the Fonts loaded in the the AssetManager
     */
    public void loadFonts() {
        assetManager.load("font/default.fnt", BitmapFont.class);
        assetManager.load("font/myFont.fnt", BitmapFont.class);
        assetManager.load("font/myFontBlack.fnt", BitmapFont.class);
        assetManager.load("font/achievementFont.fnt", BitmapFont.class);
        assetManager.load("font/gamerFont.fnt", BitmapFont.class);

        assetManager.finishLoading();
    }
    /*
     * All the Videos loaded in the the AssetManager
     */
    public void loadVideos() {

        assetManager.finishLoading();
    }
    /*
     * All the Skins loaded in the the AssetManager
     */
    public void loadSkins() {

        SkinLoader.SkinParameter skinParameterUISkin = new SkinLoader.SkinParameter("font/uiskin.atlas");
        assetManager.load("font/uiskin.json", Skin.class, skinParameterUISkin);
        SkinLoader.SkinParameter skinParameterFlatEarthSkin = new SkinLoader.SkinParameter("font/flat-earth-ui.atlas");
        assetManager.load("font/flat-earth-ui.json", Skin.class, skinParameterFlatEarthSkin);
        assetManager.finishLoading();

        assetManager.finishLoading();
    }
    /*
     * All the Textures loaded in the the AssetManager
     */
    public void loadTextures() {

        assetManager.load("images/dudes/dude_front.png", Texture.class);
        assetManager.load("images/dudes/dude_back.png", Texture.class);
        assetManager.load("images/dudes/dude_left.png", Texture.class);
        assetManager.load("images/dudes/dude_right.png", Texture.class);

        assetManager.load("images/objects/pokeball.png", Texture.class);
        assetManager.load("images/objects/earthball.png", Texture.class);
        assetManager.load("images/objects/starball.png", Texture.class);


        assetManager.finishLoading();
    }

    /*
     * All the Music loaded in the the AssetManager
     */
    public void loadMusic() {

        assetManager.finishLoading();
    }

    public void queuePlayerImages(){
        assetManager.load("images/game/game.atlas", TextureAtlas.class);
    }



    public void queueAddLoadingImages(){
        assetManager.load("images/loading/loading.atlas", TextureAtlas.class);
    }

    public void loadParticleEffects(){
        ParticleEffectLoader.ParticleEffectParameter effectParameter = new ParticleEffectLoader.ParticleEffectParameter();
        effectParameter.imagesDir = new FileHandle("images/particles/exhaust.party");
        assetManager.load("images/particles/exhaust.party", ParticleEffect.class);

    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }





}
