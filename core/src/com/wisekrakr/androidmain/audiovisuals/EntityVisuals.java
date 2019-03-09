package com.wisekrakr.androidmain.audiovisuals;

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
    public void visualizeColoredEntity(Entity entity, TypeComponent.Type type) {
        if (entity != null) {
            switch (entity.getComponent(BrickComponent.class).getBrickColorContext().getBrickColor()) {
                case RED:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "07-Breakout-Tiles",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
                case BLUE:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "01-Breakout-Tiles",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
                case WHITE:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "17-Breakout-Tiles",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
                case GREEN:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "03-Breakout-Tiles",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
                case PURPLE:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "05-Breakout-Tiles",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
                case GOLD:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "13-Breakout-Tiles",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
                case ORANGE:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "09-Breakout-Tiles",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
            }


        }else {
            System.out.println("No Bricks to draw");
        }
    }

    @Override
    public void visualizePower(Entity entity) {
        if (entity != null) {
            switch (PowerHelper.getPower()) {
                case THEY_LIVE:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "27-Breakout-Tiles",
                            entity.getComponent(PowerUpComponent.class).width, entity.getComponent(PowerUpComponent.class).height);
                    break;
                case NUKE:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "26-Breakout-Tiles",
                            entity.getComponent(PowerUpComponent.class).width, entity.getComponent(PowerUpComponent.class).height);
                    break;
                case MORE_BRICKS:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "24-Breakout-Tiles",
                            entity.getComponent(PowerUpComponent.class).width, entity.getComponent(PowerUpComponent.class).height);
                    break;
                case ENLARGE_PLAYER:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "22-Breakout-Tiles",
                            entity.getComponent(PowerUpComponent.class).width, entity.getComponent(PowerUpComponent.class).height);
                    break;
                case SHORTEN_PLAYER:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "21-Breakout-Tiles",
                            entity.getComponent(PowerUpComponent.class).width, entity.getComponent(PowerUpComponent.class).height);
                    break;
                case BIGGER_BALL:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "23-Breakout-Tiles",
                            entity.getComponent(PowerUpComponent.class).width, entity.getComponent(PowerUpComponent.class).height);
                    break;
                case EXTRA_LIFE:
                    drawObjectViaAtlas(entity, "images/breakout/breakout.atlas", "25-Breakout-Tiles",
                            entity.getComponent(PowerUpComponent.class).width, entity.getComponent(PowerUpComponent.class).height);
                    break;
            }
        }else {
            System.out.println("No Powers to draw" + PowerHelper.getPower());
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
