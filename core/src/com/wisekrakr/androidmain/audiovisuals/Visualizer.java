package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
    private float drawTime;
    private float clearTime;

    private ParticleEffect effect;

    public Visualizer(AndroidGame game) {
        this.game = game;

        spriteBatch = new SpriteBatch();

        addSystems();

        sheet = new Texture(Gdx.files.internal("images/player/player.png"));

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

    public void backgroundColorClear(){
//        if (clearTime == 0){
//            clearTime = game.getGameThread().getTimeKeeper().gameClock;
//        }
//        if (game.getGameThread().getTimeKeeper().gameClock - clearTime > 10){
//            EntityColor color = EntityColorHelper.randomEntityColor();
//            switch (color){
//                case RED:
//                    Gdx.gl.glClearColor(Color.RED.r,Color.RED.g,Color.RED.b,Color.RED.a);
//                    break;
//                case BLUE:
//                    Gdx.gl.glClearColor(Color.BLUE.r,Color.BLUE.g,Color.BLUE.b,Color.BLUE.a);
//                    break;
//                case GOLD:
//                    Gdx.gl.glClearColor(Color.GOLD.r,Color.GOLD.g,Color.GOLD.b,Color.GOLD.a);
//                    break;
//                case GREEN:
//                    Gdx.gl.glClearColor(Color.GREEN.r,Color.GREEN.g,Color.GREEN.b,Color.GREEN.a);
//                    break;
//                case PURPLE:
//                    Gdx.gl.glClearColor(Color.PURPLE.r,Color.PURPLE.g,Color.PURPLE.b,Color.PURPLE.a);
//                    break;
//
//            }
//            clearTime = game.getGameThread().getTimeKeeper().gameClock;
//        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void drawEffects(float delta){
        for (Entity entity: game.getEngine().getEntities()){
            TypeComponent.Type type = entity.getComponent(TypeComponent.class).getType();
            if (type == TypeComponent.Type.ENEMY) {
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
        drawTime += delta;

        spriteBatch.setProjectionMatrix(renderingSystem.getCamera().combined);

        spriteBatch.begin();
        for (Entity entity: game.getEngine().getEntities()){
            TypeComponent.Type type = entity.getComponent(TypeComponent.class).getType();

            switch (type){
                case ENEMY:
                    entityVisuals.visualizeColoredEntity(entity);
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
//                    TextureRegion currentFrame = animation.getKeyFrame(drawTime, true);
//                    spriteBatch.draw(currentFrame,
//                            entity.getComponent(Box2dBodyComponent.class).body.getPosition().x - entity.getComponent(PlayerComponent.class).width/2,
//                            entity.getComponent(Box2dBodyComponent.class).body.getPosition().y - entity.getComponent(PlayerComponent.class).height/2
//                    );


                    entityVisuals.visualizeColoredEntity(entity);
                    break;
                case OBSTACLE:
                    entityVisuals.visualizeColoredEntity(entity);
                    break;
                case POWER:
                    entityVisuals.visualizeColoredEntity(entity);
                    break;
                case PENIS:
                    entityVisuals.visualizeColoredEntity(entity);
                    break;
            }
        }
        spriteBatch.end();
    }


    public void debugDrawableFilled(){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(renderingSystem.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity entity: game.getEngine().getEntities()) {

            if (entity != null) {
                TypeComponent.Type type = game.getGameThread().getComponentMapperSystem().getTypeComponentMapper().get(entity).getType();
                if (type == TypeComponent.Type.ENEMY) {

                    EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

                    float radius = enemyComponent.getRadius();
                    shapeRenderer.setColor(Color.RED);
                    switch (enemyComponent.getEnemyColorContext().getEnemyColor()) {
                        case RED:
                            shapeRenderer.setColor(Color.RED);
                            break;
                        case BLUE:
                            shapeRenderer.setColor(Color.BLUE);
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
                    }
                    shapeRenderer.circle((enemyComponent.getPosition().x),
                            (enemyComponent.getPosition().y),
                            radius / 2
                    );
                } else if (type == TypeComponent.Type.POWER) {
                    PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

                    shapeRenderer.setColor(Color.CORAL);
                    shapeRenderer.triangle(
                            powerUpComponent.getPosition().x - powerUpComponent.getWidth(),
                            powerUpComponent.getPosition().y ,
                            powerUpComponent.getPosition().x - powerUpComponent.getWidth()+powerUpComponent.getWidth(),
                            powerUpComponent.getPosition().y + powerUpComponent.getHeight(),
                            powerUpComponent.getPosition().x + powerUpComponent.getWidth(),
                            powerUpComponent.getPosition().y
                    );
                } else if (type == TypeComponent.Type.OBSTACLE){
                    ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);

                    shapeRenderer.setColor(Color.BROWN);
                    shapeRenderer.rect(
                            obstacleComponent.getPosition().x - obstacleComponent.getWidth()/2,
                            obstacleComponent.getPosition().y - obstacleComponent.getHeight()/2,
                            obstacleComponent.getWidth(),
                            obstacleComponent.getHeight()
                    );
                } else if (type == TypeComponent.Type.SCENERY){
                    WallComponent wallComponent = game.getGameThread().getComponentMapperSystem().getWallComponentMapper().get(entity);

                    shapeRenderer.setColor(Color.FIREBRICK);
                    shapeRenderer.rect(
                            wallComponent.getPosition().x,
                            wallComponent.getPosition().y,
                            wallComponent.getWidth(),
                            wallComponent.getHeight()
                    );
                }
            }
        }

        shapeRenderer.end();
    }

    public void debugDrawableLine(float delta){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(renderingSystem.getCamera().combined);

        drawTime += delta;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (Entity entity: game.getEngine().getEntities()) {
            if (entity != null) {
                TypeComponent typeComponent = ComponentMapper.getFor(TypeComponent.class).get(entity);
                shapeRenderer.setColor(Color.CYAN);
                switch (typeComponent.getType()) {
                    case PLAYER:
                        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

                        Vector2 position = playerComponent.getPosition();
                        float angle = playerComponent.getDirection();
                        float radius = playerComponent.getRadius()/2;

                        shapeRenderer.circle(position.x, position.y, radius);

                        shapeRenderer.line(
                                position.x - radius * MathUtils.cos(angle), position.y - radius * MathUtils.sin(angle),
                                position.x - 20f * MathUtils.cos(angle), position.y - 20f * MathUtils.sin(angle)
                        );
                        break;
                    case ENEMY:
                        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

                        shapeRenderer.circle(
                                enemyComponent.getPosition().x,
                                enemyComponent.getPosition().y,
                                enemyComponent.getRadius() / 2
                        );
                        break;
                    case OBSTACLE:
                        ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);

                        shapeRenderer.rect(
                                obstacleComponent.getPosition().x - obstacleComponent.getWidth()/2,
                                obstacleComponent.getPosition().y - obstacleComponent.getHeight()/2,
                                obstacleComponent.getWidth(),
                                obstacleComponent.getHeight()
                        );
                        break;
                    case POWER:
                        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

//                        shapeRenderer.triangle(
//                                powerUpComponent.getPosition().x - powerUpComponent.getWidth(),
//                                powerUpComponent.getPosition().y,
//                                powerUpComponent.getPosition().x - powerUpComponent.getWidth() + powerUpComponent.getWidth(),
//                                powerUpComponent.getPosition().y + powerUpComponent.getHeight(),
//                                powerUpComponent.getPosition().x + powerUpComponent.getWidth(),
//                                powerUpComponent.getPosition().y
//                        );
                        break;
                    default:
//                        System.out.println("No entity to draw line around");
                }
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
