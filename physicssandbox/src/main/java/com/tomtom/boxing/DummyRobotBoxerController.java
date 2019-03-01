package com.tomtom.boxing;

import java.util.Random;

public class DummyRobotBoxerController implements BoxerController {
    private Random rand = new Random();
    private int ticker =0 ;

    @Override
    public BoxerCommand tick() {
        if (ticker++ ==10) {
            ticker = 0;
            if (rand.nextBoolean()) {
                return BoxerCommand.values()[rand.nextInt(BoxerCommand.values().length)];
            }
        }
        return null;
    }
}
