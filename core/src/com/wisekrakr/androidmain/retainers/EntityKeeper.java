package com.wisekrakr.androidmain.retainers;

import com.wisekrakr.androidmain.GameConstants;

public class EntityKeeper {

    /**
     * Set this background information in their respective Systems (player/ball/brick/powerup)
     * This can be used as information for entities that have been removed, but their information have been saved here,
     * for further use.
     * Used in LevelModel/LevelFactory and InfoDisplay for instance
     */

    private static int ballsInPlay;

    public static int getBallsInPlay() {
        return ballsInPlay;
    }

    public static void setBallsInPlay(int balls) {
        ballsInPlay = balls;
    }

    private static int initialBricks;

    private static int initialPowerUps;

    public static int getInitialPowerUps() {
        return initialPowerUps;
    }

    public static void setInitialPowerUps(int powerUps) {
        initialPowerUps = powerUps;
    }

    public static void setInitialBricks(int bricks) {
        initialBricks = bricks;
    }

    public static int getInitialBricks() {
        return initialBricks;
    }

    private static int playersInPlay;

    public static int getPlayersInPlay() {
        return playersInPlay;
    }

    public static void setPlayersInPlay(int playersInPlay) {
        EntityKeeper.playersInPlay = playersInPlay;
    }

    private static float playerWidth;

    public static float getPlayerWidth() {
        return playerWidth;
    }

    public static void setPlayerWidth(float playerWidth) {
        EntityKeeper.playerWidth = playerWidth;
    }

    private static float ballRadius = GameConstants.BALL_RADIUS;

    public static float getBallRadius() {
        return ballRadius;
    }

    public static void setBallRadius(float ballRadius) {
        EntityKeeper.ballRadius = ballRadius;
    }
}
