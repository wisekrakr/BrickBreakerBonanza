package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.List;

public class InfoIndicator {

    private static List<Entity> shapes = new ArrayList<Entity>();

    public static void timeChange(Entity entity){


    }

    public static boolean outOfBounds(AndroidGame game){
        shapes = game.getGameThread().getEntityFactory().getTotalBricks();
//
//        for (Entity entity: shapes){
//            if(entity.getComponent(BrickComponent.class).outOfBounds){
//                return true;
//            }
//        }
        return false;
    }

    public void updateInfo(float delta){

    }
}
