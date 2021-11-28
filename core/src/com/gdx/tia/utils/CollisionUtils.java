package com.gdx.tia.utils;

import com.badlogic.gdx.math.Rectangle;
import com.gdx.tia.enums.CollisionAccuracy;
import com.gdx.tia.enums.Direction;

public class CollisionUtils {

    public static Direction hasCollided(Rectangle r1, Rectangle r2, CollisionAccuracy acc) {
        switch (acc) {
            case LOOSE:
                break;
            default:
                return hasHit(r1, r2);
        }
        return null;
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
