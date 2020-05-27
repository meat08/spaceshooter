package ru.spaceshooter.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.BulletPool;
import ru.spaceshooter.pool.ExplosionPool;
import ru.spaceshooter.pool.HitExplodePool;
import ru.spaceshooter.sprite.Bullet;
import ru.spaceshooter.sprite.Explosion;
import ru.spaceshooter.sprite.HitExplode;

public class Ship extends Sprite {

    protected final Vector2 v0;
    protected final Vector2 v;

    protected Rect worldBounds;

    protected ExplosionPool explosionPool;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected HitExplodePool hitExplodePool;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected float bulletHeight;
    protected int damage;

    protected float reloadInterval;
    protected float reloadTimer;
    protected boolean isShootMulti;

    protected Sound sound;

    protected int hp;
    protected int maxHp;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v0 = new Vector2();
        v = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
        isShootMulti = false;
    }

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound, HitExplodePool hitExplodePool) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.hitExplodePool = hitExplodePool;
        this.worldBounds = worldBounds;
        this.sound = sound;
        v0 = new Vector2();
        v = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
    }

    protected void autoShoot(float delta) {
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            if (isShootMulti) {
                shootMulti();
            } else {
                shoot();
            }
            reloadTimer = 0f;

        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
        sound.play(0.3f);
    }

    private void shootMulti() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
        Bullet bullet1 = bulletPool.obtain();
        bullet1.set(this, bulletRegion, bulletPos, bulletV.cpy().add(0.05f, 0f), bulletHeight, worldBounds, damage);
        Bullet bullet2 = bulletPool.obtain();
        bullet2.set(this, bulletRegion, bulletPos, bulletV.cpy().add(-0.05f, 0f), bulletHeight, worldBounds, damage);
        sound.play(0.3f);
    }

    public void damage(int damage) {
        final HitExplode hitExplode = hitExplodePool.obtain();
        hitExplode.set(getHeight(), getLeft(), getRight(), getTop(), getBottom());
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void addMaxHp(int hp) {
        this.maxHp += hp;
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }
}
