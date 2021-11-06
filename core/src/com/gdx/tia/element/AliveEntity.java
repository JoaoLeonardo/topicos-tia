package com.gdx.tia.element;

public abstract class AliveEntity {

    public int healthbar;
    public boolean alive;

    public void decreaseHealth() {
        if (alive) healthbar--;
        alive = healthbar > 0;
    }

    public void revive() { alive = true; }

}
