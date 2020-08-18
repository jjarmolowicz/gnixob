package com.tomtom.boxing;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

class PhysicalBoxer {

    private static float BOXER_SPEED = 340f * 1000 / 60 / 60 / 60; //30 km/h

    private Body body;
    private PhysicalFist leftFist;
    private PhysicalFist rightFist;
    private boolean lastPunhchedLeft;
    private Vector2 currentImpulse = new Vector2(0, 0);
    private float scale;
    private boolean victimOnTheLeft;
    private Nose nose;

    public PhysicalBoxer(World world) {
        createBoxer(world);
    }

    private void createBoxer(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        addBoxerBodyToBody();
        addBoxerNoseToBody();
        leftFist = new PhysicalFist(body, PhysicalFist.FistSide.LEFT);
        rightFist = new PhysicalFist(body, PhysicalFist.FistSide.RIGHT);
        scale = body.getMass() * BOXER_SPEED;
    }

    private void addBoxerNoseToBody() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.06f, 0.03f, new Vector2(0.13f, 0), 0);
        Fixture noseFixture = createFixtureFromShape(body, shape);
        this.nose = new Nose(body);
        noseFixture.setUserData(nose);
        shape.dispose();
    }

    public Nose getNose(){
        return nose;
    }

    private void addBoxerBodyToBody() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.1f, 0.24f);
        createFixtureFromShape(body, shape).setUserData((ImportantBoxerPart) other -> {
        });
        shape.dispose();
    }

    static Fixture createFixtureFromShape(Body body, Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0;
//        fixtureDef.isSensor = true;
        return body.createFixture(fixtureDef);
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

        recalcScaleVectorAndApplyImpulse(impulse);
        return this;
    }

    public void punch(){
        if (victimOnTheLeft){
            leftFist.initPunch();
        }else{
            rightFist.initPunch();
        }
    }

    private void recalcScaleVectorAndApplyImpulse(Vector2 impulse) {
        Vector2 realImpulse = counterCurrentPlusNew(impulse);
        realImpulse.scl(scale);
        body.applyLinearImpulse(realImpulse, body.getWorldCenter(), true);
    }


    private Vector2 counterCurrentPlusNew(Vector2 newVector) {
        Vector2 result = new Vector2(this.currentImpulse);
        this.currentImpulse = newVector;
        result.rotate(180);
        result.add(newVector);
        return result;
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
                return leftFist.offset();
            }

            @Override
            public short rightArmOffset() {
                return rightFist.offset();
            }
        };
    }

    public void step() {
        rightFist.step();
        leftFist.step();

    }

    public void stop() {
        body.setAngularVelocity(0);
        body.setLinearVelocity(0, 0);
        recalcScaleVectorAndApplyImpulse(new Vector2(0,0));
    }

    public void checkVictimPosition(PhysicalBoxer victim) {

        if (victim.body.getPosition().y>this.body.getPosition().y){
            if (this.getVisualState().reversed()){
                victimOnTheLeft = false;
            }else{
                victimOnTheLeft = true;
            }
        }else{
            if (this.getVisualState().reversed()){
                victimOnTheLeft = true;
            }else{
                victimOnTheLeft = false;
            }

        }

    }
}
