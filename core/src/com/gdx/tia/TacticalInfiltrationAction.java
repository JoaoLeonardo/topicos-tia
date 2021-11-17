package com.gdx.tia;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.gdx.tia.screens.LoadingScreen;
import com.gdx.tia.screens.MainMenuScreen;

public class TacticalInfiltrationAction extends Game {

    public static AssetManager assetManager;

    @Override
    public void create() {
        assetManager = new AssetManager();
        setScreen(new LoadingScreen(this, new MainMenuScreen(this)));
    }

    public void changeScreen(Screen newScreen) {
        setScreen(newScreen);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        super.dispose();
    }

}
