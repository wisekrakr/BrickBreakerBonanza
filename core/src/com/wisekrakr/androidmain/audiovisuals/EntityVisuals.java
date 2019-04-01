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
    public void visualizeEntity(Entity entity) {


        TypeComponent.Type type = ComponentMapper.getFor(TypeComponent.class).get(entity).getType();
        if (type == TypeComponent.Type.POWER){
            PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

            switch (PowerHelper.getPower()){
                case ENLARGE_PLAYER:
                    drawObjectViaAtlas(entity, "images/powerups/powerups.atlas","enlarge",
                            powerUpComponent.getWidth(), powerUpComponent.getHeight()
                    );
                    break;
                case REDUCE_PLAYER:
                    drawObjectViaAtlas(entity, "images/powerups/powerups.atlas","reduce",
                            powerUpComponent.getWidth(), powerUpComponent.getHeight()
                    );
                    break;
                case SPEED_BOOST:
                    drawObjectViaAtlas(entity, "images/powerups/powerups.atlas","speedup",
                            powerUpComponent.getWidth(), powerUpComponent.getHeight()
                    );
                    break;
                case SLOW_MO:
                    drawObjectViaAtlas(entity, "images/powerups/powerups.atlas","slowmo",
                            powerUpComponent.getWidth(), powerUpComponent.getHeight()
                    );
                    break;
                case EXTRA_LIFE:
                    drawObjectViaAtlas(entity, "images/powerups/powerups.atlas","extralife",
                            powerUpComponent.getWidth(), powerUpComponent.getHeight()
                    );
                    break;
            }

        }else if (type == TypeComponent.Type.OBSTACLE){
            ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);

            drawObjectViaAtlas(entity, "images/others/others.atlas", "platform",
                    obstacleComponent.getWidth(), obstacleComponent.getHeight()
            );

        }

        if (type == TypeComponent.Type.ENEMY) {
            EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

            switch (enemyComponent.getEntityStyleContext().getEntityStyle()) {
                case WHITE_REDHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_redhair",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );

                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "penis", "w_hairyred_testicle");
                    }
                    break;
                case WHITE_BLONDHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_blondhair",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "penis", "w_hairyblond_testicle");
                    }
                    break;
                case WHITE_BROWNHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_brownhair",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "w_penis", "w_bald_testicle");
                    }
                    break;
                case BLACK_BLACKHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "black_blackhair",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "b_penis", "b_hairyblack_testicle");
                    }
                    break;
                case WHITE_BLACKHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_blackhair",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "w_penis", "hairyblack_testicle");
                    }
                    break;

                case BLACK_BEARD:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "black_beard",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "b_penis", "b_bald_testicle");
                    }
                    break;
                case BLACK_GREYHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "black_greyhair",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "b_penis", "b_hairygrey_testicle");
                    }
                    break;
                case WHITE_BALD:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_bald",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "w_penis", "bald_testicle");
                    }
                    break;
            }

        }else if (type == TypeComponent.Type.PLAYER){
            PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

            switch (playerComponent.getEntityStyleContext().getEntityStyle()) {
                    case WHITE_REDHAIR:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "white_redhair",
                                playerComponent.getWidth(),
                                playerComponent.getHeight()
                        );

                        for (Entity ent: playerComponent.getAttachedEntities()) {
                            addCockAndBalls(ent, "penis", "w_hairyred_testicle");
                        }
                        break;
                    case WHITE_BLONDHAIR:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "white_blondhair",
                                playerComponent.getWidth(),
                                playerComponent.getHeight()
                        );

                        for (Entity ent: playerComponent.getAttachedEntities()) {
                            addCockAndBalls(ent, "penis", "w_hairyblond_testicle");
                        }
                        break;
                    case WHITE_BROWNHAIR:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "white_brownhair",
                                playerComponent.getWidth(),
                                playerComponent.getHeight()
                        );

                        for (Entity ent: playerComponent.getAttachedEntities()) {
                            addCockAndBalls(ent, "w_penis", "w_bald_testicle");
                        }
                        break;
                    case BLACK_BLACKHAIR:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "black_blackhair",
                                playerComponent.getWidth(),
                                playerComponent.getHeight()
                        );

                        for (Entity ent: playerComponent.getAttachedEntities()) {
                            addCockAndBalls(ent, "b_penis", "b_hairyblack_testicle");
                        }
                        break;

                    case WHITE_BLACKHAIR:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "white_blackhair",
                                playerComponent.getWidth(),
                                playerComponent.getHeight()
                        );

                        for (Entity ent: playerComponent.getAttachedEntities()) {
                            addCockAndBalls(ent, "w_penis", "hairyblack_testicle");
                        }
                        break;

                    case BLACK_BEARD:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "black_beard",
                                playerComponent.getWidth(),
                                playerComponent.getHeight()
                        );

                        for (Entity ent: playerComponent.getAttachedEntities()) {
                            addCockAndBalls(ent, "b_penis", "b_bald_testicle");
                        }
                        break;
                    case BLACK_GREYHAIR:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "black_greyhair",
                                playerComponent.getWidth(),
                                playerComponent.getHeight()
                        );

                        for (Entity ent: playerComponent.getAttachedEntities()) {
                            addCockAndBalls(ent, "b_penis", "b_hairygrey_testicle");
                        }
                        break;
                    case WHITE_BALD:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "white_bald",
                                playerComponent.getWidth(),
                                playerComponent.getHeight()
                        );

                        for (Entity ent: playerComponent.getAttachedEntities()) {
                            addCockAndBalls(ent, "w_penis", "bald_testicle");
                        }
                        break;
                }

        }

    }

    @Override
    public void visualizeCensoredEntity(Entity entity) {
        TypeComponent.Type type = ComponentMapper.getFor(TypeComponent.class).get(entity).getType();

        if (type == TypeComponent.Type.ENEMY) {
            EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

            switch (enemyComponent.getEntityStyleContext().getEntityStyle()) {
                case WHITE_REDHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_redhair",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );

                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "penis_blur", "w_hairyred_testicle_blur");
                    }
                    break;
                case WHITE_BLONDHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_blondhair",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "penis_blur", "w_hairyblond_testicle_blur");
                    }
                    break;
                case WHITE_BROWNHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_brownhair",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "w_penis_blur", "w_bald_testicle_blur");
                    }
                    break;
                case BLACK_BLACKHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "black_blackhair",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "b_penis_blur", "b_hairyblack_testicle_blur");
                    }
                    break;
                case WHITE_BLACKHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_blackhair",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "w_penis_blur", "hairyblack_testicle_blur");
                    }
                    break;

                case BLACK_BEARD:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "black_beard",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "b_penis_blur", "b_bald_testicle_blur");
                    }
                    break;
                case BLACK_GREYHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "black_greyhair",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "b_penis_blur", "b_hairygrey_testicle_blur");
                    }
                    break;
                case WHITE_BALD:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_bald",
                            enemyComponent.getWidth(),
                            enemyComponent.getHeight()
                    );
                    for (Entity ent: enemyComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "w_penis_blur", "bald_testicle_blur");
                    }
                    break;
            }

        }else if (type == TypeComponent.Type.PLAYER){
            PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

            switch (playerComponent.getEntityStyleContext().getEntityStyle()) {
                case WHITE_REDHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_redhair",
                            playerComponent.getWidth(),
                            playerComponent.getHeight()
                    );

                    for (Entity ent: playerComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "penis_blur", "w_hairyred_testicle_blur");
                    }
                    break;
                case WHITE_BLONDHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_blondhair",
                            playerComponent.getWidth(),
                            playerComponent.getHeight()
                    );

                    for (Entity ent: playerComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "penis_blur", "w_hairyblond_testicle_blur");
                    }
                    break;
                case WHITE_BROWNHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_brownhair",
                            playerComponent.getWidth(),
                            playerComponent.getHeight()
                    );

                    for (Entity ent: playerComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "w_penis_blur", "w_bald_testicle_blur");
                    }
                    break;
                case BLACK_BLACKHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "black_blackhair",
                            playerComponent.getWidth(),
                            playerComponent.getHeight()
                    );

                    for (Entity ent: playerComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "b_penis_blur", "b_hairyblack_testicle_blur");
                    }
                    break;

                case WHITE_BLACKHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_blackhair",
                            playerComponent.getWidth(),
                            playerComponent.getHeight()
                    );

                    for (Entity ent: playerComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "w_penis_blur", "hairyblack_testicle_blur");
                    }
                    break;

                case BLACK_BEARD:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "black_beard",
                            playerComponent.getWidth(),
                            playerComponent.getHeight()
                    );

                    for (Entity ent: playerComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "b_penis_blur", "b_bald_testicle_blur");
                    }
                    break;
                case BLACK_GREYHAIR:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "black_greyhair",
                            playerComponent.getWidth(),
                            playerComponent.getHeight()
                    );

                    for (Entity ent: playerComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "b_penis_blur", "b_hairygrey_testicle_blur");
                    }
                    break;
                case WHITE_BALD:
                    drawObjectViaAtlas(entity,
                            "images/players/players.atlas", "white_bald",
                            playerComponent.getWidth(),
                            playerComponent.getHeight()
                    );

                    for (Entity ent: playerComponent.getAttachedEntities()) {
                        addCockAndBalls(ent, "w_penis_blur", "bald_testicle_blur");
                    }
                    break;
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

    private void addCockAndBalls(Entity ent, String regionPathPenis, String regionPathTesticle){

        if (ent.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PENIS) {
            PenisComponent penisComponent = game.getGameThread().getComponentMapperSystem().getPenisComponentMapper().get(ent);

            drawObjectViaAtlas(ent,
                    "images/cockandballs/cockandballs.atlas", regionPathPenis,
                    penisComponent.getLength(), penisComponent.getGirth()
            );
        }else if (ent.getComponent(TypeComponent.class).getType() == TypeComponent.Type.TESTICLE){
            TesticleComponent testicleComponent = game.getGameThread().getComponentMapperSystem().getTesticleComponentMapper().get(ent);

            drawObjectViaAtlas(ent,
                    "images/cockandballs/cockandballs.atlas", regionPathTesticle,
                    testicleComponent.getRadius(), testicleComponent.getRadius()
            );
        }

    }
}
