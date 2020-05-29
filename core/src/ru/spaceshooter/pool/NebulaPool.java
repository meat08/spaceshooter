package ru.spaceshooter.pool;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.sprite.Nebula;

public class NebulaPool extends SpritesPool<Nebula> {
    private Rect worldBounds;

    public NebulaPool(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    protected Nebula newObject() {
        return new Nebula(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
