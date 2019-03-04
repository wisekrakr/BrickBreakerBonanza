package com.wisekrakr.androidmain.helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.BrickComponent;

import java.util.Iterator;
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

        return new Vector2(randomGenerator.nextFloat() * GameConstants.WORLD_WIDTH, //todo change
                randomGenerator.nextFloat() * GameConstants.WORLD_HEIGHT
        );
    }

    public static Vector2 notFilledPosition(AndroidGame game){
        Vector2 filledPosition = new Vector2();

        Iterator<Entity> iterator = game.getEngine().getEntities().iterator();
        if (iterator.hasNext()){
            Entity ent = iterator.next();
            filledPosition = ent.getComponent(Box2dBodyComponent.class).body.getPosition();
        }

        Vector2 newPosition = GameHelper.randomPosition();
        Vector2 bestPosition = new Vector2();
        if (newPosition != filledPosition){
            bestPosition.x = newPosition.x;
            bestPosition.y = newPosition.y;
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
