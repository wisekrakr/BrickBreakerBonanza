package com.wisekrakr.androidmain.factories;

import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.helpers.EntityColorHelper;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.levels.LevelNumber;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

public class LevelFactory {

    private float width = GameConstants.WORLD_WIDTH;
    private float height = GameConstants.WORLD_HEIGHT;

    private AndroidGame game;

    public LevelFactory(AndroidGame game) {
        this.game = game;
    }

    public void getLevel(LevelNumber levelNumber, EntityFactory entityFactory){
//todo better positioning for multiple balls

        if (levelNumber != null){
            for (int i = 0; i < levelNumber.getValue(); i++) {

                entityFactory.createBall(
                        GameHelper.notFilledPosition(game).x,
                        GameHelper.notFilledPosition(game).y,
                        EntityColorHelper.randomEntityColor());
            }
            ScoreKeeper.setInitialBalls(levelNumber.getValue());
        }
    }

    public void dispatch(){

    }


}
