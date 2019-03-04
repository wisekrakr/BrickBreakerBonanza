package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.systems.*;
import com.wisekrakr.androidmain.audiovisuals.Visualizer;

public class PlayScreen extends ScreenAdapter  {

    private final InputMultiplexer inputMultiplexer;
    private Viewport viewport;

    private Controls controls;

    private AndroidGame game;

    private Visualizer visualizer;
    private InfoDisplay infoDisplay;
    private TouchControl touchControl;

//    private EntityAudio entityAudio;

    public PlayScreen(AndroidGame game) {
        this.game = game;

        addSystems();

        viewport = new FitViewport(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT, visualizer.getRenderingSystem().getCamera());

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(controls);

        infoDisplay = new InfoDisplay(game);

//        entityAudio = new EntityAudio(game);
    }


    /**
     * Add remaining systems we did not need to add to the Gamethread.
     */
    private void addSystems() {

        game.getGameThread().getLevelGenerationSystem().init();

        game.getEngine().addSystem(new PlayerSystem(game));
        game.getEngine().addSystem(new BrickSystem(game));
        game.getEngine().addSystem(new BallSystem(game));

        visualizer = new Visualizer(game);
        controls = new Controls();
        touchControl = new TouchControl(game);

        game.getEngine().addSystem(new PlayerControlSystem(game, controls, visualizer.getRenderingSystem().getCamera()));
        game.getEngine().addSystem(new CollisionSystem());

        game.getEngine().addSystem(new ObstacleSystem(game));

    }
    private void mapLoading() {
         game.getGameThread().getEntityFactory().loadMap();
    }

    @Override
    public void show() {

        visualizer.getRenderingSystem().getCamera().setToOrtho(false, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        /**
         * This gameclock will keep time per second and will be used over the whole game. This delta is holy. (for now)
         *         This screen will therefore keep the time, so when you get a game over or switch from screen and then start a new game,
         *         a new gameclock will start (with every new instance of a PlayScreen.
         */

        game.getGameThread().getTimeKeeper().gameClock += delta;

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getEngine().update(delta);
        visualizer.getRenderingSystem().getCamera().update();

//        game.getGameThread().getEntityCreator().getTiledMapRenderer().setView(visualizer.getRenderingSystem().getCamera());
//        game.getGameThread().getEntityCreator().getTiledMapRenderer().render();

        game.getGameThread().getLevelGenerationSystem().updateLevels(delta);

        infoDisplay.renderDisplay(
                game.getGameThread().getTimeKeeper(),
                delta
        );

        visualizer.debugDrawable();

       // System.out.println(game.getGameThread().getEntityCreator().getTotalBalls().size());//todo remove

        //touchControl.renderTouchControls();

//        visualizer.draw();

//        entityAudio.audioForAction(controls);
//        entityAudio.audioForEntity();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
    }


}
