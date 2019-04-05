package com.wisekrakr.androidmain;


import com.badlogic.gdx.Gdx;

public class GameConstants {

    /*
    Viewport constants
     */

    public static float WORLD_WIDTH = Gdx.graphics.getWidth()/4f;
    public static float WORLD_HEIGHT = Gdx.graphics.getHeight()/4f;

     /*
    HUD constants
     */

    public static float HUD_WIDTH = Gdx.graphics.getWidth()/10f;
    public static float HUD_HEIGHT = Gdx.graphics.getHeight()/10f;


    /*
   FONT constants
    */
    public static float FONT_SCALE = (Gdx.graphics.getWidth()/100f)/10f;

    /*
    Some constants for game objects
     */

    public static float BALL_RADIUS = WORLD_WIDTH /60;
    public static float BRICK_WIDTH = WORLD_WIDTH /25;
    public static float BRICK_HEIGHT = WORLD_WIDTH /40;
    public static float PLAYER_WIDTH = WORLD_WIDTH /20;
    public static float PLAYER_HEIGHT = WORLD_WIDTH /120;
    public static float POWER_WIDTH = WORLD_WIDTH /50;
    public static float POWER_HEIGHT = WORLD_WIDTH /50;

}
