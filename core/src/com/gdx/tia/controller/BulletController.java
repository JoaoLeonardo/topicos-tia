package com.gdx.tia.controller;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.gdx.tia.element.Bullet;
import com.gdx.tia.element.World;
import com.gdx.tia.enums.Direction;

import java.util.ArrayList;

public class BulletController implements ActionController {

    private final World currentWorld;

    private final ArrayList<Bullet> activeBullets = new ArrayList<>();
    private final Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet();
        }
    };

    public ArrayList<Bullet> getActiveBullets() { return activeBullets; }

    private Texture getBulletTexture() { return currentWorld.assetManager.get("bullet.png", Texture.class); }

    public BulletController(World currentWorld) {
        this.currentWorld = currentWorld;
    }

    @Override
    public void create() {
        currentWorld.assetManager.load("bullet.png", Texture.class);
    }

    @Override
    public void drawElements(Batch batch) {
        for (Bullet activeBullet : activeBullets) {
            if (activeBullet.update())
                batch.draw(getBulletTexture(), activeBullet.getPosition().x, activeBullet.getPosition().y);
        }
    }

    public void addActiveBullet(Vector2 origin, Direction direction, Sound sound) {
        // adiciona uma bala na lista de balas ativas
        Bullet freshBullet = bulletPool.obtain();
        freshBullet.init(origin.x, origin.y, direction);
        activeBullets.add(freshBullet);

        // toca o efeito de tiro
        playVfx(sound);

        // remove as balas inativas da pool
        removeInactiveBullets();
    }

    public void removeInactiveBullets() {
        Bullet item;

        // limpa o array de balas ativas (removendo as balas inativas)
        for (int i = activeBullets.size(); --i >= 0; ) {
            item = activeBullets.get(i);

            if (!item.active) {
                activeBullets.remove(i);
                bulletPool.free(item);
            }
        }
    }

    public void playVfx(Sound sound) {
        long id = sound.play(0.1f);
        sound.setLooping(id, false);
    }

}
