package com.gdx.tia.element;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.gdx.tia.controller.ActionController;
import com.gdx.tia.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public abstract class World implements ApplicationListener {

    private Batch batch;

    public AssetManager assetManager;

    private GameScreen gameScreen;

    public List<ActionController> actionControllerList;

    public World(Batch batch, AssetManager assetManager, GameScreen gameScreen) {
        this.batch = batch;
        this.assetManager = assetManager;
        this.gameScreen = gameScreen;
    }

    public GameScreen getGameScreen() { return gameScreen; }

    public Vector2 getViewportCenter() { return gameScreen.getScreenCenter(); }

    public abstract Vector2 getPlayerPosition();

    @Override
    public void create() {
        actionControllerList = new ArrayList<>();
    }

    @Override
    public void render() {
        for (ActionController actionController : actionControllerList) actionController.drawElements(batch);
        gameScreen.getCamera().position.set(getPlayerPosition(), 0);
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    public void notifyCreation() {
        for (ActionController actionController : actionControllerList) actionController.create();
    }

    public void complete(World nextWorld) {
        dispose();
        nextWorld.create();
    }

    public void complete() {
        dispose();
        // TODO: chamar menu
    }

}
