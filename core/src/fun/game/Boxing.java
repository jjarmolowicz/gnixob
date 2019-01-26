package fun.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Gnixob extends ApplicationAdapter {
	SpriteBatch batch;
	Texture white;
	Texture black;
	Texture black0;
	Texture black1;
	Texture black2;
	Texture black3;

	private int x =200; //fancy.... sooooo fancyyyy
	private int y =0;


	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String i = bufferedReader.readLine();
		String j = bufferedReader.readLine();
		int x = Integer.parseInt(i);
		int y = Integer.parseInt(j);
		int wynik = x + y;
		System.out.println(x+" + "+y+" = "+wynik);
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		white = new Texture(Gdx.files.internal("whiteBoxer.png"));
		black = new Texture(Gdx.files.internal("blackBoxer.png"));
		black0 = new Texture(Gdx.files.internal("blackBoxer.png"));
		black1 = new Texture(Gdx.files.internal("blackBoxerb1.png"));
		black2 = new Texture(Gdx.files.internal("blackBoxerb2.png"));
		black3 = new Texture(Gdx.files.internal("blackBoxerb3.png"));

		Sound sound = Gdx.audio.newSound(Gdx.files.internal("gwiazda.mp3"));

		sound.play();


		Gdx.input.setInputProcessor(new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.LEFT) {
					x -= 10;
				}else if (keycode == Input.Keys.RIGHT) {
					x += 10;
				}else if (keycode == Input.Keys.UP) {
					y += 10;
				}else if (keycode == Input.Keys.DOWN) {
					y -= 10;
				}
				if (keycode == Input.Keys.SPACE){
					animate = true;
					start = System.currentTimeMillis();
				}
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
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

	boolean animate = false;
	long start = 0;

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.35f, 0.7f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (animate){
			long step = System.currentTimeMillis() - start;
			if (step<40 || step > 280){
				black = black1;
			}else if (step<80 || step > 240){
				black = black2;
			}else if (step<120 || step > 200){
				black = black3;
			}else if (step>320){
				black = black0;
			}
		}
		batch.draw(black, x, y);
		batch.draw(white, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		white.dispose();
		black.dispose();
	}
}
