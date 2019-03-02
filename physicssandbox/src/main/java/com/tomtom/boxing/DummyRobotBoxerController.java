package com.tomtom.boxing;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DummyRobotBoxerController implements BoxerController {
    private Random rand = new Random();
    private Duration sleep;
    private BoxerCommander commander;

    public DummyRobotBoxerController(Duration sleep) {

        this.sleep = sleep;
    }

    @Override
    public void init(BoxerCommander commander) {
        this.commander = commander;
    }

    @Override
    public void tick() {
        try {
            TimeUnit.MILLISECONDS.sleep(sleep.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        if (rand.nextBoolean()) {
            commander.setCommand(BoxerCommand.values()[rand.nextInt(BoxerCommand.values().length)]);
            return;
        }
        commander.setCommand(null);
    }
}
