package com.tomtom.boxing;

import com.badlogic.gdx.physics.box2d.Body;

import java.util.concurrent.atomic.AtomicInteger;

class Nose implements ImportantBoxerPart {
    private AtomicInteger counter = new AtomicInteger(0);
    private final Body boxerBody;

    Nose(Body boxerBody) {
        this.boxerBody = boxerBody;
    }

    @Override
    public void collisionWithOther(ImportantBoxerPart other) {
        //nothing
    }

    @Override
    public String toString() {
        return "Nose{" +
                "counter=" + counter +
                '}';
    }

    public void punchedByFist(PhysicalFist.FistSide fistSide) {
        int hitCount = counter.incrementAndGet();
        System.out.println("hitCount = " + hitCount);
    }
}
