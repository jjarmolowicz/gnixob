package com.tomtom.boxing;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;

public class PhysicalFist implements ImportantBoxerPart {
    enum FistSide {
        LEFT,RIGHT;
    }

    public static final float BASE_X = 0.2f;
    private static float[] FIST_OFFSETS = {0, 0.025f, 0.05f, 0.1f, 0.2f, 0.3f, 0.5f};
    private Fixture fixture;

    private short offsetPointer = 0;
    private boolean punching = false;
    private Body boxerBody;
    private float y;
    private FistSide fistSide;

    private short accumulator = 0;

    public PhysicalFist(Body boxerBody,FistSide fistSide) {
        this.boxerBody = boxerBody;
        this.y = (FistSide.LEFT.equals(fistSide)?0.2f:-0.2f);
        this.fistSide = fistSide;
        // Create a circle shape and set its radius to 6
        putFistIntoLocatoin(BASE_X);
    }

    private void putFistIntoLocatoin(float x) {
        CircleShape circle = new CircleShape();
        circle.setRadius(0.1f);
        circle.setPosition(new Vector2(x, this.y));
        // Create a fixture definition to apply our shape to
        fixture = PhysicalBoxer.createFixtureFromShape(boxerBody, circle);
        fixture.setUserData(this);
        circle.dispose();
    }

    public void step() {
        if (punching) {
            if (offsetPointer < FIST_OFFSETS.length - 1) {
                if (accumulator++ > 2) {
                    ++offsetPointer;
                    accumulator = 0;
                }
            } else {
                punching = false;
            }
        } else {
            if (offsetPointer > 0) {
                if (accumulator++ > 2) {
                    --offsetPointer;
                    accumulator = 0;
                }
            }
        }

        moveFixtureToNewPosition();
    }

    private void moveFixtureToNewPosition() {
        boxerBody.destroyFixture(fixture);
        putFistIntoLocatoin(BASE_X + FIST_OFFSETS[offsetPointer]);
    }

    public boolean canPunch() {
        return !punching && offsetPointer == 0;
    }

    public void initPunch() {
        punching = true;
    }

    public short offset() {
        return offsetPointer;
    }

    @Override
    public void collisionWithOther(ImportantBoxerPart other) {
        if (other instanceof Nose) {
            if (punching) {
                ((Nose) other).punchedByFist(fistSide);
            }
        }
        punching = false;
    }

    @Override
    public String toString() {
        return "PhysicalFist{" +
                "fixture=" + fixture +
                ", offsetPointer=" + offsetPointer +
                ", punching=" + punching +
                ", boxerBody=" + boxerBody +
                ", y=" + y +
                ", accumulator=" + accumulator +
                '}';
    }
}
