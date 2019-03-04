package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
            switch (entity.getComponent(BrickComponent.class).getBrickColor()) {
                case RED:
                    drawObjectViaAtlas(entity, "images/objects/gameobjects.atlas", "square_red",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
                case BLUE:
                    drawObjectViaAtlas(entity, "images/objects/gameobjects.atlas", "square_blue",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
                case CYAN:
                    drawObjectViaAtlas(entity, "images/objects/gameobjects.atlas", "square_cyan",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
                case GREEN:
                    drawObjectViaAtlas(entity, "images/objects/gameobjects.atlas", "square_green",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
                case PURPLE:
                    drawObjectViaAtlas(entity, "images/objects/gameobjects.atlas", "square_purple",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
                case YELLOW:
                    drawObjectViaAtlas(entity, "images/objects/gameobjects.atlas", "square_yellow",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
                case ORANGE:
                    drawObjectViaAtlas(entity, "images/objects/gameobjects.atlas", "square_orange",
                            entity.getComponent(BrickComponent.class).width, entity.getComponent(BrickComponent.class).height);
                    break;
            }
        }else {
            System.out.println("No Entities to draw");
        }
    }

    @Override
    public void visualizePower(Entity entity) {
        switch (PowerHelper.getPower()) {
            case THEY_LIVE:
                drawObjectViaAtlas(entity, "images/game/game.atlas", "earth",
                        entity.getComponent(PowerUpComponent.class).radius, entity.getComponent(PowerUpComponent.class).radius);
                break;
            case NUKE:
                drawObjectViaAtlas(entity, "images/game/game.atlas", "mars",
                        entity.getComponent(PowerUpComponent.class).radius, entity.getComponent(PowerUpComponent.class).radius);
                break;
            case MORE_BRICKS:
                drawObjectViaAtlas(entity, "images/game/game.atlas", "mercury",
                        entity.getComponent(PowerUpComponent.class).radius, entity.getComponent(PowerUpComponent.class).radius);
                break;
            case ENLARGE_PLAYER:
                drawObjectViaAtlas(entity, "images/game/game.atlas", "earth",
                        entity.getComponent(PowerUpComponent.class).radius, entity.getComponent(PowerUpComponent.class).radius);
                break;
            case SHORTEN_PLAYER:
                drawObjectViaAtlas(entity, "images/game/game.atlas", "earth",
                        entity.getComponent(PowerUpComponent.class).radius, entity.getComponent(PowerUpComponent.class).radius);
                break;
        }
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
