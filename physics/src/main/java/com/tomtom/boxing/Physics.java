package com.tomtom.boxing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Calendar;

public class Physics {

    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;

    private float accumulator = 0;
    private World world;
    private PhysicalBoxer white;
    private PhysicalBoxer black;


    public void create() {
        Box2D.init();
        world = new World(new Vector2(0, 0), true);

        createRing();
        white = new PhysicalBoxer(world);
        white.moveToGivenPlaceInWorld(3, 3, 0).goThatWay(WorldDirections.WEST);
        black = new PhysicalBoxer(world);
            black.moveToGivenPlaceInWorld(5, 3.2f, (float) Math.toRadians(180)).goThatWay(WorldDirections.EAST);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Object aUSerData = fixtureA.getUserData();
                Object bUserData = fixtureB.getUserData();


                System.out.println("aUSerData = " + aUSerData);
                System.out.println("bUserData = " + bUserData);

                fixtureA.getBody().setLinearVelocity(0,0);
                fixtureB.getBody().setLinearVelocity(0,0);

                if (aUSerData instanceof ImportantBoxerPart) {
                    if (bUserData instanceof  ImportantBoxerPart) {
                        ImportantBoxerPart bUserData1 = (ImportantBoxerPart) bUserData;
                        ImportantBoxerPart aUSerData1 = (ImportantBoxerPart) aUSerData;
                        bUserData1.collisionWithOther(aUSerData1);
                        aUSerData1.collisionWithOther(bUserData1);
                    }
                }


            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                System.out.println("Calendar.getInstance().toString() = " + Calendar.getInstance().getTime().toGMTString());
                System.out.println("pre contact.getFixtureA().getUserData() = " + contact.getFixtureA().getUserData());
                System.out.println("pre contact.getFixtureB().getUserData() = " + contact.getFixtureB().getUserData());
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                System.out.println("Calendar.getInstance().toString() = " + Calendar.getInstance().getTime().toGMTString());
                System.out.println("post contact.getFixtureA().getUserData() = " + contact.getFixtureA().getUserData());
                System.out.println("post contact.getFixtureB().getUserData() = " + contact.getFixtureB().getUserData());
            }
        });

    }

    private void createRing() {
        top();
        bottom();

        left();
        right();
    }

    private void right() {
        verticalBar(6);
    }

    private void left() {
        verticalBar(2);
    }

    private void top() {
        horizontalBar(6);
    }

    private void verticalBar(int i) {
        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(i, 4));

// Create a body from the defintion and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

// Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(0.0025f, 4f);
// Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
// Clean up after ourselves
        groundBox.dispose();
    }

    private void horizontalBar(int i) {
        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
// Set its world position
        groundBodyDef.position.set(new Vector2(4, i));

// Create a body from the defintion and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

// Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(4f, 0.0025f);
// Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
// Clean up after ourselves
        groundBox.dispose();
    }

    private void bottom() {
        // Create our body definition
        horizontalBar(2);
    }

    void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();

        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            if (!white.areFaceToFace(black)) {
                white.turnAround();
                black.turnAround();
            }
        }
        white.step();
        black.step();
    }

    public World getWorld() {
        return world;
    }

    public void dispose() {
        world.dispose();
    }

    public BoxerVisualState getWhiteState() {
        return white.getVisualState();
    }

    public BoxerVisualState getBlackState() {
        return black.getVisualState();
    }
}
