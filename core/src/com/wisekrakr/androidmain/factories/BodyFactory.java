package com.wisekrakr.androidmain.factories;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.List;

public class BodyFactory {

    private World world;
    private static BodyFactory thisInstance;

    private static List<Body>bodies = new ArrayList<Body>();

    private BodyFactory(World world) {
        this.world = world;
    }

    static BodyFactory getBodyFactoryInstance(World world){
        if (thisInstance == null){
            thisInstance = new BodyFactory(world);
        }
        return thisInstance;
    }

    public static void makeAllFixturesSensors(Body body, boolean sensor){
        for(Fixture fixture: body.getFixtureList()){
            fixture.setSensor(sensor);
        }
    }

    public void removeFixtureAbundance(Body body){
        if (body != null){
            while (body.getFixtureList().size > 1) {
                body.destroyFixture(body.getFixtureList().first());
            }
        }
    }

    public enum Material{
        STEEL, WOOD, RUBBER, STONE
    }

    private static FixtureDef makeDefaultFixture(Material material, Shape shape){

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        switch(material){
            case STEEL:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.3f;
                fixtureDef.restitution = 0.1f;
                break;
            case WOOD:
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.7f;
                fixtureDef.restitution = 0.3f;
                break;
            case RUBBER:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 1f;
                break;
            case STONE:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.9f;
                fixtureDef.restitution = 0.01f;
            default:
                //System.out.println("No parameters were filled for this fixture " + fixtureDef.toString());
        }

        return fixtureDef;
    }

    Body makeCirclePolyBody(float positionX, float positionY, float radius, Material material, BodyDef.BodyType bodyType){
        return makeCirclePolyBody(positionX, positionY, radius, material, bodyType, false);
    }

    Body makeCirclePolyBody(float positionX, float positionY, float radius, Material material, BodyDef.BodyType bodyType, boolean fixedRotation){

        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = positionX;
        boxBodyDef.position.y = positionY;
        boxBodyDef.fixedRotation = fixedRotation;

        Body boxBody = world.createBody(boxBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius /2);
        boxBody.createFixture(makeDefaultFixture(material,circleShape));
        circleShape.dispose();
        return boxBody;
    }

    Body makeBoxPolyBody(float positionX, float positionY, float width, float height, Material material, BodyDef.BodyType bodyType){
        return makeBoxPolyBody(positionX, positionY, width, height, material, bodyType, false);
    }

    Body makeBoxPolyBody(float positionX, float positionY, float width, float height, Material material, BodyDef.BodyType bodyType, boolean fixedRotation){

        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = positionX;
        boxBodyDef.position.y = positionY;
        boxBodyDef.fixedRotation = fixedRotation;

        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width/2, height/2);
        boxBody.createFixture(makeDefaultFixture(material,poly));
        poly.dispose();

        return boxBody;
    }

    Body makeTrianglePolyBody(float positionX, float positionY, float width, float height, Material material, BodyDef.BodyType bodyType, boolean fixedRotation){

        BodyDef triangleBodyDef = new BodyDef();
        triangleBodyDef.type = bodyType;
        triangleBodyDef.position.x = positionX;
        triangleBodyDef.position.y = positionY;
        triangleBodyDef.fixedRotation = fixedRotation;

        Body triangleBody = world.createBody(triangleBodyDef);
        PolygonShape poly = new PolygonShape();

        Vector2[]vector2s = new Vector2[3];
        vector2s[0] = new Vector2(0, height);
        vector2s[1] = new Vector2(-width/2, 0);
        vector2s[2] = new Vector2(width/2, 0);

        poly.set(vector2s);

        triangleBody.createFixture(makeDefaultFixture(material,poly));
        poly.dispose();

        return triangleBody;
    }


    void makeConeSensor(Body body, float viewRadius){

        FixtureDef fixtureDef = new FixtureDef();
        //fixtureDef.isSensor = true; // when this is on it can notice something touching it

        PolygonShape polygon = new PolygonShape();

        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(0,0);
        for (int i = 2; i < 6; i++) {
            float angle = (float) (i  / 6.0 * 145 * MathUtils.degRad); // convert degrees to radians
            vertices[i-1] = new Vector2( viewRadius * ((float)Math.cos(angle)), viewRadius * ((float)Math.sin(angle)));
        }
        polygon.set(vertices);
        fixtureDef.shape = polygon;

        body.createFixture(fixtureDef);

        bodies.add(body);

        polygon.dispose();
    }


}
