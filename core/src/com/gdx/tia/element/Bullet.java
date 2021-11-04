package com.gdx.tia.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.gdx.tia.enums.Direction;

public class Bullet implements Pool.Poolable {

    private final int MOVEMENT_SPEED = 8;

    private final Vector2 position;

    private Vector2 movementDirection;
    public boolean active;

    public Bullet() {
        position = new Vector2();
        movementDirection = Direction.RIGHT.displacementVector;
        active = false;
    }

    public void init(float initialX, float initialY, Direction direction) {
        position.set(initialX, initialY);
        active = true;
        if (Direction.HALT.equals(direction)) direction = Direction.RIGHT;
        movementDirection = direction.displacementVector;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isOnScreen() {
        return (0 <= position.x && position.x <= Gdx.graphics.getWidth()) &&
               (0 <= position.y && position.y <= Gdx.graphics.getHeight());
    }

    public boolean update() {
        if (isOnScreen()) {
            position.add(movementDirection.x * MOVEMENT_SPEED, movementDirection.y * MOVEMENT_SPEED);
        } else {
            active = false;
        }
        return active;
    }

    @Override
    public void reset() {
        position.set(0, 0);
        active = false;
    }

}
