package com.wisekrakr.androidmain.retainers;

public class ScoreKeeper {

    private static int score = 0;

    public static void setScore(int scores){
        if (scores < 0 || scores > 10000){
            throw new IllegalArgumentException();
        }else {
            score += scores;
        }
    }

    public static int getScore() {
        int s = score;

        if (s < 0 || s > 999999999){
            s = 0;
        }else {
            return s;
        }
        return s;
    }

    private static int pointsToGive = 0;

    public static int getPointsToGive() {
        return pointsToGive;
    }

    private static int multiplier = 1;

    public static int getMultiplier() {
        return multiplier;
    }

    public static void setMultiplier(int multi) {
        multiplier = multi;

    }

    public static void setPointsToGive(int points) {
        pointsToGive = points;
    }

    public static void reset(){
        score = 0;
        pointsToGive = 0;
        multiplier = 1;
    }
}
