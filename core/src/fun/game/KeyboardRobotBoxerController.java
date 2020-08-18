package fun.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.tomtom.boxing.BoxerCommand;
import com.tomtom.boxing.BoxerController;
import com.tomtom.boxing.Physics;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class KeyboardRobotBoxerController implements BoxerController {
    private Random rand = new Random();
    private Duration sleep;
    private int x =200; //fancy.... sooooo fancyyyy
    private int y =0;

    int keyDown = -24;
    boolean isBlack = false;

    public KeyboardRobotBoxerController(Duration sleep) {

        this.sleep = sleep;
        keyDown =  -111;
    }

    @Override
    public void init(boolean isBlack) {
        this.isBlack = isBlack;

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                keyDown = keycode;

                return false;
            }

            @Override
            public boolean keyUp(int keycode) {

                keyDown = -2;
                return false;
            }

            @Override
            public boolean keyTyped(char character) {

                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });

    }

    @Override
    public BoxerCommand tick(Physics physics) {
        try {
            TimeUnit.MILLISECONDS.sleep(sleep.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        if (isBlack){
            if (!physics.getBlackState().reversed()){
                if (Input.Keys.DOWN == keyDown){
                    return BoxerCommand.MOVE_RIGHT;
                }

                if (Input.Keys.LEFT == keyDown){
                    return BoxerCommand.MOVE_BACK;
                }

                if (Input.Keys.RIGHT == keyDown){
                    return BoxerCommand.MOVE_FORWARD;
                }

                if (Input.Keys.UP == keyDown){
                    return BoxerCommand.MOVE_LEFT;
                }
            }else{
                if (Input.Keys.DOWN == keyDown){
                    return BoxerCommand.MOVE_LEFT;
                }

                if (Input.Keys.LEFT == keyDown){
                    return BoxerCommand.MOVE_FORWARD;
                }

                if (Input.Keys.RIGHT == keyDown){
                    return BoxerCommand.MOVE_BACK;
                }

                if (Input.Keys.UP == keyDown){
                    return BoxerCommand.MOVE_RIGHT;
                }
            }

            if (Input.Keys.SPACE == keyDown){
                return BoxerCommand.PUNCH;
            }


            return BoxerCommand.STOP;

        }

        return null;
    }
}
