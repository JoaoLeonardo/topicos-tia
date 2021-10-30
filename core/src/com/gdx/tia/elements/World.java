package com.gdx.tia.elements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;

public class World {

    private final ArrayList<Bullet> activeBullets = new ArrayList<>();

    public ArrayList<Bullet> getActiveBullets() {
        return activeBullets;
    }

    private final Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet();
        }
    };

    public void drawWorldElements(Batch batch) {
        for (Bullet activeBullet : activeBullets) {
            activeBullet.update();
            batch.draw(Bullet.texture, activeBullet.getPosition().x, activeBullet.getPosition().y);
        }
    }

    public void addBullet(Vector2 origin) {
        // adiciona uma bala na lista de balas ativas
        Bullet freshBullet = bulletPool.obtain();
        freshBullet.init(origin.x, origin.y);
        activeBullets.add(freshBullet);

        // remove as balas inativas da pool
        removeInactiveBullets();
    }

    public void removeInactiveBullets() {
        Bullet item;
        for (int i = activeBullets.size(); --i >= 0; ) {
            item = activeBullets.get(i);
            if (!item.active) {
                activeBullets.remove(i);
                bulletPool.free(item);
            }
        }
    }

}
