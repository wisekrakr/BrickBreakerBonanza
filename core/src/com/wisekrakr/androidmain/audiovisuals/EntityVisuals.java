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
            if (type == TypeComponent.Type.ENEMY) {
                EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

                switch (enemyComponent.getEnemyColorContext().getEnemyColor()) {
                    case RED:
                        drawObjectViaAtlas(entity,
                                "images/deez/balls.atlas", "redball",
                                enemyComponent.getRadius(),
                                enemyComponent.getRadius()
                        );
                        break;
                    case BLUE:
                        drawObjectViaAtlas(entity,
                                "images/deez/balls.atlas", "blueball",
                                enemyComponent.getRadius(),
                                enemyComponent.getRadius()
                        );
                        break;
                    case GREEN:
                        drawObjectViaAtlas(entity,
                                "images/deez/balls.atlas", "greenball",
                                enemyComponent.getRadius(),
                                enemyComponent.getRadius()
                        );
                        break;
                    case PURPLE:
                        drawObjectViaAtlas(entity,
                                "images/deez/balls.atlas", "purpleball",
                                enemyComponent.getRadius(),
                                enemyComponent.getRadius()
                        );
                        break;
                    case GOLD:
                        drawObjectViaAtlas(entity,
                                "images/deez/balls.atlas", "goldball",
                                enemyComponent.getRadius(),
                                enemyComponent.getRadius()
                        );
                        break;
                }
            }else if (type == TypeComponent.Type.POWER){
                PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

                drawObjectViaAtlas(entity, "images/others/others.atlas","power",
                        powerUpComponent.getWidth(), powerUpComponent.getHeight()
                );

            }else if (type == TypeComponent.Type.OBSTACLE){
                ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);

                drawObjectViaAtlas(entity, "images/others/others.atlas", "platform",
                        obstacleComponent.getWidth(), obstacleComponent.getHeight()
                );

            }else if (type == TypeComponent.Type.PLAYER){
                PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

                drawObjectViaAtlas(entity,
                        "images/player/player.atlas", "cool",
                        playerComponent.getRadius(), playerComponent.getRadius()
                );

            }else if (type == TypeComponent.Type.PENIS){
                PenisComponent penisComponent = game.getGameThread().getComponentMapperSystem().getPenisComponentMapper().get(entity);

                drawObjectViaAtlas(entity,
                        "images/others/others.atlas", "penis",
                        penisComponent.getWidth(), penisComponent.getHeight()
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
                    width, height
            );
        }
    }

    @Override
    public void drawObjectViaFileName(Entity entity, String fileName, float width, float height) {
        if (entity != null){
            SpriteHelper.entitySprite(game.assetManager(),
                    fileName,
                    width, height
            );
        }
    }
}
