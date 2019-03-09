package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.systems.PhysicsDebugSystem;
import com.wisekrakr.androidmain.systems.RenderingSystem;

public class Visualizer implements Disposable {

    private AndroidGame game;
    private RenderingSystem renderingSystem;
    private EntityVisuals entityVisuals;

    private final SpriteBatch spriteBatch;

    private Animation<TextureRegion>animation;
    private Texture sheet;
    private float stateTime;

    public Visualizer(AndroidGame game) {
        this.game = game;

        spriteBatch = new SpriteBatch();

        addSystems();

        sheet = new Texture(Gdx.files.internal("images/player/player.png"));
        stateTime = 0f;
    }

    private void addSystems(){
        renderingSystem = new RenderingSystem(spriteBatch);

        game.getEngine().addSystem(renderingSystem);
        game.getEngine().addSystem(new PhysicsDebugSystem(game.getGameThread().getEntityFactory().world, renderingSystem.getCamera()));

        entityVisuals = new EntityVisuals(game, spriteBatch);
    }

    public RenderingSystem getRenderingSystem() {
        return renderingSystem;
    }

    public void draw(float delta){
        stateTime += delta;

        spriteBatch.setProjectionMatrix(renderingSystem.getCamera().combined);

        spriteBatch.begin();

        for (Entity entity: game.getEngine().getEntities()){
            TypeComponent.Type type = entity.getComponent(TypeComponent.class).getType();

            switch (type){
                case BALL:
                    entityVisuals.drawObjectViaAtlas(entity,
                            "images/breakout/breakout.atlas",
                            "58-Breakout-Tiles",
                            entity.getComponent(BallComponent.class).radius,
                            entity.getComponent(BallComponent.class).radius
                    );
                    break;
                case BRICK:
                    entityVisuals.visualizeColoredEntity(entity, type);
                    break;
                case PLAYER:
//                    TextureRegion[][] tmp = TextureRegion.split(sheet,
//                            (int) entity.getComponent(PlayerComponent.class).width,
//                            (int) entity.getComponent(PlayerComponent.class).height
//                    );
//                    TextureRegion[] frames = new TextureRegion[6 * 5];
//                    int index = 0;
//                    for (int i = 0; i < 5; i++) {
//                        for (int j = 0; j < 6; j++) {
//                            frames[index++] = tmp[i][j];
//                        }
//                    }
//
//                    animation = new Animation<TextureRegion>(0.05f, frames);
//                    TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
//                    spriteBatch.draw(currentFrame,
//                            entity.getComponent(Box2dBodyComponent.class).body.getPosition().x - entity.getComponent(PlayerComponent.class).width/2,
//                            entity.getComponent(Box2dBodyComponent.class).body.getPosition().y - entity.getComponent(PlayerComponent.class).height/2
//                    );


                    entityVisuals.drawObjectViaAtlas(entity,
                            "images/breakout/breakout.atlas",
                            "50-Breakout-Tiles",
                            entity.getComponent(PlayerComponent.class).width,
                            entity.getComponent(PlayerComponent.class).height
                    );
                    break;
                case OBSTACLE:
                    entityVisuals.drawObjectViaAtlas(entity,
                            "images/breakout/breakout.atlas",
                            "29-Breakout-Tiles",
                            entity.getComponent(ObstacleComponent.class).width,
                            entity.getComponent(ObstacleComponent.class).height
                    );
                    break;
                case POWER:
                    entityVisuals.visualizePower(entity);
                    break;

            }


        }
        spriteBatch.end();
    }

    public void debugDrawableFilled(){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(renderingSystem.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity entity: game.getGameThread().getEntityFactory().getTotalBricks()) {
            if (entity != null) {
                BrickComponent brickComponent = ComponentMapper.getFor(BrickComponent.class).get(entity);

                float w = brickComponent.width;
                float h = brickComponent.height;

                switch (brickComponent.getBrickColorContext().getBrickColor()){
                    case RED:
                        shapeRenderer.setColor(Color.RED);
                        break;
                    case BLUE:
                        shapeRenderer.setColor(Color.BLUE);
                        break;
                    case WHITE:
                        shapeRenderer.setColor(Color.WHITE);
                        break;
                    case GREEN:
                        shapeRenderer.setColor(Color.GREEN);
                        break;
                    case PURPLE:
                        shapeRenderer.setColor(Color.PURPLE);
                        break;
                    case GOLD:
                        shapeRenderer.setColor(Color.GOLD);
                        break;
                    case ORANGE:
                        shapeRenderer.setColor(Color.ORANGE);
                        break;
                }
                if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BRICK) {

                    shapeRenderer.rect(entity.getComponent(BrickComponent.class).position.x - w/2,
                            entity.getComponent(BrickComponent.class).position.y - h/2,
                            w/2,h/2,
                            w, h,
                            1,1,
                            entity.getComponent(TransformComponent.class).rotation);

                }
            }
        }

