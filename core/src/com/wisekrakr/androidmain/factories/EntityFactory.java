package com.wisekrakr.androidmain.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.wisekrakr.androidmain.AndroidGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.PhysicalObjectContactListener;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import com.wisekrakr.androidmain.components.EntityColor;
import com.wisekrakr.androidmain.helpers.ComponentHelper;

import java.util.concurrent.ConcurrentLinkedQueue;

import static com.wisekrakr.androidmain.components.TypeComponent.Type.*;


/**
 * Class with manually made entities. Connect components to the appropriate entity. (see ComponentHelper)
 */
public class EntityFactory {


    private BodyFactory bodyFactory;
    public World world;
    private AndroidGame game;
    private PooledEngine engine;

    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private ConcurrentLinkedQueue<Entity> gameObjects = new ConcurrentLinkedQueue<Entity>();

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

//        ComponentHelper.getComponentInitializer().obstacleComponent(engine, entity,
//                width, height,
//                velocityX, velocityY,
//                x, y
//        );

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, material, bodyType);

        ComponentHelper.getComponentInitializer().gameObjectComponent(
                engine,
                entity, bodyComponent,
                x,
                y, velocityX,
                velocityY,
                0, width, height,
                0);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);

        gameObjects.add(entity);
    }

    public void createBall(float x, float y, EntityColor color){

        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, BALL);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);
        ComponentHelper.getComponentInitializer().ballComponent(engine, entity, x, y, color);

        float radius = GameConstants.BALL_RADIUS;

        bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y,
                radius,
                BodyFactory.Material.RUBBER,
                BodyDef.BodyType.DynamicBody
        );

        ComponentHelper.getComponentInitializer().gameObjectComponent(
                engine, entity, bodyComponent, x, y, 0, 0, 0, 0, 0, radius);

        ComponentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, bodyComponent.body.getAngle());



        bodyComponent.body.setBullet(true); // increase physics computation to limit body travelling through other objects
        //BodyFactory.makeAllFixturesSensors(bodyComponent.body); // make bullets sensors so they don't move player

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        gameObjects.add(entity);

        engine.addEntity(entity);

    }

    public void createPlayer(float x, float y){

        Entity player = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().typeComponent(engine, player, PLAYER);
        ComponentHelper.getComponentInitializer().levelComponent(engine, player);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, player);
        ComponentHelper.getComponentInitializer().playerComponent(engine, player);

        float radius = GameConstants.PLAYER_RADIUS;

        bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y, radius, BodyFactory.Material.WOOD, BodyDef.BodyType.DynamicBody, false);

        ComponentHelper.getComponentInitializer().gameObjectComponent(
                engine, player, bodyComponent, x, y, 0, 0, 0, 0, 0, radius);

        ComponentHelper.getComponentInitializer().transformComponent(engine, player, x, y, bodyComponent.body.getAngle());

        bodyComponent.body.setBullet(true);

        bodyComponent.body.setUserData(player);

        player.add(bodyComponent);

        gameObjects.add(player);

        engine.addEntity(player);

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

    public Entity createPower(float x, float y, float velocityX, float velocityY) {
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
                BodyDef.BodyType.DynamicBody,
                true
        );

        ComponentHelper.getComponentInitializer().gameObjectComponent(
                engine,
                entity, bodyComponent,
                x,
                y, velocityX,
                velocityY,
                0, width, height,
                0);

        bodyComponent.body.setUserData(entity);

        bodyComponent.body.setBullet(true);

        entity.add(bodyComponent);

//        ComponentHelper.getComponentInitializer().powerUpComponent(engine, entity,
//                bodyComponent,
//                velocityX, velocityY,
//                width,
//                height
//        );

        gameObjects.add(entity);

        engine.addEntity(entity);

        return entity;
    }

    public ConcurrentLinkedQueue<Entity> getGameObjects() {

        return gameObjects;
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
