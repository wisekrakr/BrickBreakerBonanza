package com.wisekrakr.androidmain.retainers;

public class ScoreKeeper {

    public static int lives = 3;

    public static void setLives(int lives) {
        ScoreKeeper.lives = lives;
    }

    private static int score = 0;

    public static void setScore(int scores){
        if (scores < 0 || scores > 10000){
            throw new IllegalArgumentException();
        }else {
            ScoreKeeper.score += scores;
        }
    }

    public static int getScore() {
        int s = ScoreKeeper.score;

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
        ScoreKeeper.multiplier = multi;
    }

    public static void setPointsToGive(int points) {
        ScoreKeeper.pointsToGive = points;
    }

    public static void reset(){
        ScoreKeeper.score = 0;
        ScoreKeeper.pointsToGive = 0;
        ScoreKeeper.multiplier = 1;
        ScoreKeeper.lives = 3;
    }
}
