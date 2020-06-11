package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.math.Rnd;
import ru.spaceshooter.pool.ExplosionAsteroidPool;

public class Asteroid extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.04f;
    private static final float SIZE_MIN = 0.035f;
    private static final float SIZE_MAX = 0.085f;
    private static final float SPEED = -0.1f;

    private float animateTimer;
    private Rect worldBounds;
    private ExplosionAsteroidPool explosionAsteroidPool;
    private Vector2 v;
    private int hp;

    public Asteroid(Rect worldBounds, ExplosionAsteroidPool explosionAsteroidPool) {
        this.worldBounds = worldBounds;
        this.explosionAsteroidPool = explosionAsteroidPool;
        this.v = new Vector2();
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
        if (getBottom() <= worldBounds.getBottom()) {
            destroy();
        }
    }

    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
        frame = 0;
    }

    private void boom() {
        ExplosionAsteroid explosionAsteroid = explosionAsteroidPool.obtain();
        explosionAsteroid.set(getHeight(), pos);
    }

    public void set(TextureRegion[] regions) {
        this.regions = regions;
        this.hp = 20;
        v.set(0f, SPEED);
        float size = Rnd.nextFloat(SIZE_MIN, SIZE_MAX);
        setHeightProportion(size);
        setBottom(worldBounds.getTop());
        pos.x = Rnd.nextFloat(worldBounds.getLeft() + getHalfWidth(), worldBounds.getRight() - getHalfWidth());
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }

    public int getDamage(int hp) {
        return (int) (hp * 0.1f);
    }

}
