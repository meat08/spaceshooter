package ru.spaceshooter.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.math.Rect;
import ru.spaceshooter.utils.Regions;

public abstract class ScaledButton extends Sprite {

    private static final float SCALE = 0.9f;
    protected static final float BUTTON_SIZE = 0.075f;
    protected static final float MARGIN = 0.05f;

    private boolean pressed;
    private int pointer;

    public ScaledButton(TextureRegion region) {
        super(region);
    }

    public ScaledButton(TextureRegion region, int rows, int cols, int frames) {
        regions = Regions.split(region, rows, cols, frames);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (pressed || !isMe(touch)) {
            return false;
        }
        this.pointer = pointer;
        pressed = true;
        scale = SCALE;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || !pressed) {
            return false;
        }
        if (isMe(touch)) {
            action();
        }
        pressed = false;
        scale = 1f;
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(BUTTON_SIZE);
    }

    public abstract void action();
}
