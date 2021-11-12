package com.gdx.tia.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.tia.element.Stage;
import com.gdx.tia.element.World;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;

    private AssetManager assetManager;
    private World gameWorld;

    private Vector2 screenCenter;

    public OrthographicCamera getCamera() { return camera; }
    public Vector2 getScreenCenter() { return screenCenter; }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        map = new TmxMapLoader().load("facility-1-1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        screenCenter = new Vector2((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);

        assetManager = new AssetManager();
        gameWorld = new Stage(renderer.getBatch(), assetManager, this);
        gameWorld.create();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // renderiza o mapa
        camera.update();
        renderer.setView(camera);
        renderer.render();

        // renderiza os elementos do mundo
        renderer.getBatch().begin();
        if (assetManager.update()) gameWorld.render();
        renderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        screenCenter = new Vector2((float) width / 2, (float) height / 2);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { dispose(); }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        gameWorld.dispose();
        assetManager.dispose();
    }

}
