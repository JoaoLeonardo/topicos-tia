package com.gdx.tia.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gdx.tia.element.Agent;
import com.gdx.tia.enums.Direction;
import com.gdx.tia.processor.AgentProcessor;
import com.gdx.tia.screens.GameScreen;

public class AgentController implements ActionController {

    public static AgentController ref;

    private AgentProcessor agentProcessor;
    private Agent agent;

    public AgentController() {
        agent = new Agent();
        ref = this;
    }

    @Override
    public void create() {
        int xCenter = (int) GameScreen.ref.getScreenCenter().x;
        int yCenter = (int) GameScreen.ref.getScreenCenter().y;

        agentProcessor = new AgentProcessor(xCenter, yCenter, this);
        Gdx.input.setInputProcessor(agentProcessor);
        setAgentSprite(Direction.RIGHT.name());
    }

    @Override
    public void drawElements(Batch batch) {
        // atualiza a posição do agente
        agentProcessor.update();
        // desenha o frame do agente
        agent.sprite.draw(batch);
    }

    public Agent getAgent() { return agent; }

    public Sprite getAgentSprite() { return agent.sprite; }

    public void setAgentSprite(String region) {
        agent.setSprite(region, agentProcessor.position);
    }

    public void dispose() {
        agent.dispose();
    }

}
