package ru.spaceshooter.sprite.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;

public class ButtonExit extends ScaledButton {
    private static final String BUTTON_TEXT = "Выход";

    public ButtonExit(TextureAtlas atlas) {
        super(atlas, BUTTON_TEXT);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
