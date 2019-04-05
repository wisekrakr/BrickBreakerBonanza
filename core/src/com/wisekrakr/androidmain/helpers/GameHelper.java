package com.wisekrakr.androidmain.helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameHelper {

    /**
     * Utility methods available to scenarios
     */
    public static Random randomGenerator = new Random();

    public static float generateRandomNumberBetween(float min, float max){
        return randomGenerator.nextFloat() * (max - min) + min;
    }

    public static float randomDirection(){
        return randomGenerator.nextFloat() * 200 - 100;
    }

    public static Vector2 randomPosition() {

        return new Vector2(10 + randomGenerator.nextFloat() *  (GameConstants.WORLD_WIDTH - 20), //todo change
                10 + randomGenerator.nextFloat() * (GameConstants.WORLD_HEIGHT - 20)
        );
    }

    public static Vector2 notFilledPosition(BricksGame game){
        List<Vector2>positions = new ArrayList<Vector2>();

        Vector2 newPosition = randomPosition();
        Vector2 bestPosition = new Vector2();

        for (Entity entity: game.getEngine().getEntities()){
            Vector2 filledPosition = entity.getComponent(Box2dBodyComponent.class).body.getPosition();
            positions.add(filledPosition);

            for (int i = positions.size() - 1; i > 0; i--) {
//                System.out.println(entity.getComponent(TypeComponent.class).getType() + " - position: " + positions.get(i));

                if (positions.get(i) != newPosition) {
                    bestPosition = newPosition;
                }
            }
        }

        return bestPosition;
    }

    public static float distanceBetween(Vector2 subject, Vector2 target) {
        float attackDistanceX = target.x - subject.x;
        float attackDistanceY = target.y - subject.y;

        return (float) Math.hypot(attackDistanceX, attackDistanceY);
    }

    public static float angleBetween(Vector2 subject, Vector2 target) {
        float attackDistanceX = target.x - subject.x;
        float attackDistanceY = target.y - subject.y;

        return (float) Math.atan2(attackDistanceY, attackDistanceX);
    }

    public static float betweenZeroAndOne(){
        return randomGenerator.nextInt(1);
    }


}
