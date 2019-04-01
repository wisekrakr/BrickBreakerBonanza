package com.wisekrakr.androidmain.retainers;

import com.wisekrakr.androidmain.components.EntityStyle;

public class SelectedCharacter {

    private static float penisLength;
    private static float penisGirth;

    public static float getPenisLength() {
        return penisLength;
    }

    public static void setPenisLengthGirth(float penisLength, float penisGirth) {
        SelectedCharacter.penisLength = penisLength;
        SelectedCharacter.penisGirth = penisGirth;
    }

    public static float getPenisGirth() {
        return penisGirth;
    }

    private static EntityStyle style;

    public static void setSelectedCharacter(EntityStyle entityStyle){
        style = entityStyle;
    }

    public static EntityStyle getSelectedCharacterStyle() {
        return style;
    }

    private static boolean destroy;

    public static boolean isDestroyed() {
        return destroy;
    }

    public static void setDestroyed(boolean destroyed) {
        destroy = destroyed;
    }
}
