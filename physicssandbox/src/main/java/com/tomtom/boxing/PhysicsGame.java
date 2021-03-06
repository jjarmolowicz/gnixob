package com.tomtom.boxing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhysicsGame extends ApplicationAdapter {

    OrthographicCamera camera;
    ExtendViewport viewport;

    Box2DDebugRenderer debugRenderer;
    private static Physics physics;
    private DummyRobotBoxerController whiteController;
    private DummyRobotBoxerController blackController;
    private Thread whiteThread;
    private RunnableControllerContainer whiteContainer;
    private RunnableControllerContainer blackContainer;
    private Thread blackThread;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(8, 8, camera);


        debugRenderer = new Box2DDebugRenderer();

        physics = new Physics();
        physics.create();


        whiteController = new DummyRobotBoxerController(Duration.ofSeconds(1));
        whiteController.init(false);
        blackController = new DummyRobotBoxerController(Duration.ofMillis(100));
        blackController.init(true);

        whiteContainer = new RunnableControllerContainer(whiteController);
        whiteThread = new Thread(whiteContainer);

        blackContainer = new RunnableControllerContainer(blackController);
        blackThread = new Thread(blackContainer);

        whiteThread.start();
        blackThread.start();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(34f / 255, 139f / 255, 34f / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        whiteContainer.tryToNotifyAboutATick();
        blackContainer.tryToNotifyAboutATick();
        physics.stepWorld(whiteContainer.command.get(), blackContainer.command.get());

        debugRenderer.render(physics.getWorld(), camera.combined);
    }

    @Override
    public void dispose() {
        physics.dispose();
        debugRenderer.dispose();
    }

    private static class RunnableControllerContainer implements Runnable {
        final Lock lock = new ReentrantLock();
        final Condition newTick = lock.newCondition();
        private final BoxerController controller;
        private final AtomicReference<BoxerCommand> command = new AtomicReference<>(null);

        public RunnableControllerContainer(BoxerController controller) {
            this.controller = controller;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                lock.lock();
                try {
                    newTick.await();
                    command.set(controller.tick(physics));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }

        public void tryToNotifyAboutATick() {
            if (lock.tryLock()) {
                newTick.signal();
                lock.unlock();
            }
        }
    }
}