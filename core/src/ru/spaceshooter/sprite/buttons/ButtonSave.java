package ru.spaceshooter.sprite.buttons;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.ScaledButton;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ButtonSave extends ScaledButton {

    private static final String BUTTON_TEXT = "Сохранить";

    private final GameScreen gameScreen;

    public ButtonSave(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas, BUTTON_TEXT);
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + getHeight() + MARGIN + 0.01f);
    }

    @Override
    public void action() {
        gameScreen.saveGame();
    }
}
