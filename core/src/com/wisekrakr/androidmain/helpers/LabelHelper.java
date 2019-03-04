package com.wisekrakr.androidmain.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LabelHelper {
    private Integer integer;
    private Label name;
    private Label number;

    public static Label label(String text, BitmapFont font, Color color){
        return new Label(text, new Label.LabelStyle(font, color));
    }
}
