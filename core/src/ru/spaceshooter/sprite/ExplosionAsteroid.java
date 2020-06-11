package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;

public class ExplosionAsteroid extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.2f;

    private float animateTimer;

    public ExplosionAsteroid(TextureAtlas atlas) {
        super(atlas.findRegion("rock_explosion"), 1, 5, 5);
    }

    public void set(float height, Vector2 pos) {
        setHeightProportion(height);
        this.pos.set(pos);
        frame = 0;
    }

    public void set(float height, float halfWidth, Vector2 pos) {
        setHeightProportion(height);
        setWidth(halfWidth);
        this.pos.set(pos);
        frame = 0;
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
