package com.gdx.tia;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.tia.elements.Agent;
import com.gdx.tia.elements.World;

// TODO LIST:
// - Câmera: Fixar no personagem (se mexer o x do agente, mexer da câmera também)
public class TacticalInfiltrationAction extends ApplicationAdapter {

    SpriteBatch batch;
    World gameWorld;
    Texture agentTexture;
    Agent agentProcessor;

    @Override
    public void create() {
        batch = new SpriteBatch();

        gameWorld = new World();

        agentTexture = new Texture("agent.png");
        agentProcessor = new Agent(0, 0, gameWorld);

        Gdx.input.setInputProcessor(agentProcessor);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 1, 1);
        batch.begin();

        // Atualiza a posição do agente
        agentProcessor.update();

        // Desenha o mundo e o agente
        gameWorld.drawWorldElements(batch);
        batch.draw(agentTexture, agentProcessor.getPosition().x, agentProcessor.getPosition().y);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        agentTexture.dispose();
    }

}
