package com.gdx.tia.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.tia.TacticalInfiltrationAction;
import com.gdx.tia.element.Stage;
import com.gdx.tia.element.World;

public class GameScreen implements Screen {

    public static GameScreen ref;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;

    private World gameWorld;

    private Vector2 screenCenter;

    private Sound theme;

    public GameScreen() {
        ref = this;
        loadAssets();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        map = new TmxMapLoader().load("facility-1-1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        screenCenter = new Vector2((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);

        gameWorld = new Stage(renderer.getBatch(), this);
        gameWorld.create();

        theme = TacticalInfiltrationAction.assetManager.get("stage-music.ogg");
        playTheme();
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
        if (TacticalInfiltrationAction.assetManager.update()) gameWorld.render();
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
        theme.dispose();
        renderer.dispose();
        gameWorld.dispose();
    }

    public OrthographicCamera getCamera() { return camera; }
    public Vector2 getScreenCenter() { return screenCenter; }

    public void playTheme() {
        long id = theme.play(0.1f);
        theme.setLooping(id, true);
    }

    private void loadAssets() {
        TacticalInfiltrationAction.assetManager.load("gunshot.ogg", Sound.class);
        TacticalInfiltrationAction.assetManager.load("stage-music.ogg", Sound.class);
        TacticalInfiltrationAction.assetManager.load("bullet.png", Texture.class);
    }

}
