package com.gdx.tia.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Pool;
import com.gdx.tia.element.Enemy;
import com.gdx.tia.element.World;

import java.util.ArrayList;

public class EnemyController implements ActionController {

    private final int MAX_WAVE_LENGTH = 6;

    private final World currentStage;

    private final AgentController agentController;

    private final ArrayList<Enemy> activeEnemies = new ArrayList<>();
    private Pool<Enemy> enemyPool;

    public TextureAtlas enemyAtlas;
    private int currentWaveLength;

    public EnemyController(World currentWorld, AgentController agentController) {
        this.currentStage = currentWorld;
        this.agentController = agentController;
    }

    @Override
    public void create() {
        enemyAtlas = new TextureAtlas("enemy.txt");
        enemyPool = new Pool<Enemy>() {
            @Override
            protected Enemy newObject() {
                return new Enemy(EnemyController.this);
            }
        };
        currentWaveLength = 1;
    }

    @Override
    public void drawElements(Batch batch) {
        if (isCleared()) spawnNewWave();
        else renderWave(batch);
    }

    private void renderWave(Batch batch) {
        for (int i = 0; i < activeEnemies.size(); i++) {
            if (activeEnemies.get(i).alive) {
                // renderiza o inimigo
                activeEnemies.get(i).update();
                activeEnemies.get(i).getSprite().draw(batch);
            } else {
                // libera o inimigo da pool/lista de ativos
                enemyPool.free(this.activeEnemies.remove(i));
            }
        }
    }

    public void spawnNewWave() {
        // incrementa o número da wave
        if (currentWaveLength < MAX_WAVE_LENGTH) this.currentWaveLength++;

        // adiciona a wave na lista de inimigos ativos
        while (activeEnemies.size() < currentWaveLength) {
            Enemy freshEnemy = enemyPool.obtain();
            freshEnemy.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            activeEnemies.add(freshEnemy);
        }
    }

    public boolean isCleared() { return activeEnemies.size() <= 0; }

    public float getAgentX() { return agentController.getAgentSprite().getX(); }
    public float getAgentY() { return agentController.getAgentSprite().getY(); }

}
