package fun.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.net.URL;

public class Gnixob extends ApplicationAdapter {
	SpriteBatch batch;
	Texture white;
	Texture black;

	private int x =200; //fancy.... sooooo fancyyyy
	private int y =0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		white = new Texture(Gdx.files.internal("whiteBoxer.png"));
		black = new Texture(Gdx.files.internal("blackBoxer.png"));

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

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.35f, 0.7f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(white, x, y);
		batch.draw(black, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		white.dispose();
		black.dispose();
	}
}
