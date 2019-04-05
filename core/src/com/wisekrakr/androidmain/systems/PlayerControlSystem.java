package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.helpers.GameHelper;

import java.util.List;

public class PlayerControlSystem extends IteratingSystem {

    private BricksGame game;
    private Controls controller;
    private OrthographicCamera camera;

    @SuppressWarnings("unchecked")
    public PlayerControlSystem(BricksGame game, Controls controls, OrthographicCamera camera) {
        super(Family.all(PlayerComponent.class).get());
        this.game = game;
        controller = controls;
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

        movement(entity);

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        camera.unproject(mousePos);

        float angle = GameHelper.angleBetween(bodyComponent.body.getPosition(), new Vector2(mousePos.x, mousePos.y));
        playerComponent.setShootDirection(angle);

        if (!playerComponent.isHasBall()) {
            if (controller.isLeftMouseDown || Gdx.input.isTouched()) {

                BallComponent ballComponent = game.getGameThread().getComponentMapperSystem().getBallComponentMapper().get(getBall());
                Box2dBodyComponent box2dBodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(getBall());
                getBall().getComponent(
                        Box2dBodyComponent.class).body.applyForceToCenter(
                        bodyComponent.body.getLinearVelocity().x + ballComponent.getSpeed() * MathUtils.cos(angle),
                        bodyComponent.body.getLinearVelocity().y + ballComponent.getSpeed() * MathUtils.sin(angle),
                        true
                );
                box2dBodyComponent.body.setTransform(box2dBodyComponent.body.getPosition(), angle);
                ballComponent.setDirection(angle);
                playerComponent.setHasBall(true);
            }
        }
        if (controller.speedUp) { //spacebar
            if (game.getGameThread().getTimeKeeper().gameClock - game.getGameThread().getTimeKeeper().speedUp > 10) {
                game.getGameThread().getTimeKeeper().speedUp = game.getGameThread().getTimeKeeper().gameClock;

                BallComponent ballComponent = game.getGameThread().getComponentMapperSystem().getBallComponentMapper().get(getBall());
                Box2dBodyComponent box2dBodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(getBall());

                getBall().getComponent(
                        Box2dBodyComponent.class).body.applyForceToCenter(
                        box2dBodyComponent.body.getLinearVelocity().x + ballComponent.getSpeed() * MathUtils.cos(ballComponent.getDirection()),
                        box2dBodyComponent.body.getLinearVelocity().y + ballComponent.getSpeed() * MathUtils.sin(ballComponent.getDirection()),
                        true
                );
            }
        }
    }

    private Entity getBall(){
        Entity ball = null;
        for (Entity ent: game.getEngine().getEntities()) {
            if (ent.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BALL) {
                ball = ent;
            }
        }
        return ball;
    }

    private void movement(Entity entity){
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (controller != null) {
            if (controller.left) {
                bodyComponent.body.setLinearVelocity(-500f, 0);//a
            }
            if (controller.right) {
                bodyComponent.body.setLinearVelocity(500f, 0); //d
            }
            if (controller.down) {
                bodyComponent.body.setLinearVelocity(0, 0); //s
            }
            if (controller.nextLevel){ //left alt

                for (Entity ent : game.getEngine().getEntities()) {
                    if (ent.getComponent(TypeComponent.class).getType() == TypeComponent.Type.BRICK) {
                        ent.getComponent(BrickComponent.class).setDestroy(true);
                    }
                }

            }
        }

    }
}
