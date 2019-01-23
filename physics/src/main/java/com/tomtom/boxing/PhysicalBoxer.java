package com.tomtom.boxing;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

class PhysicalBoxer {

    private static float BOXER_SPEED = 140f * 1000 / 60 / 60 / 60; //30 km/h

    private Body body;

    public PhysicalBoxer(World world) {
        createBoxer(world);
    }

    private void createBoxer(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        addBoxerBodyToBody();
        addFistToBody(new Vector2(0.2f, 0.2f));
        addFistToBody(new Vector2(0.2f, -0.2f));
    }

    private void addBoxerBodyToBody() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.1f, 0.2f);
        createFixtureFromShape(shape);
        shape.dispose();
    }

    private void createFixtureFromShape(Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0;
        body.createFixture(fixtureDef);
    }

    private void addFistToBody(Vector2 position) {
        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(0.1f);
        circle.setPosition(position);
        // Create a fixture definition to apply our shape to
        createFixtureFromShape(circle);
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
        } else {
            return other.body.getPosition().x <= this.body.getPosition().x;
        }
    }

    BoxerVisualState getVisualState() {
        return new BoxerVisualState() {
            @Override
            public float getX() {
                return body.getPosition().x;
            }

            @Override
            public float getY() {
                return body.getPosition().y;
            }

            @Override
            public boolean reversed() {
                return body.getAngle() != 0;
            }

            @Override
            public boolean isHit() {
                return false;
            }

            @Override
            public short leftArmOffset() {
                return 0;
            }

            @Override
            public short rightArmOffset() {
                return 0;
            }
        };
    }
}
