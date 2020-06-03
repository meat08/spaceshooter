package ru.spaceshooter.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import ru.spaceshooter.math.Rect;
import ru.spaceshooter.utils.Regions;

public abstract class ScaledButton extends Sprite {

    private static final float SCALE = 0.9f;
    protected static final float BUTTON_SIZE = 0.075f;
    protected static final float MARGIN = 0.05f;
    private static final float TEXT_SIZE = 0.035f;

    private boolean pressed;
    private int pointer;
    private Font fontButton;
    private String buttonText;

    public ScaledButton(TextureRegion region) {
        super(region);
    }

    public ScaledButton(TextureAtlas atlas, String buttonText) {
        super(atlas.findRegion("button"));
        this.buttonText = buttonText;
        this.fontButton = new Font("font/font.fnt", "font/font.png");
        fontButton.setSize(TEXT_SIZE);
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

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        if (fontButton != null) {
            fontButton.draw(batch, buttonText, pos.x, getTop() - 0.015f, Align.center);
        }
    }

    public void dispose() {
        fontButton.dispose();
    }

    public abstract void action();
}
