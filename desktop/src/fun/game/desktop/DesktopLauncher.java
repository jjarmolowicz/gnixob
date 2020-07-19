package fun.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tomtom.boxing.BoxerController;
import com.tomtom.boxing.Physics;

import fun.game.Boxing;
import fun.game.BoxingWithPhisics;

public class DesktopLauncher {

	OrthographicCamera camera;
	ExtendViewport viewport;

	Box2DDebugRenderer debugRenderer;
	private Physics physics;
	private BoxerController whiteController;
	private BoxerController blackController;
	private Thread whiteThread;
//	private RunnableControllerContainer whiteContainer;
//	private RunnableControllerContainer blackContainer;
	public DesktopLauncher() {

		debugRenderer = new Box2DDebugRenderer();
	}

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

//		new LwjglApplication(new Boxing(), config);
		new LwjglApplication(new BoxingWithPhisics(), config);

	}
}
