package com.wisekrakr.androidmain.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class ButtonHelper {
    private static ArrayList<ImageButton>buttonList = new ArrayList<ImageButton>();

    public static ArrayList<ImageButton> getButtonList() {
        return buttonList;
    }

    public static ImageButton characterButton(String region){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/players/players.atlas"));
        TextureRegion atlasRegion = atlas.findRegion(region);
        TextureRegionDrawable regionDrawable = new TextureRegionDrawable(atlasRegion);

        ImageButton button = new ImageButton(regionDrawable);
        buttonList.add(button);

        return button;
    }
}
