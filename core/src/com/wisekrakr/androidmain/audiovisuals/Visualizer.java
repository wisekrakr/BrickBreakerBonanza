package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    public Visualizer(AndroidGame game) {
        this.game = game;

        spriteBatch = new SpriteBatch();

        addSystems();
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

    public void draw(){

        spriteBatch.setProjectionMatrix(renderingSystem.getCamera().combined);

        spriteBatch.begin();

        for (Entity entity: game.getEngine().getEntities()){
            TypeComponent.Type type = entity.getComponent(TypeComponent.class).getType();

            switch (type){
                case BALL:
                    entityVisuals.visualizeColoredEntity(entity, type);
                    break;
                case BRICK:
                    entityVisuals.visualizeColoredEntity(entity, type);
                    break;

                case PLAYER:
                    float x = entity.getComponent(TransformComponent.class).position.x;

                    if (x > entity.getComponent(TransformComponent.class).position.x) {
                        entityVisuals.drawObjectViaAtlas(entity,
                                "images/game/game.atlas",
                                "dude_left",
                                GameConstants.WORLD_WIDTH / 10,
                                GameConstants.BALL_RADIUS / 3);
                    }else if (x < entity.getComponent(TransformComponent.class).position.x){
                        entityVisuals.drawObjectViaAtlas(entity,
                                "images/game/game.atlas",
                                "dude_right",
                                GameConstants.WORLD_WIDTH / 10,
                                GameConstants.BALL_RADIUS / 3);
                    }else {
                        entityVisuals.drawObjectViaAtlas(entity,
                                "images/game/game.atlas",
                                "dude_front",
                                GameConstants.WORLD_WIDTH / 10,
                                GameConstants.BALL_RADIUS / 3);
                    }

                    break;
                case OBSTACLE:
                    entityVisuals.drawObjectViaAtlas(entity,
                            "images/game/game.atlas",
                            "woodfloor",
                            entity.getComponent(ObstacleComponent.class).width,
                            entity.getComponent(ObstacleComponent.class).height);
                    break;
                case POWER:
                    entityVisuals.visualizePower(entity);
                    break;

            }


        }
        spriteBatch.end();
    }

    public void debugDrawable(){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(renderingSystem.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Entity entity: game.getGameThread().getEntityFactory().getTotalBricks()) {
            if (entity != null) {
                BrickComponent brickComponent = ComponentMapper.getFor(BrickComponent.class).get(entity);

                float w = brickComponent.width;
                float h = brickComponent.height;

                switch (brickComponent.getBrickColor()){
                    case RED:
                        shapeRenderer.setColor(Color.RED);
                        break;
                    case BLUE:
                        shapeRenderer.setColor(Color.BLUE);
                        break;
                    case CYAN:
                        shapeRenderer.setColor(Color.CYAN);
                        break;
                    case GREEN:
                        shapeRenderer.setColor(Color.GREEN);
                        break;
                    case PURPLE:
                        shapeRenderer.setColor(Color.PURPLE);
                        break;
                    case YELLOW:
                        shapeRenderer.setColor(Color.YELLOW);
                        break;
                    case ORANGE:
                        shapeRenderer.setColor(Color.ORANGE);
                        break;
                }

                if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BALL) {

                    shapeRenderer.circle((entity.getComponent(BallComponent.class).position.x ),
                            (entity.getComponent(BallComponent.class).position.y),
                            w / 2
                    );

                }else if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BRICK) {

                    shapeRenderer.rect(entity.getComponent(BrickComponent.class).position.x - w/2,
                            entity.getComponent(BrickComponent.class).position.y - h/2,
                            w/2,h/2,
                            w, h,
                            1,1,
                            entity.getComponent(TransformComponent.class).rotation);

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
