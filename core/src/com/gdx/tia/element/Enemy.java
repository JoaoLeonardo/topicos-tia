package com.gdx.tia.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.gdx.tia.controller.EnemyController;
import com.gdx.tia.enums.Direction;
import com.gdx.tia.screens.GameScreen;
import com.gdx.tia.utils.CollisionUtils;

public class Enemy extends AliveEntity implements Pool.Poolable {

    private final static int MOVEMENT_SPEED = 50;

    // espaço entre uma direção e outra (de 30 à -30)
    private final static int X_SENSITIVITY = 30;
    private final static int Y_SENSITIVITY = 30;

    private Vector2 position;

    private final EnemyController enemyController;

    private Sprite sprite;

    private Direction dodgeDirection;
    private boolean isDiverting;
    private float dodgeTimer;

    public Enemy(EnemyController enemyController) {
        this.enemyController = enemyController;
        reset();
    }

    public void init(float initialX, float initialY) {
        sprite = enemyController.enemyAtlas.createSprite(Direction.HALT.name());
        position = new Vector2(initialX + 50, initialY);
        revive();
    }

    public void update() {
        // descobre a direção do sprite em relação ao agente
        Direction direction = getSpriteDirectionRelativeToTarget();

        // seta o sprite e a movimentação de acordo com a direção descoberta
        sprite = enemyController.enemyAtlas.createSprite(direction.name());

        if (!isDiverting) {
            // segue o agente
            moveToTarget(direction);
        } else {
            // desvia do objeto o agente
            dodgeObject();
        }
    }

    public void moveToTarget(Direction direction) {
        Rectangle enemyRectangle = moveRectangle(direction.displacementVector);

        // verifica a colisão com objetos do mapa
        if (GameScreen.ref.hasCollidedWithMap(enemyRectangle)) {
            this.isDiverting = true;
            this.dodgeTimer = 0;
            dodgeDirection = CollisionUtils.getDiversionDirection(direction);
            dodgeObject();
        } else {
            position.set(enemyRectangle.x, enemyRectangle.y);
            sprite.setPosition(position.x, position.y);
        }
    }

    public void dodgeObject() {
        Rectangle enemyRectangle = moveRectangle(dodgeDirection.displacementVector);
        dodgeTimer += Gdx.graphics.getDeltaTime();
        this.isDiverting = dodgeTimer < 1;
        position.set(enemyRectangle.x, enemyRectangle.y);
        sprite.setPosition(enemyRectangle.x, enemyRectangle.y);
    }

    public Rectangle moveRectangle(Vector2 displacement) {
        // movimenta o retângulo de acordo com o delta e velocidade
        float mSpeed = MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        Rectangle enemyRectangle = sprite.getBoundingRectangle();
        float xDisp = displacement.x * mSpeed;
        float yDisp = displacement.y * mSpeed;
        System.out.println(displacement);
        enemyRectangle.setPosition(position.x + xDisp, position.y + yDisp);
        return enemyRectangle;
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

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void reset() {
        healthbar = 2;
        alive = false;
    }

}
