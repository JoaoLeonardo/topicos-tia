package com.gdx.tia.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.tia.element.Agent;
import com.gdx.tia.element.World;
import com.gdx.tia.enums.Direction;
import com.gdx.tia.processor.AgentProcessor;

public class AgentController implements ActionController {

    private final World currentStage;
    private final BulletController bulletController;

    private TextureAtlas agentAtlas;
    private Sprite agentSprite;

    private AgentProcessor agentProcessor;

    private Agent agent;

    public AgentController(World currentStage, BulletController bulletController) {
        this.currentStage = currentStage;
        this.bulletController = bulletController;
        agent = new Agent();
    }

    @Override
    public void create() {
        int xCenter = (int) currentStage.getViewportCenter().x;
        int yCenter = (int) currentStage.getViewportCenter().y;

        agentProcessor = new AgentProcessor(xCenter, yCenter, this);
        Gdx.input.setInputProcessor(agentProcessor);

        agentAtlas = new TextureAtlas("agent.txt");
        setAgentSprite(Direction.RIGHT.name());
    }

    @Override
    public void drawElements(Batch batch) {
        // atualiza a posição do agente
        agentProcessor.update();
        // desenha o frame do agente
        agentSprite.draw(batch);
    }

    public World getCurrentStage() {
        return currentStage;
    }

    public BulletController getBulletController() {
        return bulletController;
    }

    public Sprite getAgentSprite() {
        return agentSprite;
    }

    public void setAgentSprite(String region) {
        agentSprite = agentAtlas.createSprite(region);
    }

    public void dispose() {
        agentAtlas.dispose();
    }

}
