package com.gdx.tia.processor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gdx.tia.TacticalInfiltrationAction;
import com.gdx.tia.controller.AgentController;
import com.gdx.tia.enums.Direction;
import com.gdx.tia.screens.GameScreen;

public class AgentProcessor implements InputProcessor {

    private final int MOVEMENT_SPEED = 200;

    // espaço entre uma direção e outra (de 32 à -32)
    private final int SENSITIVITY_X = 32;
    private final int SENSITIVITY_Y = 32;

    private final AgentController agentController;

    private final Vector2 movementDirection;
    public final Vector2 position;

    private Direction mouseDirection;

    public AgentProcessor(int initialX, int initialY, AgentController agentController) {
        this.position = new Vector2(initialX, initialY);
        this.movementDirection = new Vector2(Direction.HALT.displacementVector);
        this.agentController = agentController;
    }

    public void update() {
        float speed = MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        float futureX = movementDirection.x * speed;
        float futureY = movementDirection.y * speed;
        Rectangle rectangle = agentController.getAgentSprite().getBoundingRectangle();

        // É ZERO QUANDO MUDA A REGIÃO
        rectangle.x += futureX;
        rectangle.y += futureY;

        if (!GameScreen.ref.hasCollidedWithMap(rectangle)) {
            position.x += futureX;
            position.y += futureY;
            agentController.getAgentSprite().setPosition(position.x, position.y);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                movementDirection.y = 1;
                break; // UP
            case Input.Keys.A:
                movementDirection.x = -1;
                break; // LEFT
            case Input.Keys.S:
                movementDirection.y = -1;
                break; // DOWN
            case Input.Keys.D:
                movementDirection.x = 1;
                break; // RIGHT
            default:
                this.position.add(Direction.HALT.displacementVector);
                break; // NONE
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                if (movementDirection.y == 1) movementDirection.y = 0;
                break;
            case Input.Keys.A:
                if (movementDirection.x == -1) movementDirection.x = 0;
                break;
            case Input.Keys.S:
                if (movementDirection.y == -1) movementDirection.y = 0;
                break;
            case Input.Keys.D:
                if (movementDirection.x == 1) movementDirection.x = 0;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Input.Buttons.LEFT == button)
            agentController.getBulletController().addActiveBullet(position, mouseDirection, getGunshotFx());
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        final float xCenter = GameScreen.ref.getScreenCenter().x;
        final float yCenter = GameScreen.ref.getScreenCenter().y;

        Vector2 diffVector = new Vector2(
                screenX > (xCenter + SENSITIVITY_X) ? 1 : (screenX < (xCenter - SENSITIVITY_X) ? -1 : 0),
                screenY < (yCenter - SENSITIVITY_Y) ? 1 : (screenY > (yCenter + SENSITIVITY_Y) ? -1 : 0)
        );

        mouseDirection = Direction.getDirectionByDisplacement(diffVector);
        agentController.setAgentSprite(mouseDirection.name());
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
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    private Sound getGunshotFx() {
        return TacticalInfiltrationAction.assetManager.get("gunshot.ogg");
    }

}
