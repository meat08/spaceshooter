package ru.spaceshooter.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;
import ru.spaceshooter.sprite.Boss;

public class BossPool extends SpritesPool<Boss> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private HitExplodePool hitExplodePool;
    private Rect worldBounds;
    private Sound sound;
    private GameScreen screen;

    public BossPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, HitExplodePool hitExplodePool, GameScreen screen) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.hitExplodePool = hitExplodePool;
        this.worldBounds = worldBounds;
        this.screen = screen;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    }

    @Override
    protected Boss newObject() {
        return new Boss(bulletPool, explosionPool, worldBounds, sound, hitExplodePool, screen);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
