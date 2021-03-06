package com.tomtom.boxing;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DummyRobotBoxerController implements BoxerController {
    private Random rand = new Random();
    private Duration sleep;

    public DummyRobotBoxerController(Duration sleep) {

        this.sleep = sleep;
    }

    @Override
    public void init(boolean isBlack) {

    }

    @Override
    public BoxerCommand tick(Physics physics) {
        try {
            TimeUnit.MILLISECONDS.sleep(sleep.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        if (rand.nextBoolean()) {
            return BoxerCommand.values()[rand.nextInt(BoxerCommand.values().length)];
        }
        return null;
    }
}
