package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;

public class PauseLogo extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.6f;

    private float animateTimer;
    private boolean scaleUP = true;

    public PauseLogo(TextureAtlas atlas) {
        super(atlas.findRegion("pause"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.075f);
        setTop(0.1f);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            scaleUP = !scaleUP;
        }
        if (scaleUP) {
            setScale(getScale() + 0.003f);
        } else {
            setScale(getScale() - 0.003f);
        }
    }
}
