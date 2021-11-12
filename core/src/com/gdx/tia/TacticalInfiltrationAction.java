package com.gdx.tia;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.gdx.tia.screens.GameScreen;

public class TacticalInfiltrationAction extends Game {

    public AssetManager assetManager;

    @Override
    public void create() {
        assetManager = new AssetManager();
        setScreen(new GameScreen());
    }

}
