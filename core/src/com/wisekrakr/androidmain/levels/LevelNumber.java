package com.wisekrakr.androidmain.levels;

import java.util.HashMap;
import java.util.Map;

public enum LevelNumber {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),
    TEN(10), ELEVEN(11), TWELVE(12), THIRTEEN(13), FOURTEEN(14), FIFTEEN(15), SIXTEEN(16),
    SEVENTEEN(17), EIGHTEEN(18), NINETEEN(19), TWENTY(20), TWENTY_ONE(21);

    private int value;
    private static Map<Integer, LevelNumber> map = new HashMap<Integer, LevelNumber>();

    LevelNumber(int value){
        this.value = value;
    }

    public LevelNumber next()
    {
        return values()[(this.ordinal()+1) % values().length];
    }

    public static LevelNumber remove(LevelNumber levelNumber){
        return map.remove(levelNumber);
    }

    static {
        for (LevelNumber levelNumber : LevelNumber.values()) {
            map.put(levelNumber.value, levelNumber);
        }
    }

    public static LevelNumber valueOf(int numberOfLevel) {
        return map.get(numberOfLevel);
    }

    public int getValue() {
        return value;
    }

}
