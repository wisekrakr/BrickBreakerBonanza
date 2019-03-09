package com.wisekrakr.androidmain.helpers;

import com.wisekrakr.androidmain.components.EntityColor;

import java.util.ArrayList;
import java.util.List;

public class EntityColorHelper {

    private static EntityColor[] entityColors = EntityColor.values();

    public static EntityColor randomEntityColor() {
        return entityColors[GameHelper.randomGenerator.nextInt(entityColors.length)];
    }

}
