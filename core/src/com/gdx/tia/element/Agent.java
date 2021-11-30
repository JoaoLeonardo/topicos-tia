package com.gdx.tia.element;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gdx.tia.screens.MainMenuScreen;

public class Agent extends AliveEntity {

    private TextureAtlas agentAtlas;

    private int score;
    public boolean canTradeScore;

    public Agent() {
        agentAtlas = new TextureAtlas("agent.txt");
        start();
    }

    public void start() {
        healthbar = 3;
        score = 0;
        alive = true;
        canTradeScore = false;
    }

    public void decreaseHealth() {
        if (!hasBeenHit) super.decreaseHealth();
        if (alive) canTradeScore = score >= 200;
        else MainMenuScreen.ref.setScore(getScore());
    }

    public void increaseScoreByKill() {
        score += 5;
        canTradeScore = score >= 200 && healthbar < 3;
    }

    public void tradeScore() {
        if (canTradeScore) {
            score -= 200;
            healthbar++;
            canTradeScore = false;
        }
    }

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
