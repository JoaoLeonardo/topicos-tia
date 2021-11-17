package com.gdx.tia.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.tia.TacticalInfiltrationAction;
import com.gdx.tia.enums.Direction;

public class MainMenuScreen implements Screen {

    private TacticalInfiltrationAction tia;
    private GameScreen gameScreen;

    Stage menuStage;
    TextureAtlas menuAtlas;
    Skin menuSkin;

    Table table;
    BitmapFont white;

    Image title;
    TextButton buttonPlay;
    Direction titleDirection;

    Sound theme;

    float titleTimer;

    public MainMenuScreen(TacticalInfiltrationAction tia) {
        this.tia = tia;
        gameScreen = new GameScreen();
        loadAssets();
    }

    @Override
    public void show() {
        menuStage = new Stage();
        menuAtlas = new TextureAtlas("menu-sheet.txt");
        menuSkin = new Skin(menuAtlas);

        table = new Table(menuSkin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        white = new BitmapFont(Gdx.files.internal("tia.fnt"));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = menuSkin.getDrawable("button");
        textButtonStyle.down = menuSkin.getDrawable("button-down");
        textButtonStyle.font = white;
        textButtonStyle.downFontColor = Color.BLACK;
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetX = -1;

        title = new Image(menuSkin.getDrawable("menu-painted"));
        titleTimer = 0;
        titleDirection = Direction.UP;

        corrigirEscalas(textButtonStyle);
        buttonPlay = new TextButton("Jogar", textButtonStyle);

        table.row().expandX().pad(8);
        table.add(title);
        table.row().expandX().pad(8);
        table.add(buttonPlay);

        theme = TacticalInfiltrationAction.assetManager.get("menu-theme.ogg");

        menuStage.addActor(table);
        addPlayButtonListener();
        playTheme();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.NAVY);
        menuStage.act(delta);
        menuStage.draw();
        moveTitle(delta);
    }

    /**
     * Anima o title movendo a imagem de cima para baixo
     */
    private void moveTitle(float delta) {
        if (titleTimer >= 0.8f) {
            titleTimer = 0;

            switch (titleDirection) {
                case UP:
                    title.setY(title.getY() + 10);
                    titleDirection = Direction.DOWN;
                    break;
                case DOWN:
                    title.setY(title.getY() - 10);
                    titleDirection = Direction.UP;
                    break;
            }
        } else {
            titleTimer += delta;
        }
    }

    /**
     * Corrige as escalas dos botões e do título de acordo com o tamanho da janela
     * TODO: Corrigir arquivos
     */
    private void corrigirEscalas(TextButton.TextButtonStyle textButtonStyle) {
        float btnWidth = Gdx.graphics.getWidth() * 0.1f;
        float btnHeight = btnWidth / 2;
        textButtonStyle.up.setMinWidth(btnWidth);
        textButtonStyle.up.setMinHeight(btnHeight);
        textButtonStyle.down.setMinWidth(btnWidth);
        textButtonStyle.down.setMinHeight(btnHeight);

        title.setWidth(Gdx.graphics.getWidth() / 4f);
        title.setHeight(title.getWidth() / 4f);
    }

    /**
     * Seta o listener do botão "Jogar"
     */
    private void addPlayButtonListener() {
        Gdx.input.setInputProcessor(menuStage);

        buttonPlay.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if (buttonPlay.isOver()) {
                    tia.changeScreen(gameScreen);
                }
            }
        });
    }

    public void playTheme() {
        long id = theme.play(0.1f);
        theme.setLooping(id, true);
    }

    private void loadAssets() {
        TacticalInfiltrationAction.assetManager.load("menu-theme.ogg", Sound.class);
    }

    @Override
    public void hide() {
        theme.pause();
    }

    @Override
    public void dispose() {
        menuStage.dispose();
        menuAtlas.dispose();
        menuSkin.dispose();
        theme.dispose();
    }

    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }

}
