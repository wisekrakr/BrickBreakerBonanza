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
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.helpers.GameHelper;

public class PlayerControlSystem extends IteratingSystem {

    private AndroidGame game;
    private Controls controller;
    private OrthographicCamera camera;

    @SuppressWarnings("unchecked")
    public PlayerControlSystem(AndroidGame game, Controls controls, OrthographicCamera camera) {
        super(Family.all(PlayerComponent.class).get());
        this.game = game;
        controller = controls;
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

//        movement(entity); //todo keyboard input if ever wanted

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        camera.unproject(mousePos);

        float angle = GameHelper.angleBetween(bodyComponent.body.getPosition(), new Vector2(mousePos.x, mousePos.y));
        bodyComponent.body.setTransform(bodyComponent.body.getPosition(), angle);

        if (controller.isLeftMouseDown || Gdx.input.isTouched()){
            bodyComponent.body.setLinearVelocity(
                    bodyComponent.body.getLinearVelocity().x + playerComponent.getSpeed() * MathUtils.cos(angle),
                    bodyComponent.body.getLinearVelocity().y + playerComponent.getSpeed() * MathUtils.sin(angle)
            );
            playerComponent.setMoving(true);
        }else if (controller.nextLevel) { //left alt
//            game.getGameThread().getTimeKeeper().setTime(0);
            game.changeScreen(AndroidGame.SUPERENDGAME);
        }

    }

    private void movement(Entity entity){
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(entity);

        float speed = playerComponent.getSpeed();

        if (controller != null) {
            if (controller.left) {
                bodyComponent.body.setLinearVelocity(-speed, 0);//a
                playerComponent.setMoving(true);
            }
            if (controller.right) {
                bodyComponent.body.setLinearVelocity(speed, 0); //d
                playerComponent.setMoving(true);
            }
            if (controller.down) {
                bodyComponent.body.setLinearVelocity(0, -speed); //s
                playerComponent.setMoving(true);
            }
            if (controller.up) {
                bodyComponent.body.setLinearVelocity(0, speed); //w
                playerComponent.setMoving(true);
            }

        }

    }
}
