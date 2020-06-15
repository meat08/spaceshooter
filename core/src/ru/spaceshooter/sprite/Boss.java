package ru.spaceshooter.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.BulletPool;
import ru.spaceshooter.pool.ExplosionPool;
import ru.spaceshooter.pool.HitExplodePool;
import ru.spaceshooter.screen.GameScreen;

public class Boss extends Ship {

    private static final float V_Y = -0.3f;
    private int bossType;

    public Boss(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound, HitExplodePool hitExplodePool, GameScreen screen) {
        super(bulletPool, explosionPool, worldBounds, sound, hitExplodePool, screen);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() < worldBounds.getTop()) {
            move();
            shoot(delta);
        }
    }

    private void shoot(float delta) {
        if (shipType == 4) {
            bulletPos.set(pos.x - getHalfWidth()/2, pos.y - getHalfHeight()/2);
            bullet2Pos.set(pos.x, pos.y - getHalfHeight());
            bullet3Pos.set(pos.x + getHalfWidth()/2, pos.y - getHalfHeight()/2);
        } else if (shipType == 3) {
            bulletPos.set(pos.x - getHalfHeight()/2, pos.y - getHalfHeight()/2);
            bullet2Pos.set(pos.x - 0.02f, pos.y - getHalfHeight()/2);
            bullet3Pos.set(pos.x + 0.02f, pos.y - getHalfHeight()/2);
            bullet4Pos.set(pos.x + getHalfHeight()/2, pos.y - getHalfHeight()/2);
        } else if (shipType == 2) {
            bulletPos.set(pos.x - getHalfWidth()/2, pos.y - getHalfHeight()/2);
            bullet2Pos.set(pos.x + getHalfWidth()/2, pos.y - getHalfHeight()/2);
        } else {
            bulletPos.set(pos.x, pos.y - getHalfHeight());
        }
        if (screen.isNotNuked()) {
            autoShoot(delta);
        }
    }

    private void move() {
        v.set(v0);
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft() + 0.01f);
            v0.scl(-1f);
        } else if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight() - 0.01f);
            v0.scl(-1f);
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            TextureRegion bulletRegion1,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            int hp,
            float height,
            int shootType,
            int shipType,
            int bossType
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletRegion1 = bulletRegion1;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        this.v.set(0, V_Y);
        this.shootType = shootType;
        this.shipType = shipType;
        this.bossType = bossType;
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }

    public int getBossType() {
        return bossType;
    }
}
