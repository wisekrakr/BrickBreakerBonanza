package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.helpers.GameHelper;

import java.awt.*;
import java.util.List;

public class PlayerControlSystem extends IteratingSystem {

    private ComponentMapper<PlayerComponent> playerComponentMapper;
    private ComponentMapper<Box2dBodyComponent> box2dBodyComponentMapper;
    private AndroidGame game;
    private Controls controller;
    private OrthographicCamera camera;

    @SuppressWarnings("unchecked")
    public PlayerControlSystem(AndroidGame game, Controls controls, OrthographicCamera camera) {
        super(Family.all(PlayerComponent.class).get());
        this.game = game;
        controller = controls;
        this.camera = camera;

        playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
        box2dBodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent bodyComponent = box2dBodyComponentMapper.get(entity);
        PlayerComponent playerComponent = playerComponentMapper.get(entity);

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
            List<Entity>removeList = game.getGameThread().getEntityFactory().getTotalBricks();
            if (removeList.size() > 0) {
                for (Entity ent : removeList) {
                    if (ent != null) {
                        ent.getComponent(BrickComponent.class).setDestroy(true);
                    }
                }
            }
        }

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        if (controller.isRightMouseDown) {
            System.out.println("X= " + (mousePos.x / 2.5f) + " , Y= " + (mousePos.y / 2.5)); //todo remove

        }
        camera.unproject(mousePos);

        float speed = 1000000000f;

        float xVelocity = mousePos.x - bodyComponent.body.getPosition().x;
        float yVelocity = mousePos.y - bodyComponent.body.getPosition().y;

        if (!game.getGameThread().getEntityFactory().getTotalBalls().isEmpty()) {
            if (!playerComponent.hasBall) {
                if (controller.isLeftMouseDown) { // || Gdx.input.isTouched()
                    playerComponent.setHasBall(true);

                    float length = (float) Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
                    if (length != 0) {
                        xVelocity = xVelocity / length;
                        yVelocity = yVelocity / length;
                    }

                    game.getGameThread().getEntityFactory().getTotalBalls().get(0).getComponent(
                            Box2dBodyComponent.class).body.applyForceToCenter(
                            xVelocity * speed, yVelocity * speed, true
                    );


                } else if (controller.up) {
                    game.getGameThread().getEntityFactory().getTotalBalls().get(0).getComponent(
                            Box2dBodyComponent.class).body.applyForceToCenter(
                            xVelocity * speed, yVelocity * speed, true
                    );
                }
            }

            if (controller.speedUp) { //spacebar
                if (game.getGameThread().getTimeKeeper().gameClock - game.getGameThread().getTimeKeeper().speedUp > 10) {
                    game.getGameThread().getTimeKeeper().speedUp = game.getGameThread().getTimeKeeper().gameClock;

                    game.getGameThread().getEntityFactory().getTotalBalls().get(0).getComponent(
                            Box2dBodyComponent.class).body.applyForceToCenter(
                            bodyComponent.body.getPosition().x + GameHelper.randomDirection() * speed,
                            bodyComponent.body.getPosition().y + GameHelper.randomDirection() * speed,
                            true
                    );
                }

            }
        }else {
            System.out.println(getClass().toString()+  " no balls to play with");
        }
    }
}
