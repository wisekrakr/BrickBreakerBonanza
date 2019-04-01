package com.wisekrakr.androidmain.helpers;

import com.wisekrakr.androidmain.components.EntityStyle;

public class EntityStyleHelper {

    private static EntityStyle style;

    private static EntityStyle[] entityStyles = EntityStyle.values();

    public static EntityStyle randomEntityStyle() {
        style = entityStyles[GameHelper.randomGenerator.nextInt(entityStyles.length)];
        return style;
    }

    public static EntityStyle[] getEntityStyles() {
        return entityStyles;
    }

    public static EntityStyle getStyle() {
        return style;
    }


}
