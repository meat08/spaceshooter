package ru.spaceshooter.pool;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.sprite.Asteroid;

public class AsteroidPool extends SpritesPool<Asteroid> {
    private Rect worldBounds;
    private ExplosionAsteroidPool explosionAsteroidPool;

    public AsteroidPool(Rect worldBounds, ExplosionAsteroidPool explosionAsteroidPool) {
        this.worldBounds = worldBounds;
        this.explosionAsteroidPool = explosionAsteroidPool;
    }

    @Override
    protected Asteroid newObject() {
        return new Asteroid(worldBounds, explosionAsteroidPool);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
