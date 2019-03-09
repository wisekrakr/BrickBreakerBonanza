package com.wisekrakr.androidmain.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.EntityColor;
import com.wisekrakr.androidmain.components.RowComponent;
import com.wisekrakr.androidmain.helpers.EntityColorHelper;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.levels.LevelNumber;

import java.util.ArrayList;
import java.util.List;

public class LevelFactory {

    private static float width = GameConstants.WORLD_WIDTH;
    private static float height = GameConstants.WORLD_HEIGHT;
    private static float brickWidth = GameConstants.BRICK_WIDTH;
    private static float brickHeight = GameConstants.BRICK_HEIGHT;
    private static RowComponent rowComponent;
    private static List<RowComponent>rowList = new ArrayList<RowComponent>();
    private static List<EntityColor>colorList = new ArrayList<EntityColor>();

    //TODO: create blocks with different attributes....blocks that need four hits to break

    public static void getLevel(LevelNumber levelNumber, EntityFactory entityFactory, int columns, int rows){

        switch (levelNumber){
            case ONE:
                colorList.add(EntityColor.RED);
                colorList.add(EntityColor.WHITE);
                colorList.add(EntityColor.BLUE);

                System.out.println("Making level 1");
                for (int i = 0; i < rows; i++){
                    rowComponent = new RowComponent(new Vector2(
                            brickWidth/2,
                            height - (brickHeight * i)),
                            columns,
                            chosenColors()
                    );
                    rowList.add(rowComponent);
                }
                for (int i = rowComponent.getNumberOfBricks()-1; i > 0; i--) {
                    for (int j = rows -1; j > 0;j--) {
                        entityFactory.createBrick(
                                rowList.get(j).getPosition().x + (i * brickWidth),
                                rowList.get(j).getPosition().y ,
                                rowList.get(j).getRowColor()
                        );
                    }
                }
                break;
            case TWO:
                System.out.println("Making level 2");
                colorList.add(EntityColor.RED);
                colorList.add(EntityColor.GOLD);

                for (int i = 0; i < rows; i++){
                    rowComponent = new RowComponent(new Vector2(
                            brickWidth/2,
                            height - (brickHeight * i)),
                            columns,
                            chosenColors()
                    );
                    rowList.add(rowComponent);
                }
                for (int i = rowComponent.getNumberOfBricks()-1; i > 0; i--) {
                    for (int j = rows -1; j > 0;j--) {
                        entityFactory.createBrick(
                                rowList.get(j).getPosition().x + (i * brickWidth),
                                rowList.get(j).getPosition().y ,
                                rowList.get(j).getRowColor()
                        );
                    }
                }
                break;
            case THREE:
                System.out.println("Making level 3");

                for (int i = 0; i < rows; i++){
                    rowComponent = new RowComponent(new Vector2(
                            brickWidth/2,
                            height - (brickHeight * i)),
                            columns,
                            EntityColorHelper.randomEntityColor()
                    );
                    rowList.add(rowComponent);
                }
                for (int i = rowComponent.getNumberOfBricks()-1; i > 0; i--) {
                    for (int j = rows -1; j > 0;j--) {
                        entityFactory.createBrick(
                                rowList.get(j).getPosition().x + (i * brickWidth),
                                rowList.get(j).getPosition().y ,
                                rowList.get(j).getRowColor()
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
                    rowComponent = new RowComponent(new Vector2(
                            brickWidth/2,
                            height - (brickHeight * i)),
                            columns,
                            EntityColorHelper.randomEntityColor()
                    );
                    rowList.add(rowComponent);
                }
                for (int i = rowComponent.getNumberOfBricks()-1; i > 0; i--) {
                    for (int j = rows -1; j > 0;j--) {
                        entityFactory.createBrick(
                                rowList.get(j).getPosition().x + (i * brickWidth),
                                rowList.get(j).getPosition().y ,
                                rowList.get(j).getRowColor()
                        );
                    }
                }
                break;
            case FIVE:
                System.out.println("Making level 5");
                for (int i = 0; i < rows; i++){
                    rowComponent = new RowComponent(new Vector2(
                            brickWidth/2,
                            height - (brickHeight * i)),
                            columns,
                            EntityColorHelper.randomEntityColor()
                    );
                    rowList.add(rowComponent);
                }
                for (int i = rowComponent.getNumberOfBricks()-1; i > 0; i--) {
                    for (int j = rows -1; j > 0;j--) {
                        entityFactory.createBrick(
                                rowList.get(j).getPosition().x + (i * brickWidth),
                                rowList.get(j).getPosition().y ,
                                rowList.get(j).getRowColor()
                        );
                    }
                }
                break;
            case SIX:
                System.out.println("Making level 6");
                for (int i = 0; i < rows; i++){
                    rowComponent = new RowComponent(new Vector2(
                            brickWidth/2,
                            height - (brickHeight * i)),
                            columns,
                            EntityColorHelper.randomEntityColor()
                    );
                    rowList.add(rowComponent);
                }
                for (int i = rowComponent.getNumberOfBricks()-1; i > 0; i--) {
                    for (int j = rows -1; j > 0;j--) {
                        entityFactory.createBrick(
                                rowList.get(j).getPosition().x + (i * brickWidth),
                                rowList.get(j).getPosition().y ,
                                rowList.get(j).getRowColor()
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
        System.out.println("ROWLIST= " + (getRowList().size() -1));
    }

    public static void dispatch(){
        rowList.clear();
        colorList.clear();
    }

    private static EntityColor chosenColors(){
        return colorList.get(GameHelper.randomGenerator.nextInt(colorList.size()));
    }

    private static List<RowComponent> getRowList() {
        return rowList;
    }
}
