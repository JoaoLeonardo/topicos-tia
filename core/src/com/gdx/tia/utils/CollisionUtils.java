package com.gdx.tia.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.gdx.tia.element.AliveEntity;
import com.gdx.tia.enums.Direction;
import com.gdx.tia.enums.HitAcc;

public class CollisionUtils {

    public static Direction getDiversionDirection(Direction direction) {
        switch (direction) {
            case UPRIGHT:
            case UPLEFT:
            case UP: return Direction.LEFT;
            case RIGHT: return Direction.DOWN;
            case DOWNRIGHT:
            case DOWNLEFT:
            case DOWN: return Direction.RIGHT;
            case LEFT: return Direction.UP;
        }
        return Direction.HALT;
    }

    public static boolean checkHit(Sprite bullet, AliveEntity entity, HitAcc accuracy) {
        if (!entity.alive) return false;

        boolean hit = false;

        switch (accuracy) {
            case PRECISE:
                hit = precisionHit(bullet.getBoundingRectangle(), entity.sprite.getBoundingRectangle());
                break;
            case LOOSE:
                hit = looseHit(bullet.getBoundingRectangle(), entity.sprite.getBoundingRectangle());
                break;
        }

        if (hit) entity.decreaseHealth();

        return hit;
    }

    private static boolean precisionHit(Rectangle r1, Rectangle r2) {
        return r1.overlaps(r2);
    }

    private static boolean looseHit(Rectangle r1, Rectangle r2) {
        r1.setWidth(r1.width * 1.5f);
        r1.setHeight(r1.height * 1.5f);
        return r1.overlaps(r2);
    }

}
