package com.gdx.tia.utils;

import com.badlogic.gdx.math.Rectangle;
import com.gdx.tia.enums.Direction;

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

    private static Direction hasHit(Rectangle r1, Rectangle r2) {
        if (r1.overlaps(r2)) {
            if (r1.x > r2.y) {
                System.out.println("GO RIGHT");
            } else {
                System.out.println("GO LEFT");
            }
        }

        return null;
    }

    private static boolean overlaps(float a1, float a2, float maxA2) {
        return a1 >= a2 && a1 <= maxA2;
    }

}
