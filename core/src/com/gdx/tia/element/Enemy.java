package com.gdx.tia.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.gdx.tia.controller.BulletController;
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

    private Direction dodgeDirection;
    private boolean isDiverting;
    private float dodgeTimer;

    private float gunTimer;

    public Enemy() { reset(); }

    public void init(float initialX, float initialY) {
        sprite = EnemyController.ref.enemyAtlas.createSprite(Direction.HALT.name());
        position = new Vector2(initialX + 50, initialY);
        gunTimer = 0;
        revive();
    }

    public void update() {
        // descobre a direção do sprite em relação ao agente
        Direction direction = getSpriteDirectionRelativeToTarget();

        // seta o sprite e a movimentação de acordo com a direção descoberta
        sprite = EnemyController.ref.enemyAtlas.createSprite(direction.name());

        if (!isDiverting) {
            // segue o agente
            moveToTarget(direction);

            // atira
            gunTimer += Gdx.graphics.getDeltaTime();
            if (gunTimer > 1) {
                gunTimer = 0;
                BulletController.ref.addActiveBullet(
                        position, direction, EnemyController.ref.gunshotSound, false
                );
            }
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
            // NÃO É A DIREÇÃO INVERSA
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
        this.isDiverting = GameScreen.ref.hasCollidedWithMap(enemyRectangle) || dodgeTimer < 1;

        position.set(enemyRectangle.x, enemyRectangle.y);
        sprite.setPosition(enemyRectangle.x, enemyRectangle.y);
    }

    public Rectangle moveRectangle(Vector2 displacement) {
        // movimenta o retângulo de acordo com o delta e velocidade
        float mSpeed = MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        Rectangle enemyRectangle = sprite.getBoundingRectangle();
        float xDisp = displacement.x * mSpeed;
        float yDisp = displacement.y * mSpeed;
        enemyRectangle.setPosition(position.x + xDisp, position.y + yDisp);
        return enemyRectangle;
    }

    public Direction getSpriteDirectionRelativeToTarget() {
        final float xDiff = position.x - EnemyController.ref.getAgentX();
        final float yDiff = EnemyController.ref.getAgentY() - position.y;

        Vector2 diffVector = new Vector2(
                xDiff > X_SENSITIVITY ? -1 : (xDiff < -X_SENSITIVITY ? 1 : 0),
                yDiff > Y_SENSITIVITY ? 1 : (yDiff < -Y_SENSITIVITY ? -1 : 0)
        );
        return Direction.getDirectionByDisplacement(diffVector);
    }

    @Override
    public void reset() {
        healthbar = 2;
        alive = false;
    }

}
