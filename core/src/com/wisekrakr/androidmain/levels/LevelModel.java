package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.factories.EntityFactory;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;


import com.wisekrakr.androidmain.factories.LevelFactory;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.EntityKeeper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;


public class LevelModel extends AbstractLevelContext{

    private BricksGame game;
    private EntityFactory entityFactory;
    private Entity player;
    private float powerInitTime;

    public LevelModel(BricksGame game, EntityFactory entityFactory) {
        this.game = game;
        this.entityFactory = entityFactory;
        constantEntities();
    }

    private void constantEntities(){

        entityFactory.createWalls(0,0, 1f, GameConstants.WORLD_HEIGHT *2);
        entityFactory.createWalls(GameConstants.WORLD_WIDTH,0, 1f, GameConstants.WORLD_HEIGHT *2);
        entityFactory.createWalls(GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT, GameConstants.WORLD_WIDTH *2,1f);

    }

    @Override
    public void startLevel(int numberOfLevel, int columns, int rows) {
        player = entityFactory.createPlayer(
                GameConstants.WORLD_WIDTH/2, GameConstants.PLAYER_HEIGHT,
                GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT
        );
        EntityKeeper.setPlayersInPlay(1);

        LevelFactory.getLevel(LevelNumber.valueOf(numberOfLevel), entityFactory, columns, rows);
    }

    @Override
    public void updateLevel(int numberOfLevel, float delta) {
        game.getGameThread().getTimeKeeper().time += delta;


        if (ScoreKeeper.lives == 0){
            gameOver(numberOfLevel);
        }else {
            powerUpInitializer();
            if (EntityKeeper.getBallsInPlay() == 0){
                spawnBall();
            }else if (EntityKeeper.getPlayersInPlay() == 0){
                spawnPlayer();
            }else if (EntityKeeper.getInitialBricks() == 0) {
                completeLevel(numberOfLevel);
            }
        }
    }

    private void powerUpInitializer(){
        if (powerInitTime == 0){
            powerInitTime = game.getGameThread().getTimeKeeper().gameClock;
        }

        if (game.getGameThread().getTimeKeeper().gameClock - powerInitTime > game.getGameThread().getTimeKeeper().powerTime) {
            if (EntityKeeper.getInitialPowerUps() == 0) {
                entityFactory.createPower(
                        GameHelper.notFilledPosition(game).x,
                        GameHelper.notFilledPosition(game).y,
                        GameHelper.generateRandomNumberBetween(-20f, 20f),
                        GameHelper.generateRandomNumberBetween(-20f, 20f),
                        PowerHelper.randomPowerUp()
                );
                EntityKeeper.setInitialPowerUps(EntityKeeper.getInitialPowerUps() + 1);
            }
            powerInitTime = game.getGameThread().getTimeKeeper().gameClock;
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

        game.changeScreen(BricksGame.ENDGAME);
    }


    private void cleanUp(){
        game.getGameThread().getTimeKeeper().setTotalGameTime(
                game.getGameThread().getTimeKeeper().getTotalGameTime() + game.getGameThread().getTimeKeeper().time
        );

        for (Entity entity: game.getEngine().getEntities()){
            entity.getComponent(Box2dBodyComponent.class).isDead = true;
        }
        LevelFactory.dispose();
        EntityKeeper.setInitialBricks(0);

        game.getGameThread().getTimeKeeper().reset();

    }

    private void spawnBall(){
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(player);

        if (player != null) {
            game.getGameThread().getEntityFactory().createBall(
                    playerComponent.getPosition().x,
                    playerComponent.getPosition().y + playerComponent.getHeight(),
                    EntityKeeper.getBallRadius(),
                    playerComponent.getShootDirection()
            );
            player.getComponent(PlayerComponent.class).setHasBall(false);
            EntityKeeper.setBallsInPlay(EntityKeeper.getBallsInPlay() + 1);
        }
    }

    private void spawnPlayer(){
        System.out.println("spawned new player in LevelModel");
        player = entityFactory.createPlayer(
                GameConstants.WORLD_WIDTH/2, GameConstants.PLAYER_HEIGHT,
                EntityKeeper.getPlayerWidth(), GameConstants.PLAYER_HEIGHT
        );
        EntityKeeper.setPlayersInPlay(EntityKeeper.getPlayersInPlay() + 1);
    }
}
