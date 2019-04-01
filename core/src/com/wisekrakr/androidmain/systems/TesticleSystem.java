package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.TesticleComponent;

public class TesticleSystem extends IteratingSystem implements SystemEntityContext {

    private AndroidGame game;

    public TesticleSystem(AndroidGame game) {
        super(Family.all(TesticleComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TesticleComponent testicleComponent = game.getGameThread().getComponentMapperSystem().getTesticleComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);



        outOfBounds(entity);
        bodyHandler(entity, bodyComponent);
    }

    @Override
    public void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        TesticleComponent testicleComponent = game.getGameThread().getComponentMapperSystem().getTesticleComponentMapper().get(entity);

        testicleComponent.setPosition(bodyComponent.body.getPosition());
        testicleComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        testicleComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);

    }

    @Override
    public void destroy(Entity entity) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        bodyComponent.isDead = true;
    }

    @Override
    public void outOfBounds(Entity entity) {
        TesticleComponent testicleComponent = game.getGameThread().getComponentMapperSystem().getTesticleComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (testicleComponent.getPosition().x + testicleComponent.getRadius()/2 > GameConstants.WORLD_WIDTH ||
                testicleComponent.getPosition().x - testicleComponent.getRadius()/2 < 0){
            bodyComponent.body.setLinearVelocity(-testicleComponent.getVelocityX(), testicleComponent.getVelocityY());
        }else if (testicleComponent.getPosition().y + testicleComponent.getRadius()/2 > GameConstants.WORLD_HEIGHT ||
                testicleComponent.getPosition().y - testicleComponent.getRadius()/2 < 0){
            bodyComponent.body.setLinearVelocity(testicleComponent.getVelocityX(), -testicleComponent.getVelocityY());
        }
    }
}
