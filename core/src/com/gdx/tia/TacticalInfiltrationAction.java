package com.gdx.tia;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.tia.element.Loadbar;
import com.gdx.tia.element.Stage;
import com.gdx.tia.element.World;

// TODO LIST:
// - Câmera: Fixar no personagem (se mexer o x do agente, mexer da câmera também)
public class TacticalInfiltrationAction extends Game {

    Loadbar loadbar;
    AssetManager assetManager;
    World gameWorld;

    @Override
    public void create() {
        assetManager = new AssetManager();
        loadbar = new Loadbar(new ShapeRenderer());
        gameWorld = new Stage(assetManager);

        gameWorld.create();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 1, 1);
        if (assetManager.update()) gameWorld.render();
        else if (!gameWorld.isRendered()) loadbar.update(assetManager.getProgress());
    }

    @Override
    public void dispose() {
        super.dispose();
        loadbar.dispose();
        gameWorld.dispose();
        assetManager.dispose();
    }

}
