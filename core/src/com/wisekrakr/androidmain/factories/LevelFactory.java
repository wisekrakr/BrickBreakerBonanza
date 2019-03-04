package com.wisekrakr.androidmain.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.BrickComponent;
import com.wisekrakr.androidmain.levels.LevelNumber;

public class LevelFactory {

    private static float width = GameConstants.WORLD_WIDTH;
    private static float height = GameConstants.WORLD_HEIGHT;

    static void createBricks(EntityFactory entityFactory) {
        float brickWidth = GameConstants.BALL_RADIUS;
        float brickHeight = GameConstants.BALL_RADIUS/2;

        // number of rows and columns of bricks that can be made
        float numCols = (int) (width / brickWidth);
        float numRows = (int) (height / brickHeight);

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                int col = (int) (i * brickWidth);
                int row = (int) (height - brickHeight - (j * brickHeight));
                Gdx.app.log("Creating", String.format("at row %d and col %d", row, col));

                entityFactory.createBrick(
                        col * GameConstants.BALL_RADIUS,
                        row *  GameConstants.BALL_RADIUS,
                        BrickComponent.randomBrickColor()
                );

            }
        }
    }
    //TODO: fix the way we set levels...
    //TODO: create blocks with different attributes....blocks that need four hits to break

    public static void getLevel(LevelNumber levelNumber, EntityFactory entityFactory, int columns, int rows){

        float brickWidth = GameConstants.BRICK_WIDTH;
        float brickHeight = GameConstants.BRICK_HEIGHT;

        switch (levelNumber){
            case ONE:

                System.out.println("Making level 1, with a total of bricks: " + ((rows - 1) * (columns - 1)) + " _rows: " + rows + " _columns: " + columns);

                for (int k = 0; k < columns -1; k++) {
                    for (int j = 1; j < rows; j++) {
                        entityFactory.createBrick(
                                (width/columns) + k * brickWidth,
                                height - j *  brickHeight,
                                 BrickComponent.randomBrickColor());
                    }
                }

                entityFactory.createObstacle(width/2,height/2,
                        100f,0,
                        40f, 3f,
                        BodyFactory.Material.STEEL,
                        BodyDef.BodyType.KinematicBody);
                break;
            case TWO:
                System.out.println("Making level 2, with a total of entities: " + ((rows - 1) * (columns - 1)));

                for (int k = 0; k < columns - 1; k++) {
                    for (int j = 1; j < rows; j++) {
                        entityFactory.createBrick(
                                (width/columns)  + k * brickWidth,
                                height - j *  brickHeight,
                                BrickComponent.randomBrickColor());
                    }
                }
                break;
            case THREE:
                System.out.println("Making level 3, with a total of entities: " + ((rows - 1) * (columns - 1)));
                for (int k = 0; k < columns - 1; k++) {
                    for (int j = 1; j < rows; j++) {
                        entityFactory.createBrick(
                                (width/columns)  + k * brickWidth,
                                height - j *  brickHeight,
                                BrickComponent.randomBrickColor());
                    }
                }
                break;

        }
    }

}
