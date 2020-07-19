package fun.game;

import com.tomtom.boxing.BoxerCommand;
import com.tomtom.boxing.BoxerController;

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
    public void init() {

    }

    @Override
    public BoxerCommand tick() {
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
