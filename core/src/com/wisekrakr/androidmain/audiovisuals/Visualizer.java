package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
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
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.systems.PhysicsDebugSystem;
import com.wisekrakr.androidmain.systems.RenderingSystem;

import java.util.ArrayList;

public class Visualizer implements Disposable {

    private AndroidGame game;
    private RenderingSystem renderingSystem;
    private EntityVisuals entityVisuals;

    private final SpriteBatch spriteBatch;

    private Animation<TextureRegion>animation;

    private float drawTime;
    private float clearTime;

    private ParticleEffect effect;
    private ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>();
    private TextureRegion backgroundRegion;

    public Visualizer(AndroidGame game) {
        this.game = game;

        spriteBatch = new SpriteBatch();

        addSystems();
    }

    private void addSystems(){
        renderingSystem = new RenderingSystem(spriteBatch);

        game.getEngine().addSystem(renderingSystem);
        game.getEngine().addSystem(new PhysicsDebugSystem(game.getGameThread().getEntityFactory().world, renderingSystem.getCamera()));

        backgroundArt();

        entityVisuals = new EntityVisuals(game, spriteBatch);

    }


    public RenderingSystem getRenderingSystem() {
        return renderingSystem;
    }

    private void backgroundArt(){

        TextureRegion regionOne = new TextureRegion(new Texture("images/background/penisPattern01.jpg"));
        TextureRegion regionTwo = new TextureRegion(new Texture("images/background/penisPattern02.jpg"));
        TextureRegion regionThree = new TextureRegion(new Texture("images/background/penisPattern03.png"));
        TextureRegion regionFour = new TextureRegion(new Texture("images/background/penisPattern04.jpg"));
        TextureRegion regionFive = new TextureRegion(new Texture("images/background/penisPattern05.jpg"));
        TextureRegion regionSix = new TextureRegion(new Texture("images/background/penisPattern06.jpeg"));
        TextureRegion regionSeven = new TextureRegion(new Texture("images/background/penisPattern07.jpg"));

        regions.add(regionOne);
        regions.add(regionTwo);
        regions.add(regionThree);
        regions.add(regionFour);
        regions.add(regionFive);
        regions.add(regionSix);
        regions.add(regionSeven);

        backgroundRegion = regions.get(GameHelper.randomGenerator.nextInt(regions.size()));
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

//        spriteBatch.draw(backgroundRegion,0,0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT); todo add background

        for (Entity entity: game.getEngine().getEntities()){

            if (!game.getGamePreferences().isCensored()) {
                entityVisuals.visualizeEntity(entity);
            }else {
                entityVisuals.visualizeCensoredEntity(entity);
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

                    float width = enemyComponent.getWidth();
                    float height = enemyComponent.getHeight();
                    shapeRenderer.setColor(Color.RED);
                    switch (enemyComponent.getEntityStyleContext().getEntityStyle()) {
                        case WHITE_REDHAIR:
                            shapeRenderer.setColor(Color.RED);
                            break;
                        case WHITE_BLONDHAIR:
                            shapeRenderer.setColor(Color.BLUE);
                            break;
                        case WHITE_BROWNHAIR:
                            shapeRenderer.setColor(Color.GREEN);
                            break;
                        case BLACK_BLACKHAIR:
                            shapeRenderer.setColor(Color.PURPLE);
                            break;
                        case WHITE_BLACKHAIR:
                            shapeRenderer.setColor(Color.GOLD);
                            break;
                    }
                    shapeRenderer.rect(enemyComponent.getPosition().x, enemyComponent.getPosition().y, width,height);

                } else if (type == TypeComponent.Type.POWER) {
                    PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

                    shapeRenderer.setColor(Color.CORAL);
                    shapeRenderer.rect(
                            powerUpComponent.getPosition().x - powerUpComponent.getWidth()/2,
                            powerUpComponent.getPosition().y - powerUpComponent.getHeight()/2,
                            powerUpComponent.getWidth(), powerUpComponent.getHeight()
                    );
                } else if (type == TypeComponent.Type.OBSTACLE){
                    ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);

                    shapeRenderer.setColor(Color.BROWN);
                    shapeRenderer.rect(
                            obstacleComponent.getPosition().x - obstacleComponent.getWidth()/2,
                            obstacleComponent.getPosition().y - obstacleComponent.getHeight()/2,
                            obstacleComponent.getWidth(), obstacleComponent.getHeight()
                    );
                } else if (type == TypeComponent.Type.SCENERY){
                    WallComponent wallComponent = game.getGameThread().getComponentMapperSystem().getWallComponentMapper().get(entity);

                    shapeRenderer.setColor(Color.FIREBRICK);
                    shapeRenderer.rect(wallComponent.getPosition().x, wallComponent.getPosition().y, wallComponent.getWidth(),wallComponent.getHeight());
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
                        float width = playerComponent.getWidth();
                        float height = playerComponent.getHeight();

                        shapeRenderer.rect(position.x - width/2, position.y - height/2, width, height);

                        shapeRenderer.line(
                                position.x - width * MathUtils.cos(angle), position.y - height * MathUtils.sin(angle),
                                position.x - 20f * MathUtils.cos(angle), position.y - 20f * MathUtils.sin(angle)
                        );
                        break;
                    case ENEMY:
                        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

                        float w = enemyComponent.getWidth();
                        float h = enemyComponent.getHeight();

                        shapeRenderer.rect(enemyComponent.getPosition().x - w/2, enemyComponent.getPosition().y - h/2,
                                enemyComponent.getWidth(),enemyComponent.getHeight()
                        );
                        break;
                    case OBSTACLE:
                        ObstacleComponent obstacleComponent = game.getGameThread().getComponentMapperSystem().getObstacleComponentMapper().get(entity);

                        shapeRenderer.rect(
                                obstacleComponent.getPosition().x - obstacleComponent.getWidth()/2,
                                obstacleComponent.getPosition().y - obstacleComponent.getHeight()/2,
                                obstacleComponent.getWidth(), obstacleComponent.getHeight()
                        );
                        break;
                    case POWER:
                        PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

                        shapeRenderer.rect(
                                powerUpComponent.getPosition().x - powerUpComponent.getWidth()/2,
                                powerUpComponent.getPosition().y - powerUpComponent.getHeight()/2,
                                powerUpComponent.getWidth(), powerUpComponent.getHeight()
                        );
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
    }
}
