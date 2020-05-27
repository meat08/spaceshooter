package ru.spaceshooter.pool;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.sprite.Bonus;

public class BonusPool extends SpritesPool<Bonus> {
    private Rect worldBounds;

    public BonusPool(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    protected Bonus newObject() {
        return new Bonus(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
