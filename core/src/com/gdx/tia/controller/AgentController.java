package com.gdx.tia.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.gdx.tia.element.World;
import com.gdx.tia.processor.AgentProcessor;

public class AgentController implements ActionController {

    private final World currentStage;
    private final BulletController bulletController;

    private AgentProcessor agentProcessor;

    public AgentController(World currentStage, BulletController bulletController) {
        this.currentStage = currentStage;
        this.bulletController = bulletController;
    }

    @Override
    public void create() {
        agentProcessor = new AgentProcessor(0, 0, this);
        Gdx.input.setInputProcessor(agentProcessor);

        currentStage.assetManager.load("agent.png", Texture.class);
        currentStage.assetManager.load("gunshot.ogg", Sound.class);
    }

    @Override
    public void drawElements(Batch batch) {
        // Atualiza a posição do agente
        agentProcessor.update();

        // Desenha o frame do agente
        batch.draw(
                currentStage.assetManager.get("agent.png", Texture.class),
                agentProcessor.getPosition().x,
                agentProcessor.getPosition().y
        );
    }

    public World getCurrentStage() { return currentStage; }

    public BulletController getBulletController() { return bulletController; }

}
