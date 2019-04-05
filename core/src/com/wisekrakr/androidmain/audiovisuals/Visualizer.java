package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.systems.PhysicsDebugSystem;
import com.wisekrakr.androidmain.systems.RenderingSystem;

public class Visualizer implements Disposable {

    private BricksGame game;
    private RenderingSystem renderingSystem;
    private EntityVisuals entityVisuals;

    private final SpriteBatch spriteBatch;

    private Animation<TextureRegion>animation;

    private ParticleEffect effect;

    public Visualizer(BricksGame game) {
        this.game = game;

        spriteBatch = new SpriteBatch();

        addSystems();
    }

    private void addSystems(){
        renderingSystem = new RenderingSystem(spriteBatch);

        game.getEngine().addSystem(renderingSystem);
//        game.getEngine().addSystem(new PhysicsDebugSystem(game.getGameThread().getEntityFactory().world, renderingSystem.getCamera()));

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

    public void draw(){

        spriteBatch.setProjectionMatrix(renderingSystem.getCamera().combined);

        spriteBatch.begin();

        for (Entity entity: game.getEngine().getEntities()){
            entityVisuals.visualizeEntity(entity);
        }


        spriteBatch.end();
    }

    public void debugDrawableFilled(){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(renderingSystem.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity entity: game.getEngine().getEntities()) {

            TypeComponent.Type type = ComponentMapper.getFor(TypeComponent.class).get(entity).getType();
            if (type == TypeComponent.Type.BRICK) {
                BrickComponent brickComponent = game.getGameThread().getComponentMapperSystem().getBrickComponentMapper().get(entity);

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
                shapeRenderer.rect((brickComponent.getPosition().x ),
                        (brickComponent.getPosition().y),
                        brickComponent.getWidth()/2,
                        brickComponent.getHeight()/2
                );
            }else if (type == TypeComponent.Type.POWER){
                shapeRenderer.setColor(Color.CORAL);
                shapeRenderer.rect((entity.getComponent(PowerUpComponent.class).getPosition().x ),
                        (entity.getComponent(PowerUpComponent.class).getPosition().y),
                        entity.getComponent(PowerUpComponent.class).getWidth(),
                        entity.getComponent(PowerUpComponent.class).getHeight()
                );
            }
        }
        shapeRenderer.end();
    }

    public void debugDrawableLine(){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(renderingSystem.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (Entity entity: game.getEngine().getEntities()) {

            TypeComponent typeComponent = ComponentMapper.getFor(TypeComponent.class).get(entity);
            shapeRenderer.setColor(Color.CYAN);
            switch (typeComponent.getType()){
                case PLAYER:
                    PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

                    Vector2 position = playerComponent.getPosition();
                    float angle = playerComponent.getShootDirection();
                    float width = playerComponent.getWidth();
                    float height = playerComponent.getHeight();

                    shapeRenderer.rect(position.x - width/2 ,position.y - height/2, width, height);

//                    shapeRenderer.line(position.x * MathUtils.cos(angle),position.y + height * MathUtils.sin(angle),
//                            position.x * MathUtils.cos(angle), position.y + 20f * MathUtils.sin(angle)
//                    );
                    break;
                case BALL:
                    shapeRenderer.circle(entity.getComponent(BallComponent.class).getPosition().x ,
                            entity.getComponent(BallComponent.class).getPosition().y,
                            entity.getComponent(BallComponent.class).getRadius()/2
                    );
                    break;
                case OBSTACLE:
                    break;
                case SCENERY:
                    break;
                case POWER:
                    PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

                    shapeRenderer.rect(powerUpComponent.getPosition().x - powerUpComponent.getWidth()/2,
                            powerUpComponent.getPosition().y - powerUpComponent.getHeight()/2,
                            powerUpComponent.getWidth(), powerUpComponent.getHeight()
                    );
                    break;
                case BRICK:
                    BrickComponent brickComponent = game.getGameThread().getComponentMapperSystem().getBrickComponentMapper().get(entity);

                    shapeRenderer.rect(brickComponent.getPosition().x - brickComponent.getWidth()/2,
                            brickComponent.getPosition().y - brickComponent.getHeight()/2,
                            brickComponent.getWidth(), brickComponent.getHeight()
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
    }
}
