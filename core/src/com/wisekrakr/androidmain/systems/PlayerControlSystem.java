package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.wisekrakr.androidmain.BodyFactory;
import com.wisekrakr.androidmain.GameUtilities;
import com.wisekrakr.androidmain.LevelFactory;
import com.wisekrakr.androidmain.components.BallComponent;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.PlayerComponent;
import com.wisekrakr.androidmain.components.StateComponent;
import com.wisekrakr.androidmain.controls.Controls;

import java.util.ArrayList;
import java.util.List;

import static com.wisekrakr.androidmain.GameUtilities.BALL_RADIUS;

public class PlayerControlSystem extends IteratingSystem {

    private ComponentMapper<BallComponent>ballComponentMapper;
    private ComponentMapper<PlayerComponent> playerComponentMapper;
    private ComponentMapper<Box2dBodyComponent> box2dBodyComponentMapper;
    private ComponentMapper<StateComponent> stateComponentMapper;
    private Controls controller;
    private LevelFactory levelFactory;
    private OrthographicCamera camera;


    @SuppressWarnings("unchecked")
    public PlayerControlSystem(Controls controls, LevelFactory levelFactory, OrthographicCamera camera) {
        super(Family.all(PlayerComponent.class).get());
        controller = controls;
        this.levelFactory = levelFactory;
        this.camera = camera;

        playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
        box2dBodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        stateComponentMapper = ComponentMapper.getFor(StateComponent.class);
        ballComponentMapper = ComponentMapper.getFor(BallComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent b2body = box2dBodyComponentMapper.get(entity);
        StateComponent state = stateComponentMapper.get(entity);
        PlayerComponent playerComponent = playerComponentMapper.get(entity);

        if (playerComponent.isDead){
            b2body.isDead = true;
            System.out.println("Player died");
        }else {

            if (b2body.body.getLinearVelocity().y == 0) {
                if (b2body.body.getLinearVelocity().x != 0) {
                    state.set(StateComponent.STATE_MOVING);
                }
            }

            if (controller.left) {
                b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -5f, 0.2f), b2body.body.getLinearVelocity().y);
            }
            if (controller.right) {
                b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 5f, 0.2f), b2body.body.getLinearVelocity().y);
            }

            if (!controller.left && !controller.right) {
                b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 0.1f), b2body.body.getLinearVelocity().y);
            }

//            if (controller.up &&
//                    (state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING)) {
//                //b2body.body.applyForceToCenter(0, 3000,true);
//                b2body.body.applyLinearImpulse(0, 10f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
//                state.set(StateComponent.STATE_JUMPING);
//            }



            if (controller.isLeftMouseDown || Gdx.input.isTouched()) {

                playerComponent.timeSinceLastShot += deltaTime;
                if (playerComponent.timeSinceLastShot > playerComponent.shootDelay) {

                    Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(mousePos); // convert position from screen to box2d world position
                    float speed = 1000f;  // set the speed of the ball
                    float shooterX = b2body.body.getPosition().x; // get player location
                    float shooterY = b2body.body.getPosition().y; // get player location
                    float xVelocity = mousePos.x - shooterX; // get distance from shooter to target on x plain
                    float yVelocity = mousePos.y - shooterY; // get distance from shooter to target on y plain
                    float length = (float) Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity); // get distance to target direct
                    if (length != 0) {
                        xVelocity = xVelocity / length;  // get required x velocity to aim at target
                        yVelocity = yVelocity / length;  // get required y velocity to aim at target
                    }

                    Entity ball = levelFactory.createBall(GameUtilities.BALL_RADIUS,
                            BodyFactory.Material.RUBBER,
                            b2body.body.getPosition().x, b2body.body.getPosition().y + GameUtilities.BALL_RADIUS,
                            xVelocity * speed, yVelocity * speed);

                    playerComponent.balls.add(ball);
                    if (ballComponentMapper.get(ball).destroyed){
                        playerComponent.balls.remove(ball);
                    }
                    playerComponent.timeSinceLastShot = 0f;
               }
            }
        }

    }
}