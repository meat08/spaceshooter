package ru.spaceshooter.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private HitExplodePool hitExplodePool;
    private Rect worldBounds;
    private Sound sound;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, HitExplodePool hitExplodePool) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.hitExplodePool = hitExplodePool;
        this.worldBounds = worldBounds;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, worldBounds, sound, hitExplodePool);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
