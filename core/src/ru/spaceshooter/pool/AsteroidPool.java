package ru.spaceshooter.pool;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.sprite.Asteroid;

public class AsteroidPool extends SpritesPool<Asteroid> {
    private Rect worldBounds;

    public AsteroidPool(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    protected Asteroid newObject() {
        return new Asteroid(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
