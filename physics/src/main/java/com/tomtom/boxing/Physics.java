package com.tomtom.boxing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

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
        black.moveToGivenPlaceInWorld(5, 5, (float) Math.toRadians(180)).goThatWay(WorldDirections.EAST);

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
