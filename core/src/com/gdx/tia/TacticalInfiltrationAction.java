package com.gdx.tia;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.gdx.tia.screens.LoadingScreen;
import com.gdx.tia.screens.MainMenuScreen;

public class TacticalInfiltrationAction extends Game {

    public static TacticalInfiltrationAction ref;

    public static AssetManager assetManager;

    @Override
    public void create() {
        ref = this;
        assetManager = new AssetManager();
        setScreen(new LoadingScreen(new MainMenuScreen(0)));
        // TODO: Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
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
