package fun.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tomtom.boxing.BoxerCommand;
import com.tomtom.boxing.BoxerController;
import com.tomtom.boxing.Physics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoxingWithPhisics extends ApplicationAdapter {
	SpriteBatch batch;
	Texture white;
	Texture black;
	Texture black0;
	Texture black1;
	Texture black2;
	Texture black3;

	private int x =200; //fancy.... sooooo fancyyyy
	private int y =0;


	OrthographicCamera camera;
	ExtendViewport viewport;

	Box2DDebugRenderer debugRenderer;
	private Physics physics;
	private DummyRobotBoxerController whiteController;
	private DummyRobotBoxerController blackController;
	private Thread whiteThread;
	private RunnableControllerContainer whiteContainer;
	private RunnableControllerContainer blackContainer;
	private Thread blackThread;
	private BoxerGraphics whiteBoxerGraphics;
	private BoxerGraphics blackBoxerGraphics;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.zoom = 0.5f;
		viewport = new ExtendViewport(8, 8, camera);


		debugRenderer = new Box2DDebugRenderer();

		physics = new Physics();
		physics.create();

		whiteBoxerGraphics = new BoxerGraphics();
		blackBoxerGraphics = new BoxerGraphics();

		whiteController = new DummyRobotBoxerController(Duration.ofSeconds(1));
		whiteController.init();
		blackController = new DummyRobotBoxerController(Duration.ofMillis(100));
		blackController.init();

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

		whiteBoxerGraphics.reder(physics.getWhiteState(), BoxerGraphics.BW.WHITE); // pieknie teraz ustawic x i y i rozmiar
		blackBoxerGraphics.reder(physics.getBlackState(), BoxerGraphics.BW.BLACK); // pieknie teraz ustawic x i y i rozmiar


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
		private final AtomicReference<BoxerCommand> command = new AtomicReference<BoxerCommand>(null);

		public RunnableControllerContainer(BoxerController controller) {
			this.controller = controller;
		}

		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				lock.lock();
				try {
					newTick.await();
					command.set(controller.tick());
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
