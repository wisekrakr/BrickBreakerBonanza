package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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

    private ParticleEffect effect;

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

    public void drawEffects(float delta){
        for (Entity entity: game.getEngine().getEntities()){
            TypeComponent.Type type = entity.getComponent(TypeComponent.class).getType();
            if (type == TypeComponent.Type.BALL) {
                ParticleEffectComponent effectComponent = game.getEngine().createComponent(ParticleEffectComponent.class);
                effectComponent.effectType = ParticleEffectComponent.ParticleEffectType.EFFECT_TYPE;
                effectComponent.position.set(
                        entity.getComponent(Box2dBodyComponent.class).body.getPosition().x,
                        entity.getComponent(Box2dBodyComponent.class).body.getPosition().y
                );
                entity.add(effectComponent);
            }
        }
    }

    public void draw(float delta){
        stateTime += delta;

        spriteBatch.setProjectionMatrix(renderingSystem.getCamera().combined);

        spriteBatch.begin();
//        for (Entity entity: game.getEngine().getEntities()){
//            TypeComponent.Type type = entity.getComponent(TypeComponent.class).getType();
//
//            switch (type){
//                case BALL:
//                    entityVisuals.drawObjectViaAtlas(entity,
//                            "images/breakout/breakout.atlas",
//                            "58-Breakout-Tiles",
//                            entity.getComponent(BallComponent.class).radius,
//                            entity.getComponent(BallComponent.class).radius
//                    );
//                    break;
//
//                case PLAYER:
////                    TextureRegion[][] tmp = TextureRegion.split(sheet,
////                            (int) entity.getComponent(PlayerComponent.class).width,
////                            (int) entity.getComponent(PlayerComponent.class).height
////                    );
////                    TextureRegion[] frames = new TextureRegion[6 * 5];
////                    int index = 0;
////                    for (int i = 0; i < 5; i++) {
////                        for (int j = 0; j < 6; j++) {
////                            frames[index++] = tmp[i][j];
////                        }
////                    }
////
////                    animation = new Animation<TextureRegion>(0.05f, frames);
////                    TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
////                    spriteBatch.draw(currentFrame,
////                            entity.getComponent(Box2dBodyComponent.class).body.getPosition().x - entity.getComponent(PlayerComponent.class).width/2,
////                            entity.getComponent(Box2dBodyComponent.class).body.getPosition().y - entity.getComponent(PlayerComponent.class).height/2
////                    );
//
//
//                    entityVisuals.drawObjectViaAtlas(entity,
//                            "images/breakout/breakout.atlas",
//                            "50-Breakout-Tiles",
//                            entity.getComponent(PlayerComponent.class).radius,
//                            entity.getComponent(PlayerComponent.class).radius
//                    );
//                    break;
//                case OBSTACLE:
//                    entityVisuals.drawObjectViaAtlas(entity,
//                            "images/breakout/breakout.atlas",
//                            "29-Breakout-Tiles",
//                            entity.getComponent(ObstacleComponent.class).width,
//                            entity.getComponent(ObstacleComponent.class).height
//                    );
//                    break;
//                case POWER:
//                    entityVisuals.visualizePower(entity);
//                    break;
//
//            }
//
//
//        }
        spriteBatch.end();
    }

    public void debugDrawableFilled(){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(renderingSystem.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity entity: game.getGameThread().getEntityFactory().getGameObjects()) {
            GameObjectComponent gameObjectComponent = ComponentMapper.getFor(GameObjectComponent.class).get(entity);
            TypeComponent.Type type = ComponentMapper.getFor(TypeComponent.class).get(entity).getType();
            if (type == TypeComponent.Type.BALL) {
                BallComponent ballComponent = ComponentMapper.getFor(BallComponent.class).get(entity);

                float radius = gameObjectComponent.radius;
                shapeRenderer.setColor(Color.RED);
                switch (ballComponent.getBallColorContext().getBallColor()){
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
                shapeRenderer.circle((entity.getComponent(GameObjectComponent.class).position.x ),
                        (entity.getComponent(GameObjectComponent.class).position.y),
                        radius/2
                );
            }else if (type == TypeComponent.Type.POWER){
                shapeRenderer.setColor(Color.CORAL);
                shapeRenderer.rect((entity.getComponent(GameObjectComponent.class).position.x ),
                        (entity.getComponent(GameObjectComponent.class).position.y),
                        entity.getComponent(GameObjectComponent.class).width,
                        entity.getComponent(GameObjectComponent.class).height
                );
            }
        }



//                shapeRenderer.triangle(entity.getComponent(PowerUpComponent.class).position.x - GameConstants.POWER_WIDTH,
//                        entity.getComponent(PowerUpComponent.class).position.y ,
//                        entity.getComponent(PowerUpComponent.class).position.x - GameConstants.POWER_WIDTH + GameConstants.POWER_WIDTH,
//                        entity.getComponent(PowerUpComponent.class).position.y + GameConstants.POWER_HEIGHT,
//                        entity.getComponent(PowerUpComponent.class).position.x + GameConstants.POWER_WIDTH,
//                        entity.getComponent(PowerUpComponent.class).position.y
//                );



        shapeRenderer.end();
    }

    public void debugDrawableLine(float delta){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(renderingSystem.getCamera().combined);

        stateTime += delta;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (Entity entity: game.getEngine().getEntities()) {
            GameObjectComponent gameObjectComponent = ComponentMapper.getFor(GameObjectComponent.class).get(entity);
            TypeComponent typeComponent = ComponentMapper.getFor(TypeComponent.class).get(entity);
            shapeRenderer.setColor(Color.CYAN);
            switch (typeComponent.getType()){
                case PLAYER:
                    Vector2 position = gameObjectComponent.position;
                    float angle = entity.getComponent(Box2dBodyComponent.class).body.getAngle();
                    float radius = gameObjectComponent.radius;

                    shapeRenderer.circle(position.x,position.y, radius);

                    shapeRenderer.line(position.x - radius * MathUtils.cos(angle),position.y - radius * MathUtils.sin(angle),
                            position.x - 20f * MathUtils.cos(angle), position.y - 20f * MathUtils.sin(angle)
                    );
                    break;
                case BALL:
                    shapeRenderer.circle(gameObjectComponent.position.x ,
                            gameObjectComponent.position.y,
                            gameObjectComponent.radius/2
                    );
                    break;
                case OBSTACLE:
                    break;
                case SCENERY:
                    break;
                case POWER:
                    shapeRenderer.triangle(gameObjectComponent.position.x - GameConstants.POWER_WIDTH,
                            gameObjectComponent.position.y ,
                            gameObjectComponent.position.x - GameConstants.POWER_WIDTH + GameConstants.POWER_WIDTH,
                            gameObjectComponent.position.y + GameConstants.POWER_HEIGHT,
                            gameObjectComponent.position.x + GameConstants.POWER_WIDTH,
                            gameObjectComponent.position.y
                    );
                    break;
                    default:
                        System.out.println("No entity to draw line around");
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
