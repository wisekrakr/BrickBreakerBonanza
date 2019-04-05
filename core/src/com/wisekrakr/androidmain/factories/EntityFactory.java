package com.wisekrakr.androidmain.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.wisekrakr.androidmain.BricksGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.PhysicalObjectContactListener;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import com.wisekrakr.androidmain.components.EntityColor;
import com.wisekrakr.androidmain.helpers.ComponentHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;

import static com.wisekrakr.androidmain.components.TypeComponent.Type.*;


/**
 * Class with manually made entities. Connect components to the appropriate entity. (see ComponentHelper)
 */
public class EntityFactory {


    private BodyFactory bodyFactory;
    public World world;
    private BricksGame game;
    private PooledEngine engine;

    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    /**
     * @param game Class that extends Game
     * @param pooledEngine Game engine. All entities are placed in here.
     */
    public EntityFactory(BricksGame game, PooledEngine pooledEngine){
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
     * @param material
     * @param bodyType static/dynamic/kinematic
     */


    public void createObstacle(float x, float y, float velocityX, float velocityY, float width, float height, BodyFactory.Material material, BodyDef.BodyType bodyType){
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, OBSTACLE);
        ComponentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, 0);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);

        ComponentHelper.getComponentInitializer().obstacleComponent(engine, entity,
                x, y, width, height,
                velocityX, velocityY
        );

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, material, bodyType);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);

    }

    public void createBall(float x, float y, float radius, float direction){

        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, BALL);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);
        ComponentHelper.getComponentInitializer().ballComponent(engine, entity, x, y, radius, direction);

        bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y,
                radius,
                BodyFactory.Material.RUBBER,
                BodyDef.BodyType.DynamicBody
        );

        ComponentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, bodyComponent.body.getAngle());

        bodyComponent.body.setBullet(true);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);

    }

    public Entity createPlayer(float x, float y, float width, float height){

        Entity player = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().typeComponent(engine, player, PLAYER);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, player);
        ComponentHelper.getComponentInitializer().playerComponent(engine, player, x, y, width, height);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.WOOD, BodyDef.BodyType.KinematicBody);

        ComponentHelper.getComponentInitializer().transformComponent(engine, player, x, y, bodyComponent.body.getAngle());

        bodyComponent.body.setBullet(true);

        bodyComponent.body.setUserData(player);

        player.add(bodyComponent);

        engine.addEntity(player);

        return player;
    }

    public void createBrick(float x, float y, EntityColor color){
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, BRICK);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);

        float width = GameConstants.BRICK_WIDTH;
        float height = GameConstants.BRICK_HEIGHT;

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y,
                width,
                height,
                BodyFactory.Material.WOOD,
                BodyDef.BodyType.StaticBody,
                false
        );

        ComponentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, bodyComponent.body.getAngle());
        ComponentHelper.getComponentInitializer().brickComponent(engine, entity, x,y, width, height, color);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);

    }

    public void createWalls(float x, float y, float width, float height) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, SCENERY);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.STEEL, BodyDef.BodyType.StaticBody);

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

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y,
                width,
                height,
                BodyFactory.Material.STONE,
                BodyDef.BodyType.KinematicBody,
                true
        );

        bodyComponent.body.setUserData(entity);

        bodyComponent.body.setBullet(true);

        entity.add(bodyComponent);

        ComponentHelper.getComponentInitializer().powerUpComponent(engine, entity,
                x,y,
                velocityX, velocityY,
                width,
                height,
                power
        );


        engine.addEntity(entity);

    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    /*
    Here we add the actual level that was created in Tiled. The level has a background and sides that can be collided with.
     */

    public void loadMap(){
        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("levels/levelOne.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }
}
