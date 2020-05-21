package ru.spaceshooter.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import ru.spaceshooter.base.Ship;
import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.BulletPool;
import ru.spaceshooter.pool.ExplosionPool;

public class Enemy extends Ship {
    private Vector2 startV;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        super(bulletPool, explosionPool, worldBounds, sound);
        startV = new Vector2(0f, -0.5f);
    }

    @Override
    public void update(float delta) {
        if (getTop() >= worldBounds.getTop()) {
            pos.mulAdd(startV, delta);
        } else {
            pos.mulAdd(v, delta);
            autoShoot(delta);
        }
        if (getBottom() <= worldBounds.getBottom()) {
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            int hp,
            float height
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        this.v.set(v0);
    }

    public boolean isBulletHit(Sprite bullet) {
        return !(bullet.getBottom() > getTop() || bullet.getTop() < getBottom()
                || bullet.getRight() < getLeft() || bullet.getLeft() > getRight());
    }

}
