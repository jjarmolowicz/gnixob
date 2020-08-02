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
        white.moveToGivenPlaceInWorld(2, 3, 0).goThatWay(WorldDirections.WEST);
        black = new PhysicalBoxer(world);
        black.moveToGivenPlaceInWorld(4, 3.2f, (float) Math.toRadians(180)).goThatWay(WorldDirections.EAST);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Object aUSerData = fixtureA.getUserData();
                Object bUserData = fixtureB.getUserData();

                if (aUSerData instanceof ImportantBoxerPart) {
                    if (bUserData instanceof ImportantBoxerPart) {
                        ImportantBoxerPart bUserData1 = (ImportantBoxerPart) bUserData;
                        ImportantBoxerPart aUSerData1 = (ImportantBoxerPart) aUSerData;

                        aUSerData1.collisionWithOther(bUserData1);
                        bUserData1.collisionWithOther(aUSerData1);
                    }
                }


            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
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
        verticalBar(5);
    }

    private void left() {
        verticalBar(1);
    }

    private void top() {
        horizontalBar(5);
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
        horizontalBar(1);
    }


    //yeah I know... sorry
    public void stepWorld(BoxerCommand whiteCommand, BoxerCommand blackCommand) {
        float delta = Gdx.graphics.getDeltaTime();

        accumulator += Math.min(delta, 0.25f);

        white.checkVictimPosition(black);
        black.checkVictimPosition(white);


        handleBoxerCommand(whiteCommand, white);
        handleBoxerCommand(blackCommand, black);

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

    private static void handleBoxerCommand(BoxerCommand command, PhysicalBoxer boxer) {
        if (command==null) {
            return;
        }
        switch (command) {
            case MOVE_FORWARD:
                if (boxer.getVisualState().reversed()) {
                    boxer.goThatWay(WorldDirections.EAST);
                } else {
                    boxer.goThatWay(WorldDirections.WEST);
                }
                break;
            case MOVE_BACK:
                if (boxer.getVisualState().reversed()) {
                    boxer.goThatWay(WorldDirections.WEST);
                } else {
                    boxer.goThatWay(WorldDirections.EAST);
                }
                break;
            case MOVE_LEFT:
                if (boxer.getVisualState().reversed()) {
                    boxer.goThatWay(WorldDirections.SOUTH);
                } else {
                    boxer.goThatWay(WorldDirections.NORTH);
                }
                break;
            case MOVE_RIGHT:
                if (boxer.getVisualState().reversed()) {
                    boxer.goThatWay(WorldDirections.NORTH);
                } else {
                    boxer.goThatWay(WorldDirections.SOUTH);
                }
                break;
            case PUNCH:
                 boxer.punch();

            case STOP:
                boxer.stop();
                break;
        }
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
