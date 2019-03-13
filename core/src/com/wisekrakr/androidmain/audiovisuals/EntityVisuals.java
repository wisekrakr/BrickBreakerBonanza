package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.helpers.SpriteHelper;

public class EntityVisuals implements EntityVisualsContext {

    private AndroidGame game;
    private SpriteBatch spriteBatch;

    EntityVisuals(AndroidGame game, SpriteBatch spriteBatch) {
        this.game = game;
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void visualizeColoredEntity(Entity entity) {

        if (entity != null) {
            TypeComponent.Type type = ComponentMapper.getFor(TypeComponent.class).get(entity).getType();
            if (type == TypeComponent.Type.BALL) {
                switch (entity.getComponent(BallComponent.class).getBallColorContext().getBallColor()) {
                    case RED:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "07-Breakout-Tiles",
                                entity.getComponent(GameObjectComponent.class).radius, entity.getComponent(GameObjectComponent.class).radius);
                        break;
                    case BLUE:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "01-Breakout-Tiles",
                                entity.getComponent(GameObjectComponent.class).radius, entity.getComponent(GameObjectComponent.class).radius);
                        break;
                    case WHITE:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "17-Breakout-Tiles",
                                entity.getComponent(GameObjectComponent.class).radius, entity.getComponent(GameObjectComponent.class).radius);
                        break;
                    case GREEN:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "03-Breakout-Tiles",
                                entity.getComponent(GameObjectComponent.class).radius, entity.getComponent(GameObjectComponent.class).radius);
                        break;
                    case PURPLE:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "05-Breakout-Tiles",
                                entity.getComponent(GameObjectComponent.class).radius, entity.getComponent(GameObjectComponent.class).radius);
                        break;
                    case GOLD:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "13-Breakout-Tiles",
                                entity.getComponent(GameObjectComponent.class).radius, entity.getComponent(GameObjectComponent.class).radius);
                        break;
                    case ORANGE:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "09-Breakout-Tiles",
                                entity.getComponent(GameObjectComponent.class).radius, entity.getComponent(GameObjectComponent.class).radius);
                        break;
                }
            }else if (type == TypeComponent.Type.POWER){
                switch (PowerHelper.getPower()) {
                    case ENLARGE_PLAYER:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "22-Breakout-Tiles",
                                entity.getComponent(GameObjectComponent.class).width, entity.getComponent(GameObjectComponent.class).height);
                        break;
                    case REDUCE_PLAYER:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "21-Breakout-Tiles",
                                entity.getComponent(GameObjectComponent.class).width, entity.getComponent(GameObjectComponent.class).height);
                        break;
                    case BIGGER_BALL:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "23-Breakout-Tiles",
                                entity.getComponent(GameObjectComponent.class).width, entity.getComponent(GameObjectComponent.class).height);
                        break;
                    case EXTRA_LIFE:
                        drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "25-Breakout-Tiles",
                                entity.getComponent(GameObjectComponent.class).width, entity.getComponent(GameObjectComponent.class).height);
                        break;
                }
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
