package com.gdx.tia.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.tia.element.Agent;
import com.gdx.tia.enums.Direction;
import com.gdx.tia.processor.AgentProcessor;
import com.gdx.tia.screens.GameScreen;

public class AgentController implements ActionController {

    public static AgentController ref;

    private final BulletController bulletController;

    private TextureAtlas agentAtlas;
    private Sprite agentSprite;

    private AgentProcessor agentProcessor;
    private Agent agent;

    public AgentController(BulletController bulletController) {
        this.bulletController = bulletController;
        agent = new Agent();
        ref = this;
    }

    @Override
    public void create() {
        int xCenter = (int) GameScreen.ref.getScreenCenter().x;
        int yCenter = (int) GameScreen.ref.getScreenCenter().y;

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

    public BulletController getBulletController() {
        return bulletController;
    }

    public Agent getAgent() { return agent; }

    public Sprite getAgentSprite() { return agentSprite; }

    public void setAgentSprite(String region) {
        agentSprite = agentAtlas.createSprite(region);
        agentSprite.setPosition(agentProcessor.position.x, agentProcessor.position.y);
        agentSprite.getBoundingRectangle().setPosition(agentProcessor.position);
    }

    public void dispose() {
        agentAtlas.dispose();
    }

}
