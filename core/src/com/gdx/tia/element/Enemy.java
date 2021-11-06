package com.gdx.tia.element;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.gdx.tia.controller.EnemyController;
import com.gdx.tia.enums.Direction;

public class Enemy extends AliveEntity implements Pool.Poolable {

    // espaço entre uma direção e outra (de 30 à -30)
    private final static int X_SENSITIVITY = 30;
    private final static int Y_SENSITIVITY = 30;

    private Vector2 position;

    private final EnemyController enemyController;

    private Sprite sprite;

    public Enemy(EnemyController enemyController) {
        this.enemyController = enemyController;
        reset();
    }

    public void init(float initialX, float initialY) {
        sprite = enemyController.enemyAtlas.createSprite(Direction.HALT.name());
        position = new Vector2(initialX, initialY);
        revive();
    }

    public void update() {
        // descobre a direção do sprite em relação ao agente
        Direction direction = getSpriteDirectionRelativeToTarget();
        // seta o sprite e a movimentação de acordo com a direção descoberta
        sprite = enemyController.enemyAtlas.createSprite(direction.name());
        position.add(direction.displacementVector);
        sprite.setPosition(position.x, position.y);
    }

    public Direction getSpriteDirectionRelativeToTarget() {
        final float xDiff = position.x - enemyController.getAgentX();
        final float yDiff = enemyController.getAgentY() - position.y;

        Vector2 diffVector = new Vector2(
                xDiff > X_SENSITIVITY ? -1 : (xDiff < -X_SENSITIVITY ? 1 : 0),
                yDiff > Y_SENSITIVITY ? 1 : (yDiff < -Y_SENSITIVITY ? -1 : 0)
        );
        return Direction.getDirectionByDisplacement(diffVector);
    }

    public Sprite getSprite() { return sprite; }

    @Override
    public void reset() {
        healthbar = 2;
        alive = false;
    }

}
