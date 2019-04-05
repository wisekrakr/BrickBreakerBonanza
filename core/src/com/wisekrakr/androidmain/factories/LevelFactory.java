package com.wisekrakr.androidmain.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.EntityColor;
import com.wisekrakr.androidmain.components.RowComponent;
import com.wisekrakr.androidmain.helpers.EntityColorHelper;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.levels.LevelNumber;
import com.wisekrakr.androidmain.retainers.EntityKeeper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

import java.util.ArrayList;
import java.util.List;

public class LevelFactory {

    private static float width = GameConstants.WORLD_WIDTH;
    private static float height = GameConstants.WORLD_HEIGHT;
    private static float brickWidth = GameConstants.BRICK_WIDTH;
    private static float brickHeight = GameConstants.BRICK_HEIGHT;
    private static List<RowComponent> rowList = new ArrayList<RowComponent>();
    private static List<EntityColor>colorList = new ArrayList<EntityColor>();

    //TODO: create blocks with different attributes....blocks that need four hits to break

    public static void getLevel(LevelNumber levelNumber, EntityFactory entityFactory, int columns, int rows){

        switch (levelNumber){
            case ONE:
                System.out.println("Making level 1 " );

                colorList.add(EntityColor.RED);
                colorList.add(EntityColor.WHITE);
                colorList.add(EntityColor.BLUE);

                for (int i = 0; i < rows; i++){
                    RowComponent rowComponent = new RowComponent(new Vector2(
                            brickWidth,
                            (height - brickHeight) - (brickHeight * i)),
                            columns,
                            chosenColors());
                    rowList.add(rowComponent);
                }

                for (RowComponent row : rowList) {

                    for (int i = 0; i < columns; i++) {
                        entityFactory.createBrick(
                                row.getPosition().x + (i * brickWidth),
                                row.getPosition().y,
                                row.getRowColor()
                        );
                    }
                }

                break;
            case TWO:
                System.out.println("Making level 2");
                colorList.add(EntityColor.RED);
                colorList.add(EntityColor.GOLD);

                for (int i = 0; i < rows; i++){
                    RowComponent rowComponent = new RowComponent(new Vector2(
                            brickWidth/2,
                            height - (brickHeight * i)),
                            columns,
                            chosenColors());
                    rowList.add(rowComponent);
                }
                for (RowComponent row : rowList) {

                    for (int i = 0; i < columns; i++) {
                        entityFactory.createBrick(
                                row.getPosition().x + (i * brickWidth),
                                row.getPosition().y,
                                row.getRowColor()
                        );
                    }
                }
                break;
            case THREE:
                System.out.println("Making level 3");

                for (int i = 0; i < rows; i++){
                    RowComponent rowComponent = new RowComponent(new Vector2(
                            brickWidth/2,
                            height - (brickHeight * i)),
                            columns,
                            EntityColorHelper.randomEntityColor());
                    rowList.add(rowComponent);
                }
                for (RowComponent row : rowList) {

                    for (int i = 0; i < columns; i++) {
                        entityFactory.createBrick(
                                row.getPosition().x + (i * brickWidth),
                                row.getPosition().y,
                                row.getRowColor()
                        );
                    }
                }
                entityFactory.createObstacle(width/2,height/2,
                        150f,0,
                        40f, 3f,
                        BodyFactory.Material.STEEL,
                        BodyDef.BodyType.KinematicBody
                );

                break;
            case FOUR:
                System.out.println("Making level 4");
                for (int i = 0; i < rows; i++){
                    RowComponent rowComponent = new RowComponent(new Vector2(
                            brickWidth/2,
                            height - (brickHeight * i)),
                            columns,
                            EntityColorHelper.randomEntityColor());
                    rowList.add(rowComponent);
                }
                for (RowComponent row : rowList) {

                    for (int i = 0; i < columns; i++) {
                        entityFactory.createBrick(
                                row.getPosition().x + (i * brickWidth),
                                row.getPosition().y,
                                row.getRowColor()
                        );
                    }
                }
                break;
            case FIVE:
                System.out.println("Making level 5");
                for (int i = 0; i < rows; i++){
                    RowComponent rowComponent = new RowComponent(new Vector2(
                            brickWidth/2,
                            height - (brickHeight * i)),
                            columns,
                            EntityColorHelper.randomEntityColor());
                    rowList.add(rowComponent);
                }
                for (RowComponent row : rowList) {

                    for (int i = 0; i < columns; i++) {
                        entityFactory.createBrick(
                                row.getPosition().x + (i * brickWidth),
                                row.getPosition().y,
                                row.getRowColor()
                        );
                    }
                }
                break;
            case SIX:
                System.out.println("Making level 6");
                for (int i = 0; i < rows; i++){
                    RowComponent rowComponent = new RowComponent(new Vector2(
                            brickWidth/2,
                            height - (brickHeight * i)),
                            columns,
                            EntityColorHelper.randomEntityColor());
                    rowList.add(rowComponent);
                }
                for (RowComponent row : rowList) {

                    for (int i = 0; i < columns; i++) {
                        entityFactory.createBrick(
                                row.getPosition().x + (i * brickWidth),
                                row.getPosition().y,
                                row.getRowColor()
                        );
                    }
                }

                entityFactory.createObstacle(500,10,
                        0,150f,
                        40f, 3f,
                        BodyFactory.Material.STEEL,
                        BodyDef.BodyType.KinematicBody
                );
                entityFactory.createObstacle(width - 50,10,
                        0,150f,
                        40f, 3f,
                        BodyFactory.Material.STEEL,
                        BodyDef.BodyType.KinematicBody
                );
                break;
            case SEVEN:
                System.out.println("Making level 7");
                for (int k = 0; k < columns -1; k++) {
                    for (int j = 1; j < rows; j++) {
                        entityFactory.createBrick(
                                (width/columns) + k * brickWidth,
                                height - j *  brickHeight,
                                EntityColorHelper.randomEntityColor());
                    }
                }
                break;
            case EIGHT:
                System.out.println("Making level 8");
                for (int k = 0; k < columns - 1; k++) {
                    for (int j = 1; j < rows; j++) {
                        entityFactory.createBrick(
                                (width/columns)  + k * brickWidth,
                                height - j *  brickHeight,
                                EntityColorHelper.randomEntityColor());
                    }
                }
                entityFactory.createObstacle(width/2,height/2,
                        100f,0,
                        40f, 3f,
                        BodyFactory.Material.STEEL,
                        BodyDef.BodyType.KinematicBody);

                break;
            case NINE:
                System.out.println("Making level 9");
                for (int k = 0; k < columns - 1; k++) {
                    for (int j = 1; j < rows; j++) {
                        entityFactory.createBrick(
                                (width/columns) + k * brickWidth,
                                height - j *  brickHeight,
                                EntityColorHelper.randomEntityColor());
                    }
                }
                break;
            case TEN:
                break;
            case ELEVEN:
                break;
            case TWELVE:
                break;
            case THIRTEEN:
                break;
            case FOURTEEN:
                break;
            case FIFTEEN:
                break;
            case SIXTEEN:
                break;
            case SEVENTEEN:
                break;
            case EIGHTEEN:
                break;
            case NINETEEN:
                break;
            case TWENTY:
                break;
            case TWENTY_ONE:
                break;
            case TWENTY_TWO:
                break;
            case TWENTY_THREE:
                break;
            case TWENTY_FOUR:
                break;
            case TWENTY_FIVE:
                break;
        }

        EntityKeeper.setInitialBricks(columns * rows);

    }

    public static void dispose(){
        rowList.clear();
        colorList.clear();
    }

    private static EntityColor chosenColors(){
        return colorList.get(GameHelper.randomGenerator.nextInt(colorList.size()));
    }


}
