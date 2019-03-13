package com.wisekrakr.androidmain.systems;

import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.factories.EntityFactory;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.levels.LevelModel;
import com.wisekrakr.androidmain.levels.LevelNumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class LevelGenerationSystem {
    private AndroidGame game;
    private EntityFactory entityFactory;
    private LevelModel levelModel;

    public enum State{
        START, BUILDING, UPDATE, END
    }
    private State state = State.START;

    private List<LevelNumber> levelsToDo = new ArrayList<LevelNumber>();
    private List<LevelNumber> levelCompleted = new ArrayList<LevelNumber>();
    private int mainLevel = 1;

    public LevelGenerationSystem(AndroidGame game, EntityFactory entityFactory){
        this.game = game;
        this.entityFactory = entityFactory;

        levelModel = new LevelModel(game, entityFactory);

        levelsToDo.addAll(Arrays.asList(LevelNumber.values()));
    }

    public void init(){

        Iterator<LevelNumber>iterator = levelsToDo.iterator();

        if (iterator.hasNext()){
            mainLevel = iterator.next().getValue();
            if (mainLevel != levelCompleted.size()) {
                state = State.START;
            }else {
                mainLevel += 1;
            }
        }
    }


    public void updateLevels(float deltaTime) {

        switch (state){
            case START:
                start();
                System.out.println("start: " + mainLevel);
                break;
            case BUILDING:
                building();
                System.out.println("build: " + mainLevel);
                break;
            case UPDATE:
                update(deltaTime);
                break;
            case END:
                completedLevel();
                System.out.println("complete: " + mainLevel);
                break;
        }
    }

    private void start(){
        game.getGamePreferences().setLevelGoing(mainLevel, false);

        state = State.BUILDING;

    }

    private void building(){

        if (!game.getGamePreferences().levelGoing(mainLevel)) {
            if (mainLevel > 0 && mainLevel <= 3) {
                levelModel.startLevel(mainLevel);
                game.getGameThread().getTimeKeeper().setTimeToChase(2f);
            }else if (mainLevel > 3 && mainLevel <= 6) {
                levelModel.startLevel(mainLevel);
                game.getGameThread().getTimeKeeper().setTimeToChase(GameHelper.generateRandomNumberBetween(1.5f,1.75f));
            }else if (mainLevel > 6 && mainLevel <= 9) {
                levelModel.startLevel(mainLevel);
                game.getGameThread().getTimeKeeper().setTimeToChase(GameHelper.generateRandomNumberBetween(1.25f,1.5f));
            }else if (mainLevel > 9 && mainLevel <= 12) {
                levelModel.startLevel(mainLevel);
                game.getGameThread().getTimeKeeper().setTimeToChase(GameHelper.generateRandomNumberBetween(1f,1.25f));
            }else if (mainLevel > 12 && mainLevel <= 15) {
                levelModel.startLevel(mainLevel);
                game.getGameThread().getTimeKeeper().setTimeToChase(GameHelper.generateRandomNumberBetween(0.75f,2f));
            }else if (mainLevel > 15 && mainLevel <= 18) {
                levelModel.startLevel(mainLevel);
                game.getGameThread().getTimeKeeper().setTimeToChase(GameHelper.generateRandomNumberBetween(0.5f,0.75f));
            }else if (mainLevel > 18 && mainLevel <= 21) {
                levelModel.startLevel(mainLevel);
                game.getGameThread().getTimeKeeper().setTimeToChase(GameHelper.generateRandomNumberBetween(0.25f,0.5f));
            }else if (mainLevel > 21 && mainLevel <= 24) {
                levelModel.startLevel(mainLevel);
                game.getGameThread().getTimeKeeper().setTimeToChase(GameHelper.generateRandomNumberBetween(0.1f,0.25f));
            }else if (mainLevel == 25) {
                levelModel.startLevel(mainLevel);
                game.getGameThread().getTimeKeeper().setTimeToChase(GameHelper.generateRandomNumberBetween(0.05f,0.1f));
            }

            game.getGamePreferences().setLevelGoing(mainLevel, true);

            game.getGamePreferences().setLevelCompleted(mainLevel, false);

            state = State.UPDATE;
        }
    }

    private void update(float deltaTime){

        if (mainLevel != 0) {
            if (game.getGamePreferences().levelGoing(mainLevel) && !game.getGamePreferences().levelDone(mainLevel)) {
                levelModel.updateLevel(mainLevel, deltaTime);
                if (game.getGamePreferences().levelDone(mainLevel)){
                    state = State.END;
                }else {
                    resetLevels();
                }
            }
        }else {
            System.out.println("No level number given ");
        }
    }

    private void completedLevel(){
        levelCompleted.add(LevelNumber.valueOf(mainLevel));

        for (LevelNumber levelNumber: levelCompleted) {
            levelsToDo.remove(levelNumber);
        }
        game.changeScreen(AndroidGame.LEVELSELECTION);

    }

    private void resetLevels(){
        levelsToDo.addAll(Arrays.asList(LevelNumber.values()));
    }

    public int getMainLevel() {
        return mainLevel;
    }

    public LevelModel getLevelModel() {
        return levelModel;
    }
}
