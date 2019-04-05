package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.helpers.PowerHelper;

public class EntityAudio implements EntityAudioContext {

    private BricksGame game;

    public EntityAudio(BricksGame game) {
        this.game = game;
    }

    @Override
    public void audioForEntity() {
        for (Entity entity: game.getEngine().getEntities()) {
            if (entity != null) {
                if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BALL) {
                    if (entity.getComponent(BallComponent.class).isDestroy()) {
                        addSound("sounds/secret.wav");
                    } else {
                        if (entity.getComponent(CollisionComponent.class).hitSurface ||
                                entity.getComponent(CollisionComponent.class).hitObstacle ||
                                entity.getComponent(CollisionComponent.class).hitPlayer){
                            addSound("sounds/hit_wall.wav");
                        }
                    }
                }
                if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.POWER) {
                    if (entity.getComponent(PowerUpComponent.class).isDestroy()){
                        if (PowerHelper.getPower() == PowerHelper.Power.EXTRA_LIFE){
                            addSound("sounds/powerup_extratime.wav");
                        }else if (PowerHelper.getPower() == PowerHelper.Power.BIGGER_BALL){
                            addSound("sounds/powerdown_moreballs.wav");
                        }else if (PowerHelper.getPower() == PowerHelper.Power.ENLARGE_PLAYER){
                            addSound("sounds/powerup_freeze.wav");
                        }else if (PowerHelper.getPower() == PowerHelper.Power.REDUCE_PLAYER){
                            addSound("sounds/powerup_freeze.wav");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void audioForAction(Controls controls) {
        if (controls.up || controls.isLeftMouseDown){
            addSound("sounds/shoot.wav");
        }

    }

    private void addSound(String fileName){
        Sound sound = game.assetManager().assetManager.get(fileName, Sound.class);
        sound.play(game.getGamePreferences().getSoundVolume());
    }
}
