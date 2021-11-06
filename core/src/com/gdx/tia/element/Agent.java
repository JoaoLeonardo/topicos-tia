package com.gdx.tia.element;

public class Agent extends AliveEntity {

    private int score;

    public Agent() { start(); }

    public void start() {
        healthbar = 5;
        score = 0;
        alive = true;
    }

    public void increaseScore(int amount) { score += amount; }

    public int getScore() { return score; }

}
