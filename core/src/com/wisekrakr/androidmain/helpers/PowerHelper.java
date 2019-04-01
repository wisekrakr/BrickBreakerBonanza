package com.wisekrakr.androidmain.helpers;

import com.badlogic.ashley.core.Entity;

import java.util.HashMap;

public class PowerHelper {

    private static HashMap<Entity, Power> map;

    public enum Power {
        ENLARGE_PLAYER, REDUCE_PLAYER, SPEED_BOOST, SLOW_MO, EXTRA_LIFE
    }

    private static Power[] powers = Power.values();

    public static Power randomPowerUp(){
        return powers[GameHelper.randomGenerator.nextInt(powers.length)];
    }

    private static Power power = null;

    public static Power getPower() {
        return power;
    }

    public static void setPowerUp(Entity entity, Power power) {
        map = new HashMap<Entity, Power>();

        PowerHelper.power = power;

        map.put(entity, power);
    }

    public static HashMap<Entity, Power> getPowerUpMap() {
        return map;
    }
}
