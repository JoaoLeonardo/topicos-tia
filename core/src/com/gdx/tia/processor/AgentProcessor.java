package com.gdx.tia.processor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.gdx.tia.controller.AgentController;
import com.gdx.tia.enums.Direction;

public class AgentProcessor implements InputProcessor {

    private final int MOVEMENT_SPEED = 4;
    private final int MAXIMUM_X = Gdx.graphics.getWidth() - 24;
    private final int MAXIMUM_Y = Gdx.graphics.getHeight() - 32;

    // espaço entre uma direção e outra (de 30 à -30)
    private final int SENSITIVITY_X = 30;
    private final int SENSITIVITY_Y = 30;

    private final AgentController agentController;

    private final Vector2 movementDirection;
    private final Vector2 position;

    private Direction mouseDirection;

    private Sound getGunshotFx() { return agentController.getCurrentStage().assetManager.get("gunshot.ogg"); }

    public AgentProcessor(int initialX, int initialY, AgentController agentController) {
        this.position = new Vector2(initialX, initialY);
        this.movementDirection = new Vector2(Direction.HALT.displacementVector);
        this.agentController = agentController;
    }

    public void update() {
        position.add(movementDirection);
        if (!isOnScreenX()) position.x = position.x > 0 ? MAXIMUM_X : 0;
        if (!isOnScreenY()) position.y = position.y > 0 ? MAXIMUM_Y : 0;
        agentController.getAgentSprite().setPosition(position.x, position.y);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W: movementDirection.y = MOVEMENT_SPEED; break; // UP
            case Input.Keys.A: movementDirection.x = -MOVEMENT_SPEED; break; // LEFT
            case Input.Keys.S: movementDirection.y = -MOVEMENT_SPEED; break; // DOWN
            case Input.Keys.D: movementDirection.x = MOVEMENT_SPEED; break; // RIGHT
            default: this.position.add(Direction.HALT.displacementVector); break; // NONE
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                if (movementDirection.y == MOVEMENT_SPEED) movementDirection.y = 0;
                break;
            case Input.Keys.A:
                if (movementDirection.x == -MOVEMENT_SPEED) movementDirection.x = 0;
                break;
            case Input.Keys.S:
                if (movementDirection.y == -MOVEMENT_SPEED) movementDirection.y = 0;
                break;
            case Input.Keys.D:
                if (movementDirection.x == MOVEMENT_SPEED) movementDirection.x = 0;
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
        if (Input.Buttons.LEFT == button)
            agentController.getBulletController().addActiveBullet(position, mouseDirection, getGunshotFx());
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        final float xDiff = screenX - position.x;
        final float yDiff = (Gdx.graphics.getHeight() - screenY) - position.y;

        Vector2 diffVector = new Vector2(
                xDiff > SENSITIVITY_X ? 1 : (xDiff < -SENSITIVITY_X ? -1 : 0),
                yDiff > SENSITIVITY_Y ? 1 : (yDiff < -SENSITIVITY_Y ? -1 : 0)
        );

        mouseDirection = Direction.getDirectionByDisplacement(diffVector);
        agentController.setAgentSprite(mouseDirection.name());
        return true;
    }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
