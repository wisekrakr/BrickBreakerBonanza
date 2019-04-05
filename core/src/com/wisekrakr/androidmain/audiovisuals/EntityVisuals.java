package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.helpers.SpriteHelper;

public class EntityVisuals implements EntityVisualsContext {

    private BricksGame game;
    private SpriteBatch spriteBatch;

    EntityVisuals(BricksGame game, SpriteBatch spriteBatch) {
        this.game = game;
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void visualizeEntity(Entity entity) {

        if (entity != null) {
            TypeComponent.Type type = ComponentMapper.getFor(TypeComponent.class).get(entity).getType();
            if (type == TypeComponent.Type.BRICK) {
                switch (entity.getComponent(BrickComponent.class).getBrickColorContext().getBrickColor()) {
                    case RED:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "07-Breakout-Tiles",
                                entity.getComponent(BrickComponent.class).getWidth(), entity.getComponent(BrickComponent.class).getHeight());
                        break;
                    case BLUE:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "01-Breakout-Tiles",
                                entity.getComponent(BrickComponent.class).getWidth(), entity.getComponent(BrickComponent.class).getHeight());
                        break;
                    case WHITE:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "17-Breakout-Tiles",
                                entity.getComponent(BrickComponent.class).getWidth(), entity.getComponent(BrickComponent.class).getHeight());
                        break;
                    case GREEN:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "03-Breakout-Tiles",
                                entity.getComponent(BrickComponent.class).getWidth(), entity.getComponent(BrickComponent.class).getHeight());
                        break;
                    case PURPLE:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "05-Breakout-Tiles",
                                entity.getComponent(BrickComponent.class).getWidth(), entity.getComponent(BrickComponent.class).getHeight());
                        break;
                    case GOLD:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "13-Breakout-Tiles",
                                entity.getComponent(BrickComponent.class).getWidth(), entity.getComponent(BrickComponent.class).getHeight());
                        break;
                    case ORANGE:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "09-Breakout-Tiles",
                                entity.getComponent(BrickComponent.class).getWidth(), entity.getComponent(BrickComponent.class).getHeight());
                        break;
                }
            }else if (type == TypeComponent.Type.POWER){
                switch (PowerHelper.getPower()) {
                    case ENLARGE_PLAYER:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "22-Breakout-Tiles",
                                entity.getComponent(PowerUpComponent.class).getWidth(), entity.getComponent(PowerUpComponent.class).getHeight());
                        break;
                    case REDUCE_PLAYER:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "21-Breakout-Tiles",
                                entity.getComponent(PowerUpComponent.class).getWidth(), entity.getComponent(PowerUpComponent.class).getHeight());
                        break;
                    case BIGGER_BALL:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "23-Breakout-Tiles",
                                entity.getComponent(PowerUpComponent.class).getWidth(), entity.getComponent(PowerUpComponent.class).getHeight());
                        break;
                    case EXTRA_LIFE:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "25-Breakout-Tiles",
                                entity.getComponent(PowerUpComponent.class).getWidth(), entity.getComponent(PowerUpComponent.class).getHeight());
                        break;
                    case THEY_LIVE:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "27-Breakout-Tiles",
                                entity.getComponent(PowerUpComponent.class).getWidth(), entity.getComponent(PowerUpComponent.class).getHeight());
                        break;
                    case NUKE:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "26-Breakout-Tiles",
                                entity.getComponent(PowerUpComponent.class).getWidth(), entity.getComponent(PowerUpComponent.class).getHeight());
                        break;
                    case MORE_BRICKS:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "24-Breakout-Tiles",
                                entity.getComponent(PowerUpComponent.class).getWidth(), entity.getComponent(PowerUpComponent.class).getHeight());
                        break;
                }
            }else if (type == TypeComponent.Type.BALL){
                drawObjectViaAtlas(entity,
                            "images/breakout/breakout.atlas",
                            "58-Breakout-Tiles",
                            entity.getComponent(BallComponent.class).getRadius(),
                            entity.getComponent(BallComponent.class).getRadius()
                    );
            }else if (type == TypeComponent.Type.PLAYER){
                drawObjectViaAtlas(entity,
                            "images/breakout/breakout.atlas",
                            "50-Breakout-Tiles",
                            entity.getComponent(PlayerComponent.class).getWidth(),
                            entity.getComponent(PlayerComponent.class).getHeight()
                    );
            }
        }
    }

    @Override
    public void animate(Entity entity) {
        Animation<TextureRegion>animation;
        Texture sheet = new Texture(Gdx.files.internal("images/player/player.png"));

        TextureRegion[][] tmp = TextureRegion.split(sheet,
                sheet.getWidth() / 20,
                sheet.getHeight() / 15
        );
        TextureRegion[] frames = new TextureRegion[20 * 15];
        int index = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 20; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        animation = new Animation<TextureRegion>(0.025f, frames);
    }

    @Override
    public void drawObjectViaAtlas(Entity entity, String atlasPath, String regionPath, float width, float height) {
        if (entity != null){
            SpriteHelper.entitySpriteAtlas(
                    entity,
                    game.assetManager(),
                    atlasPath,
                    regionPath,
                    entity.getComponent(Box2dBodyComponent.class).body,
                    spriteBatch,
                    width, height);
        }
    }

    @Override
    public void drawObjectViaFileName(Entity entity, String fileName, float width, float height) {
        if (entity != null){
            SpriteHelper.entitySprite(game.assetManager(),
                    fileName,
                    width, height);
        }
    }
}
