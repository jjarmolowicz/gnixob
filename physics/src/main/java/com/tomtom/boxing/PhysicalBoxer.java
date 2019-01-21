package com.tomtom.boxing;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class PhysicalBoxer {

    private static float BOXER_SPEED = 30f * 1000 / 60 / 60 / 60; //30 km/h

    private Body body;

    public PhysicalBoxer(World world) {
        createBoxer(world);
    }

    private void createBoxer(World world) {
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world

// Create our body in the world using our body definition
        body = world.createBody(bodyDef);
        addCircleToBody(0.2f, new Vector2(0, 0));
        addCircleToBody(0.1f, new Vector2(0.2f, 0.2f));
        addCircleToBody(0.1f, new Vector2(0.2f, -0.2f));

        // Remember to dispose of any shapes after you're done with them!
// BodyDef and FixtureDef don't need disposing, but shapes do.

    }

    private void addCircleToBody(float radius, Vector2 position) {
        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);
        circle.setPosition(position);
        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 1.1f; // Make it bounce a little bit

        body.createFixture(fixtureDef);
        circle.dispose();
    }

    public PhysicalBoxer moveToGivenPlaceInWorld(float x, float y, float angle) {
        body.setTransform(x, y, angle);
        return this;
    }


    public PhysicalBoxer turnAround() {
        Vector2 p = body.getPosition();
        float angle = (float) (Math.toDegrees(body.getAngle()) + 180);
        if (angle >= 360) {
            angle -= 360;
        }
        body.setTransform(p.x, p.y, (float) Math.toRadians(angle));
        return this;
    }


    public PhysicalBoxer goThatWay(WorldDirections dir) {
        Vector2 impulse;
        switch (dir) {
            case NORTH:
                impulse = new Vector2(0, 1);
                break;
            case SOUTH:
                impulse = new Vector2(0, -1);
                break;
            case WEST:
                impulse = new Vector2(1, 0);
                break;
            case EAST:
                impulse = new Vector2(-1, 0);
                break;
            default:
                throw new RuntimeException("unknown constant: " + dir);
        }
        float scale = body.getMass() * BOXER_SPEED;

        impulse.scl(scale);

        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
        return this;
    }

    public boolean areFaceToFace(PhysicalBoxer other) {
        if (body.getAngle() == 0) {
            return other.body.getPosition().x >= this.body.getPosition().x;
        }else {
            return other.body.getPosition().x <= this.body.getPosition().x;
        }
    }
}
