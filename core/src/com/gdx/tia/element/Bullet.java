package com.gdx.tia.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.gdx.tia.TacticalInfiltrationAction;
import com.gdx.tia.enums.Direction;

public class Bullet implements Pool.Poolable {

    private final int MOVEMENT_SPEED = 400;

    private final Vector2 position;

    private Vector2 movementDirection;
    public boolean active;

    public Bullet() {
        position = new Vector2();
        movementDirection = Direction.RIGHT.displacementVector;
        active = false;
    }

    public void init(float initialX, float initialY, Direction direction) {
        active = true;
        position.set(initialX + 6, initialY + 16); // + 6 pra cima, + 2 para baixo

        if (Direction.HALT.equals(direction)) direction = Direction.RIGHT;
        movementDirection = direction.displacementVector;
    }

    public boolean isOnScreen() { // TODO: Verificar de acordo com o mapa
        return (0 <= position.x && position.x <= Gdx.graphics.getWidth()) &&
                (0 <= position.y && position.y <= Gdx.graphics.getHeight());
    }

    public boolean update() {
        // if (isOnScreen()) {
            position.add(
                    movementDirection.x * MOVEMENT_SPEED * Gdx.graphics.getDeltaTime(),
                    movementDirection.y * MOVEMENT_SPEED * Gdx.graphics.getDeltaTime()
            );
        /*} else {
            active = false;
        }*/
        return active;
    }

    @Override
    public void reset() {
        position.set(0, 0);
        active = false;
    }

    public Vector2 getPosition() {
        return position;
    }

    public static Texture getTexture() {
        return TacticalInfiltrationAction.assetManager.get("bullet.png", Texture.class);
    }

}
