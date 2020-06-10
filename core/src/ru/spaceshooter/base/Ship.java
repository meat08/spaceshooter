package ru.spaceshooter.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.math.Rect;
import ru.spaceshooter.pool.BulletPool;
import ru.spaceshooter.pool.ExplosionPool;
import ru.spaceshooter.pool.HitExplodePool;
import ru.spaceshooter.screen.GameScreen;
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
    protected Vector2 bullet2Pos;
    protected Vector2 bullet3Pos;
    protected Vector2 bullet4Pos;
    protected float bulletHeight;
    protected int damage;
    protected float reloadInterval;
    protected float reloadTimer;
    protected boolean isShootMulti;
    protected Sound sound;
    protected int hp;
    protected int maxHp;
    protected int shootType;
    protected int shipType;
    protected GameScreen screen;
    private boolean isDestroyBottom;
    private float animateTimer;


    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v0 = new Vector2();
        v = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
        bullet2Pos = new Vector2();
        isShootMulti = false;
        isDestroyBottom = false;
    }

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound, HitExplodePool hitExplodePool, GameScreen screen) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.hitExplodePool = hitExplodePool;
        this.worldBounds = worldBounds;
        this.sound = sound;
        this.screen = screen;
        v0 = new Vector2();
        v = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
        bullet2Pos = new Vector2();
        bullet3Pos = new Vector2();
        bullet4Pos = new Vector2();
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
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                frame = 0;
            }
        }
    }

    protected void autoShoot(float delta) {
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            if (isShootMulti) {
                shootMulti();
            } else {
                if (shootType == 1) {
                    shoot();
                } else if (shootType == 2) {
                    shootDual();
                } else if (shootType == 3) {
                    shootSpinDual();
                } else if (shootType == 4) {
                    shootQuad();
                }
            }
            reloadTimer = 0f;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
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
    public void setDestroyBottom(boolean destroyBottom) {
        isDestroyBottom = destroyBottom;
    }

    public void addMaxHp(int hp) {
        this.maxHp += hp;
    }

    private void pew() {
        if (screen.isSoundOn()) {
            sound.play(screen.getVolumeSound());
        }
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage, false);
        pew();
    }

    private void shootSpinDual() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage, true);
        Bullet bullet1 = bulletPool.obtain();
        bullet1.set(this, bulletRegion, bullet2Pos, bulletV, bulletHeight, worldBounds, damage, true);
        pew();
    }

    private void shootDual() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage, false);
        Bullet bullet1 = bulletPool.obtain();
        bullet1.set(this, bulletRegion, bullet2Pos, bulletV, bulletHeight, worldBounds, damage, false);
        pew();
    }

    private void shootQuad() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage, false);
        Bullet bullet1 = bulletPool.obtain();
        bullet1.set(this, bulletRegion, bullet2Pos, bulletV, bulletHeight, worldBounds, damage, false);
        Bullet bullet2 = bulletPool.obtain();
        bullet2.set(this, bulletRegion, bullet3Pos, bulletV, bulletHeight, worldBounds, damage, false);
        Bullet bullet3 = bulletPool.obtain();
        bullet3.set(this, bulletRegion, bullet4Pos, bulletV, bulletHeight, worldBounds, damage, false);
        pew();
    }

    private void shootMulti() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage, false);
        Bullet bullet1 = bulletPool.obtain();
        bullet1.set(this, bulletRegion, bulletPos, bulletV.cpy().add(0.05f, 0f), bulletHeight, worldBounds, damage, false);
        Bullet bullet2 = bulletPool.obtain();
        bullet2.set(this, bulletRegion, bulletPos, bulletV.cpy().add(-0.05f, 0f), bulletHeight, worldBounds, damage, false);
        pew();
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

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        if (isDestroyBottom) {
            explosion.set(getHeight(), worldBounds.getHalfWidth(), pos);
        } else {
            explosion.set(getHeight(), pos);
        }
    }
}
