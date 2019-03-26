package com.wisekrakr.androidmain.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.PhysicalObjectContactListener;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import com.wisekrakr.androidmain.components.EntityColor;
import com.wisekrakr.androidmain.components.PenisComponent;
import com.wisekrakr.androidmain.helpers.ComponentHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;

import static com.wisekrakr.androidmain.components.TypeComponent.Type.*;


/**
 * Class with manually made entities. Connect components to the appropriate entity. (see ComponentHelper)
 */
public class EntityFactory {

    private BodyFactory bodyFactory;
    public World world;
    private AndroidGame game;
    private PooledEngine engine;
    /**
     * @param game Class that extends Game
     * @param pooledEngine Game engine. All entities are placed in here.
     */
    public EntityFactory(AndroidGame game, PooledEngine pooledEngine){
        this.game = game;
        this.engine = pooledEngine;

        world = new World(new Vector2(0,0), true);
        world.setContactListener(new PhysicalObjectContactListener());

        bodyFactory = BodyFactory.getBodyFactoryInstance(world);
    }

    /**FOR ALL ENTITIES CREATED BELOW:
     *
     * @param x placement on x-axis
     * @param y placement on y-axis
     * @param velocityX velocity on x-axis
     * @param velocityY velocity on y-axis
     * @param width width of the body
     * @param height height of the body
     * @param material friction/restitution etc.
     * @param bodyType static/dynamic/kinematic
     */


    public void createObstacle(float x, float y, float velocityX, float velocityY, float width, float height, BodyFactory.Material material, BodyDef.BodyType bodyType){
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, OBSTACLE);
        ComponentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, 0);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, material, bodyType);
        bodyComponent.body.setLinearVelocity(velocityX, velocityY);

        ComponentHelper.getComponentInitializer().obstacleComponent(
                engine,
                entity,
                x, y,
                velocityX, velocityY,
                width, height
        );



        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);
    }

    public void createEnemy(float x, float y, EntityColor color, float penisLength, float penisGirth){

        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, ENEMY);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);

        float radius = GameConstants.ENEMY_RADIUS;

        bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y, radius, BodyFactory.Material.RUBBER, BodyDef.BodyType.DynamicBody);

        ComponentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, bodyComponent.body.getAngle());

        bodyComponent.body.setBullet(true);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        Entity penis = createPenis(
                x + radius * MathUtils.cos(bodyComponent.body.getAngle()),
                y + radius * MathUtils.sin(bodyComponent.body.getAngle()),
                bodyComponent.body.getLinearVelocity().x, bodyComponent.body.getLinearVelocity().y,
                penisLength,penisGirth,
                bodyComponent.body.getAngle(),
                entity
        );

        WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.bodyA = bodyComponent.body;
        weldJointDef.bodyB = penis.getComponent(Box2dBodyComponent.class).body;
        weldJointDef.localAnchorA.set(new Vector2(x + radius, y + radius));
        weldJointDef.localAnchorA.set(new Vector2(
                penis.getComponent(PenisComponent.class).getPosition().x + penis.getComponent(PenisComponent.class).getWidth()/2,
                penis.getComponent(PenisComponent.class).getPosition().y + penis.getComponent(PenisComponent.class).getHeight()/3
        ));

        world.createJoint(weldJointDef);

        ComponentHelper.getComponentInitializer().enemyComponent(engine, entity, x, y, radius, color, penis, penisLength, penisGirth);

        engine.addEntity(entity);
    }

    public void createPlayer(float x, float y, float radius, float penisLength, float penisGirth){

        Entity player = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().typeComponent(engine, player, PLAYER);
        ComponentHelper.getComponentInitializer().levelComponent(engine, player);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, player);

        bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y, radius, BodyFactory.Material.WOOD, BodyDef.BodyType.DynamicBody, true);

        ComponentHelper.getComponentInitializer().transformComponent(engine, player, x, y, bodyComponent.body.getAngle());

        bodyComponent.body.setBullet(true);

        bodyComponent.body.setUserData(player);

        player.add(bodyComponent);

        Entity penis = createPenis(
                x + radius * MathUtils.cos(bodyComponent.body.getAngle()),
                y + radius * MathUtils.sin(bodyComponent.body.getAngle()),
                bodyComponent.body.getLinearVelocity().x, bodyComponent.body.getLinearVelocity().y,
                penisLength,penisGirth,
                bodyComponent.body.getAngle(),
                player
        );

        WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.bodyA = bodyComponent.body;
        weldJointDef.bodyB = penis.getComponent(Box2dBodyComponent.class).body;
        weldJointDef.localAnchorA.set(new Vector2(x + radius, y + radius));
        weldJointDef.localAnchorA.set(new Vector2(
                penis.getComponent(PenisComponent.class).getPosition().x + penis.getComponent(PenisComponent.class).getWidth()/2,
                penis.getComponent(PenisComponent.class).getPosition().y + penis.getComponent(PenisComponent.class).getHeight()/3
        ));

        world.createJoint(weldJointDef);

        ComponentHelper.getComponentInitializer().playerComponent(engine, player,
                x,y, radius, penis, penisLength, penisGirth
        );

        engine.addEntity(player);
    }

    public void createWalls(float x, float y, float width, float height) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, SCENERY);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.STEEL, BodyDef.BodyType.StaticBody);

        ComponentHelper.getComponentInitializer().wallComponent(
                engine,
                entity,
                x,y,
                width, height
        );

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);

    }

    public void createPower(float x, float y, float velocityX, float velocityY, PowerHelper.Power power) {
        Entity entity = engine.createEntity();

        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);
        ComponentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, 0);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, POWER);

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);

        float width = GameConstants.POWER_WIDTH;
        float height = GameConstants.POWER_HEIGHT;

        bodyComponent.body = bodyFactory.makeTrianglePolyBody(x, y,
                width,
                height,
                BodyFactory.Material.STONE,
                BodyDef.BodyType.KinematicBody,
                false
        );

        ComponentHelper.getComponentInitializer().powerUpComponent(
                engine,
                entity,
                bodyComponent.body.getPosition().x,
                bodyComponent.body.getPosition().y,
                velocityX, velocityY,
                width, height,
                power
        );

        bodyComponent.body.setUserData(entity);

        bodyComponent.body.setBullet(true);

        entity.add(bodyComponent);

        engine.addEntity(entity);


    }

    private Entity createPenis(float x, float y, float velocityX, float velocityY, float width, float height, float direction, Entity attachedEntity) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, PENIS);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);
        ComponentHelper.getComponentInitializer().transformComponent(engine,entity,x,y,0); //todo needed?

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.RUBBER, BodyDef.BodyType.DynamicBody);

        ComponentHelper.getComponentInitializer().penisComponent(
                engine,
                entity,
                attachedEntity,
                velocityX, velocityY,
                width, height,
                direction
        );

        bodyComponent.body.setBullet(true);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);

        return entity;
    }
}
