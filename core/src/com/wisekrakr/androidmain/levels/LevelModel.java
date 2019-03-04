package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.factories.EntityFactory;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.factories.LevelFactory;
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
        entityFactory.createPlayer(GameConstants.WORLD_WIDTH /2, GameConstants.BALL_RADIUS/2,
                GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT
        );

        entityFactory.createWalls(0,0, 1f, GameConstants.WORLD_HEIGHT *2);
        entityFactory.createWalls(GameConstants.WORLD_WIDTH,0, 1f, GameConstants.WORLD_HEIGHT *2);
        entityFactory.createWalls(GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT, GameConstants.WORLD_WIDTH *2,1f);

        entityFactory.createBall(entityFactory.getPlayer().getComponent(Box2dBodyComponent.class).body.getPosition().x,
                entityFactory.getPlayer().getComponent(Box2dBodyComponent.class).body.getPosition().y + GameConstants.BALL_RADIUS
        );
    }



    @Override
    public void startLevel(int numberOfLevel, int columns, int rows) {
        constantEntities();
        LevelFactory.getLevel(LevelNumber.valueOf(numberOfLevel), entityFactory, columns, rows);
        powerImplementation = new PowerImplementation(game, (int) GameHelper.generateRandomNumberBetween(2,5));
    }

    @Override
    public void updateLevel(int numberOfLevel, float delta) {
        game.getGameThread().getTimeKeeper().time += delta;

        powerImplementation.getPowerContext().init();

        if (entityFactory.getPlayer().getComponent(PlayerComponent.class).lives == 0){
            gameOver(numberOfLevel);
        }else {
            for (Entity entity: game.getGameThread().getEntityFactory().getTotalPowers()){
                powerImplementation.updatingPowerUpSystem(entity);
            }

            if (entityFactory.getTotalBricks().size() == 0) {
                completeLevel(numberOfLevel);
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

        entityFactory.getTotalBricks().clear();
        entityFactory.getTotalObstacles().clear();
        entityFactory.getTotalBalls().clear();
        entityFactory.getTotalPowers().clear();
    }
}
