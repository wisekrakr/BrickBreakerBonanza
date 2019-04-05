package com.wisekrakr.androidmain.helpers;

import com.wisekrakr.androidmain.components.EntityColor;

public class EntityColorHelper {

    private static EntityColor entityColor;

    private static EntityColor[] entityColors = EntityColor.values();

    public static EntityColor randomEntityColor() {
        entityColor = entityColors[GameHelper.randomGenerator.nextInt(entityColors.length)];
        return entityColor;
    }

    public static EntityColor[] getEntityColors() {
        return entityColors;
    }

    public static EntityColor getEntityColor() {
        return entityColor;
    }


}
