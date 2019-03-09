package com.wisekrakr.androidmain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePreferences {

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "breakout";
    private static final String HIGHSCORE = "highscore";

    private static final String COMPLETED = "completed";
    private static final String ONGOING = "ongoing";

    private boolean completed = false;
    private boolean onGoing = false;

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }
    protected Preferences getPrefsCompletion() {
        return Gdx.app.getPreferences(COMPLETED);
    }
    protected Preferences getPrefsOnGoing() {
        return Gdx.app.getPreferences(ONGOING);
    }

    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }

    public int getHighScore(){
        return getPrefs().getInteger(HIGHSCORE);
    }

    public void setHighScore(int score){
        getPrefs().putInteger(HIGHSCORE, score);
        getPrefs().flush();
    }

    /*
    Level selection methods
     */

    public void setLevelCompleted(int numberOfLevel, boolean setComplete) {
        completed = setComplete;
        String string = Integer.toString(numberOfLevel);

        getPrefsCompletion().putBoolean(string, completed);
        getPrefsCompletion().flush();
    }

    public void setLevelGoing(int numberOfLevel, boolean setGoing) {
        onGoing = setGoing;
        String string = Integer.toString(numberOfLevel);

        getPrefsOnGoing().putBoolean(string, onGoing);
        getPrefsOnGoing().flush();
    }

    public boolean levelDone(int numberOfLevel) {
        String string = Integer.toString(numberOfLevel);
        return getPrefsCompletion().getBoolean(string, completed);
    }

    public boolean levelGoing(int numberOfLevel){
        String string = Integer.toString(numberOfLevel);
        return getPrefsOnGoing().getBoolean(string, onGoing);
    }

}
