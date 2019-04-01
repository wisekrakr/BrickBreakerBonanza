package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.factories.EntityFactory;
import com.wisekrakr.androidmain.GameConstants;


import com.wisekrakr.androidmain.factories.LevelFactory;

import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;
import com.wisekrakr.androidmain.retainers.SelectedCharacter;


public class LevelModel extends AbstractLevelContext{

    private AndroidGame game;
    private EntityFactory entityFactory;
    private LevelFactory levelFactory;
    private float powerInitTime;

    public LevelModel(AndroidGame game, EntityFactory entityFactory) {
        this.game = game;
        this.entityFactory = entityFactory;
        constantEntities();
        levelFactory = new LevelFactory(game);
    }

    private void constantEntities(){

        entityFactory.createWalls(0,0, 1f, GameConstants.WORLD_HEIGHT *2);
        entityFactory.createWalls(GameConstants.WORLD_WIDTH,0, 1f, GameConstants.WORLD_HEIGHT *2);
        entityFactory.createWalls(GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT, GameConstants.WORLD_WIDTH *2,1f);
        entityFactory.createWalls(0,0, GameConstants.WORLD_WIDTH * 2, 1f);
    }

    @Override
    public void startLevel(int numberOfLevel) {
        spawnNewPlayer();
        levelFactory.getLevel(LevelNumber.valueOf(numberOfLevel), entityFactory);
    }

    @Override
    public void updateLevel(int numberOfLevel, float delta) {

        if (SelectedCharacter.isDestroyed() && ScoreKeeper.lives > 0){

            spawnNewPlayer();

        }else if (!SelectedCharacter.isDestroyed()){
            for (int i = 0; i < game.getEngine().getEntities().size(); i++){
                if (game.getEngine().getEntities().get(i).getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER) {
                    if (game.getEngine().getEntities().get(i).getComponent(PlayerComponent.class).isMoving) {

                        game.getGameThread().getTimeKeeper().time -= delta;
                        powerUpInitializer();

                        if (game.getGameThread().getTimeKeeper().time <=0){
                            completeLevel(numberOfLevel);
                        }
                    } else {
                        game.getGameThread().getTimeKeeper().setTime(game.getGameThread().getTimeKeeper().time);
                    }
                }
            }
        }else {
            gameOver(numberOfLevel);
        }
        scoring();
    }

    private void spawnNewPlayer(){
        SelectedCharacter.setDestroyed(false);
        entityFactory.createPlayer(
                GameConstants.WORLD_WIDTH /2, GameConstants.WORLD_HEIGHT /2,
                GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT,
                SelectedCharacter.getPenisLength(), SelectedCharacter.getPenisGirth(),
                SelectedCharacter.getSelectedCharacterStyle()
        );
    }

    private void scoring(){

        for (Entity entity: game.getEngine().getEntities()){
            if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.ENEMY){
                if (entity.getComponent(CollisionComponent.class).hitPenis && entity.getComponent(EnemyComponent.class).isDestroy()){
                    ScoreKeeper.setPointsToGive(25);
                    ScoreKeeper.setScore(ScoreKeeper.getPointsToGive());
                }
            }
            if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER){
                if (entity.getComponent(CollisionComponent.class).hitPenis && entity.getComponent(PlayerComponent.class).isDestroy()){
                    ScoreKeeper.setPointsToGive(-50);
                    ScoreKeeper.setScore(ScoreKeeper.getPointsToGive());
                }
            }
        }
    }

    private void powerUpInitializer(){
        if (powerInitTime == 0){
            powerInitTime = game.getGameThread().getTimeKeeper().gameClock;
        }

        if (game.getGameThread().getTimeKeeper().gameClock - powerInitTime > game.getGameThread().getTimeKeeper().powerTime) {
            if (ScoreKeeper.getInitialPowerUps() == 0) {
                entityFactory.createPower(
                        GameHelper.notFilledPosition(game).x,
                        GameHelper.notFilledPosition(game).y,
                        GameHelper.generateRandomNumberBetween(-20f, 20f),
                        GameHelper.generateRandomNumberBetween(-20f, 20f),
                        PowerHelper.randomPowerUp()
                );
                ScoreKeeper.setInitialPowerUps(1);
            }
            powerInitTime = game.getGameThread().getTimeKeeper().gameClock;
        }
    }

    @Override
    public void completeLevel(int numberOfLevel) {
        if (ScoreKeeper.getScore() > game.getGamePreferences().getHighScore()){
            game.getGamePreferences().setHighScore(ScoreKeeper.getScore());
        }

        game.getGamePreferences().setLevelCompleted(numberOfLevel, true);
        cleanUp();
    }

    @Override
    public void gameOver(int numberOfLevel) {
        ScoreKeeper.reset();

        cleanUp();

        game.changeScreen(AndroidGame.ENDGAME);
    }

    private void cleanUp(){
        for (Entity entity: game.getEngine().getEntities()){
            entity.getComponent(Box2dBodyComponent.class).isDead = true;
        }
        game.getGameThread().getTimeKeeper().reset();
    }
}
