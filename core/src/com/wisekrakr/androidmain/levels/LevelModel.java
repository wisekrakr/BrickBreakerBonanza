package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.factories.EntityFactory;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;


import com.wisekrakr.androidmain.helpers.EntityColorHelper;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;
import com.wisekrakr.androidmain.systems.PowerImplementation;


public class LevelModel extends AbstractLevelContext{

    private AndroidGame game;
    private EntityFactory entityFactory;
    private PowerImplementation powerImplementation;

    public LevelModel(AndroidGame game, EntityFactory entityFactory) {
        this.game = game;
        this.entityFactory = entityFactory;

    }

    private void constantEntities(){
        entityFactory.createPlayer(GameConstants.WORLD_WIDTH /2, GameConstants.WORLD_HEIGHT /2);

        entityFactory.createWalls(0,0, 1f, GameConstants.WORLD_HEIGHT *2);
        entityFactory.createWalls(GameConstants.WORLD_WIDTH,0, 1f, GameConstants.WORLD_HEIGHT *2);
        entityFactory.createWalls(GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT, GameConstants.WORLD_WIDTH *2,1f);
        entityFactory.createWalls(0,0, GameConstants.WORLD_WIDTH * 2, 1f);
    }

    @Override
    public void startLevel(int numberOfLevel) {
        constantEntities();
        if (LevelNumber.valueOf(numberOfLevel) != null){
            for (int i = 0; i < LevelNumber.valueOf(numberOfLevel).getValue(); i++) {

                entityFactory.createBall(
                        GameHelper.notFilledPosition(game).x,
                        GameHelper.notFilledPosition(game).y,
                        EntityColorHelper.randomEntityColor()
                );

                ScoreKeeper.setInitialBalls(LevelNumber.valueOf(numberOfLevel).getValue());
            }

        }
//        powerImplementation = new PowerImplementation(game, (int) GameHelper.generateRandomNumberBetween(2,5));
    }

    @Override
    public void updateLevel(int numberOfLevel, float delta) {
        for (Entity player: game.getEngine().getEntities()) {
            if (player.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER) {
                if (player.getComponent(PlayerComponent.class).isMoving) {
                    game.getGameThread().getTimeKeeper().time -= delta;
                } else {
                    game.getGameThread().getTimeKeeper().setTime(game.getGameThread().getTimeKeeper().time);
                }
            }
        }
//        powerImplementation.getPowerContext().init();

        if (ScoreKeeper.lives == 0){
            gameOver(numberOfLevel);
        }else {
            if (game.getGameThread().getTimeKeeper().time <=0){
                completeLevel(numberOfLevel);
            }

            for (Entity entity: game.getEngine().getEntities()){
                if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.POWER) {
//                powerImplementation.updatingPowerUpSystem(entity);
                }
            }
        }
    }

    @Override
    public void completeLevel(int numberOfLevel) {
        game.getGamePreferences().setLevelCompleted(numberOfLevel, true);

        cleanUp();
    }

    @Override
    public void gameOver(int numberOfLevel) {
        System.out.println("game over");

        game.getGameThread().getTimeKeeper().reset();
        ScoreKeeper.reset();

        cleanUp();

        game.changeScreen(AndroidGame.ENDGAME);
    }


    private void cleanUp(){
        for (Entity entity: game.getEngine().getEntities()){
            entity.getComponent(Box2dBodyComponent.class).isDead = true;
        }

        entityFactory.getGameObjects().clear();

        game.getGameThread().getTimeKeeper().reset();

    }
}
