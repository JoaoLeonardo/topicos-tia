package com.gdx.tia.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.gdx.tia.enums.Direction;

public class Agent implements InputProcessor {

    private final int MOVEMENT_SPEED = 4;
    private final int MAXIMUM_X = Gdx.graphics.getWidth() - 32;
    private final int MAXIMUM_Y = Gdx.graphics.getHeight() - 32;

    private Vector2 movementDirection;

    private final Vector2 position;

    Direction mouseDirection;

    private World currentWorld;

    public Agent(int initialX, int initialY, World world) {
        position = new Vector2(initialX, initialY);
        movementDirection = Direction.HALT.displacementVector;
        currentWorld = world;
    }

    public Vector2 getPosition() { return position; }

    public void update() {
        position.add(movementDirection);
        if (!isOnScreenX()) position.x = position.x > 0 ? MAXIMUM_X : 0;
        if (!isOnScreenY()) position.y = position.y > 0 ? MAXIMUM_Y : 0;
    }

    public void shoot() {
        currentWorld.addBullet(position);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W: this.movementDirection.y = MOVEMENT_SPEED; break; // UP
            case Input.Keys.A: this.movementDirection.x = -MOVEMENT_SPEED; break; // LEFT
            case Input.Keys.S: this.movementDirection.y = -MOVEMENT_SPEED; break; // DOWN
            case Input.Keys.D: this.movementDirection.x = MOVEMENT_SPEED; break; // RIGHT
            default: position.add(Direction.HALT.displacementVector); break; // NONE
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                if (this.movementDirection.y == MOVEMENT_SPEED) this.movementDirection.y = 0;
                break;
            case Input.Keys.A:
                if (this.movementDirection.x == -MOVEMENT_SPEED) this.movementDirection.x = 0;
                break;
            case Input.Keys.S:
                if (this.movementDirection.y == -MOVEMENT_SPEED) this.movementDirection.y = 0;
                break;
            case Input.Keys.D:
                if (this.movementDirection.x == MOVEMENT_SPEED) this.movementDirection.x = 0;
                break;
            default:
                break;
        }
        return true;
    }

    public boolean isOnScreenX() {
        return 0 <= position.x && position.x <= MAXIMUM_X;
    }

    public boolean isOnScreenY() {
        return 0 <= position.y && position.y <= MAXIMUM_Y;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Input.Buttons.LEFT == button) this.shoot();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        final float xDiff = screenX - position.x;
        final float yDiff = (Gdx.graphics.getHeight() - screenY) - position.y;

        // espaço entre uma direção e outra (de 30 à -30)
        final int xSensitivity = 30;
        final int ySensitivity = 30;

        Vector2 diffVector = new Vector2(
                xDiff > xSensitivity ? 1 : (xDiff < -xSensitivity ? -1 : 0),
                yDiff > ySensitivity ? 1 : (yDiff < -ySensitivity ? -1 : 0)
        );

        mouseDirection = Direction.getDirectionByDisplacement(diffVector);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
