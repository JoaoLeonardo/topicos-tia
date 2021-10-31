package com.gdx.tia.element;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.tia.controller.ActionController;

import java.util.ArrayList;
import java.util.List;

public abstract class World implements ApplicationListener {

    private SpriteBatch batch;

    private boolean rendered;

    public AssetManager assetManager;

    public List<ActionController> actionControllerList;

    public World(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public boolean isRendered() { return rendered; }

    @Override
    public void create() {
        batch = new SpriteBatch();
        actionControllerList = new ArrayList<>();
    }

    @Override
    public void render() {
        rendered = true;
        batch.begin();

        for (ActionController actionController : actionControllerList) actionController.drawElements(batch);

        batch.end();
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
