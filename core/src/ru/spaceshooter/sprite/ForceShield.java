package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.Ship;
import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;

public class ForceShield extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.017f;

    private Ship ship;
    private float animateTimer;

    public ForceShield(TextureAtlas atlas) {
        super(atlas.findRegion("bonus2"), 2, 16, 30);
    }

    public void resize(Rect worldBounds, Ship ship) {
        super.resize(worldBounds);
        this.ship = ship;
        setHeightProportion(ship.getHeight() + 0.05f);
        setBottom(worldBounds.getTop());
    }

    @Override
    public void update(float delta) {
        pos.set(ship.pos);
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                frame = 0;
            }
        }
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }
}
