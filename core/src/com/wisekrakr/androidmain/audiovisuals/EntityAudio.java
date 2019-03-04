package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.helpers.PowerHelper;

public class EntityAudio implements EntityAudioContext {

    private AndroidGame game;

    public EntityAudio(AndroidGame game) {
        this.game = game;
    }

    @Override
    public void audioForEntity() {
//        for (Entity entity: game.getEngine().getEntities()) {
//            if (entity != null) {
//                if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BALL ||
//                        entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BRICK) {
//
//                    if (entity.getComponent(BallComponent.class).hitSurface || entity.getComponent(BallComponent.class).hitObstacle) {
//                        addSound("sounds/hit_wall.wav");
//
//                    } else if (entity.getComponent(BallComponent.class).hitEntity) {
//                        if (entity.getComponent(BallComponent.class).destroy) {
//                            addSound("sounds/secret.wav");
//                        }else {
//                            //addSound("sounds/bounce thicc.wav");
//                        }
//                    }
//                } else if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.POWER) {
//                    if (entity.getComponent(BallComponent.class).destroy) {
//                        if (PowerHelper.getPower() == PowerHelper.Power.EXTRA_BALL) {
//                            addSound("sounds/powerup_extratime.wav");
//                        } else if (PowerHelper.getPower() == PowerHelper.Power.THEY_LIVE) {
//                            addSound("sounds/powerup_freeze.wav");
//                        } else if (PowerHelper.getPower() == PowerHelper.Power.NUKE) {
//                            addSound("sounds/powerup_nuke.wav");
//                        } else if (PowerHelper.getPower() == PowerHelper.Power.MORE_BRICKS) {
//                            addSound("sounds/powerdown_moreballs.wav");
//                        }
//
//                    }
//                }
//
//            }
//        }
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
