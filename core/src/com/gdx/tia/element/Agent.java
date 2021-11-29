package com.gdx.tia.element;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Agent extends AliveEntity {

    private TextureAtlas agentAtlas;

    private int score;

    public Agent() {
        agentAtlas = new TextureAtlas("agent.txt");
        start();
    }

    public void start() {
        healthbar = 3;
        score = 0;
        alive = true;
    }

    public void increaseScoreByKill() { score += 5; }

    public int getScore() { return score; }

    public void setSprite(String region, Vector2 position) {
        sprite = agentAtlas.createSprite(region);
        sprite.setPosition(position.x, position.y);
        sprite.getBoundingRectangle().setPosition(position);
    }

    public void dispose() {
        agentAtlas.dispose();
    }
}
