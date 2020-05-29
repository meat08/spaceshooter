package ru.spaceshooter.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.spaceshooter.math.Rect;

public abstract class Title  extends Sprite{

    private static final float ANIMATE_INTERVAL = 0.6f;
    private static final float SIZE = 0.075f;
    private static final float SCALE_INTERVAL = 0.003f;

    private float animateTimer;
    private boolean scaleUP = true;

    public Title(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(SIZE);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            scaleUP = !scaleUP;
        }
        if (scaleUP) {
            setScale(getScale() + SCALE_INTERVAL);
        } else {
            setScale(getScale() - SCALE_INTERVAL);
        }
    }
}
