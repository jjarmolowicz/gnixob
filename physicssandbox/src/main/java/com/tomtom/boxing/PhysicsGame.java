package com.tomtom.boxing;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class PhysicsGame extends ApplicationAdapter {

    OrthographicCamera camera;
    ExtendViewport viewport;

    Box2DDebugRenderer debugRenderer;
    private Physics physics;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(8, 8, camera);


        debugRenderer = new Box2DDebugRenderer();

        physics = new Physics();
        physics.create();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(34f / 255, 139f / 255, 34f / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        physics.stepWorld();

        debugRenderer.render(physics.getWorld(), camera.combined);
    }

    @Override
    public void dispose() {
        physics.dispose();
        debugRenderer.dispose();
    }
}