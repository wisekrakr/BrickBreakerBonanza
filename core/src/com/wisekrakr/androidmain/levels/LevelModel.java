package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.factories.EntityFactory;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;


import com.wisekrakr.androidmain.factories.LevelFactory;

import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;


public class LevelModel extends AbstractLevelContext{

    private AndroidGame game;
    private EntityFactory entityFactory;
    private LevelFactory levelFactory;
    private float powerInitTime;

    public LevelModel(AndroidGame game, EntityFactory entityFactory) {
        this.game = game;
        this.entityFactory = entityFactory;

        levelFactory = new LevelFactory(game);

    }

    private void constantEntities(){
        entityFactory.createPlayer(GameConstants.WORLD_WIDTH /2, GameConstants.WORLD_HEIGHT /2, GameConstants.PLAYER_RADIUS,
                GameConstants.PENIS_WIDTH, GameConstants.PENIS_HEIGHT);

        entityFactory.createWalls(0,0, 1f, GameConstants.WORLD_HEIGHT *2);
        entityFactory.createWalls(GameConstants.WORLD_WIDTH,0, 1f, GameConstants.WORLD_HEIGHT *2);
        entityFactory.createWalls(GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT, GameConstants.WORLD_WIDTH *2,1f);
        entityFactory.createWalls(0,0, GameConstants.WORLD_WIDTH * 2, 1f);
    }

    @Override
    public void startLevel(int numberOfLevel) {
        constantEntities();
        levelFactory.getLevel(LevelNumber.valueOf(numberOfLevel), entityFactory);
    }

    @Override
    public void updateLevel(int numberOfLevel, float delta) {
        for (Entity player: game.getEngine().getEntities()) {
            if (player.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER) {
                if (player.getComponent(PlayerComponent.class).isMoving) {
                    game.getGameThread().getTimeKeeper().time -= delta;
                    powerUpInitializer();
                } else {
                    game.getGameThread().getTimeKeeper().setTime(game.getGameThread().getTimeKeeper().time);
                }
            }
        }

        if (ScoreKeeper.lives == 0){
            gameOver(numberOfLevel);
        }else {
//            scoring();
            if (game.getGameThread().getTimeKeeper().time <=0){
                ScoreKeeper.setPointsToGive(100);
                completeLevel(numberOfLevel);
            }


        }
    }

    private void scoring(){


        for (Entity entity: game.getEngine().getEntities()){
            if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.ENEMY){
                if (entity.getComponent(CollisionComponent.class).hitPenis){
                    ScoreKeeper.setPointsToGive(25);
                }

            }else if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER){
                if (entity.getComponent(CollisionComponent.class).hitPenis){
                    ScoreKeeper.setPointsToGive(-10);
                }

            }
        }
        ScoreKeeper.setScore(ScoreKeeper.getPointsToGive() * ScoreKeeper.getMultiplier());
    }

    private void powerUpInitializer(){
        //todo: want power ups?

                    if (powerInitTime == 0){
                        powerInitTime = game.getGameThread().getTimeKeeper().gameClock;
                    }

                    if (game.getGameThread().getTimeKeeper().gameClock - powerInitTime > game.getGameThread().getTimeKeeper().powerTime) {
                        if (ScoreKeeper.getInitialPowerUps() == 0) {
                            entityFactory.createPower(
                                    GameHelper.notFilledPosition(game).x,
                                    GameHelper.notFilledPosition(game).y,
                                    GameHelper.generateRandomNumberBetween(100000f, 1000000f),
                                    GameHelper.generateRandomNumberBetween(100000f, 1000000f),
                                    PowerHelper.randomPowerUp()
                            );
                            ScoreKeeper.setInitialPowerUps(1);
                        }
                        powerInitTime = game.getGameThread().getTimeKeeper().gameClock;
                    }
    }

    @Override
    public void completeLevel(int numberOfLevel) {
        game.getGamePreferences().setLevelCompleted(numberOfLevel, true);
        ScoreKeeper.setPointsToGive(100);
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

        game.getGameThread().getTimeKeeper().reset();

    }
}
