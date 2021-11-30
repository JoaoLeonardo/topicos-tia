package com.gdx.tia.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Pool;
import com.gdx.tia.element.Enemy;

import java.util.ArrayList;
import java.util.List;

public class EnemyController implements ActionController {

    public static EnemyController ref;

    private final int MAX_WAVE_LENGTH = 8;

    private final ArrayList<Enemy> activeEnemies = new ArrayList<>();
    private Pool<Enemy> enemyPool;

    public TextureAtlas enemyAtlas;
    private int currentWaveLength;

    @Override
    public void create() {
        ref = this;
        enemyAtlas = new TextureAtlas("enemy.txt");
        enemyPool = new Pool<Enemy>() {
            @Override
            protected Enemy newObject() {
                return new Enemy(EnemyController.this);
            }
        };
        currentWaveLength = 0;
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

                if (!activeEnemies.get(i).hasBeenHit) activeEnemies.get(i).getSprite().draw(batch);
                else activeEnemies.get(i).hasBeenHit = false;
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
            // TODO: Escolher um entre os spawns do mapa
            // TODO²: Adicionar os spawns via camada no tiled, pelo menos oito
            freshEnemy.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            activeEnemies.add(freshEnemy);
        }
    }

    public List<Enemy> getActiveEnemies() { return activeEnemies; }

    public boolean isCleared() { return activeEnemies.size() <= 0; }

    public float getAgentX() { return AgentController.ref.getAgentSprite().getX(); }
    public float getAgentY() { return AgentController.ref.getAgentSprite().getY(); }

}
