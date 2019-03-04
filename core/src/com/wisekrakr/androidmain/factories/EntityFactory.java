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

import com.wisekrakr.androidmain.components.BrickComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.helpers.ComponentHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


import static com.wisekrakr.androidmain.components.TypeComponent.Type.BALL;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.PLAYER;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.POWER;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.SCENERY;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.BRICK;
import static com.wisekrakr.androidmain.components.TypeComponent.Type.OBSTACLE;


/**
 * Class with manually made entities. Connect components to the appropriate entity. (see ComponentHelper)
 */
public class EntityFactory {

    private ComponentHelper componentHelper;
    private BodyFactory bodyFactory;
    public World world;
    private AndroidGame game;
    private PooledEngine engine;

    private List<Entity> totalBricks = new ArrayList<Entity>();
    private List<Entity> totalObstacles = new ArrayList<Entity>();
    private List<Entity> totalBalls = new ArrayList<Entity>();
    private ConcurrentLinkedQueue<Entity> totalPowers = new ConcurrentLinkedQueue<Entity>();

    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Entity player;

    /**
     * @param game Class that extends Game
     * @param pooledEngine Game engine. All entities are placed in here.
     */
    public EntityFactory(AndroidGame game, PooledEngine pooledEngine){
        this.game = game;
        this.engine = pooledEngine;

        world = new World(new Vector2(0,0), true);
        world.setContactListener(new PhysicalObjectContactListener());

        componentHelper = new ComponentHelper(game);
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
        componentHelper.getComponentInitializer().textureComponent(engine, entity);
        componentHelper.getComponentInitializer().typeComponent(engine, entity, OBSTACLE, TypeComponent.Tag.NONE);
        componentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, 0);
        componentHelper.getComponentInitializer().collisionComponent(engine, entity);
        componentHelper.getComponentInitializer().obstacleComponent(engine, entity,
                width, height,
                velocityX, velocityY,
                x, y
        );

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, material, bodyType);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);

        totalObstacles.add(entity);
    }

    public void createBall(float x, float y){

        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        componentHelper.getComponentInitializer().textureComponent(engine, entity);
        componentHelper.getComponentInitializer().typeComponent(engine, entity, BALL, TypeComponent.Tag.PLAYER_BALL);
        componentHelper.getComponentInitializer().collisionComponent(engine, entity);

        float radius = GameConstants.BALL_RADIUS;

        bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y,
                radius,
                BodyFactory.Material.RUBBER,
                BodyDef.BodyType.DynamicBody
        );

        componentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, bodyComponent.body.getAngle());
        componentHelper.getComponentInitializer().ballComponent(engine,
                entity, bodyComponent,
                radius
        );

        bodyComponent.body.setBullet(true); // increase physics computation to limit body travelling through other objects
        //BodyFactory.makeAllFixturesSensors(bodyComponent.body); // make bullets sensors so they don't move player

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        totalBalls.add(0, entity);

        engine.addEntity(entity);

    }


    public void createBrick(float x, float y, BrickComponent.BrickColor color){
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        componentHelper.getComponentInitializer().textureComponent(engine, entity);
        componentHelper.getComponentInitializer().typeComponent(engine, entity, BRICK, TypeComponent.Tag.A_PRIORI_ENTITY);
        componentHelper.getComponentInitializer().collisionComponent(engine, entity);

        float width = GameConstants.BRICK_WIDTH;
        float height = GameConstants.BRICK_HEIGHT;

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y,
                width,
                height,
                BodyFactory.Material.STEEL,
                BodyDef.BodyType.StaticBody,
                false
        );

        componentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, bodyComponent.body.getAngle());
        componentHelper.getComponentInitializer().brickComponent(engine, entity,
                bodyComponent,
                width, height,
                color
        );

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);

        totalBricks.add(entity);

    }

    public Entity createPlayer(float x, float y, float width, float height){

        player = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        componentHelper.getComponentInitializer().playerComponent(engine, player, width, height);
        componentHelper.getComponentInitializer().typeComponent(engine, player, PLAYER, null);
        componentHelper.getComponentInitializer().levelComponent(engine, player);
        componentHelper.getComponentInitializer().collisionComponent(engine, player);
        componentHelper.getComponentInitializer().transformComponent(engine, player, x, y, 0);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.WOOD, BodyDef.BodyType.KinematicBody, true);

        bodyComponent.body.setBullet(true);

        bodyComponent.body.setUserData(player);

        player.add(bodyComponent);

        engine.addEntity(player);

        return player;
    }

    public void createWalls(float x, float y, float width, float height) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        componentHelper.getComponentInitializer().textureComponent(engine, entity);
        componentHelper.getComponentInitializer().typeComponent(engine, entity, SCENERY, TypeComponent.Tag.NONE);
        componentHelper.getComponentInitializer().collisionComponent(engine, entity);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.STEEL, BodyDef.BodyType.StaticBody);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);

    }

    public Entity createPower(float x, float y, float velocityX, float velocityY, float radius) {
        Entity entity = engine.createEntity();

        componentHelper.getComponentInitializer().collisionComponent(engine, entity);
        componentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, 0);
        componentHelper.getComponentInitializer().typeComponent(engine, entity, POWER, TypeComponent.Tag.NONE);

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);

        bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y,
                radius,
                BodyFactory.Material.STONE,
                BodyDef.BodyType.DynamicBody
        );

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        componentHelper.getComponentInitializer().powerUpComponent(engine, entity,
                bodyComponent,
                velocityX, velocityY,
                radius
        );

        totalPowers.add(entity);

        engine.addEntity(entity);

        return entity;
    }

    public List<Entity> getTotalBricks(){
        return totalBricks;
    }

    public List<Entity> getTotalObstacles() {
        return totalObstacles;
    }

    public List<Entity> getTotalBalls() {
        return totalBalls;
    }

    public ConcurrentLinkedQueue<Entity> getTotalPowers() {
        return totalPowers;
    }

    public Entity getPlayer() {
        return player;
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
