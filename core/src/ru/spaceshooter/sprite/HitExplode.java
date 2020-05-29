package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rnd;

public class HitExplode extends Sprite {

    private float animateTimer;

    public HitExplode(TextureAtlas atlas) {
        super(atlas.findRegion("hit"), 3, 3, 9);
    }

    public void set(float height, float left, float right, float top, float bottom) {
        setHeightProportion(height);
        float vx = Rnd.nextFloat(left, right);
        float vy = Rnd.nextFloat(top, bottom);
        this.pos.set(vx, vy);
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
