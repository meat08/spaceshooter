package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.math.Rnd;

public class Nebula extends Sprite {

    private static final float SIZE = 0.45f;

    private Rect worldBounds;
    private Vector2 v;

    public Nebula(Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.v = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);

        if (getTop() <= worldBounds.getBottom()) {
            destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    public void set(TextureRegion[] regions, float speed) {
        this.regions = regions;
        v.set(0f, speed);
        setHeightProportion(SIZE);
        setBottom(worldBounds.getTop());
        pos.x = Rnd.nextFloat(worldBounds.getLeft() + getHalfWidth(), worldBounds.getRight() - getHalfWidth());
    }
}
