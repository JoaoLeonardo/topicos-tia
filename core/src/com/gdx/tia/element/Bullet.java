package com.gdx.tia.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.gdx.tia.enums.Direction;

public class Bullet implements Pool.Poolable {

    private final int MOVEMENT_SPEED = 8;

    private final Vector2 movementDirection;
    private final Vector2 position;

    public boolean active;

    public Bullet() {
        position = new Vector2();
        movementDirection = Direction.RIGHT.displacementVector;
        active = false;
    }

    public void init(float initialX, float initialY) {
        position.set(initialX, initialY);
        active = true;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isOnScreen() {
        return 0 <= position.x && position.x <= Gdx.graphics.getWidth() &&
               0 <= position.y && position.y <= Gdx.graphics.getHeight();
    }

    public void update() {
        if (isOnScreen()) {
            position.add(movementDirection.x * MOVEMENT_SPEED, movementDirection.y * MOVEMENT_SPEED);
        } else {
            active = false;
        }
    }

    @Override
    public void reset() {
        position.set(0, 0);
        active = false;
    }

}