        for (Entity entity: game.getGameThread().getEntityFactory().getTotalPowers()) {
            if (entity != null) {
                shapeRenderer.setColor(Color.CORAL);
                shapeRenderer.rect((entity.getComponent(PowerUpComponent.class).position.x ),
                        (entity.getComponent(PowerUpComponent.class).position.y),
                        entity.getComponent(PowerUpComponent.class).width,
                        entity.getComponent(PowerUpComponent.class).height
                );
//                shapeRenderer.triangle(entity.getComponent(PowerUpComponent.class).position.x - GameConstants.POWER_WIDTH,
//                        entity.getComponent(PowerUpComponent.class).position.y ,
//                        entity.getComponent(PowerUpComponent.class).position.x - GameConstants.POWER_WIDTH + GameConstants.POWER_WIDTH,
//                        entity.getComponent(PowerUpComponent.class).position.y + GameConstants.POWER_HEIGHT,
//                        entity.getComponent(PowerUpComponent.class).position.x + GameConstants.POWER_WIDTH,
//                        entity.getComponent(PowerUpComponent.class).position.y
//                );
            }
        }
        for (Entity entity: game.getGameThread().getEntityFactory().getTotalBalls()) {
            if (entity != null) {
                shapeRenderer.setColor(Color.LIGHT_GRAY);
                shapeRenderer.circle((entity.getComponent(BallComponent.class).position.x ),
                        (entity.getComponent(BallComponent.class).position.y),
                        entity.getComponent(BallComponent.class).radius / 2
                );
            }
        }



        shapeRenderer.end();
    }

    public void debugDrawableLine(){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(renderingSystem.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Entity entity: game.getGameThread().getEntityFactory().getTotalBricks()) {
            if (entity != null) {
                BrickComponent brickComponent = ComponentMapper.getFor(BrickComponent.class).get(entity);

                float w = brickComponent.width;
                float h = brickComponent.height;

                for (EntityColor color: EntityColor.values()){
                    if (brickComponent.getBrickColorContext().getBrickColor() == color){
                        shapeRenderer.setColor(Color.CYAN);
                    }
                }

                if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BRICK) {

                    shapeRenderer.rect(entity.getComponent(BrickComponent.class).position.x - w/2,
                            entity.getComponent(BrickComponent.class).position.y - h/2,
                            w/2,h/2,
                            w, h,
                            1,1,
                            entity.getComponent(TransformComponent.class).rotation);

                }
            }
        }

        for (Entity entity: game.getGameThread().getEntityFactory().getTotalPowers()) {
            if (entity != null) {
                shapeRenderer.setColor(Color.CYAN);
//                shapeRenderer.circle((entity.getComponent(PowerUpComponent.class).position.x ),
//                        (entity.getComponent(PowerUpComponent.class).position.y),
//                        entity.getComponent(PowerUpComponent.class).radius / 2
//                );
                shapeRenderer.triangle(entity.getComponent(PowerUpComponent.class).position.x - GameConstants.POWER_WIDTH,
                        entity.getComponent(PowerUpComponent.class).position.y ,
                        entity.getComponent(PowerUpComponent.class).position.x - GameConstants.POWER_WIDTH + GameConstants.POWER_WIDTH,
                        entity.getComponent(PowerUpComponent.class).position.y + GameConstants.POWER_HEIGHT,
                        entity.getComponent(PowerUpComponent.class).position.x + GameConstants.POWER_WIDTH,
                        entity.getComponent(PowerUpComponent.class).position.y )
                ;
            }
        }
        for (Entity entity: game.getGameThread().getEntityFactory().getTotalBalls()) {
            if (entity != null) {
                shapeRenderer.setColor(Color.CYAN);
                shapeRenderer.circle((entity.getComponent(BallComponent.class).position.x ),
                        (entity.getComponent(BallComponent.class).position.y),
                        entity.getComponent(BallComponent.class).radius / 2
                );
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        sheet.dispose();
    }
}
