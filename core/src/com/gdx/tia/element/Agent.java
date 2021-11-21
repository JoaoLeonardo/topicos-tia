package com.gdx.tia.element;

public class Agent extends AliveEntity {

    private int score;

    public Agent() { start(); }

    public void start() {
        healthbar = 3;
        score = 0;
        alive = true;
    }

    public void increaseScoreByKill() { score += 5; }

    public int getScore() { return score; }

}
