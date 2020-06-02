package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.math.Rnd;

public class Bonus extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.04f;
    private static final float SIZE = 0.055f;
    private static final float SPEED = -0.15f;

    private float animateTimer;
    private Rect worldBounds;
    private Vector2 v;
    private int bonusType;

    public Bonus(Rect worldBounds) {
        this.worldBounds = worldBounds;
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

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }

    public void set(TextureRegion[] regions, int bonusType) {
        this.regions = regions;
        this.bonusType = bonusType;
        v.set(0f, SPEED);
        setHeightProportion(SIZE);
        setBottom(worldBounds.getTop());
        pos.x = Rnd.nextFloat(worldBounds.getLeft() + getHalfWidth(), worldBounds.getRight() - getHalfWidth());
    }

    public int getBonusType() {
        return bonusType;
    }
}
