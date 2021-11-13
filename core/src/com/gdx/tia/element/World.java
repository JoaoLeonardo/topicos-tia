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

    private GameScreen gameScreen;

    public List<ActionController> actionControllerList;

    public World(Batch batch, GameScreen gameScreen) {
        this.batch = batch;
        this.gameScreen = gameScreen;
    }

    @Override
    public void create() {
        actionControllerList = new ArrayList<>();
    }

    public void notifyCreation() {
        for (ActionController actionController : actionControllerList) actionController.create();
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

    public void complete(World nextWorld) {
        dispose();
        nextWorld.create();
    }

    public void complete() {
        dispose();
        // TODO: chamar menu
    }

    public GameScreen getGameScreen() { return gameScreen; }
    public Vector2 getViewportCenter() { return gameScreen.getScreenCenter(); }
    public abstract Vector2 getPlayerPosition();

}
