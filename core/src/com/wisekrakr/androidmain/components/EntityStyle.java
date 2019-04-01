package com.wisekrakr.androidmain.components;

public enum EntityStyle {
    WHITE_REDHAIR(8f,3f, 100f),
    WHITE_BLONDHAIR(8f,3f, 100f),
    WHITE_BLACKHAIR(11f,4f, 60f),
    WHITE_BROWNHAIR(9f,5f, 90f),
    BLACK_BLACKHAIR(10f,6f, 70f),
    BLACK_BEARD(14f,5f, 40f),
    BLACK_GREYHAIR(10f,4f, 80f),
    WHITE_BALD(13f,5f, 50f);

    private float penisLength;
    private float penisGirth;
    private float penisSpeed;

    EntityStyle(float length, float girth, float speed) {
        penisLength = length;
        penisGirth = girth;
        penisSpeed = speed;
    }

    public float getPenisLength() {
        return penisLength;
    }

    public float getPenisGirth() {
        return penisGirth;
    }

    public float getPenisSpeed() {
        return penisSpeed;
    }
}
