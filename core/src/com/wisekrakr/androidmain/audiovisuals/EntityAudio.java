package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.helpers.PowerHelper;

public class EntityAudio implements EntityAudioContext {

    private AndroidGame game;

    public EntityAudio(AndroidGame game) {
        this.game = game;
    }

    @Override
    public void audioForEntity() {
        for (Entity entity: game.getEngine().getEntities()) {
            if (entity != null) {
                if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BALL) {
                    if (entity.getComponent(BallComponent.class).destroy) {
                        addSound("sounds/secret.wav");
                    } else {
                        if (entity.getComponent(CollisionComponent.class).hitSurface ||
                                entity.getComponent(CollisionComponent.class).hitObstacle ||
                                entity.getComponent(CollisionComponent.class).hitEntity ||
                                entity.getComponent(CollisionComponent.class).hitPlayer){
                            addSound("sounds/hit_wall.wav");
                        }
                    }
                }
                if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BRICK) {
                    if (entity.getComponent(BrickComponent.class).destroy){
                        addSound("sounds/secret.wav");
                    }
                }
                if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.POWER) {
                    if (entity.getComponent(PowerUpComponent.class).destroy){
                        if (PowerHelper.getPower() == PowerHelper.Power.NUKE){
                            addSound("sounds/powerup_nuke.wav");
                        }else if (PowerHelper.getPower() == PowerHelper.Power.EXTRA_LIFE){
                            addSound("sounds/powerup_extratime.wav");
                        }else if (PowerHelper.getPower() == PowerHelper.Power.THEY_LIVE){
                            addSound("sounds/powerup_freeze.wav");
                        }else if (PowerHelper.getPower() == PowerHelper.Power.MORE_BRICKS){
                            addSound("sounds/powerdown_moreballs.wav");
                        }else if (PowerHelper.getPower() == PowerHelper.Power.BIGGER_BALL){
                            addSound("sounds/powerup_freeze.wav");
                        }else if (PowerHelper.getPower() == PowerHelper.Power.ENLARGE_PLAYER){
                            addSound("sounds/powerup_freeze.wav");
                        }else if (PowerHelper.getPower() == PowerHelper.Power.SHORTEN_PLAYER){
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
