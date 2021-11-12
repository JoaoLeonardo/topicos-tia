package com.gdx.tia.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gdx.tia.TacticalInfiltrationAction;
import com.gdx.tia.element.Loadbar;

public class LoadingScreen implements Screen {

    private TacticalInfiltrationAction game;
    private Loadbar loadbar;

    LoadingScreen(TacticalInfiltrationAction game) {
        this.game = game;
    }

    @Override
    public void show() {
        loadbar = new Loadbar(new ShapeRenderer());
    }

    @Override
    public void render(float delta) {
        if (!game.assetManager.update()) loadbar.update(game.assetManager.getProgress());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        loadbar.dispose();
    }

}
