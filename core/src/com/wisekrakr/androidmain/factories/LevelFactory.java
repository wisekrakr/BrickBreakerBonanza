package com.wisekrakr.androidmain.factories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.EntityStyle;
import com.wisekrakr.androidmain.helpers.EntityStyleHelper;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.levels.LevelNumber;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

public class LevelFactory {

    private static float width = GameConstants.WORLD_WIDTH;
    private static float height = GameConstants.WORLD_HEIGHT;

    private AndroidGame game;

    public LevelFactory(AndroidGame game) {
        this.game = game;
    }

    public void getLevel(LevelNumber levelNumber, EntityFactory entityFactory){

        switch (levelNumber){
            case ONE:
                for (int i = 0; i < 1; i++) {
                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(1);

                break;
            case TWO:
                for (int i = 0; i < 2; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(2);

                game.getGameThread().getEntityFactory().createObstacle(
                        200, 50, -200, 0, 30, 10, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                game.getGameThread().getEntityFactory().createObstacle(
                        50, height - 50, 200, 0, 30, 10, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                break;
            case THREE:
                for (int i = 0; i < 3; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(3);
                game.getGameThread().getEntityFactory().createObstacle(
                        200, 50, -200, 0, 30, 10, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                game.getGameThread().getEntityFactory().createObstacle(
                        50, height - 50, 200, 0, 30, 10, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                game.getGameThread().getEntityFactory().createObstacle(
                        width/2 - 15, height - 100, 0, 100, 10, 30, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                break;
            case FOUR:
                for (int i = 0; i < 4; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(4);
                break;
            case FIVE:
                for (int i = 0; i < 4; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(4);

                game.getGameThread().getEntityFactory().createObstacle(
                        200, 50, -200, 0, 30, 10, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                game.getGameThread().getEntityFactory().createObstacle(
                        50, height - 50, 200, 0, 30, 10, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                break;
            case SIX:
                for (int i = 0; i < 4; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(4);

                game.getGameThread().getEntityFactory().createObstacle(
                        200, 50, -200, 0, 30, 10, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                game.getGameThread().getEntityFactory().createObstacle(
                        50, height - 50, 200, 0, 30, 10, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                game.getGameThread().getEntityFactory().createObstacle(
                        width/2 - 15, height - 100, 100, 100, 10, 30, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                break;
            case SEVEN:
                for (int i = 0; i < 5; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(5);
                break;
            case EIGHT:
                for (int i = 0; i < 5; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(5);
                break;
            case NINE:
                for (int i = 0; i < 5; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(5);
                break;
            case TEN:
                for (int i = 0; i < 6; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(6);
                break;
            case ELEVEN:
                for (int i = 0; i < 6; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(6);
                break;
            case TWELVE:
                for (int i = 0; i < 6; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(6);
                break;
            case THIRTEEN:
                for (int i = 0; i < 7; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(7);
                break;
            case FOURTEEN:
                for (int i = 0; i < 7; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(7);
                break;
            case FIFTEEN:
                for (int i = 0; i < 7; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(7);
                break;
            case SIXTEEN:
                for (int i = 0; i < 8; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(8);
                break;
            case SEVENTEEN:
                for (int i = 0; i < 8; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(8);
                break;
            case EIGHTEEN:
                for (int i = 0; i < 8; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(8);
                break;
            case NINETEEN:
                for (int i = 0; i < 9; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(9);
                break;
            case TWENTY:
                for (int i = 0; i < 9; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(9);
                break;
            case TWENTY_ONE:
                for (int i = 0; i < 9; i++) {

                    entityFactory.createEnemy(
                            GameHelper.notFilledPosition(game).x,
                            GameHelper.notFilledPosition(game).y,
                            EntityStyleHelper.randomEntityStyle(),
                            EntityStyleHelper.getStyle().getPenisLength(),
                            EntityStyleHelper.getStyle().getPenisGirth()
                    );
                }
                ScoreKeeper.setInitialEnemies(9);
                break;
        }

    }

    public void dispatch(){

    }


}
